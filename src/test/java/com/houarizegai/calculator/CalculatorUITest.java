package com.houarizegai.calculator;

import com.houarizegai.calculator.ui.CalculatorUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Field;




class CalculatorUITest {

    private CalculatorUI calculatorUI;

    @BeforeEach
    void setUp() {
        calculatorUI = new CalculatorUI();
    }

    @ParameterizedTest
    @CsvSource({"3,5,+,8", "2,8,-,-6", "44.5,10,*,445", "320,5,/,64", "3,5,%,3", "5,3,^,125"})
    void testCalculation(double firstNumber, double secondNumber, char operator, double expectedResult) {
        assertEquals(expectedResult, calculatorUI.calculate(firstNumber, secondNumber, operator));
    }
}


class CalculatorUIButtonTest {

    private CalculatorUI calculatorUI;

    @BeforeEach
    void setUp() throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(() -> calculatorUI = new CalculatorUI());
    }

    private JButton getButtonByText(String text) {
        try {
            Field[] fields = CalculatorUI.class.getDeclaredFields();
            for (Field field : fields) {
                if (field.getType() == JButton.class) {
                    field.setAccessible(true);
                    JButton button = (JButton) field.get(calculatorUI);
                    if (button != null && text.equals(button.getText())) {
                        return button;
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to access button fields", e);
        }
        return null;
    }

    @Test
    void testAdditionButtonSequence() {
        JButton btn1 = getButtonByText("1");
        JButton btnAdd = getButtonByText("+");
        JButton btn2 = getButtonByText("2");
        JButton btnEqual = getButtonByText("=");

        // Simulate button clicks
        btn1.doClick();
        btnAdd.doClick();
        btn2.doClick();
        btnEqual.doClick();

        JTextField display = getDisplay();
        assertEquals("3", display.getText());
    }

    @Test
    void testClearButton() {
        JButton btn1 = getButtonByText("1");
        JButton btnC = getButtonByText("C");

        btn1.doClick();
        btnC.doClick();

        JTextField display = getDisplay();
        assertEquals("0", display.getText());
    }

    @Test
    void testBackspaceButton() {
        JButton btn1 = getButtonByText("1");
        JButton btn2 = getButtonByText("2");
        JButton btnBack = getButtonByText("<-");

        btn1.doClick();
        btn2.doClick();
        btnBack.doClick();

        JTextField display = getDisplay();
        assertEquals("1", display.getText());
    }

    private JTextField getDisplay() {
        try {
            Field[] fields = CalculatorUI.class.getDeclaredFields();
            for (Field field : fields) {
                if (field.getType() == JTextField.class) {
                    field.setAccessible(true);
                    return (JTextField) field.get(calculatorUI);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to access display field", e);
        }
        throw new IllegalStateException("Display not found");
    }
    
    @Test
    void testDivisionByZero() {
        JButton btn1 = getButtonByText("1");
        JButton btnDiv = getButtonByText("/");
        JButton btn0 = getButtonByText("0");
        JButton btnEqual = getButtonByText("=");

        // Simulate button clicks
        btn1.doClick();
        btnDiv.doClick();
        btn0.doClick();
        btnEqual.doClick();

        JTextField display = getDisplay();
        assertEquals("Infinity", display.getText());
    }
    @Test
    void testNegativeNumber() {
        JButton btn1 = getButtonByText("1");
        JButton btnSub = getButtonByText("-");
        JButton btn2 = getButtonByText("2");
        JButton btnEqual = getButtonByText("=");

        // Simulate button clicks
        btn1.doClick();
        btnSub.doClick();
        btn2.doClick();
        btnEqual.doClick();

        JTextField display = getDisplay();
        assertEquals("-1", display.getText());
    }
    @Test
    void testSquareRoot() {
        JButton btn4 = getButtonByText("4");
        JButton btnSqrt = getButtonByText("√");
        JButton btnEqual = getButtonByText("=");

        // Simulate button clicks
        btn4.doClick();
        btnSqrt.doClick();
        btnEqual.doClick();

        JTextField display = getDisplay();
        // assertEquals("2.0", display.getText());
        assertEquals("2", display.getText());
    }
    @Test
    void testSquare() {
        JButton btn3 = getButtonByText("3");
        JButton btnSquare = getButtonByText("pow");
        JButton btn2 = getButtonByText("2");
        JButton btnEqual = getButtonByText("=");

        // Simulate button clicks
        btn3.doClick();
        btnSquare.doClick();
        btn2.doClick();
        btnEqual.doClick();

        JTextField display = getDisplay();
        assertEquals("9", display.getText());
    }
    @Test
    void testPercentage() {
        JButton btn5 = getButtonByText("5");
        JButton btn0 = getButtonByText("0");
        JButton btnPercent = getButtonByText("%");
        JButton btn1 = getButtonByText("1");

        JButton btnEqual = getButtonByText("=");

        // Simulate button clicks
        btn5.doClick();
        btn0.doClick();
        btnPercent.doClick();
        btn1.doClick();
        btnEqual.doClick();

        JTextField display = getDisplay();
        assertEquals("0.5", display.getText());
        // assertEquals("50", display.getText());
    }
    @Test
    void testDecimalNumber() {
        JButton btn1 = getButtonByText("1");
        JButton btnDot = getButtonByText(".");
        JButton btn2 = getButtonByText("2");
        JButton btnEqual = getButtonByText("=");

        // Simulate button clicks
        btn1.doClick();
        btnDot.doClick();
        btn2.doClick();
        btnEqual.doClick();

        JTextField display = getDisplay();
        assertEquals("1.2", display.getText());
    }
    @Test
    void testEquationSequence() {
        JButton btn5 = getButtonByText("5");
        JButton btnAdd = getButtonByText("+");
        JButton btn5_2 = getButtonByText("5");
        JButton btnMul = getButtonByText("*");
        JButton btn5_3 = getButtonByText("5");
        JButton btnEqual = getButtonByText("=");
        
        // Simulate button clicks
        btn5.doClick();
        btnAdd.doClick();
        btn5_2.doClick();
        btnMul.doClick();
        btn5_3.doClick();
        btnEqual.doClick();

        JTextField display = getDisplay();
        assertEquals(30, display.getText());
    }

    @Test
    void testBigdecimal() {
        JButton btn0 = getButtonByText("0");
        JButton btnDot = getButtonByText(".");
        JButton btn1 = getButtonByText("1");
        JButton btnAdd = getButtonByText("+");
        JButton btn0_1 = getButtonByText("0");
        JButton btnDot2 = getButtonByText(".");
        JButton btn2 = getButtonByText("2");
        JButton btnEqual = getButtonByText("=");

        // Simulate button clicks
        btn0.doClick();
        btnDot.doClick();
        btn1.doClick();
        btnAdd.doClick();
        btn0_1.doClick();
        btnDot2.doClick();
        btn2.doClick();
        btnEqual.doClick();

        JTextField display = getDisplay();
        assertEquals("0.3", display.getText());

    }

    @Test
    void testNegativeSquareRoot() {
        JButton btnNeg = getButtonByText("-");
        JButton btn4 = getButtonByText("4");
        JButton btnEqual1 = getButtonByText("=");
        JButton btnSqrt = getButtonByText("√");
        JButton btnEqual = getButtonByText("=");

        // Simulate button clicks
        btnNeg.doClick();
        btn4.doClick();
        btnEqual1.doClick();
        btnSqrt.doClick();
        btnEqual.doClick();

        JTextField display = getDisplay();
        assertEquals("NaN", display.getText());
    }
    @Test
    void testLogarithm() {
        JButton btn2 = getButtonByText("2");
        JButton btnLog = getButtonByText("ln");
        JButton btnEqual = getButtonByText("=");

        // Simulate button clicks
        btn2.doClick();
        btnLog.doClick();
        btnEqual.doClick();

        JTextField display = getDisplay();

        double actualValue = Double.parseDouble(display.getText());
        double expectedValue = Math.log(2);
        double tolerance = 0.0001;
        assertEquals(expectedValue, actualValue, tolerance);
    }
    @Test
    void testExponential() {
        JButton btn2 = getButtonByText("6");
        JButton btnExp = getButtonByText("pow");
        JButton btn8 = getButtonByText("8");
        JButton btnEqual = getButtonByText("=");

        // Simulate button clicks
        btn2.doClick();
        btnExp.doClick();
        btn8.doClick();
        btnEqual.doClick();

        JTextField display = getDisplay();

        double actualValue = Double.parseDouble(display.getText());
        double expectedValue = Math.pow(6, 8);
        assertEquals(expectedValue, actualValue);
    }
    @Test
    void testBigsum() {
        JButton btn1 = getButtonByText("1");
        JButton btn2 = getButtonByText("2");
        JButton btn3 = getButtonByText("3");
        JButton btn4 = getButtonByText("4");
        JButton btn5 = getButtonByText("5");
        JButton btn6 = getButtonByText("6");
        JButton btn7 = getButtonByText("7");
        JButton btn8 = getButtonByText("8");
        JButton btn9 = getButtonByText("9");
        JButton btn0 = getButtonByText("0");
        JButton btnAdd = getButtonByText("+");
        JButton btnEqual = getButtonByText("=");

        // Simulate button clicks
        btn1.doClick();
        btnAdd.doClick();
        btn2.doClick();
        btnAdd.doClick();
        btn3.doClick();
        btnAdd.doClick();
        btn4.doClick();
        btnAdd.doClick();
        btn5.doClick();
        btnAdd.doClick();
        btn6.doClick();
        btnAdd.doClick();
        btn7.doClick();
        btnAdd.doClick();
        btn8.doClick();
        btnAdd.doClick();
        btn9.doClick();
        btnAdd.doClick();
        btn0.doClick();
        
        // Finalize the calculation
        btnEqual.doClick();

        JTextField display = getDisplay();

        assertEquals("45", display.getText());
    }

}