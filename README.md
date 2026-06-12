# FirstClub Membership Management System

## Overview

This project implements a Membership Management System for FirstClub.

The system allows users to:

* View available membership plans
* View available membership tiers
* Subscribe to a membership plan and tier
* Upgrade or downgrade membership tier
* Cancel membership
* Track active membership and expiry
* Evaluate eligible membership tier based on user activity


---

# Technology Stack

* Java 21
* Spring Boot
* Spring Data JPA
* H2 Database
* Spring Validation
* OpenAPI / Swagger
* JUnit 5
* Mockito
* Maven

---


# Database Design

## membership_plan

Stores available membership plans.

| Column   | Type    |
| -------- | ------- |
| id       | BIGINT  |
| name     | VARCHAR |
| duration | VARCHAR |
| price    | DECIMAL |

Example:

| id | name      | duration  | price |
| -- | --------- | --------- | ----- |
| 1  | Monthly   | MONTHLY   | 199   |
| 2  | Quarterly | QUARTERLY | 499   |
| 3  | Yearly    | YEARLY    | 1499  |

---

## membership_tier

Stores membership tiers and eligibility rules.

| Column          | Type    |
| --------------- | ------- |
| id              | BIGINT  |
| name            | VARCHAR |
| priority        | INT     |
| min_order_count | INT     |
| min_order_value | DECIMAL |
| cohort          | VARCHAR |

Example:

| Tier     | Priority |
| -------- | -------- |
| Silver   | 1        |
| Gold     | 2        |
| Platinum | 3        |

---

## tier_benefit

Stores configurable benefits for each tier.

| Column        | Type    |
| ------------- | ------- |
| id            | BIGINT  |
| tier_id       | BIGINT  |
| benefit_name  | VARCHAR |
| benefit_value | VARCHAR |

Examples:

* FREE_DELIVERY
* DISCOUNT_PERCENT
* EARLY_ACCESS
* PRIORITY_SUPPORT
* EXCLUSIVE_COUPON

This design allows benefits to be added without changing application code.

---

## user_subscription

Stores user memberships.

| Column     | Type    |
| ---------- | ------- |
| id         | BIGINT  |
| user_id    | BIGINT  |
| plan_id    | BIGINT  |
| tier_id    | BIGINT  |
| status     | VARCHAR |
| start_date | DATE    |
| end_date   | DATE    |
| version    | BIGINT  |

The version column is used for optimistic locking.

---

# API Documentation

Swagger UI:

http://localhost:8081/swagger-ui/index.html

OpenAPI JSON:

http://localhost:8081/v3/api-docs

---

# API Contracts

## 1. Get Membership Plans

GET

```http
/api/v1/plans
```

Response

```json
[
  {
    "id": 1,
    "name": "Monthly",
    "duration": "MONTHLY",
    "price": 199
  }
]
```

---

## 2. Get Membership Tiers

GET

```http
/api/v1/tiers
```

Response

```json
[
  {
    "id": 1,
    "name": "Silver",
    "priority": 1,
    "benefits": [
      {
        "benefitName": "FREE_DELIVERY",
        "benefitValue": "true"
      }
    ]
  }
]
```

---

## 3. Evaluate Tier

POST

```http
/api/v1/tiers/evaluate
```

Request

```json
{
  "orderCount": 15,
  "orderValue": 7000,
  "cohort": "PREMIUM"
}
```

Response

```json
{
  "tierName": "Gold"
}
```

---

## 4. Subscribe

POST

```http
/api/v1/subscriptions
```

Request

```json
{
  "userId": 101,
  "planId": 1,
  "tierId": 2
}
```

Response

```json
{
  "subscriptionId": 1,
  "userId": 101,
  "planName": "Monthly",
  "tierName": "Gold",
  "status": "ACTIVE",
  "startDate": "2026-06-12",
  "endDate": "2026-07-12",
  "remainingDays": 30
}
```

---

## 5. Get Membership

GET

```http
/api/v1/subscriptions/101
```

Response

```json
{
  "subscriptionId": 1,
  "userId": 101,
  "planName": "Monthly",
  "tierName": "Gold",
  "status": "ACTIVE"
}
```

---

## 6. Upgrade Tier

PUT

```http
/api/v1/subscriptions/{subscriptionId}/upgrade
```

Request

```json
{
  "tierId": 3
}
```

---

## 7. Downgrade Tier

PUT

```http
/api/v1/subscriptions/{subscriptionId}/downgrade
```

Request

```json
{
  "tierId": 1
}
```

---

## 8. Cancel Subscription

DELETE

```http
/api/v1/subscriptions/{subscriptionId}
```

Response

```http
204 No Content
```

---



# Test Scenerios

Test Coverage Includes:

* Successful subscription creation
* Duplicate subscription prevention
* Tier evaluation
* Tier eligibility checks
* Error scenarios

---

# Future Improvements

For production-scale systems handling millions of users:

* PostgreSQL / Aurora instead of H2
* Redis caching
* Kafka event-driven architecture
* Scheduled subscription expiry jobs
* Read replicas
* Authentication and authorization
* Rate limiting
* Metrics and monitoring
* Distributed tracing

---

# Design Decisions

This implementation intentionally keeps infrastructure simple while demonstrating:

* Clean architecture
* Domain modeling
* Configurable membership benefits
* Optimistic locking
* Exception handling
* Validation
* Unit testing
* REST API design

The focus of the assignment is correctness, maintainability, extensibility, and clear separation of concerns.
