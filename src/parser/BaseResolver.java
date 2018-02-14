package parser;
/**
 * Author: Umeugwa Dabeluchi
 * Date: December 27, 2017
 * <p>
 * Base class that resolves basic mathematical operator combination.
 * <p>
 * <p>
 * Operator combination supported
 * </p>
 * ++ = +
 * <p>
 * +- = -
 * <p>
 * -- = +
 * <p>
 * -+ = -
 * <p>
 * a() = a * ()
 * <p>
 * ()() = () * ()
 */
public class BaseResolver implements ExpressionResolver {
    private Parser.Info info;
    private Logger logger;

    public BaseResolver() {
        info = new Parser.Info();
    }

    @Override
    public Parser.Info resolve(char l, char c, char r, int index) throws RuntimeException {
        checkError(l, c, r);
        info.end = index;
        info.start = index - 1;
        Parser.Info in = null;

        // -(a+b)
        if ((l == '+' || l == '-') && c == '(') {
            in = set(l + "1*(");
        }

        if (l == '+') {
            //+-
            if (c == '-')
                in = set("-");
                //++
            else if (c == '+')
                in = set("+");
            //PI = 3.
        } else if (l == '-') {
            //--
            if (c == '-')
                in = set("+");
                //-+
            else if (c == '+')
                in = set("-");
        } else if (l == ')') {
            //()()
            if (c == '(')
                in = set(")*(");
                //()a
            else if (Character.isDigit(c))
                in = set(")*" + c);
        } else if (Character.isDigit(l)) {
            //a()
            if (c == '(')
                in = set(l + "*(");
        }

        if (in != null)
            logger.log(l + "" + c + "" + r + " = " + in.s);

        return in;
    }

    @Override
    public boolean isOperator(char c) {
        return c == '*' || c == '+' ||
                c == '-' || c == '/' || c == '^';
    }

    @Override
    public boolean hasHigherPrecedence(char top, char c2) {
        if (top == '*' || top == '/')
            return c2 == '+' || c2 == '-' || c2 == '*' || c2 == '/';
        else if (top == '^')
            return true;

        else if (top == '-' || top == '+')
            return c2 == '+' || c2 == '-';

        return false;
    }

    @Override
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    Parser.Info set(String s) {
        info.s = s;
        return info;
    }

    private void checkError(char l, char c, char r) {
        if (c == '*' || c == '/' || c == '^') {
            if ((!Character.isDigit(l) && l != ')') || (!Character.isDigit(r) && r != '('))
                throwException(l, c, r);
        } else if (c == '+' || c == '-')
            if (r == Parser.NO_CHARACTER)
                throwException(l, c, r);
    }

    private void throwException(char l, char c, char r) {
        throw new IllegalOperatorException("Invalid operator combination [" + l + "" + c + "" + r + "]");
    }
}
