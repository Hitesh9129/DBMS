import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class CollectionPointCRUD extends JFrame {
    private JTextField idField, typeField, capacityField, locationField;

    public CollectionPointCRUD() {
        setTitle("Collection Point Manager");
        setLayout(null);
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // So it doesn't close whole app

        JLabel idLbl = new JLabel("Point ID:");
        idLbl.setBounds(20, 20, 100, 25);
        add(idLbl);
        idField = new JTextField();
        idField.setBounds(130, 20, 250, 25);
        add(idField);

        JLabel typeLbl = new JLabel("Type:");
        typeLbl.setBounds(20, 60, 100, 25);
        add(typeLbl);
        typeField = new JTextField();
        typeField.setBounds(130, 60, 250, 25);
        add(typeField);

        JLabel capacityLbl = new JLabel("Capacity:");
        capacityLbl.setBounds(20, 100, 100, 25);
        add(capacityLbl);
        capacityField = new JTextField();
        capacityField.setBounds(130, 100, 250, 25);
        add(capacityField);

        JLabel locationLbl = new JLabel("Location:");
        locationLbl.setBounds(20, 140, 100, 25);
        add(locationLbl);
        locationField = new JTextField();
        locationField.setBounds(130, 140, 250, 25);
        add(locationField);

        JButton insertBtn = new JButton("Insert");
        insertBtn.setBounds(20, 190, 90, 30);
        add(insertBtn);

        JButton updateBtn = new JButton("Update");
        updateBtn.setBounds(120, 190, 90, 30);
        add(updateBtn);

        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setBounds(220, 190, 90, 30);
        add(deleteBtn);

        JButton viewBtn = new JButton("View All");
        viewBtn.setBounds(320, 190, 90, 30);
        add(viewBtn);

        insertBtn.addActionListener(e -> insert());
        updateBtn.addActionListener(e -> update());
        deleteBtn.addActionListener(e -> delete());
        viewBtn.addActionListener(e -> viewAll());

        setVisible(true);
    }

    private void insert() {
        try (Connection con = DBConnection.getConnection()) {
            String query = "INSERT INTO collection_points (point_id, type, capacity, location) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(idField.getText()));
            ps.setString(2, typeField.getText());
            ps.setInt(3, Integer.parseInt(capacityField.getText()));
            ps.setString(4, locationField.getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Inserted Successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Insert Failed!");
        }
    }

    private void update() {
        try (Connection con = DBConnection.getConnection()) {
            String query = "UPDATE collection_points SET type=?, capacity=?, location=? WHERE point_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, typeField.getText());
            ps.setInt(2, Integer.parseInt(capacityField.getText()));
            ps.setString(3, locationField.getText());
            ps.setInt(4, Integer.parseInt(idField.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Updated Successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Update Failed!");
        }
    }

    private void delete() {
        try (Connection con = DBConnection.getConnection()) {
            String query = "DELETE FROM collection_points WHERE point_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(idField.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Deleted Successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Delete Failed!");
        }
    }

    private void viewAll() {
        try (Connection con = DBConnection.getConnection()) {
            String query = "SELECT * FROM collection_points";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            JTextArea textArea = new JTextArea();
            while (rs.next()) {
                textArea.append("ID: " + rs.getInt("point_id")
                        + ", Type: " + rs.getString("type")
                        + ", Capacity: " + rs.getInt("capacity")
                        + ", Location: " + rs.getString("location") + "\n");
            }
            JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Collection Points", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "View Failed!");
        }
    }
}
