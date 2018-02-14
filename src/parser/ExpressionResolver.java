package parser;
/**
 * Author: Umeugwa Dabeluchi
 * Date: December 27, 2017
 * <p>
 * Interface used to resolve an expression and check for operator errors before
 * the expression is parsed.
 */
public interface ExpressionResolver extends LogListener{
    /**
     * Checks for operator error or modifies the expression by removing redundant
     * operators
     *
     * @param l     previous character
     * @param c     current character
     * @param r     next character
     * @param index current index
     * @return Parser.Info. info.end must equal index
     * @throws RuntimeException throws an exception
     *                          if there is an operator error
     */
    Parser.Info resolve(char l, char c, char r, int index) throws RuntimeException;

    /**
     * Checks whether a character is an operator
     *
     * @param c character to check
     * @return true if c is an operator, false otherwise
     */
    boolean isOperator(char c);

    /**
     * Checks if an operator in the stack has higher precedence
     * than the next operator about to be pushed in the stack
     *
     * @param top operator at the top of the stack
     * @param c   operator about to be pushed
     * @return true if top has higher precedence, false otherwise
     */
    boolean hasHigherPrecedence(char top, char c);

}