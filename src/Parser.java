import jdk.swing.interop.SwingInterOpUtils;

import java.io.File;
import java.util.*;

public class Parser {

    private static Set openParen = new HashSet<>(Arrays.asList("(", "{", "["));
    private static Set closedParen = new HashSet<>(Arrays.asList(")", "}", "]"));
    private static Set operators = new HashSet<>(Arrays.asList("+", "-", "*", "/"));

    public static boolean isParen(String token){
        return(openParen.contains(token) || closedParen.contains(token));
    }

    public static boolean isOpenParen(String token){
        return(openParen.contains(token));
    }

    public static boolean isClosedParen(String token){
        return(closedParen.contains(token));
    }

    public static boolean hasHigherPrec(String operatorLeft, String operatorRight){
        Map<String, Integer> precMap = new HashMap<>();
        if(isParen(operatorLeft) || isParen(operatorRight)){
            return false;
        }
        precMap.put("*", 1);
        precMap.put("/", 1);
        precMap.put("+", 0);
        precMap.put("-", 0);
        return precMap.get(operatorLeft) >= precMap.get(operatorRight);
    }

    public static boolean isOperator(String token) {
        //switch
        switch (token) {
            case "*":
            case "+":
            case "-":
            case "/":
            case "^":
                return true;
            default:
                return false;
        }
    }

    public static boolean isOperator2(String token) {
        Set<String> set = new HashSet<>(); //or fill in Arrays.asList("+", "-", "*", "/"));
        set.add("-");
        set.add("*");
        set.add("+");
        set.add("/");
        return (set.contains(token));
    }

    public static double evaluate(Double opLeft, Double opRight, String op) {
        switch (op) {
            case "+":
                return opLeft + opRight;
            case "-":
                return opLeft - opRight;
            case "/":
                return opLeft / opRight;
            case "*":
                return opLeft * opRight;
            default:
                return 0.0;
        }
    }

    public static double evalPostFix(String expression) {
        String[] arr = expression.split(":");
        Stack<Double> stack = new Stack<>();

        for (int m = 0; m < arr.length; ++m) {
            if (!isOperator(arr[m]) && !isParen(arr[m])) {
                stack.push(Double.parseDouble(arr[m]));
            } else if (!isParen(arr[m])) {
                Double a = stack.pop();
                Double b = stack.pop();
                Double answer = evaluate(b, a, arr[m]);
                stack.push(answer);
            }
        }
        return stack.pop();

    }

    public static String inToPost(String expression){
        Stack<String> stack = new Stack<>();
        String answer = "";
        String [] tokens = expression.split(" ");
        for(int i = 0; i < tokens.length; ++i) {
             if (!isOperator(tokens[i]) && !isParen(tokens[i])) {
                answer += tokens[i];
            }
             else if (isOperator(tokens[i])) {
                 while ((!stack.isEmpty() && !isOpenParen(stack.peek()) && hasHigherPrec(stack.peek(), tokens[i]))) {
                     answer += stack.peek();
                     stack.pop(); //<- separate
                 }
                 stack.push(tokens[i]);
             }
             else if(isOpenParen(tokens[i])){
                  // while its not a closed paren
                     stack.push(tokens[i]);
             }
             else if (isClosedParen(tokens[i])) {
                 while(!stack.isEmpty() && !isOpenParen(stack.peek())){
                     answer += stack.peek();
                     stack.pop(); // <- same here
                 }
                 stack.pop();
             }
        }
        while(!stack.isEmpty()){
            answer += stack.peek();
            stack.pop(); // <- same here
        }
        return answer;
    }

    public static void main (String[]args) throws Exception{
        /*assert(isOperator("*")) : "does not recognize * as an operator";
        assert(isOperator("/")) : "does not recognize * as an operator";
        //assert(isOperator(")") == true) : "does not recognize ) as an operator";
        assert(isOperator2("*"));
        assert(isOperator2(","));
        System.out.println( "all tests passed"); */

        File file = new File(args[0]);
        //try {
            Scanner p = new Scanner(file);
            final int num = Integer.parseInt(p.nextLine());
            for (int i = 0; i < num; ++i) {
                String expression = p.nextLine();
                System.out.println(inToPost(expression));
            }

        //}
       // catch (Exception o) {
        //    System.out.println("File not found");
        //}
    }
}
