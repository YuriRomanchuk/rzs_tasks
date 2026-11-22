import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { fetchProducts, createProduct, deleteProduct } from '../api.js'

// Сторінка переліку товарів із формою створення та кнопкою видалення (заняття 1/2).
// Кожна картка — посилання на сторінку товару (/products/:id).
export default function ProductListPage() {
  const [products, setProducts] = useState([])
  const [error, setError] = useState(null)
  const [loading, setLoading] = useState(true)
  const [form, setForm] = useState({ name: '', price: '', category: '', imageUrl: '' })
  const [filter, setFilter] = useState('')            // те, що вводить користувач
  const [appliedFilter, setAppliedFilter] = useState('') // уже застосований фільтр

  const reload = () =>
    fetchProducts(appliedFilter)
      .then(setProducts)
      .catch((e) => setError(e.message))

  useEffect(() => {
    reload().finally(() => setLoading(false))
  }, [appliedFilter])

  const onFilter = (e) => {
    e.preventDefault()
    setAppliedFilter(filter.trim())
  }

  const onResetFilter = () => {
    setFilter('')
    setAppliedFilter('')
  }

  const onCreate = async (e) => {
    e.preventDefault()
    setError(null)
    try {
      await createProduct({
        name: form.name,
        price: Number(form.price),
        category: form.category,
        imageUrl: form.imageUrl || '/images/headphones.svg',
        images: form.imageUrl ? [form.imageUrl] : ['/images/headphones.svg'],
        description: '',
        characteristics: [],
      })
      setForm({ name: '', price: '', category: '', imageUrl: '' })
      await reload()
    } catch (err) {
      setError(err.message)
    }
  }

  const onDelete = async (id) => {
    setError(null)
    try {
      await deleteProduct(id)
      await reload()
    } catch (err) {
      setError(err.message)
    }
  }

  if (loading) return <div className="container"><p>Завантаження…</p></div>

  return (
    <div className="container">
      <header className="header">
        <h1>AmazonUA</h1>
        <span className="muted">{products.length} товарів</span>
      </header>

      {error && <p className="error">{error}</p>}

      <form className="filter-form" onSubmit={onFilter}>
        <input placeholder="Фільтр за категорією" value={filter}
               onChange={(e) => setFilter(e.target.value)} />
        <button type="submit">Фільтрувати</button>
        {appliedFilter && (
          <button type="button" className="btn-secondary" onClick={onResetFilter}>
            Скинути
          </button>
        )}
      </form>

      <form className="create-form" onSubmit={onCreate}>
        <input placeholder="Назва" value={form.name} required
               onChange={(e) => setForm({ ...form, name: e.target.value })} />
        <input placeholder="Ціна" type="number" step="0.01" value={form.price} required
               onChange={(e) => setForm({ ...form, price: e.target.value })} />
        <input placeholder="Категорія" value={form.category}
               onChange={(e) => setForm({ ...form, category: e.target.value })} />
        <input placeholder="URL фото (необов'язково)" value={form.imageUrl}
               onChange={(e) => setForm({ ...form, imageUrl: e.target.value })} />
        <button type="submit">Додати товар</button>
      </form>

      <div className="grid">
        {products.map((p) => (
          <div key={p.id} className="card">
            <Link to={`/products/${p.id}`} className="card-link">
              <div className="card-image">
                <img src={p.imageUrl} alt={p.name} loading="lazy" />
              </div>
              <div className="card-body">
                <div className="card-name">{p.name}</div>
                <div className="card-price">{formatPrice(p.price)} грн</div>
              </div>
            </Link>
            <button className="card-delete" onClick={() => onDelete(p.id)}>Видалити</button>
          </div>
        ))}
      </div>
    </div>
  )
}

function formatPrice(price) {
  return Number(price).toLocaleString('uk-UA', { minimumFractionDigits: 2 })
}
