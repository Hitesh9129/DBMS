import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class ManagesCRUD extends JFrame {
    JTextField respIdField, pointIdField;
    JButton insertBtn, deleteBtn, viewBtn;

    public ManagesCRUD() {
        setTitle("Manages Table");
        setLayout(null);
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel respLbl = new JLabel("Authority ID:");
        respLbl.setBounds(20, 30, 100, 25);
        add(respLbl);
        respIdField = new JTextField();
        respIdField.setBounds(130, 30, 150, 25);
        add(respIdField);

        JLabel pointLbl = new JLabel("Point ID:");
        pointLbl.setBounds(20, 70, 100, 25);
        add(pointLbl);
        pointIdField = new JTextField();
        pointIdField.setBounds(130, 70, 150, 25);
        add(pointIdField);

        insertBtn = new JButton("Insert");
        insertBtn.setBounds(20, 120, 80, 30);
        add(insertBtn);

        deleteBtn = new JButton("Delete");
        deleteBtn.setBounds(120, 120, 80, 30);
        add(deleteBtn);

        viewBtn = new JButton("View All");
        viewBtn.setBounds(220, 120, 90, 30);
        add(viewBtn);

        insertBtn.addActionListener(e -> insert());
        deleteBtn.addActionListener(e -> delete());
        viewBtn.addActionListener(e -> viewAll());

        setVisible(true);
    }

    private void insert() {
        try (Connection con = DBConnection.getConnection()) {
            String query = "INSERT INTO manages (resp_id, point_id) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(respIdField.getText()));
            ps.setInt(2, Integer.parseInt(pointIdField.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Inserted Successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void delete() {
        try (Connection con = DBConnection.getConnection()) {
            String query = "DELETE FROM manages WHERE resp_id=? AND point_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(respIdField.getText()));
            ps.setInt(2, Integer.parseInt(pointIdField.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Deleted Successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void viewAll() {
        try (Connection con = DBConnection.getConnection()) {
            String query = "SELECT * FROM manages";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            JTextArea textArea = new JTextArea();
            while (rs.next()) {
                textArea.append("Authority ID: " + rs.getInt("resp_id") +
                        ", Point ID: " + rs.getInt("point_id") + "\n");
            }
            JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Manages Records", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
