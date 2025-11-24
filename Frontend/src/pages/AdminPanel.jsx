// src/pages/AdminPanel.jsx
import React from "react";
import { OPERATIONS } from "../graphql/operations";
import { OperationCard } from "../components/OperationCard";
import "./AdminPanel.css";

export function AdminPanel() {
    return (
        <div className="admin-page">
            <h1 className="page-title">GraphQL Admin — тестовый клиент</h1>
            <p className="page-subtitle">Ниже — карточки для каждой операции из схемы. Заполните поля и нажмите «Выполнить».</p>

            <div className="ops-grid">
                {OPERATIONS.map(op => (
                    <OperationCard key={op.name} op={op} />
                ))}
            </div>
        </div>
    );
}
