You will need to design and create your own DAO classes from scratch. 
You should refer to prior mini-project lab examples and course material for guidance.

Please refrain from using a 'try-with-resources' block when connecting to your database. 
The ConnectionUtil provided uses a singleton, and using a try-with-resources will cause issues in the tests. 

# DAO Layer TODO

## Overview
The DAO (Data Access Object) layer is responsible for interacting with the database. You'll implement the CRUD operations for the `Account` and `Message` models here.

---

## Tasks

### AccountDAO
1. Create a class named `AccountDAO`.
2. Add the following methods:
   - **`Account getAccountById(int id)`**: Fetch an account by its ID from the database.
   - **`Account createAccount(Account account)`**: Insert a new account into the database and return the created account with its generated ID.
   - **`List<Account> getAllAccounts()`**: Retrieve all accounts from the database.

### MessageDAO
1. Create a class named `MessageDAO`.
2. Add the following methods:
   - **`Message getMessageById(int id)`**: Fetch a message by its ID from the database.
   - **`Message createMessage(Message message)`**: Insert a new message into the database and return the created message with its generated ID.
   - **`List<Message> getAllMessages()`**: Retrieve all messages from the database.
   - **`List<Message> getMessagesByUserId(int userId)`**: Retrieve all messages posted by a specific user.

---

## Notes
- Use the provided `ConnectionUtil` to establish a connection to the database.
- Avoid using `try-with-resources` when handling database connections, as the `ConnectionUtil` uses a singleton pattern.
- Refer to course material and mini-project examples for implementing CRUD operations.

---

