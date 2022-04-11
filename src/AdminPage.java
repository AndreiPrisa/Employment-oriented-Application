import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class AdminPage extends JFrame implements ActionListener, ListSelectionListener {
    private JButton backButton;
    private JPanel listPanel, infoPanel;
    private JScrollPane usersPane, companiesPane, departmentsPane, employeesPane, jobsPane;
    private JList<String> userJList, companyJList, departmentsJList, employeesJList, jobsJList;
    private String userType;
    private JLabel budget;
    private Company selectedCompany;
    private String[] nullList = new String[0];
    final private String budgetText = "Budget: ";


    public AdminPage(String userType) {
        int factor = 4;
        Application application = Application.getInstance();
        this.userType = userType;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(1600, 1000));
        setResizable(false);
        setLayout(new GridLayout(1, 2));

        GridBagConstraints c = new GridBagConstraints();
        listPanel = new JPanel(new GridBagLayout());

        JLabel userLabel = new JLabel("USER LIST", SwingConstants.CENTER),
               companyLabel = new JLabel("COMPANIES", SwingConstants.CENTER);

        // constraints
        c.fill = GridBagConstraints.BOTH;

        Font font = userLabel.getFont();
        userLabel.setFont(FontAdjuster.increaseFont(font, Font.PLAIN, factor));

        c.weightx = 1;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 0;
        listPanel.add(userLabel, c);


        // user list
        ArrayList<User> users = application.getUsers();
        String[] userString = new String[users.size()];
        for (int i = 0; i < users.size(); i++)
            userString[i] = users.get(i).getName();

        userJList = new JList<String>(userString);
        userJList.setFont(FontAdjuster.increaseFont(font, Font.PLAIN, factor));
        usersPane = new JScrollPane(userJList);


        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 1;
        listPanel.add(usersPane, c);

        companyLabel.setFont(FontAdjuster.increaseFont(font, Font.PLAIN, factor));

        c.weightx = 1;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 2;
        listPanel.add(companyLabel, c);

        // companies list
        ArrayList<Company> companies = application.getCompanies();
        String[] companyString = new String[companies.size()];
        for (int i = 0; i < companies.size(); i++)
            companyString[i] = companies.get(i).getName();

        companyJList = new JList<String>(companyString);
        companyJList.addListSelectionListener(this);
        companyJList.setFont(FontAdjuster.increaseFont(font, Font.PLAIN, factor));
        companiesPane = new JScrollPane(companyJList);


        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 3;
        listPanel.add(companiesPane, c);

        backButton = new JButton("Back");
        backButton.addActionListener(this);
        backButton.setFont(FontAdjuster.increaseFont(font, Font.PLAIN, factor));

        c.weightx = 1;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 4;
        listPanel.add(backButton, c);

        listPanel.setBackground(Color.cyan);
        add(listPanel);

        infoPanel = new JPanel(new GridBagLayout());


        JLabel departmentLabel = new JLabel("DEPARTMENTS", SwingConstants.CENTER),
                employeesLabel = new JLabel("EMPLOYEES", SwingConstants.CENTER),
                jobsLabel = new JLabel("AVAILABLE JOBS", SwingConstants.CENTER);

        // departments
        c.weightx = 1;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 0;

        departmentLabel.setFont(FontAdjuster.increaseFont(font, Font.PLAIN, factor / 2));
        infoPanel.add(departmentLabel, c);


        departmentsJList = new JList<>();
        departmentsJList.setFont(FontAdjuster.increaseFont(font, Font.PLAIN, factor));
        departmentsJList.addListSelectionListener(this);
        departmentsPane = new JScrollPane(departmentsJList);

        c.weightx = 1;
        c.weighty = 0.3;
        c.gridx = 0;
        c.gridy = 1;


        infoPanel.add(departmentsPane, c);

        // employees
        employeesLabel.setFont(FontAdjuster.increaseFont(font, Font.PLAIN, factor / 2));
        c.weightx = 1;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 2;
        infoPanel.add(employeesLabel, c);

        employeesJList = new JList<>();
        employeesJList.setFont(FontAdjuster.increaseFont(font, Font.PLAIN, factor));
        employeesPane = new JScrollPane(employeesJList);

        c.weightx = 1;
        c.weighty = 0.3;
        c.gridx = 0;
        c.gridy = 3;
        infoPanel.add(employeesPane, c);

        // jobs
        jobsLabel.setFont(FontAdjuster.increaseFont(font, Font.PLAIN, factor / 2));
        c.weightx = 1;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 4;
        infoPanel.add(jobsLabel, c);

        jobsJList = new JList<>();
        jobsJList.setFont(FontAdjuster.increaseFont(font, Font.PLAIN, factor));
        jobsPane = new JScrollPane(jobsJList);

        c.weightx = 1;
        c.weighty = 0.3;
        c.gridx = 0;
        c.gridy = 5;
        infoPanel.add(jobsPane, c);

        budget = new JLabel("", SwingConstants.CENTER);
        budget.setFont(FontAdjuster.increaseFont(font, Font.PLAIN, factor / 2));
        c.weightx = 1;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 6;
        infoPanel.add(budget, c);

        add(infoPanel);

        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new PageSelection(userType);
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        Application application = Application.getInstance();

        if (e.getSource() == companyJList) {
            Company company = application.getCompany(companyJList.getSelectedValue());
            ArrayList<Department>  departments = company.getDepartments();

            String[] departmentsString = new String[departments.size()];

            for (int i = 0; i < departments.size(); i++)
                departmentsString[i] = departments.get(i).getName() + " Department";

            departmentsJList.setListData(departmentsString);

            if (company != selectedCompany) {
                selectedCompany = company;
                departmentsJList.clearSelection();
                jobsJList.clearSelection();
                employeesJList.clearSelection();

                departmentsJList.setListData(nullList);
                jobsJList.setListData(nullList);
                employeesJList.setListData(nullList);
            }
            return;
        }

        if (e.getSource() == departmentsJList && selectedCompany != null && departmentsJList.getSelectedValue() != null) {
            employeesJList.clearSelection();
            jobsJList.clearSelection();
            String departmentName = new StringTokenizer(departmentsJList.getSelectedValue()).nextToken();
            Department department = selectedCompany.getDepartment(departmentName);

            String[] employeeString;
            String[] jobString;

            if (department.getEmployees() != null) {
                employeeString = new String[department.getEmployees().size()];
                for (int i = 0; i < department.getEmployees().size(); i++)
                    employeeString[i] = department.getEmployees().get(i).getName();
                employeesJList.setListData(employeeString);
            }
            else
                employeesJList.setListData(nullList);

            if (department.getJobs() != null) {
                jobString = new String[department.getJobs().size()];
                for (int i = 0; i < department.getJobs().size(); i++)
                    jobString[i] = department.getJobs().get(i).getJobName();
                jobsJList.setListData(jobString);
            }
            else
                jobsJList.setListData(nullList);

            budget.setText(budgetText + department.getTotalSalaryBudget());
            return;
        }

        budget.setText("");
    }
}