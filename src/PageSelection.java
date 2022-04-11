import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class PageSelection extends JFrame implements ActionListener {

    private JButton adminPageButton, managerPageButton, profilePageButton, signOutButton;
    private JPanel pagesPanel;
    private final int factor = 5;
    private String userType;

    public PageSelection(String userType) {
        super("Page Selection");
        this.userType = userType;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(1600, 1000));
        setResizable(false);
        setLayout(new GridBagLayout());


        pagesPanel = new JPanel(new GridLayout(1,3));


        adminPageButton = new JButton("Admin Page");
        Font font = adminPageButton.getFont();
        adminPageButton.addActionListener(this);
        adminPageButton.setFont(FontAdjuster.increaseFont(font, Font.PLAIN, factor));

        managerPageButton = new JButton("Manager Page");
        managerPageButton.addActionListener(this);
        managerPageButton.setFont(FontAdjuster.increaseFont(font, Font.PLAIN, factor));


        if (!LoginPage.getManagers().contains(userType)) {
            managerPageButton.setEnabled(false);
            managerPageButton.setText("managers only");
        }


        profilePageButton = new JButton("Profile Page");
        profilePageButton.addActionListener(this);
        profilePageButton.setFont(FontAdjuster.increaseFont(font, Font.PLAIN, factor));

        pagesPanel.add(adminPageButton); pagesPanel.add(managerPageButton);
        pagesPanel.add(profilePageButton);

        // constraints
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        add(pagesPanel, c);


        signOutButton = new JButton("Sign Out");
        signOutButton.addActionListener(this);
        signOutButton.setFont(FontAdjuster.increaseFont(font, Font.PLAIN, factor));

        c.weighty = 0.05;
        c.gridx = 0;
        c.gridy = 1;
        add(signOutButton, c);

        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton jButton = (JButton) e.getSource();

        if (jButton.equals(adminPageButton)) {
            new AdminPage(userType);
        }

        if (jButton.equals(managerPageButton))
            new ManagerPage(userType);

        if (jButton.equals(signOutButton))
            new LoginPage();

        if (jButton.equals(profilePageButton))
            new ProfilePage(userType);

        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}