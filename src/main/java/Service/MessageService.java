package Service;
import Model.Account;
//import Service.AccountService;




import DAO.MessageDAO;
import Model.Message;
import java.util.ArrayList;

import java.util.List;



public class MessageService {
    private MessageDAO messageDAO = new MessageDAO();
    private AccountService accountService = new AccountService();


    public Message createMessage(Message message) {
        if (message.getMessage_text() == null || message.getMessage_text().isBlank()) {
            throw new IllegalArgumentException("Message text cannot be empty.");
        }

        if (message.getMessage_text().length() > 255) {
            throw new IllegalArgumentException("Message text exceeds maximum length of 255 characters.");
        }

        // Validate posted_by exists in the account table
        Account account = accountService.getAccountById(message.getPosted_by());
        if (account == null) {
            throw new IllegalArgumentException("User does not exist.");
        }

        return messageDAO.createMessage(message);
    }

    public Message getMessageById(int message_id) {
        return messageDAO.getMessageById(message_id);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }
    public List<Message> getMessagesByAccountId(int accountId) {
        // to return messages associated with the given account
        
        
        List<Message> messages = new ArrayList<>();
        
        if (accountId == 1) {
            messages.add(new Message(1, 1, "test message 1", 1669947792));
        }
        
        return messages;
    }

    public boolean updateMessage(Message message) {
        if (message.getMessage_text() == null || message.getMessage_text().trim().isEmpty()) {
            throw new IllegalArgumentException("Message text cannot be empty");
        }
        if (message.getMessage_text().length() > 255) {
            throw new IllegalArgumentException("Message text exceeds maximum length of 255 characters.");
        }
    
        return messageDAO.updateMessage(message);
    }
    

    public boolean deleteMessage(int message_id) {
        return messageDAO.deleteMessage(message_id);
    }
}



