package com.example.easyserver.auth.application.adapter.output.spring.jdbc;

import com.example.easyserver.auth.application.dto.RegisterUserDto;
import com.example.easyserver.auth.application.port.output.RegisterUserOutputPort;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.Map;

public final class SpringJdbcRegisterUserOutputPortAdapter extends SimpleJdbcCall
        implements RegisterUserOutputPort {

    public static final String SCHEMA_NAME = "system";
    public static final String FUNCTION_NAME = "register_user";

    public static final String PARAMETER_LOGIN = "_login";
    public static final String PARAMETER_PASSWORD = "_password";

    public SpringJdbcRegisterUserOutputPortAdapter(final DataSource dataSource) {
        super(dataSource);

        this.withSchemaName(SCHEMA_NAME)
                .withFunctionName(FUNCTION_NAME)
                .declareParameters(
                        new SqlParameter(PARAMETER_LOGIN, Types.VARCHAR),
                        new SqlParameter(PARAMETER_PASSWORD, Types.VARCHAR))
                .compile();
    }

    @Override
    public void registerUser(RegisterUserDto dto) {
        this.execute(Map.of(
                PARAMETER_LOGIN, dto.name(),
                PARAMETER_PASSWORD, dto.password()));
    }
}
