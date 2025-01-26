import db_objs.User;
import guis.BankingAppGui;
import guis.LoginGui;
import guis.RegisterGui;

import javax.swing.*;
import java.math.BigDecimal;

public class AppLauncher {
    public static void main(String[] args) {
        // Utilizați invokeLater pentru a face actualizările la GUI mai sigure pentru fire
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginGui().setVisible(true);
//                new RegisterGui().setVisible(true);
//                new BankingAppGui(new User(1,"username", "password", new BigDecimal("34.00"))).setVisible(true);
            }
        });
    }
}
