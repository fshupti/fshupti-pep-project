package Controller;

import Service.AccountService;
import Service.MessageService;
import Model.Account;
import Model.Message;
import io.javalin.Javalin;
import io.javalin.http.Handler;
//import io.javalin.http.Context;

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

        // Message endpoints
        app.post("/messages", createMessageHandler);
        app.get("/messages/{id}", getMessageByIdHandler);
        app.get("/messages", getAllMessagesHandler);
        app.put("/messages/{id}", updateMessageHandler);
        app.delete("/messages/{id}", deleteMessageHandler);

        return app;
    }

    // Handlers for Account routes
    private Handler createAccountHandler = ctx -> {
        Account account = ctx.bodyAsClass(Account.class);
        Account createdAccount = accountService.createAccount(account);
        ctx.status(201).json(createdAccount);
    };

    private Handler getAccountByIdHandler = ctx -> {
        int account_id = Integer.parseInt(ctx.pathParam("id"));
        Account account = accountService.getAccountById(account_id);
        if (account != null) {
            ctx.json(account);
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
            ctx.status(201).json(createdMessage);  // Successful creation
        } catch (IllegalArgumentException e) {
            // If the exception is thrown by MessageService, return 400 with the exception message
            ctx.status(400).result(e.getMessage());  
        }
    };
    
    private Handler getMessageByIdHandler = ctx -> {
        int message_id = Integer.parseInt(ctx.pathParam("id"));
        Message message = messageService.getMessageById(message_id);
        if (message != null) {
            ctx.json(message);  // Return the message if found
        } else {
            ctx.status(200).result("");  // Return a 200 status with an empty body if not found
        }
    };
    private Handler getAllMessagesHandler = ctx -> {
        ctx.json(messageService.getAllMessages());
    };

    private Handler updateMessageHandler = ctx -> {
        int message_id = Integer.parseInt(ctx.pathParam("id"));
        Message message = ctx.bodyAsClass(Message.class);
        message.setMessage_id(message_id);
        boolean updated = messageService.updateMessage(message);
        if (updated) {
            ctx.status(200).json(message);
        } else {
            ctx.status(404).result("Message not found");
        }
    };

    private Handler deleteMessageHandler = ctx -> {
        int message_id = Integer.parseInt(ctx.pathParam("id"));
        boolean deleted = messageService.deleteMessage(message_id);
        if (deleted) {
            ctx.status(200).result("Message deleted successfully");
        } else {
            ctx.status(404).result("Message not found");
        }
    };
} 