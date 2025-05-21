# Test Automation Framework for Petstore API

This project is a lightweight Test Automation Framework for validating the **Swagger Petstore API**. It leverages **JUnit 5**, **Rest Assured**, and **Gradle** to structure and run automated API tests with support for test tagging and modular data generation.

---

## Project Structure

### Test Packages:
- `OrderCreatePositiveTest` / `OrderCreateNegativeTest` – validation of order creation endpoint.
- `OrderGetByIdPositiveTest` / `OrderGetByIdNegativeTest` – tests for fetching orders by ID.
- `OrderDeletePositiveTest` / `OrderDeleteNegativeTest` – tests for deleting existing/non-existing orders.
  
### Tag-Based Test Suites:
Tests are organized using JUnit 5 `@Tag` annotations and can be run selectively:

- `positive` – happy-path and functional coverage.
- `negative` – validation and edge-case scenarios.

You can run them with:

```bash
./gradlew positive     # Run only @Tag("positive") tests
./gradlew negative     # Run only @Tag("negative") tests
./gradlew test         # Run all tests
```

---

## Prerequisites

- **Java Development Kit (JDK)**: JDK 21 or higher
- **Gradle**: 8.13 or higher

---

## Technologies Used

| Tool/Library         | Purpose                                  |
|----------------------|-------------------------------------------|
| **JUnit 5**          | Test framework with tagging support       |
| **Rest Assured**     | API testing and validation                |
| **Lombok**           | Boilerplate reduction (DTO builder, etc.) |
| **Java Faker**       | Fake test data generation                 |
| **SLF4J**            | Logging                                   |
| **Gradle**           | Build automation and task configuration   |

---

## Test Reports
After running the tests, Gradle generates an HTML report that summarizes all test results.
Default report location:

```bash
build/reports/tests/
```

---

## API Test Coverage

| **Method**  | **Endpoint**             | **Test Method Name**              | **Type**     | **What Is Verified**                                                                 |
|-------------|--------------------------|-----------------------------------|--------------|--------------------------------------------------------------------------------------|
| `POST`      | `/store/order`           | `createOrderWithValidData`        | Positive     | Valid order data → HTTP 200 OK + order is successfully created                      |
| `POST`      | `/store/order`           | `createOrderWithEmptyBody`        | Positive     | Empty body → HTTP 200 OK + server fills defaults or ignores missing fields          |
| `POST`      | `/store/order`           | `createOrderWithMissingFields`    | Positive     | Missing fields (e.g. status, petId) → still results in successful order creation     |
| `POST`      | `/store/order`           | `createOrderWithDifferentStatus`    | Positive     | Creates an order using various `status` values (enum); verifies 200 OK     |
| `POST`      | `/store/order`           | `createOrderWithInvalidPetId`     | Negative     | Non-existing `petId = Integer.MAX_VALUE` → API should return an error or reject     |
| `POST`      | `/store/order`           | `createOrderWithInvalidIds`       | Negative     | Invalid `id` values like -1, 0, 11 → API should return HTTP 400 or validation error |
| `GET`       | `/store/order/{orderId}` | `getOrderById`                    | Positive     | Successfully fetches an existing order by ID → HTTP 200 OK                          |
| `GET`       | `/store/order/{orderId}` | `getOrderByNonExistingId`         | Negative     | `orderId = Integer.MAX_VALUE` → API should return HTTP 404 Not Found                |
| `GET`       | `/store/order/{orderId}` | `getOrderWithInvalidIds`          | Negative     | Invalid `orderId` like -1, 0, 11 → should result in HTTP 404                        |
| `DELETE`    | `/store/order/{orderId}` | `deleteAndVerify`                 | Positive     | Deletes existing order → HTTP 200 OK → subsequent GET returns 404                   |
| `GET`       | `/store/order/{orderId}` | `verifyOrderDeleted`              | Positive     | Verifies deleted order is gone → GET returns HTTP 404 Not Found                     |
| `DELETE`    | `/store/order/{orderId}` | `deleteOrderByNonExistingId`      | Negative     | Deletes non-existing order (`id = Integer.MAX_VALUE`) → HTTP 404 expected           |
