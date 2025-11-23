import React, { useEffect, useState } from "react";
import { graphqlRequest } from "../graphql/client";
import './CatalogsPage.css';
import { CatalogCard } from "../components/CatalogCard";

export function CatalogsPage() {
    const [items, setItems] = useState([]);

    useEffect(() => {
        graphqlRequest(`
      query {
        getCatalogs {
          id
          title
          description
          price
          link
          category { name }
          series { name }
        }
      }
    `)
            .then((data) => setItems(data.getCatalogs))
            .catch((err) => console.error(err));
    }, []);

    return (
        <div className="catalogs-container">
            <h1 className="catalogs-title">Каталоги</h1>
            <div className="catalogs-grid">
                {items.map((c) => (
                    <CatalogCard key={c.id} catalog={c} />
                ))}
            </div>
        </div>
    );
}
