package com.ms.apigateway.service;

import com.ms.apigateway.dto.GraphQLResponseDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class MagazineClient {
    private final WebClient webClient;

    public MagazineClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://catalog-service:8083/graphql").build();
    }

    // Каталог
    public Mono<Object> getCatalogs() {
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

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(GraphQLResponseDTO.class)
                .map(resp -> extractData(resp, "getCatalogs"));
    }

    public Mono<Object> getCatalogById(String id) {
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

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(GraphQLResponseDTO.class)
                .map(resp -> extractData(resp, "getCatalogById"));
    }

    public Mono<Object> addCatalog(Object input) {
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
                .bodyToMono(GraphQLResponseDTO.class)
                .map(resp -> extractData(resp, "addCatalog"));
    }

    public Mono<Object> updateCatalog(String id, Object input) {
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
                .bodyToMono(GraphQLResponseDTO.class)
                .map(resp -> extractData(resp, "updateCatalog"));
    }

    public Mono<Object> deleteCatalog(String id) {
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
                .bodyToMono(GraphQLResponseDTO.class)
                .map(resp -> extractData(resp, "deleteCatalog"));
    }

    // Категории
    public Mono<Object> getCategories() {
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

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(GraphQLResponseDTO.class)
                .map(resp -> extractData(resp, "getCategories"));
    }

    public Mono<Object> getCategoryById(String id) {
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

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(GraphQLResponseDTO.class)
                .map(resp -> extractData(resp, "getCategoryById"));
    }

    public Mono<Object> addCategory(Object input) {
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
                .bodyToMono(GraphQLResponseDTO.class)
                .map(resp -> extractData(resp, "addCategory"));
    }

    public Mono<Object> updateCategory(String id, Object input) {
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
                .bodyToMono(GraphQLResponseDTO.class)
                .map(resp -> extractData(resp, "updateCategory"));
    }

    public Mono<Object> deleteCategory(String id) {
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
                .bodyToMono(GraphQLResponseDTO.class)
                .map(resp -> extractData(resp, "deleteCategory"));
    }

    // Серии
    public Mono<Object> getSeries() {
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

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(GraphQLResponseDTO.class)
                .map(resp -> extractData(resp, "getSeries"));
    }

    public Mono<Object> getSeriesById(String id) {
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

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(GraphQLResponseDTO.class)
                .map(resp -> extractData(resp, "getSeriesById"));
    }

    public Mono<Object> addSeries(Object input) {
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
                .bodyToMono(GraphQLResponseDTO.class)
                .map(resp -> extractData(resp, "addSeries"));
    }

    public Mono<Object> updateSeries(String id, Object input) {
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
                .bodyToMono(GraphQLResponseDTO.class)
                .map(resp -> extractData(resp, "updateSeries"));
    }

    public Mono<Object> deleteSeries(String id) {
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
                .bodyToMono(GraphQLResponseDTO.class)
                .map(resp -> extractData(resp, "deleteSeries"));
    }

    private Object extractData(GraphQLResponseDTO response, String fieldName) {
        if (response == null) {
            return null;
        }

        if (response.hasErrors()) {
            return null;
        }

        if (response.getData() instanceof Map<?, ?> data) {
            return data.getOrDefault(fieldName, null);
        }

        return null;
    }
}
