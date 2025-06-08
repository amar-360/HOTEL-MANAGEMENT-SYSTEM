package Hotel.Management.System;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

/**
 * CustomerInfo class displays all customer information and allows searching by name.
 */
public class CustomerInfo extends JFrame {
    JTable table;
    JTextField searchField;
    JButton searchBtn, backBtn;

    public CustomerInfo() {
        setTitle("Customer Information");
        setLayout(null);
        setSize(900,600);
        setLocation(400,200);

        JLabel label = new JLabel("Search by Name:");
        label.setBounds(20,20,120,30);
        add(label);

        searchField = new JTextField();
        searchField.setBounds(140,20,200,30);
        add(searchField);

        searchBtn = new JButton("Search");
        searchBtn.setBounds(350,20,100,30);
        add(searchBtn);

        backBtn = new JButton("Back");
        backBtn.setBounds(800,20,80,30);
        add(backBtn);

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20,70,860,470);
        add(scrollPane);

        loadTable("");

        searchBtn.addActionListener(e -> {
            String name = searchField.getText().trim();
            loadTable(name);
        });
        backBtn.addActionListener(e -> setVisible(false));

        setVisible(true);
    }

    /**
     * Loads customer data into the table, optionally filtered by name.
     */
    private void loadTable(String name) {
        try {
            con c = new con();
            java.sql.PreparedStatement pst;
            if (name == null || name.isEmpty()) {
                pst = c.connection.prepareStatement("select * from customer");
            } else {
                pst = c.connection.prepareStatement("select * from customer where name like ?");
                pst.setString(1, "%" + name + "%");
            }
            java.sql.ResultSet rs = pst.executeQuery();
            table.setModel(net.proteanit.sql.DbUtils.resultSetToTableModel(rs));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }
    }

    /**
     * Main method for standalone testing.
     */
    public static void main(String[] args) {
        new CustomerInfo();
    }
}
