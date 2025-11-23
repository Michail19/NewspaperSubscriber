import { useEffect, useState } from "react";
import { GET_CATALOGS } from "../graphql/queries";
import { graphqlRequest } from "../graphql/client";


export function CatalogsPage() {
    const [items, setItems] = useState([]);
    const [search, setSearch] = useState("");


    useEffect(() => {
        graphqlRequest(GET_CATALOGS).then(data => setItems(data.getCatalogs));
    }, []);


    const filtered = items.filter(c => c.title.toLowerCase().includes(search.toLowerCase()));


    return (
        <div className="p-6">
            <h1 className="text-3xl font-bold mb-4">Каталоги</h1>
            <input
                placeholder="Поиск..."
                className="p-2 border rounded mb-4 w-full"
                value={search}
                onChange={e => setSearch(e.target.value)}
            />
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                {filtered.map(c => (
                    <div key={c.id} className="p-4 rounded-xl border shadow bg-white">
                        <h2 className="text-xl font-semibold">{c.title}</h2>
                        <p className="text-gray-700">{c.description}</p>
                        <p className="font-bold mt-2">Цена: {c.price}₽</p>
                        {c.link && <a href={c.link} className="text-blue-500 underline">Ссылка</a>}
                    </div>
                ))}
            </div>
        </div>
    );
}
