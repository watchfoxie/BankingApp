package db_objs;

import de.svws_nrw.ext.jbcrypt.BCrypt;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyJDBC {
    private static final Logger LOGGER = Logger.getLogger(MyJDBC.class.getName());
    private static String DB_URL;
    private static String DB_USERNAME;
    private static String DB_PASSWORD;

    static {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("src/config.properties"));
            DB_URL = props.getProperty("db.url");
            DB_USERNAME = props.getProperty("db.username");
            DB_PASSWORD = props.getProperty("db.password");
            createDatabaseIfNotExists();
            createTablesIfNotExists();
        } catch (IOException | SQLException e) {
            LOGGER.log(Level.SEVERE, "Eroare la inițializare", e);
        }
    }

    private static void createDatabaseIfNotExists() throws SQLException {
        String dbName = "bankapp";
        String baseUrl = DB_URL.substring(0, DB_URL.lastIndexOf('/') + 1);
        try (Connection conn = DriverManager.getConnection(baseUrl, DB_USERNAME, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SHOW DATABASES LIKE '" + dbName + "'");
            if (!rs.next()) {
                stmt.executeUpdate("CREATE DATABASE " + dbName);
                LOGGER.info("Baza de date " + dbName + " a fost creată.");
            }
        }
    }

    private static void createTablesIfNotExists() throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SHOW TABLES LIKE 'users'");
            if (!rs.next()) {
                stmt.executeUpdate("CREATE TABLE users (" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "username VARCHAR(45) NOT NULL, " +
                        "password VARCHAR(60) NOT NULL, " +
                        "current_balance DECIMAL(10,2) NULL, " +
                        "failed_attempts INT DEFAULT 0, " +
                        "last_failed_attempt TIMESTAMP NULL, " +
                        "PRIMARY KEY (id)" +
                        ")");
                LOGGER.info("Tabela users a fost creată.");
            }
            rs = stmt.executeQuery("SHOW TABLES LIKE 'transactions'");
            if (!rs.next()) {
                stmt.executeUpdate("CREATE TABLE transactions (" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "transaction_amount DECIMAL(10,2) NOT NULL, " +
                        "transaction_date DATETIME NOT NULL, " +
                        "transaction_type VARCHAR(45) NOT NULL, " +
                        "user_id INT NOT NULL, " +
                        "PRIMARY KEY (id), " +
                        "INDEX user_id_idx (user_id ASC) VISIBLE, " +
                        "CONSTRAINT user_id " +
                        "FOREIGN KEY (user_id) " +
                        "REFERENCES users (id) " +
                        "ON DELETE NO ACTION " +
                        "ON UPDATE NO ACTION" +
                        ")");
                LOGGER.info("Tabela transactions a fost creată.");
            }
        }
    }

    public static User validateLogin(String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM users WHERE username = ?"
            );
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int failedAttempts = resultSet.getInt("failed_attempts");
                Timestamp lastFailedAttempt = resultSet.getTimestamp("last_failed_attempt");
                if (failedAttempts >= 5 && lastFailedAttempt != null) {
                    long timeDiff = System.currentTimeMillis() - lastFailedAttempt.getTime();
                    if (timeDiff < 15 * 60 * 1000) {
                        JOptionPane.showMessageDialog(null, "Cont blocat. Încercați mai târziu.");
                        return null;
                    } else {
                        resetFailedAttempts(connection, resultSet.getInt("id"));
                    }
                }
                String hashedPassword = resultSet.getString("password");
                if (BCrypt.checkpw(password, hashedPassword)) {
                    resetFailedAttempts(connection, resultSet.getInt("id"));
                    int userId = resultSet.getInt("id");
                    BigDecimal currentBalance = resultSet.getBigDecimal("current_balance");
                    LOGGER.info("Autentificare reușită pentru utilizatorul: " + username);
                    return new User(userId, username, hashedPassword, currentBalance);
                } else {
                    incrementFailedAttempts(connection, resultSet.getInt("id"));
                    LOGGER.warning("Încercare de autentificare eșuată pentru utilizatorul: " + username);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Eroare SQL la autentificare", e);
        }
        return null;
    }

    private static void incrementFailedAttempts(Connection connection, int userId) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "UPDATE users SET failed_attempts = failed_attempts + 1, last_failed_attempt = NOW() WHERE id = ?"
        );
        ps.setInt(1, userId);
        ps.executeUpdate();
    }

    private static void resetFailedAttempts(Connection connection, int userId) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "UPDATE users SET failed_attempts = 0, last_failed_attempt = NULL WHERE id = ?"
        );
        ps.setInt(1, userId);
        ps.executeUpdate();
    }

    public static boolean register(String username, String password) {
        try {
            if (!checkUser(username)) {
                Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO users(username, password, current_balance) VALUES(?, ?, ?)"
                );
                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, hashedPassword);
                preparedStatement.setBigDecimal(3, new BigDecimal(0));
                preparedStatement.executeUpdate();
                LOGGER.info("Utilizator înregistrat: " + username);
                return true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Eroare SQL la înregistrare", e);
        }
        return false;
    }

    private static boolean checkUser(String username) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM users WHERE username = ?"
            );
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Eroare SQL la verificarea utilizatorului", e);
        }
        return false;
    }

    public static boolean addTransactionToDatabase(Transaction transaction) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            PreparedStatement insertTransaction = connection.prepareStatement(
                    "INSERT transactions(user_id, transaction_type, transaction_amount, transaction_date) VALUES(?, ?, ?, NOW())"
            );
            insertTransaction.setInt(1, transaction.getUserId());
            insertTransaction.setString(2, transaction.getTransactionType());
            insertTransaction.setBigDecimal(3, transaction.getTransactionAmount());
            insertTransaction.executeUpdate();
            LOGGER.info("Tranzacție adăugată pentru user_id: " + transaction.getUserId());
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Eroare SQL la adăugarea tranzacției", e);
        }
        return false;
    }

    public static boolean updateCurrentBalance(User user) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            PreparedStatement updateBalance = connection.prepareStatement(
                    "UPDATE users SET current_balance = ? WHERE id = ?"
            );
            updateBalance.setBigDecimal(1, user.getCurrentBalance());
            updateBalance.setInt(2, user.getId());
            updateBalance.executeUpdate();
            LOGGER.info("Sold actualizat pentru user_id: " + user.getId());
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Eroare SQL la actualizarea soldului", e);
        }
        return false;
    }

    public static boolean transfer(User user, String transferredUsername, float transferAmount) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            connection.setAutoCommit(false);
            PreparedStatement queryUser = connection.prepareStatement(
                    "SELECT * FROM users WHERE username = ? FOR UPDATE"
            );
            queryUser.setString(1, transferredUsername);
            ResultSet resultSet = queryUser.executeQuery();
            if (resultSet.next()) {
                User transferredUser = new User(
                        resultSet.getInt("id"),
                        transferredUsername,
                        resultSet.getString("password"),
                        resultSet.getBigDecimal("current_balance")
                );
                Transaction transferTransaction = new Transaction(
                        user.getId(),
                        "Transfer",
                        new BigDecimal(-transferAmount),
                        null
                );
                Transaction receivedTransaction = new Transaction(
                        transferredUser.getId(),
                        "Transfer",
                        new BigDecimal(transferAmount),
                        null
                );
                transferredUser.setCurrentBalance(transferredUser.getCurrentBalance().add(BigDecimal.valueOf(transferAmount)));
                updateCurrentBalance(transferredUser);
                user.setCurrentBalance(user.getCurrentBalance().subtract(BigDecimal.valueOf(transferAmount)));
                updateCurrentBalance(user);
                addTransactionToDatabase(transferTransaction);
                addTransactionToDatabase(receivedTransaction);
                connection.commit();
                LOGGER.info("Transfer efectuat de la " + user.getUsername() + " la " + transferredUsername);
                return true;
            }
            connection.rollback();
            return false;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Eroare SQL la transfer", e);
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    LOGGER.log(Level.SEVERE, "Eroare la rollback", ex);
                }
            }
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    LOGGER.log(Level.SEVERE, "Eroare la resetare auto-commit", e);
                }
            }
        }
        return false;
    }

    public static ArrayList<Transaction> getPastTransaction(User user) {
        ArrayList<Transaction> pastTransactions = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            PreparedStatement selectAllTransaction = connection.prepareStatement(
                    "SELECT * FROM transactions WHERE user_id = ?"
            );
            selectAllTransaction.setInt(1, user.getId());
            ResultSet resultSet = selectAllTransaction.executeQuery();
            while (resultSet.next()) {
                Transaction transaction = new Transaction(
                        user.getId(),
                        resultSet.getString("transaction_type"),
                        resultSet.getBigDecimal("transaction_amount"),
                        resultSet.getDate("transaction_date")
                );
                pastTransactions.add(transaction);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Eroare SQL la obținerea tranzacțiilor anterioare", e);
        }
        return pastTransactions;
    }
}