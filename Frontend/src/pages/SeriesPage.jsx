import { useEffect, useState } from "react";
import { GET_SERIES } from "../graphql/queries";
import { graphqlRequest } from "../graphql/client";
import './CommonPages.css';

export function SeriesPage() {
    const [data, setData] = useState([]);

    useEffect(() => {
        graphqlRequest(GET_SERIES).then(r => setData(r.getSeries));
    }, []);

    return (
        <div className="page-container">
            <h1 className="page-title">Серии</h1>
            <div className="items-list">
                {data.map(s => <div key={s.id} className="list-item">{s.name}</div>)}
            </div>
        </div>
    );
}
