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
public class GraphQLExceptionHandler {

    @GraphQlExceptionHandler
    public GraphQLError handleCatalogNotFound(CatalogNotFoundException ex, DataFetchingEnvironment env) {
        return buildNotFoundError(ex, env, "CATALOG_NOT_FOUND");
    }

    @GraphQlExceptionHandler
    public GraphQLError handleCategoryNotFound(CategoryNotFoundException ex, DataFetchingEnvironment env) {
        return buildNotFoundError(ex, env, "CATEGORY_NOT_FOUND");
    }

    @GraphQlExceptionHandler
    public GraphQLError handleSeriesNotFound(SeriesNotFoundException ex, DataFetchingEnvironment env) {
        return buildNotFoundError(ex, env, "SERIES_NOT_FOUND");
    }

    private GraphQLError buildNotFoundError(RuntimeException ex,
                                            DataFetchingEnvironment env,
                                            String code) {
        Map<String, Object> extensions = new HashMap<>();
        extensions.put("statusCode", 404);
        extensions.put("code", code);

        return GraphqlErrorBuilder.newError(env)
                .message(ex.getMessage())
                .errorType(ErrorType.NOT_FOUND)
                .extensions(extensions)
                .build();
    }
}
