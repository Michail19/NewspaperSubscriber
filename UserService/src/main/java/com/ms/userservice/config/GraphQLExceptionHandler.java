package com.ms.userservice.config;

import com.ms.userservice.exception.UserNotFoundException;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;

import java.util.Map;
import java.util.HashMap;

@ControllerAdvice
public class GraphQLExceptionHandler {

    @GraphQlExceptionHandler
    public GraphQLError handleUserNotFoundException(UserNotFoundException ex) {
        Map<String, Object> extensions = new HashMap<>();
        extensions.put("statusCode", 404);
        extensions.put("code", "USER_NOT_FOUND");

        return GraphqlErrorBuilder.newError()
                .message(ex.getMessage())
                .errorType(ErrorType.NOT_FOUND)
                .extensions(extensions)
                .build();
    }
}
