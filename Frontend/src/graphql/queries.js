export const GET_CATALOGS = `query { getCatalogs { id title price description link } }`;
export const GET_CATEGORIES = `query { getCategories { id name } }`;
export const GET_SERIES = `query { getSeries { id name } }`;
export const GET_USER = `query($id: ID!) { getUser(id: $id) { id firstName secondName age } }`;
export const GET_USER_SUBSCRIPTIONS = `query($id: ID!) { getUserSubscriptions(id: $id) { id magazineId status durationMonths }} `;
