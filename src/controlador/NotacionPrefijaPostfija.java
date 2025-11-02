package controlador; 

import javax.swing.JOptionPane;
import modelo.Stack; // Importa tu clase Stack personalizada
import java.util.StringTokenizer;
import java.lang.Math;

public class NotacionPrefijaPostfija { 

	/**
     * Convierte una expresión Infija a Postfija.
     */
    public String convertToPostfix(String infix) {
        if (!validateExpression(infix)) return "";
        try {
            String postfix = infixToPostfix(infix);
            return "Expresión Postfija:\\n" + postfix;
        } catch (Exception e) {
            showError("Error al convertir a notación postfija. Verifique la sintaxis");
            return "";
        }
    }

    /**
     * Convierte una expresión Infija a Prefija.
     */
    public String convertToPrefix(String infix) {
        if (!validateExpression(infix)) return "";
        try {
            String prefix = infixToPrefix(infix);
            return "Expresión Prefija:\\n" + prefix;
        } catch (Exception e) {
            showError("Error al convertir a notación prefija. Verifique la expresión.");
            return "";
        }
    }

    /**
     * Evalúa una expresión postfija y genera los pasos.
     */
    public String evaluatePostfix(String infix) {
        if (!validateExpression(infix)) return "";
        if (infix.matches(".*[a-zA-Z].*")) {
            showError("No se puede evaluar una expresión con variables (letras).");
            return "";
        }

        try {
            String postfix = infixToPostfix(infix);
            StringBuilder steps = new StringBuilder("Expresión Postfija: " + postfix + "\\n\\nEvaluación paso a paso:\\n");
            
            double result = evaluatePostfixStepByStep(postfix, steps);
            
            if (result != Double.MIN_VALUE) { 
                 steps.append("\nResultado final: ").append(result);
            } else {
                 return ""; // Error handled inside evaluation method
            }

            return steps.toString();
        } catch (Exception e) {
            showError("Error al evaluar la expresión. Verifique la sintaxis.");
            return "";
        }
    }

    // ===================== VALIDATIONS AND UTILITIES =====================

    private boolean validateExpression(String expression) {
        // Validation logic remains the same (parentheses balance, invalid chars, etc.)
        if (expression == null || expression.isEmpty()) {
            showError("Por favor, ingrese una expresión antes de continuar.");
            return false;
        }

        if (!expression.matches("[a-zA-Z0-9+\\-*/^()\\s\\.]+")) {
            showError("La expresión contiene caracteres inválidos.");
            return false;
        }

        int balance = 0;
        for (char c : expression.toCharArray()) {
            if (c == '(') balance++;
            if (c == ')') balance--;
            if (balance < 0) {
                showError("Paréntesis mal colocados (cierre antes de apertura).");
                return false;
            }
        }
        if (balance != 0) {
            showError("Paréntesis desbalanceados.");
            return false;
        }

        if (expression.replaceAll("\\s+", "").matches(".*[+\\-*/^]{2,}.*") || expression.replaceAll("\\s+", "").matches("^[+\\-*/^].*")) {
             showError("Hay operadores consecutivos o al inicio de la expresión.");
             return false;
        }

        return true;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    

    public boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    public int infixPrecedence(char op) {
        switch (op) {
            case '^': return 4;
            case '*': case '/': return 2;
            case '+': case '-': return 1;
            case '(' : return 5; 
            default: return 0;
        }
    }

    public int stackPrecedence(char op) {
        switch (op) {
            case '^': return 3; 
            case '*': case '/': return 2;
            case '+': case '-': return 1;
            case '(' : return 0; 
            default: return 0;
        }
    }

    /**
     * Realiza la operación entre dos números.
     */
    public double operate(double a, char op, double b) throws Exception {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/':
                if (b == 0) {
                    showError("Error: División por cero.");
                    throw new Exception("División por cero");
                }
                return a / b;
            case '^': return Math.pow(a, b);
            default: return 0;
        }
    }

    
    
    @SuppressWarnings("unchecked") 
    public String infixToPostfix(String infix) throws Exception {
        StringBuilder output = new StringBuilder();
        Stack stack = new Stack(); 
        char[] chars = infix.replaceAll("\\s+", "").toCharArray();
        String number = ""; 

        for (char c : chars) {
            if (Character.isDigit(c) || c == '.') {
                number += c;
            } else {
                if (!number.isEmpty()) {
                    output.append(number).append(' ');
                    number = "";
                }

                if (Character.isLetter(c)) {
                    output.append(c).append(' ');
                } else if (c == '(') {
                    stack.push(c);
                } else if (c == ')') {
                    while (!stack.isEmpty() && (char)stack.peek() != '(') {
                        output.append(stack.pop()).append(' ');
                    }
                    if (!stack.isEmpty()) stack.pop(); 
                } else if (isOperator(c)) {
                    char topOp;
                    while (!stack.isEmpty() && isOperator((char)stack.peek())) {
                        topOp = (char)stack.peek();
                        if (stackPrecedence(topOp) >= infixPrecedence(c)) {
                            output.append(stack.pop()).append(' ');
                        } else {
                            break;
                        }
                    }
                    stack.push(c);
                }
            }
        }

        if (!number.isEmpty()) {
            output.append(number).append(' ');
        }

        while (!stack.isEmpty()) {
            output.append(stack.pop()).append(' ');
        }

        return output.toString().trim();
    }

    
    
    public String infixToPrefix(String infix) throws Exception {
        // 1. Reverse the infix string
        StringBuilder reversed = new StringBuilder(infix).reverse();
        
        // 2. Swap parentheses: ( for ) and ) for (
        String swapped = reversed.toString()
                .replace('(', '☼').replace(')', '(').replace('☼', ')');
        
        // 3. Convert the modified infix to postfix
        String postfix = infixToPostfix(swapped);
        
        // 4. Reverse the resulting postfix string to get prefix
        return new StringBuilder(postfix).reverse().toString();
    }

    // ===================== EVALUATE POSTFIX STEP-BY-STEP =====================
    
    @SuppressWarnings("unchecked")
    public double evaluatePostfixStepByStep(String postfix, StringBuilder steps) throws Exception {
        Stack stack = new Stack(); 
        StringTokenizer tokens = new StringTokenizer(postfix, " "); 
        
        try {
            while (tokens.hasMoreTokens()) {
                String token = tokens.nextToken();
                
                if (token.matches("[0-9\\.]+")) { 
                    stack.push(Double.parseDouble(token));
                    
                } else if (token.length() == 1 && isOperator(token.charAt(0))) { 
                    if (stack.isEmpty()) {
                        showError("\"Error: Faltan operandos antes del operador " + token);
                        return Double.MIN_VALUE;
                    }
                    double b = (double) stack.pop(); 

                    if (stack.isEmpty()) {
                        showError("Error: Faltan operandos antes del operador " + token);
                        return Double.MIN_VALUE;
                    }
                    double a = (double) stack.pop(); 

                    double result = operate(a, token.charAt(0), b); 
                    stack.push(result);
                    
                    steps.append(String.format("%.2f %c %.2f = %.2f\n", a, token.charAt(0), b, result));
                }
            }

            double finalResult = 0;
            if (!stack.isEmpty()) {
                finalResult = (double) stack.pop(); 
                if (!stack.isEmpty()) {
                    showError("Expresión postfija inválida: sobran operandos.");
                    return Double.MIN_VALUE;
                }
            } else {
                showError("Expresión postfija inválida: faltan operandos.");
                return Double.MIN_VALUE;
            }

            return finalResult;
        } catch (ClassCastException e) {
             showError("Error en la conversión de tipos. Asegúrese de que la pila solo contenga números o caracteres según corresponda.");
             return Double.MIN_VALUE;
        }
    }
}
