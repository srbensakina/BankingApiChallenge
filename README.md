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
#### Description 
This method retrieves all customers from the repository and maps them to a list of CustomerDto objects containing customer IDs and names.

#### Request
Endpoint: `GET /api/customers/`

curl -i -H 'Accept: application/json' `http://localhost:8080/api/customers/`

#### Response
Success (200 OK): Returns a list of CustomerDto in the response body.

Error (404 Not Found): Returns an error message if no customers are found.


### 3. createBankAccount
 #### Endpoint
    Path: `/api/bank-accounts/create`
    Method: POST
    
#### Description
Creates a new bank account for a customer with an initial deposit amount. It fetches the customer from the repository, creates a new bank account, associates it with the customer, and saves the entities.

#### Request
Type: JSON
Example:
json
Copy code
{
  "customerId": 1,
  "initialDeposit": 1000.0
}

##### Success Response
Status Code: 201 Created
Body: The created BankAccount object.
##### Error Response
Status Code: 400 Bad Request
Body: Error message if the operation fails.

### 4. transferAmounts
#### Endpoint
Path: `/api/bank-accounts/transfer`
Method: POST

#### Description
Transfers amounts between two bank accounts. It fetches the source and destination accounts from the repository, performs the transfer, and records the transfer history.

#### Request
##### Parameters:
amountTransferred (Float): Amount to be transferred.
transferredTo (Long): Account ID to transfer funds to.
transferredFrom (Long): Account ID to transfer funds from.

##### Success Response
Status Code: 200 OK
Body: Success message.

##### Error Response
Status Code: 400 Bad Request
Body: Error message if the operation fails.

### 5. getTransferHistory
#### Endpoint
Path: `/api/bank-accounts/transfer_history/{account_id}`
Method: GET

#### Description
Retrieves the transfer history for a given account. It fetches transfer records from the repository and maps them to a list of TransferHistoryResponse objects containing source account ID, destination account ID, and transferred amount.

#### Request

##### Path Variable:
account_id (Long): Account ID for which the transfer history is requested.

##### Success Response
Status Code: 200 OK
Body: List of TransferHistoryResponse objects.

##### Error Response
Status Code: 404 Not Found
Body: Error message if the transfer history is not found.

### 6. getAllBankAccounts
#### Endpoint
Path: `/api/bank-accounts`
Method: GET

#### Description
Retrieves a list of all bank accounts from the repository and maps them to a list of BankAccountDto objects containing account ID, balance, and customer ID.


#### Success Response
Status Code: 200 OK
Body: List of BankAccountDto objects.
### 5. getBankAccountById
#### Endpoint
Path: `/api/bank-accounts/{bankAccountId}`
Method: GET

#### Description
Retrieves a specific bank account by ID.

#### Request
Path Variable:
bankAccountId (Long): ID of the bank account.
##### Success Response
Status Code: 200 OK
Body: BankAccountDto object.
##### Error Response
Status Code: 404 Not Found
Body: Error message if the bank account is not found.


## Conclusion
The Banking App API provides a simple way to interact with customer-related functionalities. Please refer to the provided commands for testing and ensure to handle response statuses accordingly. If you have any questions or encounter issues, contact the development team at sr.bensakina@gmail.com
