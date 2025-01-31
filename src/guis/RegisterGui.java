package guis;

import db_objs.MyJDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obținerea numelui de utilizator
                String username = usernameField.getText();

                // Obținerea parolei
                String password = String.valueOf(passwordField.getPassword());

                // Obținerea parolei repetate
                String rePassword = String.valueOf(rePasswordField.getPassword());

                // Va trebui să validăm datele introduse de utilizator
                if(validateUserInput(username, password, rePassword)){
                    // Încercarea de a înregistra utilizatorul în baza de date
                    if(MyJDBC.register(username, password)){
                        // Înregistrare reușită
                        // Eliminarea acestui GUI
                        RegisterGui.this.dispose();

                        // Lansarea GUI-ului de autentificare
                        LoginGui loginGui = new LoginGui();
                        loginGui.setVisible(true);

                        // Crearea unui dialog privind rezultatele
                        JOptionPane.showMessageDialog(loginGui, "Cont înregistrat cu succes!");
                    }else{
                        // Înregistrare eșuată
                        JOptionPane.showMessageDialog(RegisterGui.this, "Eroare: Utilizator deja înregistrat!");
                    }
                }else{
                    // Introducere utilizator invalidă
                    JOptionPane.showMessageDialog(RegisterGui.this,
                            "Eroare: Numele de utilizator trebuie să aibă cel puțin 6 caractere\n" +
                            "și/sau parola trebuie să corespundă pentru ambele casete");
                }
            }
        });
        add(registerButton);

        // Crearea etichetei de autentificare
        JLabel loginLabel = new JLabel("<html><a href=\"#\">Aveți cont? Autentificare</a></html>");
        loginLabel.setBounds(0, 510, getWidth() - 10, 30);
        loginLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(loginLabel);
    }

    private boolean validateUserInput(String username, String password, String rePassword){
        // Toate câmpurile trebuie să aibă o valoare
        if(username.length() == 0 || password.length() == 0 || rePassword.length() == 0) return false;

        // Numele de utilizator trebuie să aibă cel puțin 6 caractere
        if(username.length() < 6) return false;

        // Parola și reintroducerea parolii trebuie să fie aceleași
        if(!password.equals(rePassword)) return false;

        // Trece validarea
        return true;
    }
}
