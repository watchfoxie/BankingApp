package guis;

import db_objs.MyJDBC;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class RegisterGui extends BaseFrame {
    private JButton registerButton;

    public RegisterGui() {
        super("Aplicație bancara - Înregistrare");
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
        passwordLabel.setBounds(20, 220, getWidth() - 50, 24);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(20, 260, getWidth() - 50, 40);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(passwordField);

        JLabel passwordRulesLabel = new JLabel("<html>Parola trebuie să:<br>" +
                "- Conțină 6-24 caractere<br>" +
                "- Conțină cel puțin o literă mare<br>" +
                "- Conțină cel puțin o literă mică<br>" +
                "- Conțină cel puțin o cifră<br>" +
                "- NU conțină caractere speciale</html>");
        passwordRulesLabel.setBounds(20, 300, getWidth() - 50, 100);
        passwordRulesLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
        add(passwordRulesLabel);

        JLabel rePasswordLabel = new JLabel("Repetați parola:");
        rePasswordLabel.setBounds(20, 400, getWidth() - 50, 40);
        rePasswordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(rePasswordLabel);

        JPasswordField rePasswordField = new JPasswordField();
        rePasswordField.setBounds(20, 440, getWidth() - 50, 40);
        rePasswordField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(rePasswordField);

        registerButton = new JButton("Înregistrare");
        registerButton.setBounds(20, 500, getWidth() - 50, 40);
        registerButton.setFont(new Font("Dialog", Font.BOLD, 20));
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                char[] rePasswordChars = rePasswordField.getPassword();
                String password = new String(passwordChars);
                String rePassword = new String(rePasswordChars);

                String validationError = validateUserInput(username, password, rePassword);
                if (validationError == null) {
                    if (MyJDBC.register(username, password)) {
                        RegisterGui.this.dispose();
                        LoginGui loginGui = new LoginGui();
                        loginGui.setVisible(true);
                        JOptionPane.showMessageDialog(loginGui, "Cont înregistrat cu succes!");
                    } else {
                        JOptionPane.showMessageDialog(RegisterGui.this, "Eroare: Utilizator deja înregistrat!");
                    }
                } else {
                    JOptionPane.showMessageDialog(RegisterGui.this, validationError, "Eroare la înregistrare", JOptionPane.ERROR_MESSAGE);
                }
                Arrays.fill(passwordChars, '0');
                Arrays.fill(rePasswordChars, '0');
            }
        });
        add(registerButton);

        // Setarea butonului de înregistrare ca buton implicit pentru tasta Enter
        getRootPane().setDefaultButton(registerButton);

        // Maparea tastei Esc pentru a închide fereastra
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getRootPane().getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "close");
        actionMap.put("close", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JLabel loginLabel = new JLabel("<html><a href=\"#\">Aveți cont? Autentificare</a></html>");
        loginLabel.setBounds(0, 550, getWidth() - 10, 30);
        loginLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point location = RegisterGui.this.getLocation();
                RegisterGui.this.dispose();
                LoginGui loginGui = new LoginGui();
                loginGui.setLocation(location);
                loginGui.setVisible(true);
            }
        });
        add(loginLabel);
    }

    private String validateUserInput(String username, String password, String rePassword) {
        // Validate username
        if (username.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
            return "Toate câmpurile sunt obligatorii!";
        }

        if (username.length() < 6) {
            return "Numele de utilizator trebuie să aibă cel puțin 6 caractere!";
        }

        // Validate password match
        if (!password.equals(rePassword)) {
            return "Parolele nu coincid!";
        }

        // Validate password length
        if (password.length() < 6 || password.length() > 24) {
            return "Parola trebuie să aibă între 6 și 24 de caractere!";
        }

        // Check for at least one uppercase letter
        if (!password.matches(".*[A-Z].*")) {
            return "Parola trebuie să conțină cel puțin o literă mare!";
        }

        // Check for at least one lowercase letter
        if (!password.matches(".*[a-z].*")) {
            return "Parola trebuie să conțină cel puțin o literă mică!";
        }

        // Check for at least one digit
        if (!password.matches(".*\\d.*")) {
            return "Parola trebuie să conțină cel puțin o cifră!";
        }

        // Check for special characters (not allowed)
        if (!password.matches("^[a-zA-Z0-9]*$")) {
            return "Parola nu poate conține caractere speciale!";
        }

        // All validations passed
        return null;
    }
}