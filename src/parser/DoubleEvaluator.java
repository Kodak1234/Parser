package parser;
/**
 * Author Umeugwa Dabeluchi
 * Date: December 27, 2017
 * <p>
 * Number is a double.
 *
 * @see ExpressionEvaluator
 */

public class DoubleEvaluator implements ExpressionEvaluator<Double> {
    private Logger logger;

    @Override
    public String eval(String num1, String num2, char op) {
        String v = null;
        switch (op) {
            case '-':
                v = Double.toString(convert(num2) - convert(num1));
                break;
            case '+':
                v = Double.toString(convert(num1) + convert(num2));
                break;
            case '*':
                v = Double.toString(convert(num1) * convert(num2));
                break;
            case '/':
                v = Double.toString(convert(num2) / convert(num1));
                break;
            case '^':
                v = Double.toString(Math.pow(convert(num2), convert(num1)));
                break;
        }

        logger.log(num2 + " " + op + " " + num1 + " = " + v);

        return v;
    }

    @Override
    public Double convert(String n) {
        return Double.parseDouble(n);
    }

    @Override
    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
