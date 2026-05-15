import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class ResponsibleAuthoritiesCRUD extends JFrame {
    JTextField idField, nameField, locationField;
    JButton insertBtn, updateBtn, deleteBtn, viewBtn;

    public ResponsibleAuthoritiesCRUD() {
        setTitle("Responsible Authorities");
        setLayout(null);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel idLbl = new JLabel("ID:");
        idLbl.setBounds(20, 20, 100, 25);
        add(idLbl);
        idField = new JTextField();
        idField.setBounds(130, 20, 200, 25);
        add(idField);

        JLabel nameLbl = new JLabel("Name:");
        nameLbl.setBounds(20, 60, 100, 25);
        add(nameLbl);
        nameField = new JTextField();
        nameField.setBounds(130, 60, 200, 25);
        add(nameField);

        JLabel locLbl = new JLabel("Location:");
        locLbl.setBounds(20, 100, 100, 25);
        add(locLbl);
        locationField = new JTextField();
        locationField.setBounds(130, 100, 200, 25);
        add(locationField);

        insertBtn = new JButton("Insert");
        insertBtn.setBounds(20, 150, 80, 30);
        add(insertBtn);

        updateBtn = new JButton("Update");
        updateBtn.setBounds(110, 150, 80, 30);
        add(updateBtn);

        deleteBtn = new JButton("Delete");
        deleteBtn.setBounds(200, 150, 80, 30);
        add(deleteBtn);

        viewBtn = new JButton("View All");
        viewBtn.setBounds(290, 150, 80, 30);
        add(viewBtn);

        insertBtn.addActionListener(e -> insert());
        updateBtn.addActionListener(e -> update());
        deleteBtn.addActionListener(e -> delete());
        viewBtn.addActionListener(e -> viewAll());

        setVisible(true);
    }

    private void insert() {
        try (Connection con = DBConnection.getConnection()) {
            String query = "INSERT INTO responsible_authorities (resp_id, r_name, location) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(idField.getText()));
            ps.setString(2, nameField.getText());
            ps.setString(3, locationField.getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Inserted Successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void update() {
        try (Connection con = DBConnection.getConnection()) {
            String query = "UPDATE responsible_authorities SET r_name=?, location=? WHERE resp_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, nameField.getText());
            ps.setString(2, locationField.getText());
            ps.setInt(3, Integer.parseInt(idField.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Updated Successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void delete() {
        try (Connection con = DBConnection.getConnection()) {
            String query = "DELETE FROM responsible_authorities WHERE resp_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(idField.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Deleted Successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void viewAll() {
        try (Connection con = DBConnection.getConnection()) {
            String query = "SELECT * FROM responsible_authorities";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            JTextArea textArea = new JTextArea();
            while (rs.next()) {
                textArea.append("ID: " + rs.getInt("resp_id") +
                        ", Name: " + rs.getString("r_name") +
                        ", Location: " + rs.getString("location") + "\n");
            }
            JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Authorities", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
