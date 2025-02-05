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

    public Transaction(int userId, String transactionType, BigDecimal transactionAmount, Date transactionDate){
        this.userId = userId;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
    }

    // De completat 1
}
