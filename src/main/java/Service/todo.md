You will need to design and create your own Service classes from scratch.
You should refer to prior mini-project lab examples and course material for guidance.

# Service Layer TODO

## Overview
The Service layer contains business logic and acts as a bridge between the DAO layer and the Controller layer. You will implement logic to validate data and enforce application rules here.

---

## Tasks

### AccountService
1. Create a class named `AccountService`.
2. Add the following methods:
   - **`Account createAccount(String username, String password)`**:
     - Validate that the username is unique and not blank.
     - Validate that the password is longer than 4 characters.
     - Use the `AccountDAO` to save the account.
     - Return the created account or throw an exception if validation fails.
   - **`Account getAccountById(int id)`**:
     - Use `AccountDAO` to fetch the account.
     - Return the account or throw an exception if the account does not exist.

### MessageService
1. Create a class named `MessageService`.
2. Add the following methods:
   - **`Message createMessage(int postedBy, String messageText, long timePostedEpoch)`**:
     - Validate that the `messageText` is not blank and is less than 255 characters.
     - Use the `MessageDAO` to save the message.
     - Return the created message or throw an exception if validation fails.
   - **`List<Message> getMessagesByUserId(int userId)`**:
     - Use the `MessageDAO` to fetch all messages by a specific user.
   - **`Message getMessageById(int id)`**:
     - Use `MessageDAO` to fetch the message.
     - Return the message or throw an exception if it does not exist.

---

## Notes
- Ensure all input data is properly validated before interacting with the DAO layer.
- Leverage exceptions to handle errors and return meaningful messages to the Controller.
- Refer to course material for implementing validation and business logic.

---

