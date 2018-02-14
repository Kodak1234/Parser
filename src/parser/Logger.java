package parser;

import static java.lang.System.getProperty;

/**
 * Author: Umeugwa Dabeluchi
 * Date: January 19, 2018
 * <p>
 * Logger instance is passed to ExpressionEvaluator,ExpressionResolver,
 * <p>
 * and FunctionExecutor before the parsing process begins. Users can log their actions.
 *
 * @see FunctionExecutor
 * @see ExpressionEvaluator
 * @see ExpressionResolver
 */
public final class Logger {
    private final String LINE_FEED;
    private StringBuilder log;

    Logger() {
        log = new StringBuilder();
        LINE_FEED = getProperty("line.separator");
    }

    public void log(String x) {
        log.append(x).append(LINE_FEED);
    }

    public void dumpLog() {
        System.out.println(toString());
    }

    public void clearLog() {
        log.delete(0, log.length());
    }

    @Override
    public String toString() {
        return log.toString();
    }
}
