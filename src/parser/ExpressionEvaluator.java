package parser;
/**
 * Author: Umeugwa Dabeluchi
 * Date: December 27, 2017
 * <p>
 * Interface used to evaluate expressions parsed by Parser
 *
 * @param <E> Data type of the evaluated expression
 * @see Parser
 */
public interface ExpressionEvaluator<E> extends LogListener{
    /**
     * Evaluates an expression
     *
     * @param num1 left operand
     * @param num2 right operand
     * @param op   operator
     * @return result
     */
    String eval(String num1, String num2, char op);

    /**
     * Convert result to the right data type
     *
     * @param r result
     * @return result in the correct data type
     */
    E convert(String r);
}