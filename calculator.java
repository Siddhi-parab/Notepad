import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JTextField;

public class calculator {

    String operator = "";
    double num1;
    boolean operaterpreesed;

    calculator() {
        JFrame f1 = new JFrame("Calculator");
        JTextField t1 = new JTextField();
        t1.setBounds(10, 50, 363, 80);
        t1.setEditable(false);

        JPanel buttoPanel = new JPanel();
        buttoPanel.setBackground(Color.WHITE);
        buttoPanel.setBounds(10, 150, 363, 400);
        buttoPanel.setLayout(new GridLayout(5, 4, 5, 5));
        String[] buttons = {
                "CE", "%", "/", "CLEAR",
                "7", "8", "9", "+",
                "4", "5", "6", "-",
                "1", "2", "3", "*",
                "00", "0", ".", "="
        };
        for (String label : buttons) {
            JButton b1 = new JButton(label);
            b1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String buttontext = b1.getText();

                    if ("+-%*/".contains(buttontext)) {
                        if (!t1.getText().isEmpty()) {
                            num1 = Double.parseDouble(t1.getText());
                            operator = buttontext;
                            t1.setText(t1.getText() + operator);
                            operaterpreesed = true;
                        }
                    } else if (buttontext.equals("CE") || buttontext.equals("CLEAR")) {
                        t1.setText(" ");
                        num1 = 0;
                        operator = "";
                        operaterpreesed = false;
                    } else if (buttontext.equals("=")) {
                        if (operaterpreesed && !t1.getText().isEmpty()) {
                            double num2 = Double.parseDouble(t1.getText().split("\\" + operator)[1]);
                            double result = perform(num1, num2, operator);
                            t1.setText(String.valueOf(result));
                            operator = "";
                            operaterpreesed = false;
                        }
                    } else {
                        t1.setText(t1.getText() + buttontext);
                    }
                };

            });
            buttoPanel.add(b1);
        }

        f1.setVisible(true);
        f1.setLayout(null);
        f1.setSize(400, 600);
        f1.add(t1);
        f1.add(buttoPanel);
    }

    static double perform(double num1, double num2, String operator) {
        switch (operator) {

            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 != 0) {
                    return num1 / num2;
                } else {
                    return 0;
                }
            case "%":
                return num1 % num2;

            default:
                return num2;
        }
    }

    public static void main(String[] args) {
        new calculator();
    }
}