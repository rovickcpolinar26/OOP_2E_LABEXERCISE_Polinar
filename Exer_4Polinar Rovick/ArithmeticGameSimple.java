import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class ArithmeticGameSimple implements ActionListener {

    JFrame frame;
    JLabel number1Label, operatorLabel, number2Label, equalsLabel;
    JTextField answerField;
    JButton submitButton;
    JLabel scoreLabel, correctLabel, wrongLabel, correctScore, wrongScore;
    JRadioButton addBtn, subBtn, mulBtn, divBtn, randomBtn;
    JRadioButton level1, level2, level3;
    ButtonGroup operationGroup, levelGroup;
    Random rand = new Random();

    int number1, number2, correctAnswer;
    int correctCount = 0, wrongCount = 0;
    String operator = "+";

    public ArithmeticGameSimple() {
        
        frame = new JFrame("Arithmetic Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 550);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setResizable(true); // ✅ Allow resizing

        Font mainFont = new Font("Segoe UI", Font.BOLD, 26);
        Font smallFont = new Font("Segoe UI", Font.PLAIN, 15);

        number1Label = createBoxLabel("12", 200, 80, 100, 70, mainFont);
        operatorLabel = createBoxLabel("+", 330, 80, 70, 70, mainFont);
        number2Label = createBoxLabel("15", 430, 80, 100, 70, mainFont);
        equalsLabel = createBoxLabel("=", 550, 80, 60, 70, mainFont);

        answerField = new JTextField();
        answerField.setBounds(630, 80, 110, 70);
        answerField.setHorizontalAlignment(JTextField.CENTER);
        answerField.setFont(mainFont);
        answerField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        frame.add(answerField);

        // === OPERATION PANEL ===
        addBtn = createRadioButton("Addition");
        subBtn = createRadioButton("Subtraction");
        mulBtn = createRadioButton("Multiplication");
        divBtn = createRadioButton("Division");
        randomBtn = createRadioButton("Random");

        operationGroup = new ButtonGroup();
        operationGroup.add(addBtn);
        operationGroup.add(subBtn);
        operationGroup.add(mulBtn);
        operationGroup.add(divBtn);
        operationGroup.add(randomBtn);
        addBtn.setSelected(true);

        JPanel operationPanel = new JPanel();
        operationPanel.setLayout(new GridLayout(5, 1, 5, 5));
        operationPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1), "Operation"));
        operationPanel.setBounds(150, 200, 200, 180);
        operationPanel.setBackground(Color.WHITE); // ✅ White background
        operationPanel.add(addBtn);
        operationPanel.add(subBtn);
        operationPanel.add(mulBtn);
        operationPanel.add(divBtn);
        operationPanel.add(randomBtn);
        frame.add(operationPanel);

        // === LEVEL PANEL ===
        level1 = createRadioButton("Level 1 (1–10)");
        level2 = createRadioButton("Level 2 (10–20)");
        level3 = createRadioButton("Level 3 (20–100)");

        levelGroup = new ButtonGroup();
        levelGroup.add(level1);
        levelGroup.add(level2);
        levelGroup.add(level3);
        level1.setSelected(true);

        JPanel levelPanel = new JPanel();
        levelPanel.setLayout(new GridLayout(3, 1, 5, 5));
        levelPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1), "Level"));
        levelPanel.setBounds(400, 200, 200, 130);
        levelPanel.setBackground(Color.WHITE); // ✅ White background
        levelPanel.add(level1);
        levelPanel.add(level2);
        levelPanel.add(level3);
        frame.add(levelPanel);

        // === SUBMIT BUTTON ===
        submitButton = new JButton("SUBMIT");
        submitButton.setBounds(650, 220, 120, 45);
        submitButton.setBackground(new Color(240, 240, 240));
        submitButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        submitButton.setFocusPainted(false);
        frame.add(submitButton);

        // === SCORE SECTION ===
        scoreLabel = new JLabel("Score", SwingConstants.CENTER);
        scoreLabel.setBounds(650, 280, 120, 25);
        scoreLabel.setFont(smallFont);
        frame.add(scoreLabel);

        correctLabel = new JLabel("Correct", SwingConstants.CENTER);
        wrongLabel = new JLabel("Wrong", SwingConstants.CENTER);
        correctLabel.setFont(smallFont);
        wrongLabel.setFont(smallFont);

        correctLabel.setBounds(640, 380, 60, 25);
        wrongLabel.setBounds(730, 380, 60, 25);

        correctScore = createBoxLabel("0", 640, 340, 60, 40, mainFont);
        wrongScore = createBoxLabel("0", 730, 340, 60, 40, mainFont);

        frame.add(correctLabel);
        frame.add(wrongLabel);

        // === EVENT HANDLERS ===
        submitButton.addActionListener(this);
        addBtn.addActionListener(this);
        subBtn.addActionListener(this);
        mulBtn.addActionListener(this);
        divBtn.addActionListener(this);
        randomBtn.addActionListener(this);
        level1.addActionListener(this);
        level2.addActionListener(this);
        level3.addActionListener(this);

        frame.setVisible(true);
        generateQuestion();
    }

    private JLabel createBoxLabel(String text, int x, int y, int w, int h, Font f) {
        JLabel lbl = new JLabel(text, SwingConstants.CENTER);
        lbl.setFont(f);
        lbl.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        lbl.setBounds(x, y, w, h);
        lbl.setOpaque(true);
        lbl.setBackground(Color.WHITE);
        frame.add(lbl);
        return lbl;
    }

    private JRadioButton createRadioButton(String text) {
        JRadioButton btn = new JRadioButton(text);
        btn.setBackground(Color.WHITE); // ✅ White background
        btn.setOpaque(true);
        return btn;
    }

    private void generateQuestion() {
        int min = 1, max = 10;
        if (level2.isSelected()) { min = 10; max = 20; }
        else if (level3.isSelected()) { min = 20; max = 100; }

        number1 = rand.nextInt(max - min + 1) + min;
        number2 = rand.nextInt(max - min + 1) + min;

        if (randomBtn.isSelected()) {
            String[] ops = {"+", "-", "*", "/"};
            operator = ops[rand.nextInt(4)];
        } else if (addBtn.isSelected()) operator = "+";
        else if (subBtn.isSelected()) operator = "-";
        else if (mulBtn.isSelected()) operator = "*";
        else if (divBtn.isSelected()) operator = "/";

        if (operator.equals("/") && number2 == 0) number2 = 1;

        switch (operator) {
            case "+": correctAnswer = number1 + number2; break;
            case "-": correctAnswer = number1 - number2; break;
            case "*": correctAnswer = number1 * number2; break;
            case "/": correctAnswer = number1 / number2; break;
        }

        number1Label.setText(String.valueOf(number1));
        operatorLabel.setText(operator);
        number2Label.setText(String.valueOf(number2));
        answerField.setText("");
    }

    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == submitButton) {
            try {
                int ans = Integer.parseInt(answerField.getText());
                if (ans == correctAnswer) {
                    correctCount++;
                    JOptionPane.showMessageDialog(frame, "✅ Correct!");
                } else {
                    wrongCount++;
                    JOptionPane.showMessageDialog(frame, "❌ Wrong! Correct answer: " + correctAnswer);
                }
                correctScore.setText(String.valueOf(correctCount));
                wrongScore.setText(String.valueOf(wrongCount));
                generateQuestion();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a number!");
            }
        } else {
            generateQuestion();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ArithmeticGameSimple::new);
    }
}
