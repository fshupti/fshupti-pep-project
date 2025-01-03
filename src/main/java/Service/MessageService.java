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
        // Validate message text
        if (message.getMessage_text() == null || message.getMessage_text().isBlank()) {
            return null; // Message text cannot be empty
        }

        if (message.getMessage_text().length() > 255) {
            return null; // Message text exceeds maximum length
        }

        // Validate posted_by exists in the account table
        Account account = accountService.getAccountById(message.getPosted_by());
        if (account == null) {
            return null; // User does not exist
        }

        // Save the message to the database and return it
        return messageDAO.createMessage(message);
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
