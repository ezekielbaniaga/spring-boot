# A repo for Spring Boot demo and playground

A Spring Boot demo and playground repository that also serves as a production-ready
REST API for expense management, featuring JWT authentication, PostgreSQL,
Flyway migrations, and Docker support.

## Tech Stack

- **Framework**: Spring Boot 4.0.3
- **Language**: Java 17
- **Database**: PostgreSQL
- **Authentication**: Spring Security + JWT
- **Database Migration**: Flyway
- **Code Generation**: Lombok
- **UUID**: Java UUID Generator (v7)
- **Containerization**: Docker & Docker Compose

## Features

- **Expense Management**: Create, read, update, delete, and archive expenses
- **API Versioning**: Support for multiple API versions (v1.0, v1.1)
- **JWT Authentication**: Secure token-based authentication
- **Database Migrations**: Version-controlled schema evolution with Flyway
- **Scheduled Jobs**: Automated expense cleanup tasks
- **Validation**: Input validation using Jakarta Validation
- **Docker Support**: Containerized deployment with Docker Compose

## Prerequisites

- Java 17 or higher
- Maven 3.8+
- PostgreSQL 18+ (or use Docker)
- Docker & Docker Compose (optional, for containerized deployment)

**Note**: The Docker image uses Eclipse Temurin 25, but the project is compiled for Java 17.

## Getting Started

### Running Locally

1. **Start PostgreSQL Database**

   Using Docker:
   ```bash
   cd spring-boot-maven-sample/docker_compose
   ./run_compose.sh
   ```

   Or start PostgreSQL manually and update the connection details in `application.yml`.

2. **Build the Application**

   ```bash
   cd spring-boot-maven-sample/backend
   mvn clean package
   ```

3. **Run the Application**

   ```bash
   mvn spring-boot:run
   ```

   The application will start on `http://localhost:8080`.

### Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `ADMIN_USERNAME` | Admin username | `admin` |
| `ADMIN_PASSWORD` | Admin password | (required) |
| `JWT_SECRET` | JWT signing secret | (required) |
| `POSTGRES_USER` | Database username | `appuser` |
| `POSTGRES_PASSWORD` | Database password | `apppwd$1234` |
| `POSTGRES_DB` | Database name | `appdb` |

## API Endpoints

### Authentication

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/login` | Authenticate and get JWT token (public) |

### Expenses (v1.0)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/expense` | Get all expenses |
| POST | `/api/expense` | Create a new expense |
| GET | `/api/expense/{uniqueId}` | Get expense by ID |
| PUT | `/api/expense/{uniqueId}` | Update expense |
| DELETE | `/api/expense/{uniqueId}` | Delete expense |
| PATCH | `/api/expense/{uniqueId}/description` | Update expense description |
| GET | `/api/expense/categories` | Get all supported categories |
| GET | `/api/expense/archived` | Get archived expenses |

### Expenses (v1.1)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/expense` | Get all expenses (enhanced response) |

### Security

All endpoints except `/auth/login` require JWT authentication via the `Authorization: Bearer <token>` header.

### API Versioning

API versions are specified using the `X-API-Version` header. Supported versions:

| Header Value | Description |
|--------------|-------------|
| `X-API-Version: 1.0` | Default version |
| `X-API-Version: 1.1` | Enhanced response format |

Example:
```bash
curl -X GET http://localhost:8080/api/expense \
  -H "Authorization: Bearer <your-token>" \
  -H "X-API-Version: 1.1"
```

## API Usage Examples

### Login

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "yourpassword"}'
```

### Create Expense

```bash
curl -X POST http://localhost:8080/api/expense \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your-token>" \
  -d '{
    "description": "Office Supplies",
    "amount": 99.99,
    "category": "OFFICE",
    "expenseDate": "2026-07-15"
  }'
```

### Get All Expenses

```bash
curl -X GET http://localhost:8080/api/expense \
  -H "Authorization: Bearer <your-token>"
```

## Project Structure

```
spring-boot-maven-sample/
├── backend/                    # Spring Boot application
│   ├── src/main/java/          # Java source code
│   │   ├── auth/               # Authentication related code
│   │   ├── config/             # Configuration classes
│   │   ├── expense/            # Expense management logic
│   │   ├── user/               # User entity and repository
│   │   └── common/             # Common utilities and exceptions
│   ├── src/main/resources/
│   │   ├── db/migration/       # Flyway migration scripts
│   │   └── application.yml     # Application configuration
│   └── Dockerfile              # Docker build file
└── docker_compose/             # Docker Compose configuration
    ├── docker-compose.yml      # Multi-container setup
    └── .env                    # Environment variables
```

## Database Migrations

The project uses Flyway for database schema management. Migrations are located in:

```
src/main/resources/db/migration/
```

Available migrations:
- `V1__initial_tables.sql` - Initial expense table
- `V2__add_archive_to_expense.sql` - Add archive flag
- `V3__add_index_for_expense_cleanup.sql` - Add cleanup index
- `V4__versioning_expense.sql` - Add version and last_modified columns
- `V5__users_table.sql` - Create users table for authentication
- `V6__fix_categories_in_expense.sql` - Fix category constraints

## Docker Deployment

### Build Docker Image

```bash
cd spring-boot-maven-sample/backend
mvn clean package
./build_docker_image.sh
```

### Run with Docker Compose

```bash
cd spring-boot-maven-sample/docker_compose
./run_compose.sh
```

### Stop Docker Compose

```bash
cd spring-boot-maven-sample/docker_compose
./stop_compose.sh
```

## Configuration

### Application Properties

Key configuration in `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/appdb
    username: devuser
    password: devpwd
  jpa:
    hibernate:
      ddl-auto: none
  flyway:
    enabled: true

app:
  admin:
    username: admin
    password: ${ADMIN_PASSWORD}
  jwt:
    secret: ${JWT_SECRET}
    expiration: 15m
```

## License

This project is for demonstration purposes and is licensed under the MIT License.
