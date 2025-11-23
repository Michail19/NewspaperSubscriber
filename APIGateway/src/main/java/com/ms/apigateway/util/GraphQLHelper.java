package com.ms.apigateway.util;

import java.util.Map;
import java.util.Objects;

public class GraphQLHelper {

    @SuppressWarnings("unchecked")
    public static Object extractSingle(Map<String, Object> response, String field) {
        if (response == null) return null;

        Object dataObj = response.get("data");
        if (!(dataObj instanceof Map<?, ?> data)) return null;

        Object value = data.get(field);

        // Если сервис вернул напрямую null
        if (value == null) return null;

        // Если это пустой объект {}
        if (value instanceof Map<?, ?> map) {
            if (map.isEmpty()) return null;

            boolean allNull = map.values().stream().allMatch(Objects::isNull);
            if (allNull) return null;
        }

        return value;
    }
}
