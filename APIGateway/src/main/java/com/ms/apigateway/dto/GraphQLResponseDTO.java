package com.ms.apigateway.dto;

import java.util.List;
import java.util.Map;

public class GraphQLResponseDTO {
    private Object data;
    private List<GraphQLError> errors;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<GraphQLError> getErrors() {
        return errors;
    }

    public void setErrors(List<GraphQLError> errors) {
        this.errors = errors;
    }

    public boolean hasErrors() {
        return errors != null && !errors.isEmpty();
    }

    public static class GraphQLError {
        private String message;
        private List<Object> path;
        private Map<String, Object> extensions;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<Object> getPath() {
            return path;
        }

        public void setPath(List<Object> path) {
            this.path = path;
        }

        public Map<String, Object> getExtensions() {
            return extensions;
        }

        public void setExtensions(Map<String, Object> extensions) {
            this.extensions = extensions;
        }
    }
}
