# Banking App API
This document provides details for the REST API of the Banking App. The API is designed to manage customer-related operations, such as retrieving account balances and listing all customers.

## Build and Run
To build and run the application, you can use Maven. Open a terminal and navigate to the project directory:

cd /path/to/your/project

Then, execute the following commands:


### Build the application
mvn clean install

### Run the application
java -jar target/banking-api-<version>.jar

Replace <version> with the actual version of your application.

## REST API
### 1. Get Balances for a Customer
#### Description 
This method retrieves the balances associated with the specified customer ID. It fetches the customer from the repository and maps their associated bank accounts to a list of BalanceResponse objects containing account IDs and balances.

#### Request
Endpoint: `GET /api/customers/{customerId}/balances`


curl -i -H 'Accept: application/json' `http://localhost:8080/api/customers/{customerId}/balances`
#### Response
Success (200 OK): Returns a list of BalanceResponse in the response body.

Error (404 Not Found): Returns an error message if the customer or balances are not found.

### 2. Get All Customers
#### Request
Endpoint: `GET /api/customers/`

curl -i -H 'Accept: application/json' `http://localhost:8080/api/customers/`

#### Response
Success (200 OK): Returns a list of CustomerDto in the response body.

Error (404 Not Found): Returns an error message if no customers are found.

## Conclusion
The Banking App API provides a simple and secure way to interact with customer-related functionalities. Please refer to the provided commands for testing and ensure to handle response statuses accordingly. If you have any questions or encounter issues, contact the development team at support@bankingapp.com.
