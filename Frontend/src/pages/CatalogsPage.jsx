import React, { useEffect, useState } from "react";
import { graphqlRequest } from "../graphql/client";
import './CatalogsPage.css';
import { CatalogCard } from "../components/CatalogCard";

export function CatalogsPage() {
    const hash = window.location.hash;
    const [items, setItems] = useState([]);
    const [item, setItem] = useState(null);

    useEffect(() => {
        if (hash.startsWith("#/catalog/")) {
            const id = hash.replace("#/catalog/", "");
            graphqlRequest(`
              query($id: ID!) {
                getCatalogById(id: $id) {
                  id
                  title
                  description
                  price
                  link
                  category { id name }
                  series { id name }
                }
              }
            `, { id }).then(r => setItem(r.getCatalogById));
        } else {
            graphqlRequest(`
              query {
                getCatalogs {
                  id
                  title
                  description
                  price
                  link
                  category { id name }
                  series { id name }
                }
              }
            `).then((data) => setItems(data.getCatalogs));
        }
    }, [hash]);

    if (item) {
        return (
            <div className="catalog-view">
                <h1 className="catalog-title">{item.title}</h1>
                <div className="catalog-description">{item.description}</div>

                <div className="catalog-meta">Цена: {item.price} ₽</div>

                {item.category && (
                    <div className="catalog-tag">
                        Категория:
                        <a href={`#/categories?id=${item.category.id}`}> {item.category.name}</a>
                    </div>
                )}

                {item.series && (
                    <div className="catalog-tag">
                        Серия:
                        <a href={`#/series?id=${item.series.id}`}> {item.series.name}</a>
                    </div>
                )}

                {item.link && (
                    <a href={item.link} target="_blank" className="catalog-link">
                        Открыть материал
                    </a>
                )}

                <a href="#/" className="back-link">Назад</a>
            </div>
        );
    }

    return (
        <div className="catalogs-container">
            <h1 className="catalogs-title">Каталоги</h1>
            <div className="catalogs-grid">
                {items.map((c) => (
                    <a key={c.id} href={`#/catalog/${c.id}`}>
                        <CatalogCard catalog={c} />
                    </a>
                ))}
            </div>
        </div>
    );
}
