package db_objs;

/*
    Clasa JDBC este utilizată pentru a interacționa cu baza de date MySQL pentru a efectua operații
    cum ar fi preluarea și actualizarea bazei de date.
 */

import java.math.BigDecimal;
import java.sql.*;

public class MyJDBC {
    // Configurarea bazei de date
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/bankapp";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "mt67521124";

    // Dacă este valid, returnează un obiect cu informațiile utilizatorului
    public static User validateLogin(String username, String password){
        try{
            // Stabilirea unei conexiuni la baza de date utilizând configurații
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            // Crearea unei interogări SQL
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM users WHERE username = ? AND password = ?"
            );

            // Înlocuim ? cu valorile corespunzătoare
            // Indicele parametrului care se referă la iterația ? astfel încât 1 este primul ? și 2 este al doilea ?
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            // Executarea interogării și stocarea într-un set de rezultate
            ResultSet resultSet = preparedStatement.executeQuery();

            // next() returnează true sau false
            // true - interogarea a returnat date și setul de rezultate indică acum primul rând
            // false - interogarea nu a returnat date și setul de rezultate este egal cu null
            if(resultSet.next()){
                // Succes
                // get id
                int userId = resultSet.getInt("id");

                // Obținerea soldului curent
                BigDecimal currentBalance = resultSet.getBigDecimal("current_balance");

                // Returnează obiectul utilizator
                return new User(userId, username, password, currentBalance);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        // Utilizator invalid
        return null;
    }

    // Înregistrarea unui nou utilizator în baza de date
    // true - înregistrarea reușește, false - înregistrarea eșuează
    public static boolean register(String username, String password){
        try{
            // Mai întâi va trebui să verificăm dacă numele de utilizator a fost deja luat
            if(!checkUser(username)){
                Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO users(username, password, current_balance)" +
                                "VALUES(?, ?, ?)"
                );

                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setBigDecimal(3, new BigDecimal(0));

                preparedStatement.executeUpdate();
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    // Verifică dacă numele de utilizator există deja în baza de date
    // true - utilizatorul există, false - utilizatorul nu există
    private static boolean checkUser(String username){
        try{
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM users WHERE username = ?"
            );

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Acest lucru înseamnă că interogarea nu a returnat date, ceea ce înseamnă că numele de utilizator este disponibil
            if(!resultSet.next()){
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    // true - actualizarea reușită a bazei de date, false - nereușită
    public static boolean addTransactionToDatabase(Transaction transaction){
        try{
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            PreparedStatement insertTransaction = connection.prepareStatement(
                    "INSERT transactions(user_id, transaction_type, transaction_amount, transaction_date) " +
                            "VALUES(?, ?, ?, NOW())"
            );

            insertTransaction.setInt(1, transaction.getUserId());
            insertTransaction.setString(2, transaction.getTransactionType());
            insertTransaction.setBigDecimal(3, transaction.getTransactionAmount());
            return true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    // true - actualizarea soldului reușită, false - actualizarea soldului eșuată
    public static boolean updateCurrentBalance(User user){
        try{
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            PreparedStatement updateBalance = connection.prepareStatement(
                    "UPDATE users SET current_balance = ? WHERE id = ?"
            );

            updateBalance.setBigDecimal(1, user.getCurrentBalance());
            updateBalance.setInt(2, user.getId());
            updateBalance.executeUpdate();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
