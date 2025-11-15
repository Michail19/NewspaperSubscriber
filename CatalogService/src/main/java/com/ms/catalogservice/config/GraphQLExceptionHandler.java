package com.ms.catalogservice.config;

import com.ms.catalogservice.exception.CatalogNotFoundException;
import com.ms.catalogservice.exception.CategoryNotFoundException;
import com.ms.catalogservice.exception.SeriesNotFoundException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GraphQLExceptionHandler {

    @GraphQlExceptionHandler
    public GraphQLError handleUserNotFoundException(CatalogNotFoundException ex) {
        Map<String, Object> extensions = new HashMap<>();
        extensions.put("statusCode", 404);
        extensions.put("code", "USER_NOT_FOUND");

        return GraphqlErrorBuilder.newError()
                .message(ex.getMessage())
                .errorType(ErrorType.NOT_FOUND)
                .extensions(extensions)
                .build();
    }

    @GraphQlExceptionHandler
    public GraphQLError handleUserNotFoundException(CategoryNotFoundException ex) {
        Map<String, Object> extensions = new HashMap<>();
        extensions.put("statusCode", 404);
        extensions.put("code", "USER_NOT_FOUND");

        return GraphqlErrorBuilder.newError()
                .message(ex.getMessage())
                .errorType(ErrorType.NOT_FOUND)
                .extensions(extensions)
                .build();
    }

    @GraphQlExceptionHandler
    public GraphQLError handleUserNotFoundException(SeriesNotFoundException ex) {
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
