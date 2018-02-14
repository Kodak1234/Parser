package parser;

import java.util.Stack;

/**
 * Author: Umeugwa Dabeluchi
 * Date: December 27, 2017
 * <p>
 * Class that parse mathematical expressions.
 * <p>
 * Users need to implement
 * <p>
 * ExpressionResolver
 * </p>
 * <p>
 * ExpressionEvaluator
 * </p>
 * <p>
 * FunctionExecutor
 * </p>
 *
 * @see ExpressionEvaluator
 * @see ExpressionResolver
 * @see FunctionExecutor
 */
public final class Parser<E> {

    public static char NO_CHARACTER = '\u0000';
    private Builder<E> b;
    private String posFix;
    private E result;

    private Parser(Builder<E> b) {
        this.b = b;
    }

    public void reset() {
        posFix = null;
        result = null;
    }

    public E getResult() {
        return result;
    }

    public Logger getLogger() {
        return b.logger;
    }

    public E parse(String x) {
        reset();
        x = x.toLowerCase();
        x = resolve(x);
        System.out.println("x = [" + x + "]");
        posFix = toPosFix(x);
        System.out.println("posFix = [" + posFix + "]");
        return result = eval();
    }

    /**
     * Remove redundant characters and check for error.
     *
     * @param x expression to resolve
     * @return resolved expression
     * @see ExpressionResolver#resolve(char, char, char, int)
     */
    @SuppressWarnings("unchecked")
    private String resolve(String x) {
        boolean skip = false;
        Info info = null;
        int len = x.length();
        for (int i = 0; i < len; i++) {

            //let functionExecutor process the expression
            if (b.executor.isFunction(x.charAt(i))) {
                info = b.executor.execute(i, x, this);
                if (info != null) {
                    i = info.end;
                    skip = true;
                }
            }

            if (!skip) {
                char l = i == 0 ? NO_CHARACTER : x.charAt(i - 1);
                char r = i == x.length() - 1 ? NO_CHARACTER : x.charAt(i + 1);
                info = b.resolver.resolve(l, x.charAt(i), r, i);
            }

            if (info == null) {
                continue;
            } else if (info.end != i || info.start < 0)
                throw new IllegalStateException("positions out of range");

            //replace character range with new characters
            String left = x.substring(0, info.start);
            String right = x.substring(info.end + 1, x.length());
            x = left + info.s + right;

            if (x.length() < len)
                i = Math.max(0, i - (len - x.length()));
            len = x.length();
            skip = false;

        }

        return x;

    }

    /**
     * Converts an infix expression to posfix expression
     * <p>
     * a + b = a b +
     * a + b * c = abc * +
     * (a + b) * c = ab+c*
     * (a + b) * (c+d) = ab+cd+*
     *
     * @param x resolved expression to be converted to posFix format
     * @return posFix expression
     */
    private String toPosFix(String x) {
        StringBuilder p = new StringBuilder();
        Stack<Character> opStack = new Stack<>();
        char[] c = x.toCharArray();
        int bracketCount = 0;
        for (int i = 0; i < c.length; i++) {

            if (c[i] == ')') {
                bracketCount--;
                boolean hit = false;
                while (!opStack.isEmpty() && !hit) {
                    char op = opStack.pop();
                    if (op == '(') {
                        hit = true;
                    } else {
                        p.append(" ").append(op);
                    }
                }

                if (!hit) {
                    throw new IllegalExpression("missing opening bracket");
                }
            } else if (c[i] == '(') {
                bracketCount++;
                opStack.push(c[i]);
            } else {

                if (b.resolver.isOperator(c[i])) {
                    //operator without a left operand is not an operator
                    //but the sign of the right operand, so it should not be pushed
                    //in the opStack
                    if (i == 0 || (c[i - 1] == '(' && (c[i] == '-' || c[i] == '+'))) {
                        Info info = getNum(i + 1, c);
                        p.append(" ").append(c[i]).append(info.s);
                        i = info.end;
                    } else
                        addOperator(c[i], opStack, p);

                } else if (Character.isDigit(c[i])) {
                    Info info = getNum(i, c);
                    p.append(" ").append(info.s);
                    i = info.end;
                }
            }
        }

        //opening bracket count does not match
        //closing bracket count
        if (bracketCount != 0)
            throw new IllegalExpression(x + " is missing closing bracket");

        //append everything left in the stack
        while (!opStack.isEmpty())
            p.append(" ").append(opStack.pop());

        return p.toString().trim();
    }

    private void addOperator(char op, Stack<Character> opStack, StringBuilder p) {
        if (!opStack.isEmpty()) {
            if (opStack.peek() != '(') {
                while (!opStack.isEmpty() && b.resolver.hasHigherPrecedence(opStack.peek(), op)) {
                    p.append(" ").append(opStack.pop());
                }
                opStack.push(op);
            } else
                //if top == (. just push it on top
                opStack.push(op);
        } else
            //stack is empty. just push
            opStack.push(op);

    }

    @SuppressWarnings("unchecked")
    private E eval() {
        Stack<String> val = new Stack<>();
        String[] c = posFix.split(" ");
        for (String s : c) {
            if (s.length() == 1 && b.resolver.isOperator(s.charAt(0))) {
                String l = val.pop();
                String r = val.pop();
                s = b.eval.eval(l, r, s.charAt(0));
                val.push(s);

            } else {
                val.push(s);
            }
        }

        return (E) b.eval.convert(val.pop());
    }

    private Parser.Info getNum(int i, char[] c) {
        StringBuilder b = new StringBuilder();
        while (i < c.length && (Character.isDigit(c[i]) || (c[i] == '.' && i + 1 < c.length
                && Character.isDigit(c[i + 1])))) {
            b.append(c[i++]);
        }

        i = i - 1;///point to the last digit

        Parser.Info info = new Parser.Info();
        info.end = i;
        info.s = b.toString();

        return info;
    }

    /**
     * Builds a Parser of a particular data type
     *
     * @param <D>
     */
    public static class Builder<D> {

        ExpressionResolver resolver;
        ExpressionEvaluator eval;
        FunctionExecutor executor;
        Logger logger;

        public Builder() {
            logger = new Logger();
        }

        public Builder setFunctionExecutor(FunctionExecutor<D> executor) {
            this.executor = executor;
            return this;
        }

        public Builder setResolver(ExpressionResolver resolver) {
            this.resolver = resolver;
            return this;
        }

        public Builder setEvaluator(ExpressionEvaluator<D> eval) {
            this.eval = eval;
            return this;
        }

        public Builder<D> defaultInt() {
            eval = new IntEvaluator();
            executor = new IntExecutor();
            resolver = new BaseResolver();
            return this;
        }

        public Builder<D> defaultDouble() {
            eval = new DoubleEvaluator();
            executor = new DoubleExecutor();
            resolver = new DoubleResolver();
            return this;
        }

        public Parser<D> build() {

            if (resolver == null)
                throw new IllegalArgumentException("ExpressionResolver not set");

            if (eval == null)
                throw new IllegalArgumentException("ExpressionEvaluator not set");

            if (executor == null)
                throw new IllegalArgumentException("ExpressionExecutor not set");

            resolver.setLogger(logger);
            eval.setLogger(logger);
            executor.setLogger(logger);

            return new Parser<>(this);
        }

    }

    /**
     * Utility class that contains range information.
     */
    public static class Info {
        /**
         * start index
         **/
        public int start = -1;
        /**
         * end index
         **/
        public int end = -1;

        /**
         * new value to insert in the range
         **/
        public String s;

    }
}