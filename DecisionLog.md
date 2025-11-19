# 
## Decision Log

### 1. Scope Interpretation & Timeboxing
- Focused on testing **3 main endpoints** of the Restful-Booker API: `POST /booking`, `PUT /booking/{id}`, `GET /booking/{id}`.
- Timeboxed to ensure **positive, negative, and boundary tests** for each endpoint within the available timeframe.
- DELETE endpoint excluded due to time constraints, but can be added later.

### 2. Test Selection & Coverage Rationale
- **POST /booking:** Valid booking creation (positive), invalid payload (negative), long name (boundary).
- **PUT /booking/{id}:** Valid update (positive), invalid ID (negative), empty payload (boundary), minimal valid payload (boundary).
- **GET /booking/{id}:** Valid booking retrieval (positive), invalid ID (negative), zero ID (boundary).
- Ensures coverage of **happy paths, unhappy paths, and edge cases**.

### 3. Stability & Data Strategies
- **Data-driven testing:** JSON file (`data/bookings.json`) for parameterization.
- **State management:** Created booking IDs stored in static variables for dependent tests.
- **Authentication handling:** Token generation for PUT/DELETE endpoints to prevent 403 errors.
- Designed tests to be **idempotent** when possible; created bookings are not deleted.

### 4. Project Structure Decisions
- **`clients/`**: Low-level Rest-Assured API calls.
- **`services/`**: Business logic encapsulating clients and authentication.
- **`tests/`**: TestNG classes referencing services only.
- **`utils/`**: JSON utility functions for reading test data.
- **Maven**: Dependency management and build lifecycle.
- **Surefire plugin**: Test reports in `target/surefire-reports/`.

### 5. Next Steps if Given More Time
- Add **DELETE /booking/{id} tests** for full CRUD coverage.
- Integrate **JSON Schema validation** for response contracts.
- Enhance reporting using **Allure or ExtentReports**.
- Set up **CI/CD pipeline** to automatically run tests on push/PR.
- Implement **test data cleanup** to avoid residual bookings on the API.
