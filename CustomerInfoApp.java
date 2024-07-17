import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerInfoApp extends JFrame implements ActionListener {
    private JLabel idLabel, lastNameLabel, firstNameLabel, phoneLabel;
    private JButton previousButton, nextButton;
    private ResultSet resultSet;

    public CustomerInfoApp() {
        setTitle("Customer");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("ID:"), gbc);

        gbc.gridx = 1;
        idLabel = new JLabel();
        add(idLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Last Name:"), gbc);

        gbc.gridx = 1;
        lastNameLabel = new JLabel();
        add(lastNameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("First Name:"), gbc);

        gbc.gridx = 1;
        firstNameLabel = new JLabel();
        add(firstNameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Phone:"), gbc);

        gbc.gridx = 1;
        phoneLabel = new JLabel();
        add(phoneLabel, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        previousButton = new JButton("Previous");
        previousButton.addActionListener(this);
        previousButton.setEnabled(false);
        buttonPanel.add(previousButton);

        nextButton = new JButton("Next");
        nextButton.addActionListener(this);
        buttonPanel.add(nextButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        loadCustomerData();
    }

    private void loadCustomerData() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery("SELECT * FROM customer");
            if (resultSet.next()) {
                displayCustomerData();
            } else {
                nextButton.setEnabled(false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading customer data",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayCustomerData() {
        try {
            int customerId = resultSet.getInt("customer_id");
            String lastName = resultSet.getString("customer_last_name");
            String firstName = resultSet.getString("customer_first_name");
            String phone = resultSet.getString("customer_phone");

            idLabel.setText(String.valueOf(customerId));
            lastNameLabel.setText(lastName);
            firstNameLabel.setText(firstName);
            phoneLabel.setText(phone);

            previousButton.setEnabled(!resultSet.isFirst());
            nextButton.setEnabled(!resultSet.isLast());

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error displaying customer data",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == previousButton && resultSet.previous()) {
                displayCustomerData();
            } else if (e.getSource() == nextButton && resultSet.next()) {
                displayCustomerData();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error navigating customer data",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CustomerInfoApp app = new CustomerInfoApp();
            app.setVisible(true);
        });
    }
}
