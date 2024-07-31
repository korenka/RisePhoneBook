
# API Documentation for Contact Management

## Overview

This API allows for the management of contacts, including operations such as adding, updating, deleting, and searching for contacts. It supports both single and bulk operations.

## Endpoints

### Get All Contacts

- **URL**: `/api/contacts/all`
- **Method**: `GET`
- **Description**: Fetches all contacts using pagination of max 10 contacts per page.
- **Responses**:
  - `200 OK`: Successfully fetched all contacts. Returns a list of contacts.

#### Example Request
```http
GET /api/contacts/all HTTP/1.1
Host: example.com
```

#### Example Response
```json
[
  {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "phone": "1234567890",
    "address": "123 Main St"
  },
  ...
]
```

### Get Contact by ID

- **URL**: `/api/contacts/{id}`
- **Method**: `GET`
- **Description**: Fetches a contact by its ID.
- **Path Parameters**:
  - `id` (Long): The ID of the contact.
- **Responses**:
  - `200 OK`: Successfully fetched the contact. Returns the contact details.
  - `404 NOT FOUND`: Contact not found.

#### Example Request
```http
GET /api/contacts/1 HTTP/1.1
Host: example.com
```

#### Example Response
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "phone": "1234567890",
  "address": "123 Main St"
}
```

### Search Contacts

- **URL**: `/api/contacts/search`
- **Method**: `GET`
- **Description**: Searches for contacts based on first name, last name, or phone number.
- **Query Parameters**:
  - `firstName` (String not required): The first name of the contact.
  - `lastName` (String not required): The last name of the contact.
  - `phone` (String not required): The phone number of the contact.
- **Responses**:
  - `200 OK`: Successfully found matching contacts. Returns a list of contacts.

#### Example Request
```http
GET /api/contacts/search?firstName=John&lastName=Doe&phone=1234567890 HTTP/1.1
Host: example.com
```

#### Example Response
```json
[
  {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "phone": "1234567890",
    "address": "123 Main St"
  }
]
```

### Add Contact

- **URL**: `/api/contacts`
- **Method**: `POST`
- **Description**: Adds a new contact.
- **Request Body**:
  - `Contact` (application/json): The contact details.
	-   `firstName` (String): The first name of the contact. Must contain only English letters.
	-   `lastName` (String): The last name of the contact. Must contain only English letters.
	-   `phone` (String): The phone number of the contact. Must be exactly 10 digits.
	-   `address` (String): The address of the contact.
- **Responses**:
  - `201 CREATED`: Successfully added the contact. Returns the added contact.

#### Example Request
```http
POST /api/contacts HTTP/1.1
Host: example.com
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "phone": "1234567890",
  "address": "123 Main St"
}
```

#### Example Response
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "phone": "1234567890",
  "address": "123 Main St"
}
```

### Bulk Add Contacts

- **URL**: `/api/contacts/bulk`
- **Method**: `POST`
- **Description**: Adds multiple new contacts.
- **Request Body**:
  - List of `Contact` (application/json): A list of contact details.
- **Responses**:
  - `201 CREATED`: Successfully added the contacts. Returns the added contacts.

#### Example Request
```http
POST /api/contacts/bulk HTTP/1.1
Host: example.com
Content-Type: application/json

[
  {
    "firstName": "John",
    "lastName": "Doe",
    "phone": "1234567890",
    "address": "123 Main St"
  },
  {
    "firstName": "Jane",
    "lastName": "Doe",
    "phone": "0987654321",
    "address": "456 Elm St"
  }
]
```

#### Example Response
```json
[
  {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "phone": "1234567890",
    "address": "123 Main St"
  },
  {
    "id": 2,
    "firstName": "Jane",
    "lastName": "Doe",
    "phone": "0987654321",
    "address": "456 Elm St"
  }
]
```

### Update Contact

- **URL**: `/api/contacts/{id}`
- **Method**: `PUT`
- **Description**: Updates an existing contact.
- **Path Parameters**:
  - `id` (Long): The ID of the contact.
- **Request Body**:
  - `Contact` (application/json): The updated contact details.
- **Responses**:
  - `200 OK`: Successfully updated the contact. Returns the updated contact.
  - `404 NOT FOUND`: Contact not found.

#### Example Request
```http
PUT /api/contacts/1 HTTP/1.1
Host: example.com
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Smith",
  "phone": "1234567890",
  "address": "123 Main St"
}
```

#### Example Response
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Smith",
  "phone": "1234567890",
  "address": "123 Main St"
}
```

### Delete Contact

- **URL**: `/api/contacts/{id}`
- **Method**: `DELETE`
- **Description**: Deletes a contact by its ID.
- **Path Parameters**:
  - `id` (Long): The ID of the contact.
- **Responses**:
  - `200 OK`: Successfully deleted the contact. Returns the deleted contact.
  - `404 NOT FOUND`: Contact not found.

#### Example Request
```http
DELETE /api/contacts/1 HTTP/1.1
Host: example.com
```

#### Example Response
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "phone": "1234567890",
  "address": "123 Main St"
}
```

### Bulk Delete Contacts

- **URL**: `/api/contacts/bulk`
- **Method**: `DELETE`
- **Description**: Deletes multiple contacts by their IDs.
- **Request Body**:
  - List of `Long` (application/json): A list of contact IDs.
- **Responses**:
  - `200 OK`: Successfully deleted the contacts. Returns the deleted contacts.

#### Example Request
```http
DELETE /api/contacts/bulk HTTP/1.1
Host: example.com
Content-Type: application/json

[1, 2]
```

#### Example Response
```json
[
  {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "phone": "1234567890",
    "address": "123 Main St"
  },
  {
    "id": 2,
    "firstName": "Jane",
    "lastName": "Doe",
    "phone": "0987654321",
    "address": "456 Elm St"
  }
]
```

## Error Handling

### Common Error Codes

- `400 BAD REQUEST`: Validation errors, such as missing required fields or invalid data.
- `404 NOT FOUND`: The specified contact was not found.
