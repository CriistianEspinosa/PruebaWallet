/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package walletdigital;

/**
 *
 * @author espin
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class WalletDigital extends JFrame {
    private JLabel balanceLabel;
    private JTextField amountField;
    private JButton purchaseButton, saleButton;
    private JTable transactionTable;
    private DefaultTableModel tableModel;
    private double balance = 100.00; // Saldo inicial
    private Random random = new Random();

    public WalletDigital() {
        setTitle("Simple Wallet");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior
        JPanel topPanel = new JPanel(new FlowLayout());
        balanceLabel = new JLabel("Balance: $" + balance);
        topPanel.add(balanceLabel);
        amountField = new JTextField(10);
        topPanel.add(new JLabel("Amount:"));
        topPanel.add(amountField);
        add(topPanel, BorderLayout.NORTH);

        // Botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        purchaseButton = new JButton("Purchase");
        saleButton = new JButton("Sale");
        buttonPanel.add(purchaseButton);
        buttonPanel.add(saleButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Tabla de transacciones
        String[] columnNames = {"ID", "Type", "Amount", "Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        transactionTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        add(scrollPane, BorderLayout.CENTER);

        // Agregar action listeners
        purchaseButton.addActionListener(e -> processTransaction('P'));
        saleButton.addActionListener(e -> processTransaction('S'));
    }

    private void processTransaction(char type) {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (type == 'P') {
                if (balance >= amount) {
                    balance -= amount;
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient funds");
                    return;
                }
            } else {
                balance += amount;
            }
            updateBalance();
            addTransactionToTable(type, amount);
            amountField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount");
        }
    }

    private void updateBalance() {
        balanceLabel.setText("Balance: $" + String.format("%.2f", balance));
    }

    private void addTransactionToTable(char type, double amount) {
        int id = 10000 + random.nextInt(90000);
        String typeStr = (type == 'P') ? "P" : "S";
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        tableModel.addRow(new Object[]{id, typeStr, amount, date});
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WalletDigital().setVisible(true));
    }
}