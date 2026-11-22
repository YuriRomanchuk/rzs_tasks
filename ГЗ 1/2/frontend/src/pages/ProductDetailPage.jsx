import { useEffect, useState } from 'react'
import { useParams, Link } from 'react-router-dom'
import { fetchProduct, updateProduct } from '../api.js'
import Lightbox from '../components/Lightbox.jsx'

// Сторінка одного товару: галерея фото + деталі + характеристики.
// Клік на велике фото → збільшення (лайтбокс).
export default function ProductDetailPage() {
  const { id } = useParams()
  const [product, setProduct] = useState(null)
  const [error, setError] = useState(null)
  const [activeImage, setActiveImage] = useState(null) // яке фото показано великим
  const [zoomed, setZoomed] = useState(false)          // чи відкрито лайтбокс
  const [editing, setEditing] = useState(false)        // чи відкрита форма редагування
  const [saving, setSaving] = useState(false)
  const [form, setForm] = useState({ name: '', price: '', category: '', imageUrl: '', description: '' })

  useEffect(() => {
    fetchProduct(id)
      .then((p) => {
        setProduct(p)
        setActiveImage(p.imageUrl)
      })
      .catch((e) => setError(e.message))
  }, [id])

  if (error && !product) {
    return (
      <div className="container">
        <p className="error">{error}</p>
        <Link to="/" className="back">← До списку</Link>
      </div>
    )
  }
  if (!product) return <div className="container"><p>Завантаження…</p></div>

  const startEdit = () => {
    setError(null)
    setForm({
      name: product.name,
      price: product.price,
      category: product.category || '',
      imageUrl: product.imageUrl || '',
      description: product.description || '',
    })
    setEditing(true)
  }

  const onSave = async (e) => {
    e.preventDefault()
    setSaving(true)
    setError(null)
    try {
      const updated = await updateProduct(id, {
        name: form.name,
        price: Number(form.price),
        category: form.category,
        imageUrl: form.imageUrl,
        images: product.images?.length ? product.images : [form.imageUrl],
        description: form.description,
        characteristics: product.characteristics || [],
      })
      setProduct(updated)
      setActiveImage(updated.imageUrl)
      setEditing(false)
    } catch (err) {
      setError(err.message)
    } finally {
      setSaving(false)
    }
  }

  const images = product.images?.length ? product.images : [product.imageUrl]

  return (
    <div className="container">
      <Link to="/" className="back">← До списку</Link>

      <div className="detail">
        <div className="gallery">
          <div className="gallery-main" onClick={() => setZoomed(true)} title="Натисніть, щоб збільшити">
            <img src={activeImage} alt={product.name} />
          </div>
          {images.length > 1 && (
            <div className="thumbs">
              {images.map((img) => (
                <button
                  key={img}
                  className={'thumb' + (img === activeImage ? ' thumb-active' : '')}
                  onClick={() => setActiveImage(img)}
                >
                  <img src={img} alt="" />
                </button>
              ))}
            </div>
          )}
        </div>

        {editing ? (
          <form className="edit-form" onSubmit={onSave}>
            {error && <p className="error">{error}</p>}

            <label>
              Назва
              <input value={form.name} required
                     onChange={(e) => setForm({ ...form, name: e.target.value })} />
            </label>
            <label>
              Ціна
              <input type="number" step="0.01" value={form.price} required
                     onChange={(e) => setForm({ ...form, price: e.target.value })} />
            </label>
            <label>
              Категорія
              <input value={form.category}
                     onChange={(e) => setForm({ ...form, category: e.target.value })} />
            </label>
            <label>
              URL фото
              <input value={form.imageUrl}
                     onChange={(e) => setForm({ ...form, imageUrl: e.target.value })} />
            </label>
            <label>
              Опис
              <textarea rows="4" value={form.description}
                        onChange={(e) => setForm({ ...form, description: e.target.value })} />
            </label>

            <div className="edit-actions">
              <button type="submit" disabled={saving}>
                {saving ? 'Зберігаю…' : 'Зберегти'}
              </button>
              <button type="button" className="btn-secondary"
                      onClick={() => { setEditing(false); setError(null) }}>
                Скасувати
              </button>
            </div>
          </form>
        ) : (
          <div className="detail-info">
            <h1>{product.name}</h1>
            <div className="detail-price">{formatPrice(product.price)} грн</div>
            <p className="detail-description">{product.description}</p>

            {product.characteristics?.length > 0 && (
              <table className="chars">
                <tbody>
                  {product.characteristics.map((c) => (
                    <tr key={c.name}>
                      <td className="chars-name">{c.name}</td>
                      <td className="chars-value">{c.value}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            )}

            <button className="edit-btn" onClick={startEdit}>Редагувати</button>
          </div>
        )}
      </div>

      {zoomed && <Lightbox src={activeImage} alt={product.name} onClose={() => setZoomed(false)} />}
    </div>
  )
}

function formatPrice(price) {
  return Number(price).toLocaleString('uk-UA', { minimumFractionDigits: 2 })
}
