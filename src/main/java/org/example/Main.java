package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {
    private static ArrayList<String> customers = new ArrayList<>();
    private static final DefaultListModel<String> listModel = new DefaultListModel<>();

    // Zambian provinces shown in the combo box
    private static final String[] PROVINCES = {
            "-- Select Province --",
            "Central",
            "Copperbelt",
            "Eastern",
            "Luapula",
            "Lusaka",
            "Muchinga",
            "Northern",
            "North-Western",
            "Southern",
            "Western"
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createWindow);
    }

    private static void createWindow() {
        JFrame frame = new JFrame("Customer Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout(10, 10));

        // Top panel - input form
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel nameLabel = new JLabel("Name");
        JTextField nameField = new JTextField();

        JLabel provinceLabel = new JLabel("Province");
        JComboBox<String> provinceCombo = new JComboBox<>(PROVINCES);

        JButton saveButton = new JButton("Save customer");
        JButton clearButton = new JButton("Clear");

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(provinceLabel);
        inputPanel.add(provinceCombo);
        inputPanel.add(saveButton);
        inputPanel.add(clearButton);

        // Center panel - list of customers
        JList<String> customerList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(customerList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Saved Customer"));

        // Bottom label
        JLabel statusLabel = new JLabel("Enter customer details above");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 15, 10, 15));

        // Button actions
        saveButton.addActionListener(e -> {
            String name = nameField.getText().trim();

            if (name.isEmpty() || provinceCombo.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(frame,
                        "Please enter a Name and select a Province!",
                        "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String province = (String) provinceCombo.getSelectedItem();
            String customer = name + " - " + province;
            customers.add(customer);
            listModel.addElement(customer);

            statusLabel.setText("Saved: " + customer);
            nameField.setText("");
            provinceCombo.setSelectedIndex(0);
            nameField.requestFocus();
        });

        clearButton.addActionListener(e -> {
            nameField.setText("");
            provinceCombo.setSelectedIndex(0);
            statusLabel.setText("Cleared");
        });

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(statusLabel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);
    }

    public static ArrayList<String> getCustomers() {
        return customers;
    }

    public static void setCustomers(ArrayList<String> customers) {
        Main.customers = customers;
    }
}