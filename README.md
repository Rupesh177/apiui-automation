# 🚀 Automation Framework (Enterprise-Ready)

A scalable, modular, and future-ready **automation framework** built using Java 21, designed for **UI + API + DB + Event-driven systems** with strong support for **parallel execution, CI/CD, observability, and intelligent test execution**.

---

## 🧱 Tech Stack

* Java 21
* Selenium (pluggable → Playwright ready)
* RestAssured
* TestNG
* Allure Reporting
* Docker & Docker Compose
* Selenium Grid (Distributed execution)
* Kafka (Event validation)
* Flyway (DB migrations)
* HikariCP (DB pooling)
* Vault (Secrets management)
* JMeter / Gatling (Performance hooks)

---

## 🏗️ Framework Architecture

```
core/        → Driver, context, base setup  
ui/          → Locators + Actions (POM optimized)  
api/         → Client + Services + Schema validation  
db/          → DB utils + migrations  
kafka/       → Event validation  
utils/       → Config, test data, helpers  
tests/       → UI / API / E2E tests  
docker/      → Containerization  
```

---

## ⚡ Key Features

### 🧪 Testing Capabilities

* UI Automation (cross-browser)
* API Testing (RestAssured)
* DB Validation (SQL + pooling)
* Kafka/Event Validation
* Contract Testing (JSON schema)

---

### ⚡ Execution Engine

* Parallel execution (ThreadLocal)
* Distributed execution (Docker + Grid)
* Cross-browser (Chrome, Firefox, Edge)
* Remote execution (Grid / Cloud)

---

### 🧠 Intelligent Testing

* Flaky test detection
* Dynamic test prioritization
* Test impact analysis
* Smart tagging (smoke / regression / release)

---

### 📊 Observability

* Allure reports (rich UI)
* Historical trends
* Grafana dashboards
* Metrics tracking

---

### 🔐 Security

* Vault-based secrets (no plaintext)
* Token-based authentication
* Security test hooks (negative scenarios)

---

### 🚩 Feature Flags

* Toggle-based validation
* Data-driven feature testing

---

### 📦 Test Data Management

* Centralized Test Data Service (API-based)
* Dynamic data generation
* Test data lifecycle (create → use → cleanup)

---

### 🐞 Quality Ops

* Auto bug creation in Jira
* Jira linking in Allure reports

---

### ⚡ Performance

* JMeter / Gatling integration hooks
* CI-ready performance execution

---

## ▶️ Getting Started

### 🔧 Prerequisites

* Java 21
* Maven
* Docker (optional but recommended)

---

## 🧪 Run Tests

### Run all tests

```
mvn clean test
```

---

### Run specific groups

```
mvn test -Dgroups=smoke
mvn test -Dgroups=api
mvn test -Dgroups=ui
```

---

### Run with parameters

```
mvn test -Dbrowser=chrome -Dheadless=true
```

---

## 🐳 Run with Docker

### Build

```
docker-compose up --build
```

---

### Scale execution

```
docker-compose up --scale test-runner=3
```

---

## 🌍 Cross Browser Execution

```
mvn test -Dbrowser=firefox
mvn test -Dbrowser=edge
```

---

## ☁️ Remote Execution (Grid)

```
mvn test -Dremote=true -Dgrid.url=http://localhost:4444
```

---

## 📊 Allure Reporting

### Generate report

```
mvn allure:report
```

### Open report

```
mvn allure:serve
```

---

## 🔗 Jira Integration

* Auto-create bugs on failure
* Linked directly in Allure reports

---

## 📡 Test Data Service

Base URL:

```
http://localhost:8081/testdata
```

Example:

```
GET /user
POST /user
DELETE /user/{id}
```

---

## 🔐 Secrets Management

Secrets are fetched from Vault:

```
VaultClient.getSecret("db.password")
```

❌ No plaintext credentials stored in code

---

## 📈 Metrics & Dashboard

* Metrics exposed via `/metrics`
* Integrated with Prometheus + Grafana

---

## ⚡ Performance Testing

### Run JMeter

```
jmeter -n -t test-plan.jmx
```

---

## 🧠 Smart Execution Strategy

| Type       | Usage          |
| ---------- | -------------- |
| smoke      | critical flows |
| regression | full suite     |
| release    | E2E validation |
| api        | backend tests  |
| ui         | UI flows       |

---

## 🔁 CI/CD (GitHub Actions)

* Automated test execution on PR
* Parallel jobs (UI/API split)
* Allure artifacts uploaded

---

## 🧨 Best Practices

* Prefer API tests over UI for speed
* Keep UI tests limited to critical flows
* Use feature flags for dual-path validation
* Always clean test data
* Avoid hardcoded values

---

## 🚀 Future Ready

* Easily plug Playwright instead of Selenium
* Scales to distributed environments
* Supports microservices architecture
* Extensible for AI-driven testing

---

## 👨‍💻 Author Notes

This framework is designed to be:

* Minimal yet powerful
* Scalable without over-engineering
* Practical for real-world microservices

---

## 📌 Final Thought

> This is not just a test framework — it’s a **test platform**.

Use it wisely:

* Run smart, not everything
* Optimize continuously
* Keep it maintainable

---
