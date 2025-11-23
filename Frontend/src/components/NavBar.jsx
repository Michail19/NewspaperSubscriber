export function NavBar() {
    return (
        <nav className="w-full bg-gray-900 text-white p-4 flex gap-6 text-lg">
            <a href="#/" className="hover:text-blue-300">Каталоги</a>
            <a href="#/categories" className="hover:text-blue-300">Категории</a>
            <a href="#/series" className="hover:text-blue-300">Серии</a>
            <a href="#/user" className="hover:text-blue-300">Пользователь</a>
            <a href="#/subscriptions" className="hover:text-blue-300">Подписки</a>
        </nav>
    );
}
