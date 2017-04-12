package univ.lecture.riotapi.controller;

/**
 * Created by Calvus on 2017-04-12.
 */

import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.List;
import java.util.ArrayList;

public class Calculator {

    public String calc(String token) {

        final Calculator calculator = new Calculator();

        String formula = calculator.formulaConvert(token);
        String result = calculator.calculate(formula);

        return result;
    }

    private static boolean isNumeric(char ch) {
        return (ch >= '0' && ch <= '9');
    }

    private static int operatorPriority(char operator) {
        if(operator == '(') return 0;
        if(operator == '+' || operator == '-') return 1;
        if(operator == '*' || operator == '/') return 2;
        return 3;
    }

    private static boolean isOperator(char ch) {
        return (ch == '+' || ch == '-' || ch == '*' || ch == '/');
    }

    public String formulaConvert(String input) {

        ArrayStack stack = new ArrayStack();
        char[] exp;
        char ch;
        StringBuffer sb = new StringBuffer();
        exp = input.toCharArray();

        for(int i=0; i< exp.length; i++) {
            if(exp[i] == '(') {
                stack.push(exp[i]);
            } else if(exp[i] == ')') {
                while((ch = (Character)stack.pop()) != '(') {
                    sb.append(ch);
                    sb.append(' ');
                }
            } else if(isOperator(exp[i])) {
                while(!stack.isEmpty() && operatorPriority((Character)stack.peek()) >= operatorPriority(exp[i])) {
                    sb.append(stack.pop());
                    sb.append(' ');
                }
                stack.push(exp[i]);

            } else if(isNumeric(exp[i])) {
                do {
                    sb.append(exp[i++]);
                } while(i<exp.length && isNumeric(exp[i]));
                sb.append(' '); i--;
            }
        }
        while(!stack.isEmpty()) {
            sb.append(stack.pop());
            sb.append(' ');
        }
        return sb.toString();
    }

    public String calculate(String input) {
        //Process the list into an ArrayList
        List<String> processedList = new ArrayList<String>();

        if (!input.isEmpty()) {
            StringTokenizer st = new StringTokenizer(input);
            while (st.hasMoreTokens()) {
                processedList.add(st.nextToken());
            }
        } else {
            return "Error";
        }
        //A Stack, we will use this for the calculation
        ArrayStack tempList = new ArrayStack();

        //Iterate over the whole processed list
        Iterator<String> iter = processedList.iterator();
        while (iter.hasNext()) {
            String temp = iter.next();
            if (temp.matches("[0-9]*")) {
                //If the current item is a number (aka operand), push it onto the stack
                tempList.push(temp);
            }

            else if (temp.matches("[*-/+]")) {
                //If the current item is an operator we pop off the last two elements
                //of our stack and calculate them using the operator we are looking at.
                //Push the result onto the stack.
                if (temp.equals("*")) {
                    int rs = Integer.parseInt(tempList.pop().toString());
                    int ls = Integer.parseInt(tempList.pop().toString());
                    int result = ls * rs;
                    tempList.push("" + result);
                }

                else if (temp.equals("-")) {
                    int rs = Integer.parseInt(tempList.pop().toString());
                    int ls = Integer.parseInt(tempList.pop().toString());
                    int result = ls - rs;
                    tempList.push("" + result);
                }

                else if (temp.equals("/")) {
                    int rs = Integer.parseInt(tempList.pop().toString());
                    int ls = Integer.parseInt(tempList.pop().toString());
                    int result = ls / rs;
                    tempList.push("" + result);
                }

                else if (temp.equals("+")) {
                    int rs = Integer.parseInt(tempList.pop().toString());
                    int ls = Integer.parseInt(tempList.pop().toString());
                    int result = ls + rs;
                    tempList.push("" + result);
                }
            } else {
                return "Error";
            }
        }
        //Return the last element on the Stack.
        return tempList.pop().toString();
    }
}
