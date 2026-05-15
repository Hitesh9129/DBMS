import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class ComplaintsCRUD extends JFrame {
    JTextField compIdField, userIdField, statusField, locationField, dateField;
    JTextArea descriptionArea;
    JButton insertBtn, updateBtn, deleteBtn, viewBtn;

    public ComplaintsCRUD() {
        setTitle("Complaints Manager");
        setLayout(null);
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel compIdLbl = new JLabel("Complaint ID:");
        compIdLbl.setBounds(20, 20, 100, 25);
        add(compIdLbl);
        compIdField = new JTextField();
        compIdField.setBounds(130, 20, 300, 25);
        add(compIdField);

        JLabel userIdLbl = new JLabel("User ID:");
        userIdLbl.setBounds(20, 60, 100, 25);
        add(userIdLbl);
        userIdField = new JTextField();
        userIdField.setBounds(130, 60, 300, 25);
        add(userIdField);

        JLabel descLbl = new JLabel("Description:");
        descLbl.setBounds(20, 100, 100, 25);
        add(descLbl);
        descriptionArea = new JTextArea();
        JScrollPane descScroll = new JScrollPane(descriptionArea);
        descScroll.setBounds(130, 100, 300, 80);
        add(descScroll);

        JLabel statusLbl = new JLabel("Status:");
        statusLbl.setBounds(20, 190, 100, 25);
        add(statusLbl);
        statusField = new JTextField();
        statusField.setBounds(130, 190, 300, 25);
        add(statusField);

        JLabel locationLbl = new JLabel("Location:");
        locationLbl.setBounds(20, 230, 100, 25);
        add(locationLbl);
        locationField = new JTextField();
        locationField.setBounds(130, 230, 300, 25);
        add(locationField);

        JLabel dateLbl = new JLabel("Date (YYYY-MM-DD):");
        dateLbl.setBounds(20, 270, 150, 25);
        add(dateLbl);
        dateField = new JTextField();
        dateField.setBounds(180, 270, 250, 25);
        add(dateField);

        insertBtn = new JButton("Insert");
        insertBtn.setBounds(20, 320, 100, 30);
        add(insertBtn);

        updateBtn = new JButton("Update");
        updateBtn.setBounds(130, 320, 100, 30);
        add(updateBtn);

        deleteBtn = new JButton("Delete");
        deleteBtn.setBounds(240, 320, 100, 30);
        add(deleteBtn);

        viewBtn = new JButton("View All");
        viewBtn.setBounds(350, 320, 100, 30);
        add(viewBtn);

        insertBtn.addActionListener(e -> insert());
        updateBtn.addActionListener(e -> update());
        deleteBtn.addActionListener(e -> delete());
        viewBtn.addActionListener(e -> viewAll());

        setVisible(true);
    }

    private void insert() {
        try (Connection con = DBConnection.getConnection()) {
            String query = "INSERT INTO complaints (comp_id, user_id, description, status, location, date) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(compIdField.getText()));
            ps.setInt(2, Integer.parseInt(userIdField.getText()));
            ps.setString(3, descriptionArea.getText());
            ps.setString(4, statusField.getText());
            ps.setString(5, locationField.getText());
            ps.setDate(6, Date.valueOf(dateField.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Inserted Successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void update() {
        try (Connection con = DBConnection.getConnection()) {
            String query = "UPDATE complaints SET user_id=?, description=?, status=?, location=?, date=? WHERE comp_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(userIdField.getText()));
            ps.setString(2, descriptionArea.getText());
            ps.setString(3, statusField.getText());
            ps.setString(4, locationField.getText());
            ps.setDate(5, Date.valueOf(dateField.getText()));
            ps.setInt(6, Integer.parseInt(compIdField.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Updated Successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void delete() {
        try (Connection con = DBConnection.getConnection()) {
            String query = "DELETE FROM complaints WHERE comp_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(compIdField.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Deleted Successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void viewAll() {
        try (Connection con = DBConnection.getConnection()) {
            String query = "SELECT * FROM complaints";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            JTextArea textArea = new JTextArea();
            while (rs.next()) {
                textArea.append("ID: " + rs.getInt("comp_id") +
                        ", User ID: " + rs.getInt("user_id") +
                        ", Status: " + rs.getString("status") +
                        ", Date: " + rs.getDate("date") +
                        "\nDesc: " + rs.getString("description") +
                        "\nLocation: " + rs.getString("location") + "\n\n");
            }
            JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Complaints", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
