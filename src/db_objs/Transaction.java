package db_objs;

/*
    Entitatea de tranzacție utilizată pentru a stoca datele tranzacției
 */

import java.math.BigDecimal;
import java.sql.Date;

public class Transaction {
    private final int userId;
    private final String transactionType;
    private final BigDecimal transactionAmount;
    private final Date transactionDate;
    private final String senderUsername;
    private final String recipientUsername;

    public Transaction(int userId, String transactionType, BigDecimal transactionAmount, Date transactionDate) {
        this(userId, transactionType, transactionAmount, transactionDate, null, null);
    }

    public Transaction(int userId, String transactionType, BigDecimal transactionAmount, Date transactionDate,
                       String senderUsername, String recipientUsername) {
        this.userId = userId;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
        this.senderUsername = senderUsername;
        this.recipientUsername = recipientUsername;
    }

    public int getUserId() {
        return userId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public String getRecipientUsername() {
        return recipientUsername;
    }
}