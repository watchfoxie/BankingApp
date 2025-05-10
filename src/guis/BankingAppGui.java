package guis;

import db_objs.User;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;

public class BankingAppGui extends BaseFrame implements ActionListener {
    private JTextField currentBalanceField;
    private List<JButton> operationalButtons;
    private int currentFocusIndex = 0;
    private CommandRouter commandRouter = new CommandRouter();

    public JTextField getCurrentBalanceField() {
        return currentBalanceField;
    }

    public BankingAppGui(User user) {
        super("Aplicație Bancara", user);
        setupCommands();
    }

    private void setupCommands() {
        commandRouter.registerCommand("depozit", () -> {
            BankingAppDialog dialog = new BankingAppDialog(this, user);
            dialog.setTitle("Depozit");
            dialog.addCurrentBalanceAndAmount();
            dialog.addActionButton("Depozit");
            dialog.setVisible(true);
        });

        commandRouter.registerCommand("retragere", () -> {
            BankingAppDialog dialog = new BankingAppDialog(this, user);
            dialog.setTitle("Retragere");
            dialog.addCurrentBalanceAndAmount();
            dialog.addActionButton("Retragere");
            dialog.setVisible(true);
        });

        commandRouter.registerCommand("tranzacție anterioară", () -> {
            BankingAppDialog dialog = new BankingAppDialog(this, user);
            dialog.setTitle("Tranzacție Anterioară");
            dialog.addPastTransactionComponents();
            dialog.setVisible(true);
        });

        commandRouter.registerCommand("transfer", () -> {
            BankingAppDialog dialog = new BankingAppDialog(this, user);
            dialog.setTitle("Transfer");
            dialog.addCurrentBalanceAndAmount();
            dialog.addUserField();
            dialog.addActionButton("Transfer");
            dialog.setVisible(true);
        });

        commandRouter.registerCommand("ieșire", () -> {
            user = null;
            new LoginGui().setVisible(true);
            this.dispose();
        });
    }

    @Override
    protected void addGuiComponents() {
        String welcomeMessage = "<html><body style='text-align:center'><b>Salut " + user.getUsername() + "</b><br>Ce operațiuni ați dori să faceți astăzi?</body></html>";
        JLabel welcomeMessageLabel = new JLabel(welcomeMessage);
        welcomeMessageLabel.setBounds(0, 20, getWidth() - 10, 40);
        welcomeMessageLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
        welcomeMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeMessageLabel);

        JLabel currentBalanceLabel = new JLabel("Soldul Curent");
        currentBalanceLabel.setBounds(0, 80, getWidth() - 10, 30);
        currentBalanceLabel.setFont(new Font("Dialog", Font.BOLD, 22));
        currentBalanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(currentBalanceLabel);

        currentBalanceField = new JTextField(user.getCurrentBalance() + " MDL");
        currentBalanceField.setBounds(15, 120, getWidth() - 50, 40);
        currentBalanceField.setFont(new Font("Dialog", Font.BOLD, 28));
        currentBalanceField.setHorizontalAlignment(SwingConstants.RIGHT);
        currentBalanceField.setEditable(false);
        currentBalanceField.setCaret(new DefaultCaret() {
            @Override
            public void setVisible(boolean visible) {
                super.setVisible(false);
            }
        });
        add(currentBalanceField);

        JButton depositButton = new JButton("Depozit");
        depositButton.setBounds(15, 180, getWidth() - 50, 50);
        depositButton.setFont(new Font("Dialog", Font.BOLD, 22));
        depositButton.addActionListener(this);
        depositButton.addMouseListener(new CustomMouseAdapter(depositButton));
        depositButton.setToolTipText("Depuneți bani în cont");
        add(depositButton);

        JButton withdrawButton = new JButton("Retragere");
        withdrawButton.setBounds(15, 250, getWidth() - 50, 50);
        withdrawButton.setFont(new Font("Dialog", Font.BOLD, 22));
        withdrawButton.addActionListener(this);
        withdrawButton.addMouseListener(new CustomMouseAdapter(withdrawButton));
        withdrawButton.setToolTipText("Retrageți bani din cont");
        add(withdrawButton);

        JButton pastTransactionButton = new JButton("Tranzacție Anterioară");
        pastTransactionButton.setBounds(15, 320, getWidth() - 50, 50);
        pastTransactionButton.setFont(new Font("Dialog", Font.BOLD, 22));
        pastTransactionButton.addActionListener(this);
        pastTransactionButton.addMouseListener(new CustomMouseAdapter(pastTransactionButton));
        pastTransactionButton.setToolTipText("Vizualizați tranzacțiile anterioare");
        add(pastTransactionButton);

        JButton transferButton = new JButton("Transfer");
        transferButton.setBounds(15, 390, getWidth() - 50, 50);
        transferButton.setFont(new Font("Dialog", Font.BOLD, 22));
        transferButton.addActionListener(this);
        transferButton.addMouseListener(new CustomMouseAdapter(transferButton));
        transferButton.setToolTipText("Transferați bani către alt utilizator");
        add(transferButton);

        JButton logoutButton = new JButton("Ieșire");
        logoutButton.setBounds(15, 500, getWidth() - 50, 50);
        logoutButton.setFont(new Font("Dialog", Font.BOLD, 22));
        logoutButton.addActionListener(this);
        logoutButton.addMouseListener(new CustomMouseAdapter(logoutButton));
        logoutButton.setToolTipText("Ieșiți din aplicație");
        add(logoutButton);

        operationalButtons = Arrays.asList(depositButton, withdrawButton, pastTransactionButton, transferButton, logoutButton);
        for (JButton button : operationalButtons) {
            button.addFocusListener(new ButtonFocusListener(button));
        }

        setupKeyboardNavigation();
    }

    private void setupKeyboardNavigation() {
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getRootPane().getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "nextButton");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "prevButton");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "pressButton");

        actionMap.put("nextButton", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentFocusIndex = (currentFocusIndex + 1) % operationalButtons.size();
                operationalButtons.get(currentFocusIndex).requestFocusInWindow();
            }
        });

        actionMap.put("prevButton", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentFocusIndex = (currentFocusIndex - 1 + operationalButtons.size()) % operationalButtons.size();
                operationalButtons.get(currentFocusIndex).requestFocusInWindow();
            }
        });

        actionMap.put("pressButton", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operationalButtons.get(currentFocusIndex).doClick();
            }
        });

        operationalButtons.get(0).requestFocusInWindow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand().toLowerCase();
        commandRouter.executeCommand(action);
    }

    private static class CustomMouseAdapter extends MouseAdapter {
        private final JButton button;
        private final Color originalBackground;
        private final Color hoverBackground = new Color(173, 216, 230);

        public CustomMouseAdapter(JButton button) {
            this.button = button;
            this.originalBackground = button.getBackground();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            button.setBackground(hoverBackground);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            button.setBackground(originalBackground);
        }
    }

    private static class ButtonFocusListener implements FocusListener {
        private final JButton button;
        private final Border originalBorder;

        public ButtonFocusListener(JButton button) {
            this.button = button;
            this.originalBorder = button.getBorder();
        }

        @Override
        public void focusGained(FocusEvent e) {
            button.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        }

        @Override
        public void focusLost(FocusEvent e) {
            button.setBorder(originalBorder);
        }
    }
}