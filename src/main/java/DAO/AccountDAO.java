package DAO;

import DAO.MessageDAO;
import Model.Message;


import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    /**
     * Creates a new Account in the database.
     * @param account The Account object to be added.
     * @return The Account object with the generated account_id.
     */
    public Account createAccount(Account account) {
        String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, account.getUsername());
            stmt.setString(2, account.getPassword());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        account.setAccount_id(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    /**
     * Retrieves an Account from the database by its ID.
     * @param account_id The ID of the Account to be retrieved.
     * @return The Account object if found, otherwise null.
     */
    public Account getAccountById(int account_id) {
        String sql = "SELECT * FROM account WHERE account_id = ?";
        Account account = null;
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, account_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                account = new Account(
                        rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    /**
     * Retrieves all Accounts from the database.
     * @return A list of all Accounts in the database.
     */
    public List<Account> getAllAccounts() {
        String sql = "SELECT * FROM account";
        List<Account> accounts = new ArrayList<>();
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Account account = new Account(
                        rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password")
                );
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    /**
     * Updates an Account in the database.
     * @param account The Account object to be updated.
     * @return True if the update is successful, otherwise false.
     */
    public boolean updateAccount(Account account) {
        String sql = "UPDATE account SET username = ?, password = ? WHERE account_id = ?";
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, account.getUsername());
            stmt.setString(2, account.getPassword());
            stmt.setInt(3, account.getAccount_id());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes an Account from the database by its ID.
     * @param account_id The ID of the Account to be deleted.
     * @return True if the deletion is successful, otherwise false.
     */
    public boolean deleteAccount(int account_id) {
        String sql = "DELETE FROM account WHERE account_id = ?";
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, account_id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
