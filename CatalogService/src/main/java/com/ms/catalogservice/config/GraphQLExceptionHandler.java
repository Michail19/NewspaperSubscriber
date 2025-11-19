package com.ms.catalogservice.config;

import com.ms.catalogservice.exception.CatalogNotFoundException;
import com.ms.catalogservice.exception.CategoryNotFoundException;
import com.ms.catalogservice.exception.SeriesNotFoundException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {

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

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof CatalogNotFoundException ||
                ex instanceof CategoryNotFoundException ||
                ex instanceof SeriesNotFoundException) {

            return GraphqlErrorBuilder.newError()
                    .message(ex.getMessage())
                    .path(env.getExecutionStepInfo().getPath())
                    .build();
        }
        return null;
    }
}
