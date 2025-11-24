// src/graphql/operations.js
export const ADD_USER = `mutation($input: UserInput!) { addUser(input: $input) { id firstName secondName age } }`;
export const CREATE_SUBSCRIPTION = `mutation($input: CreateSubscriptionInput!) { createSubscription(input: $input) { id userId magazineId status durationMonths startDate endDate } }`;
export const CANCEL_SUB = `mutation($subscriptionId: ID!) { cancelSubscription(subscriptionId: $subscriptionId) }`;

// Полезные query константы (частично для обратной совместимости)
export const GET_CATALOGS = `query { getCatalogs { id title price description link category { id name } series { id name } } }`;
export const GET_CATEGORIES = `query { getCategories { id name } }`;
export const GET_SERIES = `query { getSeries { id name } }`;
export const GET_USER = `query($id: ID!) { getUser(id: $id) { id firstName secondName thirdName age registrationDate } }`;
export const GET_USER_SUBSCRIPTIONS = `query($id: ID!) { getUserSubscriptions(id: $id) { id userId magazineId status durationMonths startDate endDate } }`;

// Универсальный список операций, используемый AdminPanel
// Каждая операция описана: name, type, query, variables — variables может быть пустым или содержать:
// - простые переменные: { name, type, required }
// - input-объект: { name: "input", type: "CatalogInput", inputFields: [ {name,type,required}, ... ] }
export const OPERATIONS = [
    // Queries
    {
        type: "query",
        name: "getCatalogs",
        title: "Получить все каталоги",
        query: GET_CATALOGS,
        variables: []
    },
    {
        type: "query",
        name: "getCatalogById",
        title: "Получить каталог по id",
        query: `query($id: ID!) { getCatalogById(id: $id) { id title description price link category { id name } series { id name } } }`,
        variables: [{ name: "id", type: "ID", required: true }]
    },
    {
        type: "query",
        name: "getCategories",
        title: "Получить все категории",
        query: GET_CATEGORIES,
        variables: []
    },
    {
        type: "query",
        name: "getCategoryById",
        title: "Получить категорию по id",
        query: `query($id: ID!) { getCategoryById(id: $id) { id name catalogs { id title price } } }`,
        variables: [{ name: "id", type: "ID", required: true }]
    },
    {
        type: "query",
        name: "getSeries",
        title: "Получить все серии",
        query: GET_SERIES,
        variables: []
    },
    {
        type: "query",
        name: "getSeriesById",
        title: "Получить серию по id",
        query: `query($id: ID!) { getSeriesById(id: $id) { id name catalogs { id title price } } }`,
        variables: [{ name: "id", type: "ID", required: true }]
    },
    {
        type: "query",
        name: "getUser",
        title: "Получить пользователя по id",
        query: GET_USER,
        variables: [{ name: "id", type: "ID", required: true }]
    },
    {
        type: "query",
        name: "getUserSubscriptions",
        title: "Получить подписки пользователя",
        query: GET_USER_SUBSCRIPTIONS,
        variables: [{ name: "id", type: "ID", required: true }]
    },

    // Mutations - Catalog
    {
        type: "mutation",
        name: "addCatalog",
        title: "Добавить каталог",
        query: `mutation($input: CatalogInput!) { addCatalog(input: $input) { id title description price link category { id name } series { id name } } }`,
        variables: [
            {
                name: "input",
                type: "CatalogInput",
                inputFields: [
                    { name: "title", type: "String", required: true },
                    { name: "description", type: "String", required: false },
                    { name: "price", type: "Float", required: true },
                    { name: "link", type: "String", required: false },
                    { name: "categoryId", type: "ID", required: false },
                    { name: "seriesId", type: "ID", required: false }
                ]
            }
        ]
    },
    {
        type: "mutation",
        name: "updateCatalog",
        title: "Обновить каталог",
        query: `mutation($id: ID!, $input: CatalogInput!) { updateCatalog(id: $id, input: $input) { id title description price link } }`,
        variables: [
            { name: "id", type: "ID", required: true },
            {
                name: "input",
                type: "CatalogInput",
                inputFields: [
                    { name: "title", type: "String", required: true },
                    { name: "description", type: "String", required: false },
                    { name: "price", type: "Float", required: true },
                    { name: "link", type: "String", required: false },
                    { name: "categoryId", type: "ID", required: false },
                    { name: "seriesId", type: "ID", required: false }
                ]
            }
        ]
    },
    {
        type: "mutation",
        name: "deleteCatalog",
        title: "Удалить каталог",
        query: `mutation($id: ID!) { deleteCatalog(id: $id) }`,
        variables: [{ name: "id", type: "ID", required: true }]
    },

    // Category mutations
    {
        type: "mutation",
        name: "addCategory",
        title: "Добавить категорию",
        query: `mutation($input: CategoryInput!) { addCategory(input: $input) { id name } }`,
        variables: [
            { name: "input", type: "CategoryInput", inputFields: [{ name: "name", type: "String", required: true }] }
        ]
    },
    {
        type: "mutation",
        name: "updateCategory",
        title: "Обновить категорию",
        query: `mutation($id: ID!, $input: CategoryInput!) { updateCategory(id: $id, input: $input) { id name } }`,
        variables: [
            { name: "id", type: "ID", required: true },
            { name: "input", type: "CategoryInput", inputFields: [{ name: "name", type: "String", required: true }] }
        ]
    },
    {
        type: "mutation",
        name: "deleteCategory",
        title: "Удалить категорию",
        query: `mutation($id: ID!) { deleteCategory(id: $id) }`,
        variables: [{ name: "id", type: "ID", required: true }]
    },

    // Series mutations
    {
        type: "mutation",
        name: "addSeries",
        title: "Добавить серию",
        query: `mutation($input: SeriesInput!) { addSeries(input: $input) { id name } }`,
        variables: [{ name: "input", type: "SeriesInput", inputFields: [{ name: "name", type: "String", required: true }] }]
    },
    {
        type: "mutation",
        name: "updateSeries",
        title: "Обновить серию",
        query: `mutation($id: ID!, $input: SeriesInput!) { updateSeries(id: $id, input: $input) { id name } }`,
        variables: [
            { name: "id", type: "ID", required: true },
            { name: "input", type: "SeriesInput", inputFields: [{ name: "name", type: "String", required: true }] }
        ]
    },
    {
        type: "mutation",
        name: "deleteSeries",
        title: "Удалить серию",
        query: `mutation($id: ID!) { deleteSeries(id: $id) }`,
        variables: [{ name: "id", type: "ID", required: true }]
    },

    // Users mutations
    {
        type: "mutation",
        name: "addUser",
        title: "Добавить пользователя",
        query: ADD_USER,
        variables: [
            {
                name: "input",
                type: "UserInput",
                inputFields: [
                    { name: "firstName", type: "String", required: true },
                    { name: "secondName", type: "String", required: true },
                    { name: "thirdName", type: "String", required: false },
                    { name: "age", type: "Int", required: true }
                ]
            }
        ]
    },
    {
        type: "mutation",
        name: "updateUser",
        title: "Обновить пользователя",
        query: `mutation($id: ID!, $input: UserInput!) { updateUser(id: $id, input: $input) { id firstName secondName age } }`,
        variables: [
            { name: "id", type: "ID", required: true },
            {
                name: "input",
                type: "UserInput",
                inputFields: [
                    { name: "firstName", type: "String", required: true },
                    { name: "secondName", type: "String", required: true },
                    { name: "thirdName", type: "String", required: false },
                    { name: "age", type: "Int", required: true }
                ]
            }
        ]
    },
    {
        type: "mutation",
        name: "removeUser",
        title: "Удалить пользователя",
        query: `mutation($id: ID!) { removeUser(id: $id) }`,
        variables: [{ name: "id", type: "ID", required: true }]
    },

    // Subscriptions mutations
    {
        type: "mutation",
        name: "createSubscription",
        title: "Создать подписку",
        query: CREATE_SUBSCRIPTION,
        variables: [
            {
                name: "input",
                type: "CreateSubscriptionInput",
                inputFields: [
                    { name: "userId", type: "ID", required: true },
                    { name: "magazineId", type: "ID", required: true },
                    { name: "durationMonths", type: "Int", required: true }
                ]
            }
        ]
    },
    {
        type: "mutation",
        name: "cancelSubscription",
        title: "Отменить подписку",
        query: CANCEL_SUB,
        variables: [{ name: "subscriptionId", type: "ID", required: true }]
    },
    {
        type: "mutation",
        name: "updateSubscription",
        title: "Обновить подписку",
        query: `mutation($id: ID!, $input: UpdateSubscriptionInput!) { updateSubscription(id: $id, input: $input) { id status durationMonths } }`,
        variables: [
            { name: "id", type: "ID", required: true },
            {
                name: "input",
                type: "UpdateSubscriptionInput",
                inputFields: [
                    { name: "durationMonths", type: "Int", required: false },
                    { name: "status", type: "SubscriptionStatus", required: false }
                ]
            }
        ]
    }
];
