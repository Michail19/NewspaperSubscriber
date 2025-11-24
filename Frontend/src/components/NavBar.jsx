import './NavBar.css';

export function NavBar() {
    return (
        <nav className="navbar">
            <div className="navbar-container">
                <div className="flex gap-4">
                    <a href="#/" className="nav-link">Каталоги</a>
                    <a href="#/categories" className="nav-link">Категории</a>
                    <a href="#/series" className="nav-link">Серии</a>
                    <a href="#/subscriptions" className="nav-link">Подписки</a>
                    <a href="#/user" className="nav-link">Пользователь</a>
                    <a href="#/admin" className="nav-link">Admin</a>
                </div>
            </div>
        </nav>
    );
}
