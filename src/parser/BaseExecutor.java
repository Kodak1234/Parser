package parser;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Author: Umeugwa Dabeluchi
 * Date: December 27, 2017
 * <p>
 * Base class that executes function call
 * <p>
 * Supported functions
 * <p>
 * square root (sqrt)
 * <p>
 * <p>
 * exponential(exp)
 * <p>
 * log
 * <p>
 * cos
 * <p>
 * sin
 * <p>
 * tan
 */
public abstract class BaseExecutor<D> implements FunctionExecutor<D> {

    private int maxLength = 4;
    private HashSet<String> funs;
    private Logger logger;

    public BaseExecutor() {
        funs = new HashSet<>(Arrays.asList("sqrt", "log", "exp"
                , "cos", "sin", "tan"));
    }

    @Override
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public boolean isFunction(char c) {
        return c == 's' || c == 'l' || c == 'e' || c == 'c'
                || c == 't';
    }

    @Override
    public Parser.Info execute(int i, String x, Parser<D> parser) {
        Parser.Info info = null;
        StringBuilder b = new StringBuilder();
        for (int j = 0; j < x.length() && j < maxLength; j++) {
            b.append(x.charAt(i + j));
            if (funs.contains(b.toString())) {
                int[] index = index(i, b.toString().length(), x);
                if (index != null) {
                    info = execFunction(parser, b.toString(),
                            x.substring(index[0], 1 + index[1]));
                    info.start = i;
                    info.end = index[1];
                }
                break;
            }
        }


        return info;
    }

    /**
     * Add a new function to the set of supported function. This function identifier
     * <p>
     * needs to be implemented in ExpressionExecutor#isFunction
     * <p>
     * and ExpressionExecutor#execFunction.
     *
     * @param fun function name
     * @see BaseExecutor#isFunction(char)
     * @see BaseExecutor#execFunction(Parser, String, String)
     */
    public void addFunction(String fun) {
        funs.add(fun.toLowerCase());
        maxLength = fun.length() > maxLength ? fun.length() : maxLength;
    }

    /**
     * Execute a function call
     *
     * @param parser parser
     * @param fun    function name
     * @param x      expression inside function brackets
     * @return Parser.Info
     * @see Parser.Info
     */
    protected Parser.Info execFunction(Parser<D> parser, String fun, String x) {
        Parser.Info info = new Parser.Info();
        double d = toDouble(parser.parse(x));
        switch (fun) {
            case "log":
                info.s = toString(Math.log10(d));
                break;
            case "sqrt":
                info.s = toString(Math.sqrt(d));
                break;
            case "exp":
                info.s = toString(Math.pow(Math.E, d));
                break;
            case "cos":
                info.s = toString(Math.cos(d));
                break;
            case "sin":
                info.s = toString(Math.sin(d));
                break;
            case "tan":
                info.s = toString(Math.tan(d));
                break;
            default:
                throw new UnsupportedOperationException(fun);
        }

        logger.log(fun + "" + x + " = " + info.s);

        return info;
    }

    /**
     * Convert type to double
     *
     * @param d type
     * @return double representation
     */
    protected abstract double toDouble(D d);

    /**
     * Convert double toString. This is necessary for classes that support
     * other number representation. They can convert to the write format from here.
     * The value returned here should be compatible with ExpressionEvaluator implementation
     *
     * @param d double
     * @return string representation
     */
    protected abstract String toString(double d);

    /**
     * Finds bracket index
     *
     * @param i   start index of the function call in the expression
     * @param len length of the function name
     * @param x   expression
     * @return array.
     * array[0] opening bracket of the function
     * <p>
     * array[1] closing bracket of the function
     */
    private int[] index(int i, int len, String x) {
        int start = x.indexOf('(', i + len);
        if (start == -1)
            return null;

        int end = findClosingBracket(start, x);
        if (end == -1)
            return null;

        return new int[]{start, end};
    }

    /**
     * Find the closing bracket of the function
     *
     * @param from start index
     * @param x    expression
     * @return index of the closing bracket, or -1 if it was not found
     */
    private int findClosingBracket(int from, String x) {
        int count = 0;
        int i = from;
        for (; i < x.length(); i++) {
            char c = x.charAt(i);
            if (c == '(')
                count++;
            else if (c == ')')
                count--;
            if (count == 0)
                break;
        }

        return count == 0 ? i : -1;
    }
}
