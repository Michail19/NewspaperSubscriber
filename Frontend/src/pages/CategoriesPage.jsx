import { useEffect, useState } from "react";
import { graphqlRequest } from "../graphql/client";
import './CommonPages.css';

export function CategoriesPage() {
    const hash = window.location.hash;
    const url = new URLSearchParams(hash.split("?")[1] || "");

    const [items, setItems] = useState([]);
    const [category, setCategory] = useState(null);

    const id = url.get("id");

    useEffect(() => {
        if (id) {
            graphqlRequest(`
                query($id: ID!) {
                    getCategoryById(id: $id) {
                        id
                        name
                        catalogs {
                            id
                            title
                            price
                        }
                    }
                }
            `, { id }).then(r => setCategory(r.getCategoryById));
        } else {
            graphqlRequest(`
                query {
                    getCategories {
                        id
                        name
                    }
                }
            `).then(r => setItems(r.getCategories));
        }
    }, [id]);

    if (category) {
        return (
            <div className="page-container">
                <h1 className="page-title">Категория: {category.name}</h1>

                <h2 className="section-title">Каталоги в категории:</h2>
                <div className="items-list">
                    {category.catalogs.map(cat => (
                        <a key={cat.id} className="list-item" href={`#/catalog/${cat.id}`}>
                            {cat.title} — {cat.price}₽
                        </a>
                    ))}
                </div>

                <a href="#/categories" className="back-link">Назад</a>
            </div>
        );
    }

    return (
        <div className="page-container">
            <h1 className="page-title">Категории</h1>
            <div className="items-list">
                {items.map(c => (
                    <a key={c.id} href={`#/categories?id=${c.id}`} className="list-item">
                        {c.name}
                    </a>
                ))}
            </div>
        </div>
    );
}
