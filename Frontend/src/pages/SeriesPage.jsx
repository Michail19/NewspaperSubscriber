import { useEffect, useState } from "react";
import { graphqlRequest } from "../graphql/client";
import './CommonPages.css';

export function SeriesPage() {
    const hash = window.location.hash;
    const url = new URLSearchParams(hash.split("?")[1] || "");
    const id = url.get("id");

    const [items, setItems] = useState([]);
    const [series, setSeries] = useState(null);

    useEffect(() => {
        if (id) {
            graphqlRequest(`
                query($id: ID!) {
                    getSeriesById(id: $id) {
                        id
                        name
                        catalogs {
                            id
                            title
                            price
                        }
                    }
                }
            `, { id }).then(r => setSeries(r.getSeriesById));
        } else {
            graphqlRequest(`
                query {
                    getSeries {
                        id
                        name
                    }
                }
            `).then(r => setItems(r.getSeries));
        }
    }, [id]);

    if (series) {
        return (
            <div className="page-container">
                <h1 className="page-title">Серия: {series.name}</h1>

                <h2 className="section-title">Каталоги в серии:</h2>
                <div className="items-list">
                    {series.catalogs.map(cat => (
                        <a key={cat.id} className="list-item" href={`#/catalog/${cat.id}`}>
                            {cat.title} — {cat.price}₽
                        </a>
                    ))}
                </div>

                <a href="#/series" className="back-link">Назад</a>
            </div>
        );
    }

    return (
        <div className="page-container">
            <h1 className="page-title">Серии</h1>
            <div className="items-list">
                {items.map(s => (
                    <a key={s.id} href={`#/series?id=${s.id}`} className="list-item">
                        {s.name}
                    </a>
                ))}
            </div>
        </div>
    );
}
