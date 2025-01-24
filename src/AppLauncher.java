import guis.LoginGui;
import guis.RegisterGui;

import javax.swing.*;

public class AppLauncher {
    public static void main(String[] args) {
        // Utilizați invokeLater pentru a face actualizările la GUI mai sigure pentru fire
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
//                new LoginGui().setVisible(true);
                new RegisterGui().setVisible(true);
            }
        });
    }
}
