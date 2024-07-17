import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {
    private JTextField firstNumberField;
    private JTextField secondNumberField;
    private JTextField resultField;
    private JPanel buttonPanel;

    private String[] operators = { "+", "-", "*", "/", "C" };

    public Calculator() {
        firstNumberField = new JTextField();
        secondNumberField = new JTextField();
        resultField = new JTextField();
        resultField.setEditable(false);

        firstNumberField.setPreferredSize(new Dimension(100, 30));
        secondNumberField.setPreferredSize(new Dimension(100, 30));
        resultField.setPreferredSize(new Dimension(100, 30));

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 5, 10, 10));

        for (String operator : operators) {
            JButton button = new JButton(operator);
            button.addActionListener(this);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            buttonPanel.add(button);
        }

        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 10, 10));
        inputPanel.add(new JLabel("First Number:", JLabel.RIGHT));
        inputPanel.add(firstNumberField);
        inputPanel.add(new JLabel("Second Number:", JLabel.RIGHT));
        inputPanel.add(secondNumberField);
        inputPanel.add(new JLabel("Result:", JLabel.RIGHT));
        inputPanel.add(resultField);

        // Add input panel and button panel to the frame
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Set frame properties
        setTitle("Calculator");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        try {
            double firstNumber = Double.parseDouble(firstNumberField.getText());
            double secondNumber = Double.parseDouble(secondNumberField.getText());
            double result = 0;

            switch (command) {
                case "+":
                    result = firstNumber + secondNumber;
                    break;
                case "-":
                    result = firstNumber - secondNumber;
                    break;
                case "*":
                    result = firstNumber * secondNumber;
                    break;
                case "/":
                    result = firstNumber / secondNumber;
                    break;
                case "C":
                    firstNumberField.setText("");
                    secondNumberField.setText("");
                    resultField.setText("");
                    return;
            }
            resultField.setText(String.valueOf(result));
        } catch (NumberFormatException ex) {
            resultField.setText("Error");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calculator::new);
    }
}
