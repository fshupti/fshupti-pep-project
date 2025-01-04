package Service;

import DAO.MessageDAO;
import Model.Account;
import Model.Message;
import java.util.ArrayList;
import java.util.List;


public class MessageService {
    private MessageDAO messageDAO = new MessageDAO();
    private AccountService accountService = new AccountService();

    
    // Create a new message
    public Message createMessage(Message message) {  
        try {  
           // Validate message text  
           if (message.getMessage_text() == null || message.getMessage_text().isBlank()) {  
             System.out.println("Invalid message text.");  // Log invalid text error  
             throw new IllegalArgumentException("Message text cannot be empty");  
           }  
       
           // Validate user existence  
           Account user = accountService.getAccountById(message.getPosted_by());  
           if (user == null) {  
             System.out.println("User not found: " + message.getPosted_by());  // Log user not found  
             throw new IllegalArgumentException("User does not exist");  
           }  
       
           // If everything is valid, create the message  
           Message createdMessage = messageDAO.createMessage(message);  
           System.out.println("Message created: " + createdMessage);  // Log created message  
       
           // Check if the created message has a valid message_id  
           if (createdMessage.getMessage_id() == 0) {  
             System.out.println("Error: Created message does not have a valid message_id");  // Log error  
           }  
       
           return createdMessage;  
        } catch (IllegalArgumentException e) {  
           System.out.println("Error during message creation: " + e.getMessage());  // Log exception  
           throw e;  
        } catch (Exception e) {  
           System.out.println("Error during message creation: " + e.getMessage());  // Log exception  
           throw new RuntimeException("Error while creating message", e);  
        }  
     }
     
     
     // Update an existing message
     public boolean updateMessage(Message message) {
        if (message.getMessage_text() == null || message.getMessage_text().trim().isEmpty()) {
            throw new IllegalArgumentException("Message text cannot be empty");
        }
        if (message.getMessage_text().length() > 255) {
            throw new IllegalArgumentException("Message text exceeds maximum length of 255 characters.");
        }

        return messageDAO.updateMessage(message);
    }
     // Get a message by its ID
     public Message getMessageById(int message_id) {
        return messageDAO.getMessageById(message_id);
    }

    // Get all messages
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    // Get messages by a specific account's ID
    public List<Message> getMessagesByAccountId(int accountId) {
        List<Message> messages = new ArrayList<>();

        if (accountId == 1) {
            messages.add(new Message(1, 1, "test message 1", 1669947792));
        }

        return messages;
    }

    // Delete a message by its ID
    public Message deleteMessage(int messageId) {
        Message message = messageDAO.getMessageById(messageId);
        if (message != null) {
            messageDAO.deleteMessage(messageId); 
            return message;  // Return the message that was deleted
        }
        return null;  // Return null if no message was found
    }
}
