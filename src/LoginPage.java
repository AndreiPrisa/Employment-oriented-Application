import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class LoginPage extends JFrame implements ActionListener {
    private final int factor = 4;
    private JTextField usernameText;
    private JPasswordField passwordText;

    // credentials
    private static ArrayList<String> users; // password: user
    private static ArrayList<String> managers; // password: manager
    private static ArrayList<String> employees; // password: employee

    static {
        Application application = Application.getInstance();

        managers = new ArrayList<>();

        for (Company company : application.getCompanies()) {
            managers.add(company.getManager().getName());
        }
    }

    {
        Application application = Application.getInstance();

        users = new ArrayList<>();
        users.add("user");
        for (User user : application.getUsers()) {
            users.add(user.getName());
        }

        employees = new ArrayList<>();
        for (Company company : application.getCompanies()) {
            for (Department department : company.getDepartments()) {
               for (Employee employee : department.getEmployees())
                   employees.add(employee.getName());
            }
        }

    }

    public LoginPage() {
        super("Login Page");

        JButton loginButton;
        JLabel usernameLabel = new JLabel("username", SwingConstants.CENTER),
                passwordLabel = new JLabel("password", SwingConstants.CENTER),
                loginLabel = new JLabel("Login to Account", SwingConstants.CENTER);

        Font labelFont = usernameLabel.getFont();
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 20));

        loginLabel.setFont(FontAdjuster.increaseFont(labelFont, Font.PLAIN, factor));
        add(loginLabel);

        panel.setPreferredSize(new Dimension(600, 500));
        add(panel);



        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 500));
        setLayout(new GridLayout(3, 1, 0, 50));
        setResizable(false);

        usernameLabel.setFont(FontAdjuster.increaseFont(labelFont, Font.PLAIN, factor));
        panel.add(usernameLabel);

        // username text
        usernameText = new JTextField(50);
        usernameText.setFont(FontAdjuster.increaseFont(labelFont, Font.PLAIN, factor));
        panel.add(usernameText);

        panel.add(passwordLabel);
        passwordLabel.setFont(FontAdjuster.increaseFont(labelFont, Font.PLAIN, factor));

        passwordText = new JPasswordField();
        passwordText.setFont(FontAdjuster.increaseFont(labelFont, Font.PLAIN, factor));
        panel.add(passwordText);

        // login button
        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        loginButton.setFont(FontAdjuster.increaseFont(labelFont, Font.PLAIN, factor / 2));
        add(loginButton);

        pack();
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String userName = usernameText.getText();
        String password = passwordText.getText();

            if (users.contains(userName) && password.equals("user")) {
                new PageSelection(userName);
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                return;
            }

            if (managers.contains(userName) && password.equals("manager")) {
                new PageSelection(userName);
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                return;
            }

            if (employees.contains(userName) && password.equals("employee")) {
                new PageSelection(userName);
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                return;
            }


        // authentification error
        usernameText.setBackground(Color.RED);
        usernameText.setText("Invalid credentials");
        usernameText.setForeground(Color.gray);
        passwordText.setBackground(Color.RED);

    }

    public static ArrayList<String> getManagers() {
        return managers;
    }
}
