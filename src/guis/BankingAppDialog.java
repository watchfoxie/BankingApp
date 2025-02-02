package guis;

import db_objs.User;

import javax.swing.*;

/*
    Afișează un dialog personalizat pentru aplicația noastră BankingAppGui
 */

public class BankingAppDialog extends JDialog {
    private User user;
    private BankingAppGui bankingAppGui;

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
}
