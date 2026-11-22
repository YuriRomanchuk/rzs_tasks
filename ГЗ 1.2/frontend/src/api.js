// Тонкий клієнт до нашого Spring Boot API.
// Шляхи відносні ('/api/...') — Vite-проксі перенаправить їх на :8080 (див. vite.config.js).

export async function fetchProducts(category) {
  const query = category ? `?category=${encodeURIComponent(category)}` : ''
  const res = await fetch(`/api/products${query}`)
  if (!res.ok) throw new Error('Не вдалося завантажити список товарів')
  return res.json()
}

export async function fetchProduct(id) {
  const res = await fetch(`/api/products/${id}`)
  if (res.status === 404) throw new Error('Товар не знайдено')
  if (!res.ok) throw new Error('Не вдалося завантажити товар')
  return res.json()
}

// Заняття 1/2: створення / оновлення / видалення товару.

export async function createProduct(product) {
  const res = await fetch('/api/products', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(product),
  })
  if (!res.ok) throw new Error('Не вдалося створити товар')
  return res.json()
}

export async function updateProduct(id, product) {
  const res = await fetch(`/api/products/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(product),
  })
  if (res.status === 404) throw new Error('Товар не знайдено')
  if (!res.ok) throw new Error('Не вдалося оновити товар')
  return res.json()
}

export async function deleteProduct(id) {
  const res = await fetch(`/api/products/${id}`, { method: 'DELETE' })
  if (res.status === 404) throw new Error('Товар не знайдено')
  if (!res.ok) throw new Error('Не вдалося видалити товар')
}
