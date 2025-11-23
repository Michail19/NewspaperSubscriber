import './CatalogCard.css';

export function CatalogCard({ catalog }) {
    return (
        <div className="catalog-card">
            <div className="catalog-title">{catalog.title}</div>

            {catalog.description && (
                <div className="catalog-description">{catalog.description}</div>
            )}

            <div className="catalog-meta">
                <span>Цена: <span className="catalog-price">{catalog.price}₽</span></span>
            </div>

            <div className="catalog-tags">
                {catalog.category && <span className="catalog-tag">Категория: {catalog.category.name}</span>}
                {catalog.series && <span className="catalog-tag">Серия: {catalog.series.name}</span>}
            </div>

            {catalog.link && (
                <a
                    href={catalog.link}
                    target="_blank"
                    rel="noopener noreferrer"
                    className="catalog-link"
                >
                    Открыть
                </a>
            )}
        </div>
    );
}
