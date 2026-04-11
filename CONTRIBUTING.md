# 🤝 Contributing Guide

Thanks for contributing to this automation framework 🚀
This is a **production-grade system**, so consistency and quality matter.

---

## 🧱 Project Principles

* Keep it **simple and maintainable**
* Avoid unnecessary abstraction
* Follow **SOLID + DRY**
* Prefer **composition over inheritance**
* Do not introduce tools/patterns without clear value

---

## 📁 Project Structure

```id="k1j3lp"
ui/        → UI locators + actions  
api/       → API clients + services  
db/        → DB helpers  
kafka/     → event validation  
utils/     → reusable helpers  
tests/     → test classes  
```

---

## 🧪 Writing Tests

### ✅ Guidelines

* Use **TestNG groups**

```java
@Test(groups = {"smoke", "api"})
```

* Keep tests:

    * Independent
    * Idempotent
    * Fast

---

### ❌ Avoid

* Hardcoded data
* Static/shared state
* UI-heavy validation when API exists

---

## 📦 Test Data

* Use **Test Data Service**

```java
TestDataClient.getUser();
```

* Always cleanup:

```java
@AfterMethod
```

---

## 🧵 Parallel Execution

* Never use static WebDriver ❌
* Always use `DriverManager` (ThreadLocal)

---

## 🔐 Secrets

* Never store credentials in code
* Use Vault:

```java
VaultClient.getSecret("db.password");
```

---

## 🧠 Intelligent Features

* Tag tests correctly:

    * smoke
    * regression
    * release

* Ensure new tests:

    * Fit into impact analysis mapping
    * Don’t increase flakiness

---

## 🐞 Bug Handling

* Failures auto-create Jira tickets
* Do not suppress failures unless justified

---

## 📊 Reporting

* Add meaningful Allure steps
* Attach:

    * API responses
    * Screenshots (UI)

---

## ⚡ Performance Tests

* Keep separate under `performance` group
* Do NOT mix with functional tests

---

## 🐳 Docker

* Ensure tests run headless
* No local-only dependencies

---

## 🔁 Pull Request Guidelines

### Before raising PR:

* ✅ All tests pass
* ✅ No flaky tests
* ✅ Code reviewed locally

---

### PR must include:

* Description of change
* Impacted modules
* Test coverage added

---

## 🧨 Golden Rules

* Prefer API > UI
* Fix flaky tests, don’t retry blindly
* Keep execution fast
* Keep framework clean

---

## 🚀 Final Thought

> This is a shared engineering asset — treat it like production code.
