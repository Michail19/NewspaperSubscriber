import { useEffect, useState } from "react";
import { GET_SERIES } from "../graphql/queries";
import { graphqlRequest } from "../graphql/client";


export function SeriesPage() {
    const [data, setData] = useState([]);


    useEffect(() => {
        graphqlRequest(GET_SERIES).then(r => setData(r.getSeries));
    }, []);


    return (
        <div className="p-6">
            <h1 className="text-3xl font-bold mb-4">Серии</h1>
            <ul className="list-disc ml-6 text-lg">
                {data.map(s => <li key={s.id}>{s.name}</li>)}
            </ul>
        </div>
    );
}
