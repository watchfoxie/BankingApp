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
}
