import { useEffect, useState } from "react";
import { GET_CATEGORIES } from "../graphql/queries";
import { graphqlRequest } from "../graphql/client";


export function CategoriesPage() {
    const [data, setData] = useState([]);


    useEffect(() => {
        graphqlRequest(GET_CATEGORIES).then(r => setData(r.getCategories));
    }, []);


    return (
        <div className="p-6">
            <h1 className="text-3xl font-bold mb-4">Категории</h1>
            <ul className="list-disc ml-6 text-lg">
                {data.map(c => <li key={c.id}>{c.name}</li>)}
            </ul>
        </div>
    );
}
