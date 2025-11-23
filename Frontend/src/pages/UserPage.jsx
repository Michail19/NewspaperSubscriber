import { useState } from "react";
import { graphqlRequest } from "../graphql/client";
import { GET_USER } from "../graphql/queries";
import { ADD_USER } from "../graphql/mutations";
import './UserPage.css';

export function UserPage() {
    const [input, setInput] = useState({ firstName: "", secondName: "", age: 18 });
    const [searchId, setSearchId] = useState("");
    const [user, setUser] = useState(null);

    async function register() {
        const r = await graphqlRequest(ADD_USER, { input });
        alert("Пользователь зарегистрирован: " + r.addUser.firstName);
    }

    async function loadUser() {
        const r = await graphqlRequest(GET_USER, { id: searchId });
        setUser(r.getUser);
    }

    return (
        <div className="user-container">
            <h1 className="user-title">Пользователь</h1>

            <div className="form-section">
                <h2 className="section-title">Регистрация</h2>
                <div className="form-grid">
                    <input
                        className="form-input"
                        placeholder="Имя"
                        onChange={e => setInput({ ...input, firstName: e.target.value })}
                    />
                    <input
                        className="form-input"
                        placeholder="Фамилия"
                        onChange={e => setInput({ ...input, secondName: e.target.value })}
                    />
                    <input
                        className="form-input"
                        type="number"
                        placeholder="Возраст"
                        onChange={e => setInput({ ...input, age: Number(e.target.value) })}
                    />
                    <button className="btn btn-primary" onClick={register}>Создать</button>
                </div>
            </div>

            <div className="form-section">
                <h2 className="section-title">Поиск пользователя</h2>
                <div className="search-form">
                    <input
                        className="form-input"
                        placeholder="ID пользователя"
                        value={searchId}
                        onChange={e => setSearchId(e.target.value)}
                    />
                    <button className="btn btn-success" onClick={loadUser}>Загрузить</button>
                </div>

                {user && (
                    <div className="user-card">
                        <div className="user-info">
                            <div className="info-item">
                                <span className="info-label">ID</span>
                                <span className="info-value">{user.id}</span>
                            </div>
                            <div className="info-item">
                                <span className="info-label">Имя</span>
                                <span className="info-value">{user.firstName} {user.secondName}</span>
                            </div>
                            <div className="info-item">
                                <span className="info-label">Возраст</span>
                                <span className="info-value">{user.age}</span>
                            </div>
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
}
