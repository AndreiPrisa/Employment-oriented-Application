import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.lang.reflect.AccessibleObject;
import java.util.ArrayList;

public class ManagerPage extends JFrame implements ActionListener, ListSelectionListener {
   private String userType;
   private JButton acceptButton, rejectButton, processAllButton, backButton;
   private JPanel requestsPanel, infoPanel;
   private JLabel companyLabel, managerLabel;
   Manager manager;
   Company company;
   JScrollPane requestsPane;
   JList requestJList;

    public ManagerPage(String userType) {
        int factor = 4;
        Application application = Application.getInstance();
        this.userType = userType;

        manager = null;
        company = null;

        // get company + manager
        for (Company c : application.getCompanies()) {
            if (c.getManager().getName().equals(userType)) {
                company = c;
                manager = c.getManager();
                break;
            }
        }

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(1600, 1000));
        setResizable(false);
        setLayout(new GridLayout(1, 2));

        requestsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // constraint
        c.fill = GridBagConstraints.BOTH;

        // company label
        companyLabel = new JLabel();
        if (company != null)
             companyLabel.setText(company.getName());
        else
            companyLabel.setText("NaN");
        Font font = companyLabel.getFont();
        companyLabel.setFont(FontAdjuster.increaseFont(font, Font.PLAIN, factor));
        c.weightx = 0.0;
        c.weighty = 0.1;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        requestsPanel.add(companyLabel, c);

        // manager label
        managerLabel = new JLabel();
        if (manager != null)
            managerLabel.setText(manager.getName());
        else
            managerLabel.setText("NaN");
        managerLabel.setFont(FontAdjuster.increaseFont(font, Font.PLAIN, factor));
        c.gridy = 1;
        requestsPanel.add(managerLabel, c);

        // request JList
        requestJList = new JList<>();
        updateJobs();
        requestJList.addListSelectionListener(this);
        requestJList.setFont(FontAdjuster.increaseFont(font, Font.PLAIN, factor / 2));

        // request Pane
        requestsPane = new JScrollPane(requestJList);
        c.weighty = 0.7;
        c.gridy = 2;
        requestsPanel.add(requestsPane, c);

        // accept button
        acceptButton = new JButton("Accept");
        acceptButton.setEnabled(false);
        acceptButton.addActionListener(this);
        acceptButton.setFont(FontAdjuster.increaseFont(font, Font.PLAIN, factor));
        c.weighty = 0.1;
        c.weightx = 0.5;
        c.gridwidth = 1;
        c.gridy = 3;
        c.gridx = 0;
        requestsPanel.add(acceptButton, c);

        // reject button
        rejectButton = new JButton("Reject");
        rejectButton.setEnabled(false);
        rejectButton.addActionListener(this);
        rejectButton.setFont(FontAdjuster.increaseFont(font, Font.PLAIN, factor));
        c.weighty = 0.1;
        c.weightx = 0.5;
        c.gridy = 3;
        c.gridx = 1;
        requestsPanel.add(rejectButton, c);

        // process all button
        processAllButton = new JButton("Process All");
        if (manager == null || manager.getHiringRequests() == null || manager.getHiringRequests().isEmpty())
            processAllButton.setEnabled(false);
        processAllButton.addActionListener(this);
        processAllButton.setFont(FontAdjuster.increaseFont(font, Font.PLAIN, factor));
        c.weighty = 0.1;
        c.weightx = 0.5;
        c.gridy = 4;
        c.gridx = 0;
        c.gridwidth = 2;
        requestsPanel.add(processAllButton, c);

        backButton = new JButton("Back");
        backButton.addActionListener(this);
        backButton.setFont(FontAdjuster.increaseFont(font, Font.PLAIN, factor));
        c.weighty = 0.1;
        c.weightx = 0.5;
        c.gridy = 5;
        c.gridx = 0;
        c.gridwidth = 2;
        requestsPanel.add(backButton, c);

        add(requestsPanel);
        pack();
        setVisible(true);
    }


    public void updateJobs() {
        if (manager != null && manager.getHiringRequests() != null && !manager.getHiringRequests().isEmpty()) {
            requestJList.setListData(manager.getHiringRequests().toArray());
        }
        else {
            Object[] nullList = new Object[0];
            requestJList.setListData(nullList);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(backButton)) {
            new PageSelection(userType);
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            return;
        }

        if (e.getSource().equals(processAllButton)) {
            Application application = Application.getInstance();
            ArrayList<String> companyString = new ArrayList<>();
            companyString.add(manager.getCompanyName());
            ArrayList<Job> jobs = application.getJobs(companyString);
            for (Job job : jobs) {
                manager.process(job);
            }
        }

        if (e.getSource().equals(acceptButton)) {
            manager.accept((Request<Job, Consumer>) requestJList.getSelectedValue());
        }

        if (e.getSource().equals(rejectButton)) {
            manager.reject((Request<Job, Consumer>) requestJList.getSelectedValue());
        }

        manager.updateHiringRequests();
        updateJobs();

        if (manager.getHiringRequests() == null || manager.getHiringRequests().isEmpty())
            processAllButton.setEnabled(false);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        JList jList = (JList) e.getSource();

        acceptButton.setEnabled(true);
        rejectButton.setEnabled(true);

        if (!jList.isSelectionEmpty()) {
            acceptButton.setEnabled(true);
            rejectButton.setEnabled(true);
        }
        else {
            acceptButton.setEnabled(false);
            rejectButton.setEnabled(false);
        }
    }
}
