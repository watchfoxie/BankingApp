package guis;

import javax.swing.*;
import java.awt.*;

public class RegisterGui extends BaseFrame{
    public RegisterGui(){
        super("Aplicatie bancara - Inregistrare");
    }

    @Override
    protected void addGuiComponents() {
        // Crearea etichetei aplicației bancare
        JLabel bankingAppLabel = new JLabel("Aplicație Bancară");

        // Setați locația și dimensiunea componentei GUI
        bankingAppLabel.setBounds(0, 20, super.getWidth(), 40);

        // Schimbă stilul fontului
        bankingAppLabel.setFont(new Font("Dialog", Font.BOLD, 32));

        // Centrează textul în JLabel
        bankingAppLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Adaugă la GUI
        add(bankingAppLabel);

        // Etichetă nume de utilizator
        JLabel usernameLabel = new JLabel("Nume utilizator:");

        // getWidth() ne returnează lățimea cadrului nostru care este de aproximativ 420
        usernameLabel.setBounds(20, 120, getWidth() - 30, 24);

        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(usernameLabel);

        // Crearea câmpului nume de utilizator
        JTextField usernameField = new JTextField();
        usernameField.setBounds(20, 160, getWidth() - 50, 40);
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(usernameField);

        // Crearea etichetei parolei
        JLabel passwordLabel = new JLabel("Parolă:");
        passwordLabel.setBounds(20, 220, getWidth() - 50, 24);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(passwordLabel);

        // Crearea câmpului parolă
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(20, 260, getWidth() - 50, 40);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(passwordField);

        // Introducerea reoetată a etichetei parolei
        JLabel rePasswordLabel = new JLabel("Repetați parola:");
        rePasswordLabel.setBounds(20, 320, getWidth() - 50, 40);
        rePasswordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(rePasswordLabel);

        // Crearea câmpului introducerii repetate a parolei
        JPasswordField rePasswordField = new JPasswordField();
        rePasswordField.setBounds(20, 360, getWidth() - 50, 40);
        rePasswordField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(rePasswordField);

        // Crearea butonului de înregistrare
        JButton registerButton = new JButton("Înregistrare");
        registerButton.setBounds(20, 460, getWidth() - 50, 40);
        registerButton.setFont(new Font("Dialog", Font.BOLD, 20));
        add(registerButton);

        // Crearea etichetei de autentificare
        JLabel loginLabel = new JLabel("<html><a href=\"#\">Aveți cont? Autentificare</a></html>");
        loginLabel.setBounds(0, 510, getWidth() - 10, 30);
        loginLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(loginLabel);
    }
}
