package com.ms.apigateway.service;

import com.ms.apigateway.util.GraphQLHelper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class CatalogClient {
    private final WebClient webClient;

    public CatalogClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://catalog-service:8083/graphql").build();
    }

    // Каталог
    public Object getCatalogs() {
        String query = """
            query GetCatalogs {
                getCatalogs {
                    id
                    title
                    description
                    price
                    link
                    category {
                        id
                        name
                    }
                    series {
                        id
                        name
                    }
                }
            }
            """;

        Map<String, Object> payload = new HashMap<>();
        payload.put("query", query);

        Map<String, Object> response = webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        return GraphQLHelper.extractSingle(response, "getCatalogs");
    }

    public Object getCatalogById(String id) {
        String query = """
            query GetCatalogById($id: ID!) {
                getCatalogById(id: $id) {
                    id
                    title
                    description
                    price
                    link
                    category {
                        id
                        name
                    }
                    series {
                        id
                        name
                    }
                }
            }
            """;

        Map<String, Object> payload = new HashMap<>();
        payload.put("query", query);
        payload.put("variables", Map.of("id", id));

        Map<String, Object> response = webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        return GraphQLHelper.extractSingle(response, "getCatalogById");
    }

    public Object addCatalog(Object input) {
        String mutation = """
            mutation AddCatalog($input: CatalogInput!) {
                addCatalog(input: $input) {
                    id
                    title
                    description
                    price
                    link
                    category {
                        id
                        name
                    }
                    series {
                        id
                        name
                    }
                }
            }
            """;

        Map<String, Object> payload = new HashMap<>();
        payload.put("query", mutation);
        payload.put("variables", Map.of("input", input));

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

    public Object updateCatalog(String id, Object input) {
        String mutation = """
            mutation UpdateCatalog($id: ID!, $input: CatalogInput!) {
                updateCatalog(id: $id, input: $input) {
                    id
                    title
                    description
                    price
                    link
                    category {
                        id
                        name
                    }
                    series {
                        id
                        name
                    }
                }
            }
            """;

        Map<String, Object> payload = new HashMap<>();
        payload.put("query", mutation);
        payload.put("variables", Map.of("id", id, "input", input));

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

    public Object deleteCatalog(String id) {
        String mutation = """
            mutation DeleteCatalog($id: ID!) {
                deleteCatalog(id: $id)
            }
            """;

        Map<String, Object> payload = new HashMap<>();
        payload.put("query", mutation);
        payload.put("variables", Map.of("id", id));

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

    // Категории
    public Object getCategories() {
        String query = """
            query GetCategories {
                getCategories {
                    id
                    name
                    catalogs {
                        id
                        title
                    }
                }
            }
            """;

        Map<String, Object> payload = new HashMap<>();
        payload.put("query", query);

        Map<String, Object> response = webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        return GraphQLHelper.extractSingle(response, "getCategories");
    }

    public Object getCategoryById(String id) {
        String query = """
            query GetCategoryById($id: ID!) {
                getCategoryById(id: $id) {
                    id
                    name
                    catalogs {
                        id
                        title
                        price
                    }
                }
            }
            """;

        Map<String, Object> payload = new HashMap<>();
        payload.put("query", query);
        payload.put("variables", Map.of("id", id));

        Map<String, Object> response = webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        return GraphQLHelper.extractSingle(response, "getCategoryById");
    }

    public Object addCategory(Object input) {
        String mutation = """
            mutation AddCategory($input: CategoryInput!) {
                addCategory(input: $input) {
                    id
                    name
                }
            }
            """;

        Map<String, Object> payload = new HashMap<>();
        payload.put("query", mutation);
        payload.put("variables", Map.of("input", input));

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

    public Object updateCategory(String id, Object input) {
        String mutation = """
            mutation UpdateCategory($id: ID!, $input: CategoryInput!) {
                updateCategory(id: $id, input: $input) {
                    id
                    name
                }
            }
            """;

        Map<String, Object> payload = new HashMap<>();
        payload.put("query", mutation);
        payload.put("variables", Map.of("id", id, "input", input));

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

    public Object deleteCategory(String id) {
        String mutation = """
            mutation DeleteCategory($id: ID!) {
                deleteCategory(id: $id)
            }
            """;

        Map<String, Object> payload = new HashMap<>();
        payload.put("query", mutation);
        payload.put("variables", Map.of("id", id));

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

    // Серии
    public Object getSeries() {
        String query = """
            query GetSeries {
                getSeries {
                    id
                    name
                    catalogs {
                        id
                        title
                    }
                }
            }
            """;

        Map<String, Object> payload = new HashMap<>();
        payload.put("query", query);

        Map<String, Object> response = webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        return GraphQLHelper.extractSingle(response, "getSeries");
    }

    public Object getSeriesById(String id) {
        String query = """
            query GetSeriesById($id: ID!) {
                getSeriesById(id: $id) {
                    id
                    name
                    catalogs {
                        id
                        title
                        price
                    }
                }
            }
            """;

        Map<String, Object> payload = new HashMap<>();
        payload.put("query", query);
        payload.put("variables", Map.of("id", id));

        Map<String, Object> response = webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        return GraphQLHelper.extractSingle(response, "getSeriesById");
    }

    public Object addSeries(Object input) {
        String mutation = """
            mutation AddSeries($input: SeriesInput!) {
                addSeries(input: $input) {
                    id
                    name
                }
            }
            """;

        Map<String, Object> payload = new HashMap<>();
        payload.put("query", mutation);
        payload.put("variables", Map.of("input", input));

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

    public Object updateSeries(String id, Object input) {
        String mutation = """
            mutation UpdateSeries($id: ID!, $input: SeriesInput!) {
                updateSeries(id: $id, input: $input) {
                    id
                    name
                }
            }
            """;

        Map<String, Object> payload = new HashMap<>();
        payload.put("query", mutation);
        payload.put("variables", Map.of("id", id, "input", input));

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

    public Object deleteSeries(String id) {
        String mutation = """
            mutation DeleteSeries($id: ID!) {
                deleteSeries(id: $id)
            }
            """;

        Map<String, Object> payload = new HashMap<>();
        payload.put("query", mutation);
        payload.put("variables", Map.of("id", id));

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }
}
