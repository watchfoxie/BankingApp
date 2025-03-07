package guis;

import db_objs.MyJDBC;
import db_objs.Transaction;
import db_objs.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

/*
    Afișează un dialog personalizat pentru aplicația noastră BankingAppGui
 */

public class BankingAppDialog extends JDialog implements ActionListener {
    private User user;
    private BankingAppGui bankingAppGui;
    private JLabel balanceLabel;
    private JLabel enterAmountLabel;
    private JLabel enterUserLabel;
    private JTextField enterAmountField;
    private JTextField enterUserField;
    private JButton actionButton;

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
        balanceLabel = new JLabel("Resurse financiare: " + user.getCurrentBalance() + " MDL");
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
        enterAmountField.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterAmountField);
    }

    public void addActionButton(String actionButtonType){
        actionButton = new JButton(actionButtonType);
        actionButton.setBounds(15, 300, getWidth() - 50, 40);
        actionButton.setFont(new Font("Dialog", Font.BOLD, 20));
        add(actionButton);
    }

    public void addUserField(){
        // Introducerea în eticheta utilizatorului
        enterUserLabel = new JLabel("Introduceți nume utilizator: ");
        enterUserLabel.setBounds(0, 160, getWidth() - 20, 20);
        enterUserLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        enterUserLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterUserLabel);

        // Introducerea în câmpul utilizatorului
        enterUserField = new JTextField();
        enterUserField.setBounds(15, 190, getWidth() - 50, 40);
        enterUserField.setFont(new Font("Dialog", Font.BOLD, 20));
        enterUserField.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterUserField);
    }

    private void handleTransaction(String transactionType, float amountVal){
        Transaction transaction;

        if(transactionType.equalsIgnoreCase("Depozit")){
            // Tipul tranzacției (depozit), adăugarea la soldul curent
            user.setCurrentBalance(user.getCurrentBalance().add(new BigDecimal(amountVal)));

            // Crearea tranzacției
            // Lăs data nulă deoarece folosim funcția NOW() în SQL care va obține data curentă
            transaction = new Transaction(user.getId(), transactionType, new BigDecimal(amountVal), null);
        }else{
            // Tipul tranzacției (retragere), scăderea din soldul curent
            user.setCurrentBalance(user.getCurrentBalance().subtract(new BigDecimal(amountVal)));

            // Doresc să afișez un semn negativ pentru suma amountVal la retragere
            transaction = new Transaction(user.getId(), transactionType, new BigDecimal(-amountVal), null);
        }

        // Actualizarea bazei de date
        if(MyJDBC.addTransactionToDatabase(transaction) && MyJDBC.updateCurrentBalance(user)){
            // Afișarea dialogului de succes
            JOptionPane.showMessageDialog(this, transactionType + " Succes!");

            // Resetarea câmpurilor
            resetFieldsAndUpdateCurrentBalance();
        }
    }

    private void resetFieldsAndUpdateCurrentBalance(){
        // Resetarea câmpurilor
        enterAmountField.setText("");

        // Apare numai atunci când se face click pe transfer
        if(enterUserField != null){
            enterUserField.setText("");
        }

        // Actualizarea soldului curent pe dialog
        balanceLabel.setText("Soldul curent: MDL" + user.getCurrentBalance());

        // Actualizarea soldului curent pe GUI-ul principal
        bankingAppGui.getCurrentBalanceField().setText("MDL" + user.getCurrentBalance());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonPressed = e.getActionCommand();

        // Obținerea valorii sumei
        float amountVal = Float.parseFloat(enterAmountField.getText());

        // Dacă se apasă butonul depozitării
        if(buttonPressed.equalsIgnoreCase("Depozit")){
            // Dorim să gestionăm tranzacția de depunere
        }
    }
}
