import javax.swing.*;
import java.awt.event.*;

public class Dashboard extends JFrame {
    public Dashboard() {
        setTitle("Waste Management Dashboard");
        setSize(400, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("Select a Module:");
        title.setBounds(130, 30, 200, 30);
        add(title);

        JButton userBtn = new JButton("User Info");
        userBtn.setBounds(100, 70, 200, 30);
        add(userBtn);

        JButton complaintBtn = new JButton("Complaints");
        complaintBtn.setBounds(100, 110, 200, 30);
        add(complaintBtn);

        JButton authorityBtn = new JButton("Authorities");
        authorityBtn.setBounds(100, 150, 200, 30);
        add(authorityBtn);

        JButton collectionBtn = new JButton("Collection Points");
        collectionBtn.setBounds(100, 190, 200, 30);
        add(collectionBtn);

        JButton managesBtn = new JButton("Manages (Relation)");
        managesBtn.setBounds(100, 230, 200, 30);
        add(managesBtn);

        JButton exitBtn = new JButton("Exit");
        exitBtn.setBounds(100, 270, 200, 30);
        add(exitBtn);

        // Button listeners to open respective CRUD windows
        userBtn.addActionListener(e -> new UserInfo());
        complaintBtn.addActionListener(e -> new ComplaintsCRUD());
        authorityBtn.addActionListener(e -> new ResponsibleAuthoritiesCRUD());
        collectionBtn.addActionListener(e -> new CollectionPointCRUD());
        managesBtn.addActionListener(e -> new ManagesCRUD());
        exitBtn.addActionListener(e -> System.exit(0));
        setVisible(true);
    }

}
