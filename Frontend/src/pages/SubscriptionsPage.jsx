import { useState } from "react";
import { graphqlRequest } from "../graphql/client";
import { GET_USER_SUBSCRIPTIONS } from "../graphql/queries";
import { CREATE_SUBSCRIPTION, CANCEL_SUB } from "../graphql/mutations";


export function SubscriptionsPage() {
    const [userId, setUserId] = useState("");
    const [subs, setSubs] = useState([]);


    const [form, setForm] = useState({ userId: "", magazineId: "", durationMonths: 1 });


    async function loadSubs() {
        const r = await graphqlRequest(GET_USER_SUBSCRIPTIONS, { id: userId });
        setSubs(r.getUserSubscriptions);
    }


    async function create() {
        await graphqlRequest(CREATE_SUBSCRIPTION, { input: form });
        alert("Готово");
    }


    async function cancel(id) {
        await graphqlRequest(CANCEL_SUB, { id });
        loadSubs();
    }


    return (
        <div className="p-6 max-w-2xl mx-auto">
            <h1 className="text-3xl font-bold mb-4">Подписки</h1>


            <div className="mb-6">
                <h2 className="text-xl font-semibold mb-2">Загрузить подписки пользователя</h2>
                <input className="p-2 border rounded mr-2" placeholder="User ID" onChange={e => setUserId(e.target.value)} />
                <button className="p-2 bg-blue-600 text-white rounded" onClick={loadSubs}>Загрузить</button>
            </div>


            <ul className="mb-6">
                {subs.map(s => (
                    <li key={s.id} className="p-2 border rounded mb-2">
                        <p>Magazine ID: {s.magazineId}</p>
                        <p>Статус: {s.status}</p>
                        <p>Месяцев: {s.durationMonths}</p>
                        <button className="p-1 bg-red-500 text-white rounded mt-2" onClick={() => cancel(s.id)}>Отменить</button>
                    </li>
                ))}
            </ul>


            <h2 className="text-xl font-semibold mb-2">Создать подписку</h2>
            <div className="flex flex-col gap-2">
                <input className="p-2 border rounded" placeholder="User ID" onChange={e => setForm({ ...form, userId: e.target.value })} />
                <input className="p-2 border rounded" placeholder="Magazine ID" onChange={e => setForm({ ...form, magazineId: e.target.value })} />
                <input className="p-2 border rounded" type="number" placeholder="Месяцев" onChange={e => setForm({ ...form, durationMonths: Number(e.target.value) })} />
                <button className="p-2 bg-green-600 text-white rounded" onClick={create}>Создать</button>
            </div>
        </div>
    );
}
