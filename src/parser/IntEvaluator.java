package parser;

public class IntEvaluator implements ExpressionEvaluator<Integer> {
    private Logger logger;

    @Override
    public String eval(String num1, String num2, char op) {
        String v = null;
        switch (op) {
            case '-':
                v = Integer.toString(convert(num2) - convert(num1));
                break;
            case '+':
                v = Integer.toString(convert(num2) + convert(num1));
                break;
            case '*':
                v = Integer.toString(convert(num2) * convert(num1));
                break;
            case '/':
                v = Integer.toString(convert(num2) / convert(num1));
                break;
            case '^':
                v = Integer.toString((int) Math.pow(convert(num2), convert(num1)));
                break;
        }

        logger.log(num2 + " " + op + " " + num1 + " = " + v);

        return v;
    }

    @Override
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public Integer convert(String n) {
        return Integer.parseInt(n);
    }
}
