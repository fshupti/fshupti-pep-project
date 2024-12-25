package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MessageDAO {

    /**
     * Creates a new Message in the database.
     * @param message The Message object to be added.
     * @return The Message object with the generated message_id.
     */
    public Message createMessage(Message message) {
        String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());
    
            int rowsAffected = ps.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);  // Debugging statement
    
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedMessageId = generatedKeys.getInt(1);
                message.setMessage_id(generatedMessageId); // Set the generated ID in message
                System.out.println("Generated message_id: " + generatedMessageId);  // Debugging statement
            } else {
                throw new SQLException("No ID obtained");
            }
            return message;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    

    
    /**
     * Retrieves a Message from the database by its ID.
     * @param message_id The ID of the Message to be retrieved.
     * @return The Message object if found, otherwise null.
     */
    public Message getMessageById(int message_id) {
        String sql = "SELECT * FROM message WHERE message_id = ?";
        Message message = null;
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, message_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                message = new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error while retrieving message with ID " + message_id);
            e.printStackTrace();
            throw new DataAccessException("Error while retrieving message", e);
        }
        return message;
    }

    /**
     * Retrieves all Messages from the database.
     * @return A list of all Messages in the database.
     */
    public List<Message> getAllMessages() {
        String sql = "SELECT * FROM message";
        List<Message> messages = new ArrayList<>();
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Message message = new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch")
                );
                messages.add(message);
            }
        } catch (SQLException e) {
            System.err.println("Error while retrieving all messages");
            e.printStackTrace();
            throw new DataAccessException("Error while retrieving all messages", e);
        }
        return messages;
    }

    /**
     * Updates a Message in the database.
     * @param message The Message object to be updated.
     * @return True if the update is successful, otherwise false.
     */
    public boolean updateMessage(Message message) {
        if (message.getMessage_text() == null || message.getMessage_text().trim().isEmpty()) {
            throw new IllegalArgumentException("Message text cannot be empty");
        }

        String sql = "UPDATE message SET posted_by = ?, message_text = ?, time_posted_epoch = ? WHERE message_id = ?";
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, message.getPosted_by());
            stmt.setString(2, message.getMessage_text());
            stmt.setLong(3, message.getTime_posted_epoch());
            stmt.setInt(4, message.getMessage_id());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error while updating message with ID " + message.getMessage_id());
            e.printStackTrace();
            throw new DataAccessException("Error while updating message", e);
        }
    }

    /**
     * Deletes a Message from the database by its ID.
     * @param message_id The ID of the Message to be deleted.
     * @return True if the deletion is successful, otherwise false.
     */
    public boolean deleteMessage(int message_id) {
        String sql = "DELETE FROM message WHERE message_id = ?";
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, message_id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error while deleting message with ID " + message_id);
            e.printStackTrace();
            throw new DataAccessException("Error while deleting message", e);
        }
    }

    /**
     * Custom exception for data access errors.
     */
    public static class DataAccessException extends RuntimeException {
        public DataAccessException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

