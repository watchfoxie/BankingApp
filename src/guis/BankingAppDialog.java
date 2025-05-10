package guis;

import db_objs.MyJDBC;
import db_objs.Transaction;
import db_objs.User;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;

public class BankingAppDialog extends JDialog implements ActionListener {
    private User user;
    private BankingAppGui bankingAppGui;
    private JLabel balanceLabel;
    private JLabel enterAmountLabel;
    private JLabel enterUserLabel;
    private JTextField enterAmountField;
    private JTextField enterUserField;
    private JButton actionButton;
    private JPanel pastTransactionPanel;
    private ArrayList<Transaction> pastTransactions;

    public BankingAppDialog(BankingAppGui bankingAppGui, User user) {
        setSize(400, 460);
        setModal(true);
        setLocationRelativeTo(bankingAppGui);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        this.bankingAppGui = bankingAppGui;
        this.user = user;
    }

    public void addCurrentBalanceAndAmount() {
        balanceLabel = new JLabel("Resurse financiare: " + user.getCurrentBalance() + " MDL");
        balanceLabel.setBounds(0, 10, getWidth() - 20, 20);
        balanceLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(balanceLabel);

        enterAmountLabel = new JLabel("Introdu suma:");
        enterAmountLabel.setBounds(0, 50, getWidth() - 20, 20);
        enterAmountLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        enterAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterAmountLabel);

        enterAmountField = new JTextField();
        enterAmountField.setBounds(15, 80, getWidth() - 50, 40);
        enterAmountField.setFont(new Font("Dialog", Font.BOLD, 20));
        enterAmountField.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterAmountField);
    }

    public void addActionButton(String actionButtonType) {
        actionButton = new JButton(actionButtonType);
        actionButton.setBounds(15, 300, getWidth() - 50, 40);
        actionButton.setFont(new Font("Dialog", Font.BOLD, 20));
        actionButton.addActionListener(this);
        add(actionButton);
    }

    public void addUserField() {
        enterUserLabel = new JLabel("Introduceți nume utilizator: ");
        enterUserLabel.setBounds(0, 160, getWidth() - 20, 20);
        enterUserLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        enterUserLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterUserLabel);

        enterUserField = new JTextField();
        enterUserField.setBounds(15, 190, getWidth() - 50, 40);
        enterUserField.setFont(new Font("Dialog", Font.BOLD, 20));
        enterUserField.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterUserField);
    }

    public void addPastTransactionComponents() {
        // Titlul secțiunii de tranzacții
        JLabel titleLabel = new JLabel("Istoricul tranzacțiilor");
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        titleLabel.setBounds(0, 10, getWidth() - 20, 25);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel);

        // Creăm panoul principal pentru tranzacții
        pastTransactionPanel = new JPanel();
        pastTransactionPanel.setLayout(new BoxLayout(pastTransactionPanel, BoxLayout.Y_AXIS));
        pastTransactionPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        pastTransactionPanel.setBackground(new Color(245, 245, 245));

        // Creăm panoul pentru scroll
        JScrollPane scrollPane = new JScrollPane(pastTransactionPanel);
        scrollPane.setBounds(15, 45, getWidth() - 40, getHeight() - 70);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Obținem tranzacțiile din baza de date
        pastTransactions = MyJDBC.getPastTransaction(user);

        // Adăugăm un header pentru tranzacții
        JPanel headerPanel = new JPanel(new GridLayout(1, 3));
        headerPanel.setMaximumSize(new Dimension(scrollPane.getWidth() - 30, 30));
        headerPanel.setBackground(new Color(220, 220, 220));

        JLabel typeHeader = new JLabel("Tip tranzacție");
        typeHeader.setFont(new Font("Dialog", Font.BOLD, 14));
        typeHeader.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel amountHeader = new JLabel("Sumă");
        amountHeader.setFont(new Font("Dialog", Font.BOLD, 14));
        amountHeader.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel dateHeader = new JLabel("Data");
        dateHeader.setFont(new Font("Dialog", Font.BOLD, 14));
        dateHeader.setHorizontalAlignment(SwingConstants.CENTER);

        headerPanel.add(typeHeader);
        headerPanel.add(amountHeader);
        headerPanel.add(dateHeader);
        pastTransactionPanel.add(headerPanel);

        // Adăugăm tranzacțiile în panou
        for (Transaction pastTransaction : pastTransactions) {
            JPanel transactionPanel = new JPanel(new GridLayout(1, 3));
            transactionPanel.setMaximumSize(new Dimension(scrollPane.getWidth() - 30, 40));
            transactionPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

            // Formatăm tipul tranzacției
            JLabel typeLabel = new JLabel(pastTransaction.getTransactionType());
            typeLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
            typeLabel.setHorizontalAlignment(SwingConstants.CENTER);

            // Formatăm suma tranzacției cu culori diferite în funcție de tip
            JLabel amountLabel = new JLabel(String.valueOf(pastTransaction.getTransactionAmount()));
            amountLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
            amountLabel.setHorizontalAlignment(SwingConstants.CENTER);

            if (pastTransaction.getTransactionAmount().compareTo(BigDecimal.ZERO) < 0) {
                amountLabel.setForeground(new Color(220, 0, 0));
            } else {
                amountLabel.setForeground(new Color(0, 150, 0));
            }

            // Formatăm data tranzacției
            JLabel dateLabel = new JLabel(String.valueOf(pastTransaction.getTransactionDate()));
            dateLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
            dateLabel.setHorizontalAlignment(SwingConstants.CENTER);

            transactionPanel.add(typeLabel);
            transactionPanel.add(amountLabel);
            transactionPanel.add(dateLabel);

            // Adăugăm efect de hover (opțional, necesită MouseListener)
            transactionPanel.setBackground(Color.WHITE);

            pastTransactionPanel.add(transactionPanel);
            // Adăugăm un mic spațiu între tranzacții
            pastTransactionPanel.add(Box.createRigidArea(new Dimension(0, 2)));
        }

        // Adăugăm un spațiu la final pentru aspect estetic
        if (pastTransactions.isEmpty()) {
            JLabel noTransactionsLabel = new JLabel("Nu există tranzacții");
            noTransactionsLabel.setFont(new Font("Dialog", Font.ITALIC, 14));
            noTransactionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
            pastTransactionPanel.add(noTransactionsLabel);
        } else {
            pastTransactionPanel.add(Box.createVerticalGlue());
        }

        add(scrollPane);
    }

    private void handleTransaction(String transactionType, float amountVal) {
        Transaction transaction;
        if (transactionType.equalsIgnoreCase("Depozit")) {
            user.setCurrentBalance(user.getCurrentBalance().add(new BigDecimal(amountVal)));
            transaction = new Transaction(user.getId(), transactionType, new BigDecimal(amountVal), null);
        } else {
            user.setCurrentBalance(user.getCurrentBalance().subtract(new BigDecimal(amountVal)));
            transaction = new Transaction(user.getId(), transactionType, new BigDecimal(-amountVal), null);
        }
        if (MyJDBC.addTransactionToDatabase(transaction) && MyJDBC.updateCurrentBalance(user)) {
            JOptionPane.showMessageDialog(this, transactionType + " Succes!");
            resetFieldsAndUpdateCurrentBalance();
        } else {
            JOptionPane.showMessageDialog(this, transactionType + " Eșuat!");
        }
    }

    private void resetFieldsAndUpdateCurrentBalance() {
        enterAmountField.setText("");
        if (enterUserField != null) {
            enterUserField.setText("");
        }
        balanceLabel.setText("Soldul curent: MDL" + user.getCurrentBalance());
        bankingAppGui.getCurrentBalanceField().setText("MDL" + user.getCurrentBalance());
    }

    private void handleTransfer(User user, String transferredUser, float amount) {
        if (MyJDBC.transfer(user, transferredUser, amount)) {
            JOptionPane.showMessageDialog(this, "Transferul a fost efectuat cu succes!");
            resetFieldsAndUpdateCurrentBalance();
        } else {
            JOptionPane.showMessageDialog(this, "Transferul a eșuat!");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonPressed = e.getActionCommand();
        String amountText = enterAmountField.getText();
        float amountVal;
        try {
            amountVal = Float.parseFloat(amountText);
            if (amountVal <= 0) {
                JOptionPane.showMessageDialog(this, "Suma trebuie să fie un număr pozitiv.");
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Suma trebuie să fie un număr valid.");
            return;
        }

        if (buttonPressed.equalsIgnoreCase("Depozit")) {
            handleTransaction(buttonPressed, amountVal);
        } else {
            int result = user.getCurrentBalance().compareTo(BigDecimal.valueOf(amountVal));
            if (result < 0) {
                JOptionPane.showMessageDialog(this, "Eroare: Valoarea de intrare este mai mare decât soldul curent");
                return;
            }
            if (buttonPressed.equalsIgnoreCase("Retragere")) {
                handleTransaction(buttonPressed, amountVal);
            } else if (buttonPressed.equalsIgnoreCase("Transfer")) {
                String transferredUser = enterUserField.getText();
                if (transferredUser.isEmpty() || !transferredUser.matches("[a-zA-Z0-9]+")) {
                    JOptionPane.showMessageDialog(this, "Numele utilizatorului trebuie să conțină doar litere și cifre.");
                    return;
                }
                handleTransfer(user, transferredUser, amountVal);
            }
        }
    }
}