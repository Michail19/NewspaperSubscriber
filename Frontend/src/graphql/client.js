export async function graphqlRequest(query, variables = {}) {
    const response = await fetch("/graphql", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ query, variables })
    });
    const json = await response.json();
    if (json.errors) throw new Error(json.errors.map(e => e.message).join("; "));
    return json.data;
}
