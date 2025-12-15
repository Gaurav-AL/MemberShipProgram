# Membership Service

A production‑ready **Membership & Subscription Management Service** built with **Spring Boot + PostgreSQL**, supporting **idempotent operations**, **plan lifecycle management**, **order & transaction tracking**, and **tier upgrades based on spending**.

---

## ✨ Features

* User membership lifecycle

  * Subscribe
  * Upgrade
  * Downgrade
  * Cancel
* Idempotent APIs (safe retries)
* Order & order‑transaction separation
* Tier auto‑upgrade based on total spend
* PostgreSQL persistence using JPA/Hibernate
* Clean REST APIs with validation
* Designed for extensibility (cron jobs, alarms, analytics)

---

## 🧱 Tech Stack

* **Java 17+**
* **Spring Boot 3 / Spring 7**
* **Spring Data JPA**
* **PostgreSQL**
* **Hibernate**
* **Lombok**
* **Maven**

---

## 📦 Domain Model (High Level)

* **UserMembership** – current active plan & tier
* **Plan / PlanTier** – pricing & hierarchy
* **Product** – purchasable plans
* **Order** – purchase intent
* **OrderTransaction** – money movement (BUY / REFUND)
* **MembershipTransaction** – membership state changes

---

## 🔐 Idempotency Design

All **write operations** require an `Idempotency-Key` header.

* Prevents duplicate orders on retries
* Enforced using **unique DB constraints**
* Same request + same key → same result

```
Header: Idempotency-Key: <UUID>
```

---

## 🚀 API Endpoints

### Membership APIs

| Method | Endpoint                  | Description              |
| ------ | ------------------------- | ------------------------ |
| POST   | /api/membership/subscribe | Subscribe to a plan      |
| POST   | /api/membership/upgrade   | Upgrade membership       |
| POST   | /api/membership/downgrade | Downgrade membership     |
| POST   | /api/membership/cancel    | Cancel membership        |
| GET    | /api/membership/info      | Get user membership info |

---

### Orders APIs

| Method | Endpoint           | Description            |
| ------ | ------------------ | ---------------------- |
| POST   | /api/orders/create | Create an order        |
| GET    | /api/orders/get    | Fetch orders by userId |

---

## 📄 Sample Requests

### Subscribe

```bash
curl --location 'http://localhost:9090/api/membership/subscribe' \
--header 'Content-Type: application/json' \
--header 'Idempotency-Key: 8c3c8c7e-1a0e-4f5c-b3a1-111111111111' \
--data '{
  "userId": 1,
  "planId": 1,
  "tier": "BASIC"
}'
```

---

### Create Order

```bash
curl --location 'http://localhost:9090/api/orders/create' \
--header 'Content-Type: application/json' \
--header 'Idempotency-Key: 111e4567-e89b-12d3-a456-426614174000' \
--data '{
  "userId": 1,
  "productId": "c6d29c1e-1f55-4e0b-9a32-cc10cde333aa"
}'
```

---

## 🗄️ Database Tables (Core)

* user_membership
* membership_transaction
* orders
* order_transaction
* product

Each transactional table includes:

* `idempotency_key` (UNIQUE)
* `created_at`

---

## ⏰ Tier Upgrade Logic

* Total spend is calculated from **order_transaction**
* Cron/async worker can:

  * Evaluate thresholds
  * Upgrade tier automatically

(Ready for Quartz / @Scheduled integration)

---

## ⚠️ Error Handling

* Duplicate idempotency key → existing response returned
* Validation errors → 400
* Missing resources → 404
* Constraint violations → handled gracefully

---

## 🧪 Testing

* APIs tested using **Thunder Client / curl**
* Safe retry behavior verified via idempotency

---

## 📌 How to Run

```bash
mvn clean install
mvn spring-boot:run
```

PostgreSQL must be running and configured in `application.yml`.

---

## 📈 Future Enhancements

* Payment gateway integration
* Refund workflows
* Webhooks
* Admin dashboard
* Analytics & reports

---

## 👤 Author

**Gaurav Chaudhary**
Backend Engineer | Java | Spring Boot | Systems Design

---

If you like this project ⭐ the repository and feel free to contribute.
