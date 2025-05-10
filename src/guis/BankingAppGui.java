package guis;

import db_objs.User;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankingAppGui extends BaseFrame implements ActionListener {
    private JTextField currentBalanceField;

    public JTextField getCurrentBalanceField() {
        return currentBalanceField;
    }

    public BankingAppGui(User user) {
        super("Aplicatie Bancara", user);
    }

    @Override
    protected void addGuiComponents() {
        String welcomeMessage = "<html><body style='text-align:center'><b>Salut " + user.getUsername() + "</b><br>Ce operațiuni ați dori să faceți astăzi?</body></html>";
        JLabel welcomeMessageLabel = new JLabel(welcomeMessage);
        welcomeMessageLabel.setBounds(0, 20, getWidth() - 10, 40);
        welcomeMessageLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
        welcomeMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeMessageLabel);

        JLabel currentBalanceLabel = new JLabel("Soldul Curent");
        currentBalanceLabel.setBounds(0, 80, getWidth() - 10, 30);
        currentBalanceLabel.setFont(new Font("Dialog", Font.BOLD, 22));
        currentBalanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(currentBalanceLabel);

        currentBalanceField = new JTextField(user.getCurrentBalance() + " MDL");
        currentBalanceField.setBounds(15, 120, getWidth() - 50, 40);
        currentBalanceField.setFont(new Font("Dialog", Font.BOLD, 28));
        currentBalanceField.setHorizontalAlignment(SwingConstants.RIGHT);
        currentBalanceField.setEditable(false);
        currentBalanceField.setCaret(new DefaultCaret() {
            @Override
            public void setVisible(boolean visible) {
                super.setVisible(false);
            }
        });
        add(currentBalanceField);

        JButton depositButton = new JButton("Depozit");
        depositButton.setBounds(15, 180, getWidth() - 50, 50);
        depositButton.setFont(new Font("Dialog", Font.BOLD, 22));
        depositButton.addActionListener(this);
        add(depositButton);

        JButton withdrawButton = new JButton("Retragere");
        withdrawButton.setBounds(15, 250, getWidth() - 50, 50);
        withdrawButton.setFont(new Font("Dialog", Font.BOLD, 22));
        withdrawButton.addActionListener(this);
        add(withdrawButton);

        JButton pastTransactionButton = new JButton("Tranzacție Anterioară");
        pastTransactionButton.setBounds(15, 320, getWidth() - 50, 50);
        pastTransactionButton.setFont(new Font("Dialog", Font.BOLD, 22));
        pastTransactionButton.addActionListener(this);
        add(pastTransactionButton);

        JButton transferButton = new JButton("Transfer");
        transferButton.setBounds(15, 390, getWidth() - 50, 50);
        transferButton.setFont(new Font("Dialog", Font.BOLD, 22));
        transferButton.addActionListener(this);
        add(transferButton);

        JButton logoutButton = new JButton("Ieșire");
        logoutButton.setBounds(15, 500, getWidth() - 50, 50);
        logoutButton.setFont(new Font("Dialog", Font.BOLD, 22));
        logoutButton.addActionListener(this);
        add(logoutButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonPressed = e.getActionCommand();
        if (buttonPressed.equalsIgnoreCase("Ieșire")) {
            user = null; // Curățare referință utilizator
            new LoginGui().setVisible(true);
            this.dispose();
            return;
        }

        BankingAppDialog bankingAppDialog = new BankingAppDialog(this, user);
        bankingAppDialog.setTitle(buttonPressed);
        if (buttonPressed.equalsIgnoreCase("Depozit") || buttonPressed.equalsIgnoreCase("Retragere") || buttonPressed.equalsIgnoreCase("Transfer")) {
            bankingAppDialog.addCurrentBalanceAndAmount();
            bankingAppDialog.addActionButton(buttonPressed);
            if (buttonPressed.equalsIgnoreCase("Transfer")) {
                bankingAppDialog.addUserField();
            }
        } else if (buttonPressed.equalsIgnoreCase("Tranzacție Anterioară")) {
            bankingAppDialog.addPastTransactionComponents();
        }
        bankingAppDialog.setVisible(true);
    }
}