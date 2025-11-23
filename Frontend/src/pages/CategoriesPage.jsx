import { useEffect, useState } from "react";
import { GET_CATEGORIES } from "../graphql/queries";
import { graphqlRequest } from "../graphql/client";
import './CommonPages.css';

export function CategoriesPage() {
    const [data, setData] = useState([]);

    useEffect(() => {
        graphqlRequest(GET_CATEGORIES).then(r => setData(r.getCategories));
    }, []);

    return (
        <div className="page-container">
            <h1 className="page-title">Категории</h1>
            <div className="items-list">
                {data.map(c => <div key={c.id} className="list-item">{c.name}</div>)}
            </div>
        </div>
    );
}
