// src/components/OperationCard.jsx
import React, { useState } from "react";
import { graphqlRequest } from "../graphql/client";

/*
Props:
  - op: { type, name, title, query, variables }
*/
export function OperationCard({ op }) {
    // state for simple variables and nested input fields
    const [values, setValues] = useState({});
    const [result, setResult] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    function setValue(key, value) {
        setValues(prev => ({ ...prev, [key]: value }));
    }

    function renderInputField(field, prefix = "") {
        const key = prefix ? `${prefix}.${field.name}` : field.name;
        const label = field.name + (field.required ? " *" : "");
        const value = values[key] ?? "";

        // simple rendering for types: String, Int, Float, ID, Boolean, Enum
        const inputType = field.type === "Int" || field.type === "Float" ? "number" : (field.type === "Boolean" ? "checkbox" : "text");

        return (
            <div key={key} className="op-field">
                <label className="op-label">{label}</label>
                {field.type === "Boolean" ? (
                    <input
                        type="checkbox"
                        checked={!!value}
                        onChange={e => setValue(key, e.target.checked)}
                    />
                ) : (
                    <input
                        type={inputType}
                        className="op-input"
                        value={value}
                        onChange={e => setValue(key, e.target.value)}
                        placeholder={`${field.type}`}
                    />
                )}
            </div>
        );
    }

    function buildVariablesFromSpec() {
        // iterate op.variables
        const vars = {};
        for (const v of op.variables || []) {
            if (v.inputFields) {
                // build object
                const obj = {};
                for (const f of v.inputFields) {
                    const key = `${v.name}.${f.name}`;
                    const raw = values[key];
                    if (raw === undefined || raw === "") {
                        if (f.required) {
                            // still include empty if required - server will validate
                            obj[f.name] = null;
                        } else {
                            continue;
                        }
                    } else {
                        // convert types
                        if (f.type === "Int") obj[f.name] = Number(raw);
                        else if (f.type === "Float") obj[f.name] = Number(raw);
                        else if (f.type === "Boolean") obj[f.name] = Boolean(raw);
                        else obj[f.name] = raw;
                    }
                }
                vars[v.name] = obj;
            } else {
                // primitive variable
                const raw = values[v.name];
                if (raw === undefined || raw === "") {
                    if (v.required) vars[v.name] = null;
                    else continue;
                } else {
                    if (v.type === "Int") vars[v.name] = Number(raw);
                    else if (v.type === "Float") vars[v.name] = Number(raw);
                    else if (v.type === "Boolean") vars[v.name] = Boolean(raw);
                    else vars[v.name] = raw;
                }
            }
        }
        return vars;
    }

    async function execute() {
        setLoading(true);
        setError(null);
        setResult(null);
        try {
            const variables = buildVariablesFromSpec();
            const data = await graphqlRequest(op.query, variables);
            setResult(data);
        } catch (e) {
            setError(e.message || String(e));
        } finally {
            setLoading(false);
        }
    }

    return (
        <div className="operation-card">
            <div className="op-header">
                <div className="op-title">{op.title || op.name} <small>({op.type})</small></div>
            </div>

            <div className="op-body">
                {(op.variables || []).length === 0 && <div className="no-params">Нет параметров</div>}

                {(op.variables || []).map(v => {
                    if (v.inputFields) {
                        return (
                            <div key={v.name} className="op-input-group">
                                <div className="op-input-group-title">{v.name} — {v.type}</div>
                                {v.inputFields.map(f => renderInputField(f, v.name))}
                            </div>
                        );
                    }
                    return renderInputField(v);
                })}

                <div className="op-actions">
                    <button className="btn btn-primary" onClick={execute} disabled={loading}>
                        {loading ? "Выполняется..." : `${op.type === "mutation" ? "Выполнить (mutation)" : "Выполнить (query)"}`}
                    </button>
                </div>

                {error && <div className="op-error">Ошибка: {error}</div>}

                {result && (
                    <div className="op-result">
                        <div className="op-result-title">Результат</div>
                        <pre style={{ whiteSpace: "pre-wrap", maxHeight: 400, overflow: "auto" }}>
              {JSON.stringify(result, null, 2)}
            </pre>
                    </div>
                )}
            </div>
        </div>
    );
}
