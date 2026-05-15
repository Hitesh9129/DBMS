import javax.swing.*;
import java.sql.*;

public class UserInfo extends JFrame {
    JTextField userIdField, emailField;
    JButton loginBtn, registerBtn;

    public UserInfo() {
        setTitle("User Login & Registration");
        setLayout(null);
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel userIdLbl = new JLabel("User ID:");
        userIdLbl.setBounds(30, 30, 100, 25);
        add(userIdLbl);
        userIdField = new JTextField();
        userIdField.setBounds(140, 30, 200, 25);
        add(userIdField);

        JLabel emailLbl = new JLabel("Email:");
        emailLbl.setBounds(30, 70, 100, 25);
        add(emailLbl);
        emailField = new JTextField();
        emailField.setBounds(140, 70, 200, 25);
        add(emailField);

        loginBtn = new JButton("Login");
        loginBtn.setBounds(50, 130, 120, 30);
        add(loginBtn);

        registerBtn = new JButton("Register");
        registerBtn.setBounds(200, 130, 120, 30);
        add(registerBtn);

        loginBtn.addActionListener(e -> login());
        registerBtn.addActionListener(e -> register());

        setVisible(true);
    }

    private void login() {
        try (Connection con = DBConnection.getConnection()) {
            String query = "SELECT * FROM user_info WHERE user_id=? AND email=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(userIdField.getText()));
            ps.setString(2, emailField.getText());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful!\nWelcome " + rs.getString("name"));
            } else {
                JOptionPane.showMessageDialog(this, "Invalid ID or Email.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void register() {
        String name = JOptionPane.showInputDialog(this, "Enter your name:");
        String location = JOptionPane.showInputDialog(this, "Enter your location:");

        if (name == null || location == null || name.trim().isEmpty() || location.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Registration cancelled or incomplete.");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {
            String query = "INSERT INTO user_info (user_id, name, email, location) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(userIdField.getText()));
            ps.setString(2, name);
            ps.setString(3, emailField.getText());
            ps.setString(4, location);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Registration Successful!");
        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(this, "User ID already exists.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}