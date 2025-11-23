import React from "react";
import { NavBar } from "./components/NavBar";
import { CatalogsPage } from "./pages/CatalogsPage";
import { CategoriesPage } from "./pages/CategoriesPage";
import { SeriesPage } from "./pages/SeriesPage";
import { UserPage } from "./pages/UserPage";
import { SubscriptionsPage } from "./pages/SubscriptionsPage";


export default function App() {
  const route = window.location.hash;


  let Page = CatalogsPage;
  if (route.startsWith("#/categories")) Page = CategoriesPage;
  if (route.startsWith("#/series")) Page = SeriesPage;
  if (route.startsWith("#/user")) Page = UserPage;
  if (route.startsWith("#/subscriptions")) Page = SubscriptionsPage;


  return (
      <div className="min-h-screen bg-gray-100">
        <NavBar />
        <Page />
      </div>
  );
}
