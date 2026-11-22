# Приклади запитів — AmazonUA (самостійна робота 1.2)

Бекенд: `cd backend && ./mvnw spring-boot:run` (порт 8080).
UI (за бажанням): `cd frontend && npm install && npm run dev` (порт 5173, проксі на 8080).

Повний CRUD + фільтр за категорією. Нижче — по одному прикладу на кожну операцію.

---

## 1. Список товарів (картки)

```bash
curl http://localhost:8080/api/products
```

Скорочена відповідь:
```json
[
  { "id": 1, "name": "Навушники AudioPro X1", "price": 1299.00, "imageUrl": "/images/headphones.svg" },
  { "id": 2, "name": "Клавіатура MechKey TKL", "price": 1890.50, "imageUrl": "/images/keyboard.svg" }
]
```

Красивий вивід (якщо встановлено `jq`):
```bash
curl -s http://localhost:8080/api/products | jq
```

## 2. Фільтр за категорією

```bash
curl "http://localhost:8080/api/products?category=Аудіо"
```

## 3. Один товар з деталями

```bash
curl http://localhost:8080/api/products/1
```

Відповідь:
```json
{
  "id": 1,
  "name": "Навушники AudioPro X1",
  "price": 1299.00,
  "imageUrl": "/images/headphones.svg",
  "images": ["/images/headphones.svg", "/images/headphones-2.svg"],
  "description": "Бездротові навушники з активним шумозаглушенням і автономністю до 30 годин.",
  "category": "Аудіо",
  "characteristics": [
    { "name": "Тип", "value": "Накладні" },
    { "name": "Підключення", "value": "Bluetooth 5.3" }
  ]
}
```

Неіснуючий id → `404 Not Found`.

## 4. Створити товар (201 Created)

```bash
curl -i -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Новий товар",
    "price": 199.00,
    "imageUrl": "/images/headphones.svg",
    "images": ["/images/headphones.svg"],
    "description": "Опис нового товару",
    "category": "Аудіо",
    "characteristics": [{ "name": "Колір", "value": "Чорний" }]
  }'
```

## 5. Оновити товар (200 OK / 404)

```bash
curl -i -X PUT http://localhost:8080/api/products/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Оновлені навушники",
    "price": 1499.00,
    "imageUrl": "/images/headphones.svg",
    "images": ["/images/headphones.svg", "/images/headphones-2.svg"],
    "description": "Оновлений опис",
    "category": "Аудіо",
    "characteristics": [{ "name": "Колір", "value": "Білий" }]
  }'
```

Неіснуючий id → `404 Not Found`.

## 6. Видалити товар (204 No Content / 404)

```bash
curl -i -X DELETE http://localhost:8080/api/products/2
```

Неіснуючий id → `404 Not Found`.

---

## Коди відповідей

| Операція        | Метод                          | Успіх | Помилка |
|-----------------|--------------------------------|-------|---------|
| Список          | `GET /api/products`            | 200   | —       |
| Фільтр          | `GET /api/products?category=`  | 200   | —       |
| Один товар      | `GET /api/products/{id}`       | 200   | 404     |
| Створити        | `POST /api/products`           | 201   | —       |
| Оновити         | `PUT /api/products/{id}`       | 200   | 404     |
| Видалити        | `DELETE /api/products/{id}`    | 204   | 404     |
