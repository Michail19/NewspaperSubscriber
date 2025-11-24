export async function graphqlRequest(query, variables = {}) {
    try {
        console.log("Sending GraphQL request to apigateway:8080...");

        const response = await fetch("http://localhost:8080/graphql", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ query, variables })
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const json = await response.json();

        if (json.errors) {
            console.error("GraphQL errors:", json.errors);
            throw new Error(json.errors.map(e => e.message).join("; "));
        }

        console.log("GraphQL request successful");
        return json.data;
    } catch (error) {
        console.error("GraphQL request failed:", error);
        throw new Error(`Network error: ${error.message}. Please check if the server is running.`);
    }
}
