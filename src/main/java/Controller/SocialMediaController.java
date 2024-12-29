package Controller;

import Service.AccountService;
import Service.MessageService;
import Model.Account;
import Model.Message;
import io.javalin.Javalin;
import io.javalin.http.Handler;
//import io.javalin.http.Context;
import java.util.List;
import java.util.ArrayList;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    
    private AccountService accountService = new AccountService();
    private MessageService messageService = new MessageService();

    // Start API and map routes
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        // Account endpoints
        app.post("/accounts", createAccountHandler);
        app.get("/accounts/{id}", getAccountByIdHandler);
        app.get("/accounts", getAllAccountsHandler);
        app.put("/accounts/{id}", updateAccountHandler);
        app.delete("/accounts/{id}", deleteAccountHandler);
        app.get("/accounts/{id}/messages", getMessagesByAccountIdHandler);
    


        // Message endpoints
        app.post("/messages", createMessageHandler);
        app.get("/messages/{id}", getMessageByIdHandler);
        app.get("/messages", getAllMessagesHandler);
        app.patch("/messages/{id}", updateMessageHandler); // Changed to PATCH
        app.delete("/messages/{id}", deleteMessageHandler);

        app.post("/login", loginHandler);
        
     


        return app;
    }

    

   // POST /accounts - Create a new account
private Handler createAccountHandler = ctx -> {
    Account account = ctx.bodyAsClass(Account.class); // Assuming JSON input
    Account createdAccount = accountService.createAccount(account);

    // Send back the created account and set the status to 201 Created
    ctx.status(201).json(createdAccount);
};

// GET /accounts/{id} - Retrieve account by ID
private Handler getAccountByIdHandler = ctx -> {
    int accountId = Integer.parseInt(ctx.pathParam("id"));
    Account account = accountService.getAccountById(accountId);

    if (account != null) {
        ctx.status(200).json(account);
    } else {
        ctx.status(404).result("Account not found");
    }
};

    private Handler getAllAccountsHandler = ctx -> {
        ctx.json(accountService.getAllAccounts());
    };

    private Handler updateAccountHandler = ctx -> {
        int account_id = Integer.parseInt(ctx.pathParam("id"));
        Account account = ctx.bodyAsClass(Account.class);
        account.setAccount_id(account_id);
        boolean updated = accountService.updateAccount(account);
        if (updated) {
            ctx.status(200).json(account);
        } else {
            ctx.status(404).result("Account not found");
        }
    };

    private Handler deleteAccountHandler = ctx -> {
        int account_id = Integer.parseInt(ctx.pathParam("id"));
        boolean deleted = accountService.deleteAccount(account_id);
        if (deleted) {
            ctx.status(200).result("Account deleted successfully");
        } else {
            ctx.status(404).result("Account not found");
        }
    };

    // Handlers for Message routes
    private Handler createMessageHandler = ctx -> {
        try {
            Message message = ctx.bodyAsClass(Message.class);
            Message createdMessage = messageService.createMessage(message);
            ctx.status(201).json(createdMessage);
        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        }
    };

    private Handler getMessageByIdHandler = ctx -> {
        int message_id = Integer.parseInt(ctx.pathParam("id"));
        Message message = messageService.getMessageById(message_id);
        if (message != null) {
            ctx.json(message);  // Return the message if found
        } else {
            ctx.status(200).result("");  // Return 200 with empty body if not found
        }
    };
    

    private Handler getAllMessagesHandler = ctx -> {
        // Fetch all messages from the message service
        var messages = messageService.getAllMessages();
    
        // If there are messages, return them
        if (messages != null) {
            ctx.json(messages);
        } else {
            // If no messages are found, return a simple 404 response
            ctx.status(404).result("No messages found");
        }
    };

    private Handler updateMessageHandler = ctx -> {
        int message_id = Integer.parseInt(ctx.pathParam("id"));
        
        // Retrieve the existing message from the database
        Message existingMessage = messageService.getMessageById(message_id);
        if (existingMessage == null) {
            ctx.status(400).result("");  // Return empty body with 400 status if message is not found
            return;
        }
        
        // Parse the new data from the request body
        Message newMessageData = ctx.bodyAsClass(Message.class);
        String newMessageText = newMessageData.getMessage_text();
        
        // Validate the message text
        if (newMessageText == null || newMessageText.isEmpty() || newMessageText.length() > 255) {
            ctx.status(400).result("");  // Return empty body with 400 status for invalid message text
            return;
        }
        
        // Update the message text
        existingMessage.setMessage_text(newMessageText);
        
        // Persist the updated message in the database
        boolean updated = messageService.updateMessage(existingMessage);
        if (updated) {
            ctx.status(200).json(existingMessage);
        } else {
            ctx.status(500).result("Message update failed");
        }
    };
    
    
    private Handler deleteMessageHandler = ctx -> {
        int message_id = Integer.parseInt(ctx.pathParam("id"));
        Message deletedMessage = messageService.deleteMessage(message_id);  // Expecting Message return type
    
        if (deletedMessage != null) {
            ctx.status(200).json(deletedMessage);  // Return 200 OK and the deleted message in response
        } else {
            ctx.status(200).result("");  // Return 200 OK with empty body if message is not found
        }
    };
    
    


/**public void deleteMessageById(Context ctx) {
    int messageId = Integer.parseInt(ctx.pathParam("messageId"));
    boolean success = messageService.deleteMessage(messageId);
    
    if (success) {
        // Message deleted successfully, return the deleted message
        Message deletedMessage = messageService.getMessageById(messageId);
        ctx.status(200).json(deletedMessage);
    } else {
        // Message not found, return 404
        ctx.status(404).result("Message not found");
    }
} **/
// Handler for retrieving all messages associated with a specific account

private Handler getMessagesByAccountIdHandler = ctx -> {
    int account_id = Integer.parseInt(ctx.pathParam("id"));
    List<Message> messages = messageService.getMessagesByAccountId(account_id);

    if (messages != null) {
        // If no messages are found, return an empty list with a 200 OK status
        if (messages.isEmpty()) {
            ctx.status(200).json(messages); // Return empty list with 200 OK
        } else {
            ctx.json(messages); // Return messages if found
        }
    } else {
        // In case of an issue with fetching messages (e.g., null), still return a 200 OK with an empty list
        ctx.status(200).json(new ArrayList<Message>()); // Return empty list with 200 OK
    }
};

// POST /login - Login with username and password
private Handler loginHandler = ctx -> {
    // Parse the request body into an Account object
    Account loginRequest = ctx.bodyAsClass(Account.class);

    // Validate the username and password
    Account account = accountService.login(loginRequest.getUsername(), loginRequest.getPassword());

    if (account != null) {
        // Login successful, return 200 OK with the account details as JSON
        ctx.status(200).json(account);
    } else {
        // Login failed, return 401 Unauthorized with an empty body
        ctx.status(401).result("");
    }
};


}

