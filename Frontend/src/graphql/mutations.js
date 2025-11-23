export const ADD_USER = `mutation($input: UserInput!) { addUser(input: $input) { id firstName } }`;
export const CREATE_SUBSCRIPTION = `mutation($input: CreateSubscriptionInput!) { createSubscription(input: $input) { id status }} `;
export const CANCEL_SUB = `mutation($id: ID!) { cancelSubscription(subscriptionId: $id) }`;
