# Features:
## Customer Features:
- Create a loan request by specifying the amount and term.

- View their own loans.

- Add repayments towards their loans.

## Admin Features:
- Approve pending loan requests.

# Design Patterns

**Service Layer Pattern**: Business logic is encapsulated in service classes.

**Repository Pattern**: Data access logic is encapsulated in repository interfaces.

# Technologies Used

**Spring Boot**: Framework to create the application.

**Spring Data JPA**: For database interaction.

**Spring Security**: For authentication and authorization.

**H2 Database**: In-memory database.

**JUnit & Mockito**: For unit and integration tests.

# API Endpoints
- POST /api/loans/new: Create a new loan request.
- GET /api/loans: Retrieve all loans for a customer.
- POST /api/loans/{id}/apporve: Approve a customer loan (Admin Only).
- POST /api/repayment: Add a repayment for the loan.

# Installation
## Git Repo Clone
git clone https://github.com/Abhishek-lodha/AspireCustomerLoan.git
cd AspireCustomerLoan

## mvn clean install

## mvn spring-boot:run
