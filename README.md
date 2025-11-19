# 
## API Automation Framework for Restful-Booker API

### Prerequisites
- Java JDK 17+
- Maven 3.8+
- IntelliJ IDEA (optional, for IDE execution)
- Internet connection to hit https://restful-booker.herokuapp.com

### Setup
1. Unzip the project or clone the repository.
2. Navigate to the project folder.
3. Download dependencies:
   ```bash
   mvn clean install
   
### Assumptions Made

Authentication:

Only PUT and DELETE endpoints require token authentication; POST and GET are public.

Boundary Tests:

Empty payloads result in 400 Bad Request for PUT /booking/{id}

Minimal valid payloads with edge values are used to test boundaries.

Test Order:

Tests are executed in order: POST → PUT → GET to ensure dependencies are met.

Environment:

Framework assumes access to the public Restful-Booker API
.

No local server setup required.

Data Persistence:

Bookings created during tests are not deleted.

Test IDs are stored in static variables to maintain references across dependent tests.
