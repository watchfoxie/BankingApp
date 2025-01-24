package guis;

import javax.swing.*;
/*
    Crearea unei clase abstracte ne ajută să stabilim modelul pe care îl va urma GUIS-ul nostru, de exemplu,
    în fiecare GUIS va avea aceeași dimensiune și va trebui să invoce propriul addGuiComponents()
    care va fi unic pentru fiecare subclasă.
 */
public abstract class BaseFrame extends JFrame {
    public BaseFrame(String title) {
        initialize(title);
    }

    private void initialize(String title) {
        // Instanțiați proprietățile jframe și adăugați un titlu la bară
        setTitle(title);

        // Setează dimensiunea (în pixeli)
        setSize(420, 600);

        // Terminarea programului atunci când GUI este închis
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Setați Layout la null pentru a avea Layout absolut care ne permite să specificăm manual
        // dimensiunea și poziția fiecărei componente GUI
        setLayout(null);

        // Prevenirea redimensionării GUI
        setResizable(false);

        // Lansați GUI-ul în centrul ecranului
        setLocationRelativeTo(null);

        // Apel pe subclasa addGuiComponent()
        addGuiComponents();
    }

    // Această metodă va trebui să fie definită de subclase atunci când această clasă este moștenită de la
    protected abstract void addGuiComponents();
}