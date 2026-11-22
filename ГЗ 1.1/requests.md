# Приклади запитів — GameStore (самостійна робота 1.1)

Бекенд має бути запущений: `cd gamestore && ./mvnw spring-boot:run` (порт 8080).

На цьому етапі є лише **два GET-ендпоінти** (перегляд). Створення ігор — пізніше.

---

## 1. Список ігор (картки)

```bash
curl http://localhost:8080/api/games
```

Очікувана відповідь (скорочено):
```json
[
  { "id": 1, "title": "Легенди Козацької Січі", "studio": "Volyn Games", "price": 649.00, "coverUrl": "/covers/kozaky.svg" },
  { "id": 2, "title": "Космічний Шлях", "studio": "Nova Studio", "price": 899.00, "coverUrl": "/covers/space.svg" }
]
```

Красивий вивід (якщо встановлено `jq`):
```bash
curl -s http://localhost:8080/api/games | jq
```

---

## 2. Одна гра з деталями

```bash
curl http://localhost:8080/api/games/1
```

Очікувана відповідь:
```json
{
  "id": 1,
  "title": "Легенди Козацької Січі",
  "studio": "Volyn Games",
  "price": 649.00,
  "releaseYear": 2023,
  "coverUrl": "/covers/kozaky.svg",
  "description": "Історична RPG про пригоди козацького старшини на Дикому Полі.",
  "genre": "RPG",
  "platform": "PC, PlayStation 5"
}
```

---

## 3. Гра, якої немає → 404

```bash
curl -i http://localhost:8080/api/games/999
```

Очікувано: перший рядок `HTTP/1.1 404`.

---

## 4. Обкладинки віддаються як статика

```bash
curl -i http://localhost:8080/covers/kozaky.svg
```

Очікувано: `HTTP/1.1 200` і `Content-Type: image/svg+xml`.
