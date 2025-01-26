package guis;

import db_objs.User;
import db_objs.MyJDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
    Acest GUI va permite utilizatorului să se conecteze sau să lanseze GUI-ul de înregistrare
    Aceasta se extinde de la BaseFrame, ceea ce înseamnă că va trebui să ne definim propriul addGuiComponent()
 */
public class LoginGui extends BaseFrame{
    public LoginGui() {
        super("Aplicatie bancara - Autentificare");
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
        passwordLabel.setBounds(20, 280, getWidth() - 50, 24);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(passwordLabel);

        // Crearea câmpului parolă
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(20, 320, getWidth() - 50, 40);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(passwordField);

        // Crearea butonului de autentificare
        JButton loginButton = new JButton("Autentificare");
        loginButton.setBounds(20, 460, getWidth() - 50, 40);
        loginButton.setFont(new Font("Dialog", Font.BOLD, 20));
        loginButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
               // Obținem username
               String username = usernameField.getText();

               // Obținem parola
               String password = String.valueOf(passwordField.getPassword());

               // Validarea autentificării
                User user = MyJDBC.validateLogin(username, password);

               // Dacă utilizatorul este nul, înseamnă invalid, altfel este un cont valid
               if(user != null){
                   // Înseamnă autentificare validă

                   // Eliminarea acestui GUI
                   LoginGui.this.dispose();

                   // Lansarea GUI al aplicației bancare
                   BankingAppGui bankingAppGui = new BankingAppGui(user);
                   bankingAppGui.setVisible(true);

                   // Afișarea dialogului de succes
                   JOptionPane.showMessageDialog(bankingAppGui, "Autentificare reușită!");
               }else{
                   // Autentificare invalidă
                   JOptionPane.showMessageDialog(LoginGui.this, "Autentificare nereușită!");
               }
            }
        });
        add(loginButton);

        // Crearea etichetei de înregistrare
        JLabel registerLabel = new JLabel("<html><a href=\"#\">Nu aveți cont? Înregistrare</a></html>");
        registerLabel.setBounds(0, 510, getWidth() - 10, 30);
        registerLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(registerLabel);
    }
}
