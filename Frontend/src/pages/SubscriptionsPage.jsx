import { useState } from "react";
import { graphqlRequest } from "../graphql/client";
import { GET_USER_SUBSCRIPTIONS } from "../graphql/queries";
import { CREATE_SUBSCRIPTION, CANCEL_SUB } from "../graphql/mutations";
import './SubscriptionsPage.css';

export function SubscriptionsPage() {
    const [userId, setUserId] = useState("");
    const [subs, setSubs] = useState([]);
    const [form, setForm] = useState({ userId: "", magazineId: "", durationMonths: 1 });

    async function loadSubs() {
        const r = await graphqlRequest(GET_USER_SUBSCRIPTIONS, { id: userId });
        setSubs(r.getUserSubscriptions);
    }

    async function create() {
        await graphqlRequest(CREATE_SUBSCRIPTION, { input: form });
        alert("Готово");
    }

    async function cancel(id) {
        await graphqlRequest(CANCEL_SUB, { id });
        loadSubs();
    }

    return (
        <div className="subscriptions-container">
            <h1 className="subscriptions-title">Подписки</h1>

            <div className="section">
                <h2 className="section-title">Загрузить подписки пользователя</h2>
                <div className="form-group">
                    <input
                        className="input-field"
                        placeholder="User ID"
                        value={userId}
                        onChange={e => setUserId(e.target.value)}
                    />
                    <button className="btn btn-primary" onClick={loadSubs}>Загрузить</button>
                </div>
            </div>

            {subs.length > 0 && (
                <div className="section">
                    <h2 className="section-title">Подписки пользователя</h2>
                    <div className="subscriptions-list">
                        {subs.map(s => (
                            <div key={s.id} className={`subscription-item ${s.status === 'CANCELLED' ? 'cancelled' : ''}`}>
                                <div className="subscription-info">
                                    <div>
                                        <div className="info-label">Magazine ID</div>
                                        <div className="info-value">{s.magazineId}</div>
                                    </div>
                                    <div>
                                        <div className="info-label">Статус</div>
                                        <div className={`info-value ${s.status === 'ACTIVE' ? 'status-active' : 'status-cancelled'}`}>
                                            {s.status === 'ACTIVE' ? 'Активна' : 'Отменена'}
                                        </div>
                                    </div>
                                    <div>
                                        <div className="info-label">Месяцев</div>
                                        <div className="info-value">{s.durationMonths}</div>
                                    </div>
                                </div>
                                {s.status === 'ACTIVE' && (
                                    <button className="btn btn-danger" onClick={() => cancel(s.id)}>Отменить</button>
                                )}
                            </div>
                        ))}
                    </div>
                </div>
            )}

            <div className="section">
                <h2 className="section-title">Создать подписку</h2>
                <div className="form-grid">
                    <input
                        className="input-field"
                        placeholder="User ID"
                        onChange={e => setForm({ ...form, userId: e.target.value })}
                    />
                    <input
                        className="input-field"
                        placeholder="Magazine ID"
                        onChange={e => setForm({ ...form, magazineId: e.target.value })}
                    />
                    <input
                        className="input-field"
                        type="number"
                        placeholder="Месяцев"
                        onChange={e => setForm({ ...form, durationMonths: Number(e.target.value) })}
                    />
                    <button className="btn btn-primary" onClick={create}>Создать</button>
                </div>
            </div>
        </div>
    );
}
