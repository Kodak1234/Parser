package parser;
/**
 * Author: Umeugwa Dabeluchi
 * Date: December 27, 2017
 * <p>
 * Interface used to execute function calls.
 *
 * @param <D> type of result returned y Parser
 * @see Parser
 */
public interface FunctionExecutor<D> extends LogListener {
    /**
     * Check if this character matches the first character in a function name
     *
     * @param c character
     * @return true if match, false otherwise
     */
    boolean isFunction(char c);

    /**
     * Called when isFunction returns true. Implementors should check that
     * <p>
     * the word is actually a function call before they start processing.
     *
     * @param index  index of the first character of the function name
     * @param x      expression where the function call was invoked.
     * @param parser implementors can use this Parser to parse expression inside a function call
     * @return Parser.info. info.start should equal = index and info.end should equal the index of
     * <p>
     * the closing bracket of the function call
     */
    Parser.Info execute(int index, String x, Parser<D> parser);
}
