import { useState } from "react";
import { graphqlRequest } from "../graphql/client";
import { GET_USER } from "../graphql/queries";
import { ADD_USER } from "../graphql/mutations";


export function UserPage() {
    const [input, setInput] = useState({ firstName: "", secondName: "", age: 18 });
    const [searchId, setSearchId] = useState("");
    const [user, setUser] = useState(null);


    async function register() {
        const r = await graphqlRequest(ADD_USER, { input });
        alert("Пользователь зарегистрирован: " + r.addUser.firstName);
    }


    async function loadUser() {
        const r = await graphqlRequest(GET_USER, { id: searchId });
        setUser(r.getUser);
    }


    return (
        <div className="p-6 max-w-lg mx-auto">
            <h1 className="text-3xl font-bold mb-4">Пользователь</h1>


            <h2 className="text-xl font-semibold mb-2">Регистрация</h2>
            <div className="flex flex-col gap-2 mb-6">
                <input className="p-2 border rounded" placeholder="Имя" onChange={e => setInput({ ...input, firstName: e.target.value })} />
                <input className="p-2 border rounded" placeholder="Фамилия" onChange={e => setInput({ ...input, secondName: e.target.value })} />
                <input className="p-2 border rounded" type="number" placeholder="Возраст" onChange={e => setInput({ ...input, age: Number(e.target.value) })} />
                <button className="p-2 bg-blue-600 text-white rounded" onClick={register}>Создать</button>
            </div>


            <h2 className="text-xl font-semibold mb-2">Поиск пользователя</h2>
            <input className="p-2 border rounded mb-2 w-full" placeholder="ID пользователя" onChange={e => setSearchId(e.target.value)} />
            <button className="p-2 bg-green-600 text-white rounded" onClick={loadUser}>Загрузить</button>


            {user && (
                <div className="mt-4 p-4 border rounded bg-gray-50">
                    <p>ID: {user.id}</p>
                    <p>Имя: {user.firstName} {user.secondName}</p>
                    <p>Возраст: {user.age}</p>
                </div>
            )}
        </div>
    );
}
