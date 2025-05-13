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

        JLabel rePasswordLabel = new JLabel("Repetați parola:");
        rePasswordLabel.setBounds(20, 320, getWidth() - 50, 40);
        rePasswordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(rePasswordLabel);

        JPasswordField rePasswordField = new JPasswordField();
        rePasswordField.setBounds(20, 360, getWidth() - 50, 40);
        rePasswordField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(rePasswordField);

        registerButton = new JButton("Înregistrare");
        registerButton.setBounds(20, 460, getWidth() - 50, 40);
        registerButton.setFont(new Font("Dialog", Font.BOLD, 20));
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                char[] rePasswordChars = rePasswordField.getPassword();
                String password = new String(passwordChars);
                String rePassword = new String(rePasswordChars);
                if (validateUserInput(username, password, rePassword)) {
                    if (MyJDBC.register(username, password)) {
                        RegisterGui.this.dispose();
                        LoginGui loginGui = new LoginGui();
                        loginGui.setVisible(true);
                        JOptionPane.showMessageDialog(loginGui, "Cont înregistrat cu succes!");
                    } else {
                        JOptionPane.showMessageDialog(RegisterGui.this, "Eroare: Utilizator deja înregistrat!");
                    }
                } else {
                    JOptionPane.showMessageDialog(RegisterGui.this,
                            "Eroare: Numele de utilizator trebuie să aibă cel puțin 6 caractere\n" +
                                    "și/sau parola trebuie să corespundă pentru ambele casete");
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
        loginLabel.setBounds(0, 510, getWidth() - 10, 30);
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

    private boolean validateUserInput(String username, String password, String rePassword) {
        if (username.length() == 0 || password.length() == 0 || rePassword.length() == 0) return false;
        if (username.length() < 6) return false;
        if (!password.equals(rePassword)) return false;
        return true;
    }
}