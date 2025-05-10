package guis;

import db_objs.User;
import db_objs.MyJDBC;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class LoginGui extends BaseFrame {
    public LoginGui() {
        super("Aplicație bancara - Autentificare");
        setLocationRelativeTo(null);
    }

    @Override
    protected void addGuiComponents() {
        JLabel bankingAppLabel = new JLabel("Aplicație Bancară");
        bankingAppLabel.setBounds(0, 20, super.getWidth(), 40);
        bankingAppLabel.setFont(new Font("Dialog", Font.BOLD, 32));
        bankingAppLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(bankingAppLabel);

        JLabel usernameLabel = new JLabel("Nume utilizator:");
        usernameLabel.setBounds(20, 120, getWidth() - 30, 24);
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(20, 160, getWidth() - 50, 40);
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(usernameField);

        JLabel passwordLabel = new JLabel("Parolă:");
        passwordLabel.setBounds(20, 280, getWidth() - 50, 24);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(20, 320, getWidth() - 50, 40);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(passwordField);

        JButton loginButton = new JButton("Autentificare");
        loginButton.setBounds(20, 460, getWidth() - 50, 40);
        loginButton.setFont(new Font("Dialog", Font.BOLD, 20));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);
                User user = MyJDBC.validateLogin(username, password);
                Arrays.fill(passwordChars, '0');
                if (user != null) {
                    LoginGui.this.dispose();
                    BankingAppGui bankingAppGui = new BankingAppGui(user);
                    bankingAppGui.setVisible(true);
                    JOptionPane.showMessageDialog(bankingAppGui, "Autentificare reușită!");
                } else {
                    JOptionPane.showMessageDialog(LoginGui.this, "Autentificare nereușită!");
                }
            }
        });
        add(loginButton);

        JLabel registerLabel = new JLabel("<html><a href=\"#\">Nu aveți cont? Înregistrare</a></html>");
        registerLabel.setBounds(0, 510, getWidth() - 10, 30);
        registerLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point location = LoginGui.this.getLocation();
                LoginGui.this.dispose();
                RegisterGui registerGui = new RegisterGui();
                registerGui.setLocation(location);
                registerGui.setVisible(true);
            }
        });
        add(registerLabel);
    }
}