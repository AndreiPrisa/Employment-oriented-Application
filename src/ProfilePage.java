import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class ProfilePage extends JFrame implements ActionListener {
    private String userType;
    private JButton searchButton, backButton, clearAllButton;
    private JTextField searchField;
    private JTextArea infoArea;
    private JLabel searchLabel, userLabel, notificationLabel;
    private JScrollPane infoPane, notificationPane;
    private JPanel mainPanel, notificationPanel;
    private JList<Object> notificationJList;
    private Object[] nullList = new Object[0];

    ProfilePage(String userType) {
        int factor = 4;
        Application application = Application.getInstance();
        this.userType = userType;


        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(1600, 1000));
        //setResizable(false);
        setLayout(new GridBagLayout());

        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;

        // search label
        searchLabel = new JLabel("Search for user");
        Font font = searchLabel.getFont();
        searchLabel.setFont(FontAdjuster.increaseFont(font, Font.PLAIN, factor));
        searchLabel.setBorder(BorderFactory.createLineBorder(Color.black));

        c.weighty = 0.0;
        c.gridx = 0;
        c.gridy = 0;
        mainPanel.add(searchLabel, c);

        // search field
        searchField = new JTextField(100);
        searchField.setFont(FontAdjuster.increaseFont(font, Font.PLAIN, factor));

        c.weighty = 0.0;
        c.gridy = 1;
        mainPanel.add(searchField, c);



        // search button
        searchButton = new JButton("search");
        searchButton.setFont(FontAdjuster.increaseFont(font, Font.PLAIN, factor));
        searchButton.addActionListener(this);

        c.weighty = 0.1;
        c.gridy = 2;
        c.gridx = 0;
        mainPanel.add(searchButton, c);

        // info area
        infoArea = new JTextArea();
        infoArea.setFont(FontAdjuster.increaseFont(font, Font.PLAIN, factor));
        infoArea.setEditable(false);

        // info pane
        infoPane = new JScrollPane(infoArea);
        c.weighty = 1.0;
        c.gridy = 3;
        mainPanel.add(infoPane, c);



        // back button
        backButton = new JButton("Back");
        backButton.setFont(FontAdjuster.increaseFont(font, Font.PLAIN, factor));
        backButton.addActionListener(this);

        c.gridheight = 1;
        c.weighty = 0.1;
        c.gridy = 4;
        mainPanel.add(backButton, c);

        c.weightx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 2;
        add(mainPanel, c);




        // clear all button
        clearAllButton = new JButton("Clear All");
        clearAllButton.setFont(FontAdjuster.increaseFont(font, Font.PLAIN, factor));
        clearAllButton.addActionListener(this);
        for (User user : application.getUsers()) {
            if (user.getName().equals(userType)) {
                setPreferredSize(new Dimension(2500, 1000));

                // notification panel
                notificationPanel = new JPanel(new GridBagLayout());
                notificationPanel.setBorder(BorderFactory.createLineBorder(Color.black));

                c.gridheight = 1;

                // user label
                userLabel = new JLabel("Logged in as " + userType, SwingConstants.CENTER);
                userLabel.setFont(FontAdjuster.increaseFont(font, Font.PLAIN, factor));
                userLabel.setBorder(BorderFactory.createLineBorder(Color.black));
                c.weighty = 0.0;
                c.gridx = 0;
                c.gridy = 0;
                notificationPanel.add(userLabel, c);

                // notification label
                notificationLabel = new JLabel("Notifications:");
                notificationLabel.setFont(FontAdjuster.increaseFont(font, Font.PLAIN, factor));
                c.gridy = 1;
                notificationPanel.add(notificationLabel, c);

                // notification list

                notificationJList = new JList<>();

                if (user.getNotifications() != null) {
                    ArrayList<Notification> reverseNotifications = new ArrayList<>();
                    int size = user.getNotifications().size();
                    for (int i = size - 1; i >= 0; i--)
                        reverseNotifications.add(user.getNotifications().get(i));
                    notificationJList.setListData(reverseNotifications.toArray());
                }

                else
                    notificationJList.setListData(nullList);

                notificationJList.setFont(FontAdjuster.increaseFont(font, Font.PLAIN, factor / 2));

                //notification pane
                notificationPane = new JScrollPane(notificationJList);
                c.gridy = 2;
                c.weighty = 1.0;
                notificationPanel.add(notificationPane, c);

                // clear all button
                clearAllButton = new JButton("Clear All");
                clearAllButton.setFont(FontAdjuster.increaseFont(font, Font.PLAIN, factor));
                clearAllButton.addActionListener(this);

                if(user.getNotifications() == null || user.getNotifications().isEmpty())
                    clearAllButton.setEnabled(false);

                c.gridy = 3;
                c.weighty = 0.1;
                notificationPanel.add(clearAllButton, c);

                c.weightx = 0.5;
                c.gridheight = 2;
                c.gridx = 1;
                c.gridy = 0;
                add(notificationPanel, c);
            }
        }


        pack();
        setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(backButton)) {
            new PageSelection(userType);
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }

        if (e.getSource().equals(searchButton)) {
            Application application = Application.getInstance();
            String searchedName = searchField.getText();

            for (User u : application.getUsers()) {
                if (u.getName().equals(searchedName)) {
                    infoArea.setBackground(Color.WHITE);
                    infoArea.setText(u.getResume().toString());
                    return;
                }
            }
            infoArea.setText("USER NOT FOUND");
            infoArea.setBackground(Color.RED);

        }

        if  (e.getSource().equals(clearAllButton)) {
            Application application = Application.getInstance();

            for (User user : application.getUsers()) {
                if (user.getName().equals(userType)) {
                    user.clearNotifications();
                    notificationJList.setListData(nullList);
                    clearAllButton.setEnabled(false);
                }
            }
        }
    }
}
