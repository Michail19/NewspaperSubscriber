import React, { useEffect, useState } from "react";
import { NavBar } from "./components/NavBar";
import { CatalogsPage } from "./pages/CatalogsPage";
import { CategoriesPage } from "./pages/CategoriesPage";
import { SeriesPage } from "./pages/SeriesPage";
import { UserPage } from "./pages/UserPage";
import { SubscriptionsPage } from "./pages/SubscriptionsPage";
import { AdminPanel } from "./pages/AdminPanel";

export default function App() {
    const [route, setRoute] = useState(window.location.hash || "#/");

    useEffect(() => {
        const handler = () => setRoute(window.location.hash || "#/");
        window.addEventListener("hashchange", handler);
        return () => window.removeEventListener("hashchange", handler);
    }, []);

    let Page = CatalogsPage;
    if (route.startsWith("#/categories")) Page = CategoriesPage;
    if (route.startsWith("#/series")) Page = SeriesPage;
    if (route.startsWith("#/user")) Page = UserPage;
    if (route.startsWith("#/subscriptions")) Page = SubscriptionsPage;
    if (route.startsWith("#/admin")) Page = AdminPanel;

    return (
        <div className="min-h-screen bg-gray-100">
            <NavBar />
            <Page />
        </div>
    );
}
