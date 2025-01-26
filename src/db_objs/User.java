package db_objs;

/*
    Entitate utilizator care este utilizată pentru a stoca informații despre utilizator
    (de exemplu, id, nume de utilizator, parolă și sold curent)
 */

import java.math.BigDecimal;
import java.math.RoundingMode;

public class User {
    private final int id;
    private final String username;
    private final String password;
    private BigDecimal currentBalance;

    public User(int id, String username, String password, BigDecimal currentBalance) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.currentBalance = currentBalance;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal newBalance){
        // Stochează noua valoare la a doua zecimală
        currentBalance = newBalance.setScale(2, RoundingMode.FLOOR);
    }
}
