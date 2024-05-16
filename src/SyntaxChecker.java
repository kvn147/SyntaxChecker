import java.io.*;
import java.util.*;

public class SyntaxChecker {

    private List<String> expressions;
    private Map<String, String> syntax;

    /**
     * Constructs a list full of expressions to validate and constructs a map
     * filled
     *
     * @param fileExp    file for expressions to validate
     * @param fileSyntax file for symbols to match
     */
    public SyntaxChecker(String fileExp, String fileSyntax) {

        expressions = new ArrayList<>();
        loadExpressions(fileExp);

        syntax = new HashMap<>();
        loadSyntax(fileSyntax);

    }

    /**
     * Loads expressions to validate from a file into an arraylist
     *
     * @param fileName file containing expressions
     */
    public void loadExpressions(String fileName) {
        try (Scanner fileIn = new Scanner(new File(fileName))) {

            while (fileIn.hasNextLine()) {
                String line = fileIn.nextLine();
                expressions.add(line);
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Loads Opening and Closing Symbols into a map
     * The opening symbol is the key, and the matching closing symbol is the value
     * For example { } would be placed in the map where key = { and value = }
     *
     * @param fileName file containing opening and closing symbols symbols
     */
    //TODO
    public void loadSyntax(String fileName) {
        // try - with clause
        try (Scanner fileIn = new Scanner(new File(fileName))) {

            // while there is a symbol in the file
            while (fileIn.hasNext()) {
                String open = fileIn.next();
                String close = fileIn.next();

                // place in syntax map where opening symbol is the key and
                // closing symbol is the value
                syntax.put(open, close);
            }
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // TODO checkExpressions method determines if the expression
    // has a closing symbol for every opening symbol

    public boolean checkExpression(String exp) {
        // Create a stack to hold opening symbols as strings
        Stack<String> openStack = new Stack<>();

        for (int i = 0; i < exp.length(); i++) {
            String character = exp.substring(i, i + 1);

            if (syntax.containsKey(character)) {
                openStack.push(character);
            } else if (syntax.containsValue(character)) { // closing symbol
                // have a closing symbol which has no match in the open stack
                if (openStack.isEmpty()) {
                    return false;
                }
                String openSymbol = openStack.pop();
                String closeSymbol = syntax.get(openSymbol);

                if (!closeSymbol.equals(character)) {
                    return false;
                }
            }
        }
        return true;
    }

    // goes through all the loaded expressions and
    // checks their syntax
    public String validate() {
        String result = "Syntax Checking... \n";
        if (expressions.size() == 0) {
            result += "No expressions to check.";
        }

        for (String s : expressions) {
            if (checkExpression(s))
                result += "  VALID -> " + s + "\n";
            else
                result += "INVALID -> " + s + "\n";
        }

        return result;
    }

    @Override
    public String toString() {
        return "SyntaxChecker{" +
                "expressions=" + expressions +
                "\n, syntax=" + syntax +
                '}';
    }
}
