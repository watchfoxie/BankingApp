package guis;

import db_objs.User;

import javax.swing.*;
import java.awt.*;

/*
    Afișează un dialog personalizat pentru aplicația noastră BankingAppGui
 */

public class BankingAppDialog extends JDialog {
    private User user;
    private BankingAppGui bankingAppGui;
    private JLabel balanceLabel;
    private JLabel enterAmountLabel;
    private JTextField enterAmountField;

    public BankingAppDialog(BankingAppGui bankingAppGui, User user){
        // Setarea dimensiunii
        setSize(400, 400);

        // Adăugarea focus-ului la dialog (nu puteți interacționa cu nimic altceva până când dialogul nu este închis)
        setModal(true);

        // Se încarcă în centrul GUI-ului nostru bancar
        setLocationRelativeTo(bankingAppGui);

        // Atunci când utilizatorul închide fereastra de dialog, aceasta eliberează resursele care sunt utilizate
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Împiedicarea redimensionării dialogului
        setResizable(false);

        // Ne permite să specificăm manual dimensiunea și poziția fiecărei componente
        setLayout(null);

        // Vom avea nevoie de referință la GUI-ul nostru pentru a putea actualiza soldul curent
        this.bankingAppGui = bankingAppGui;

        // Vom avea nevoie de acces la informațiile despre utilizator pentru a face actualizări în baza noastră de date sau pentru a prelua date despre utilizator
        this.user = user;
    }

    public void addCurrentBalanceAndAmount(){
        // Eticheta soldului curent
        balanceLabel = new JLabel("Resurse financiare: MDL" + user.getCurrentBalance());
        balanceLabel.setBounds(0, 10, getWidth() - 20, 20);
        balanceLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(balanceLabel);

        // Introducerea etichetei sumei
        enterAmountLabel = new JLabel("Introdu suma:");
        enterAmountLabel.setBounds(0, 50, getWidth() - 20, 20);
        enterAmountLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        enterAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterAmountLabel);

        // Introducerea sumei în câmp
        enterAmountField = new JTextField();
        enterAmountField.setBounds(15, 80, getWidth() - 50, 40);
        enterAmountField.setFont(new Font("Dialog", Font.BOLD, 20));
        enterAmountField.setHorizontalAlignment(SwingConstants.RIGHT);
        add(enterAmountField);
    }
}
