package parser;
/**
 * Author: Umeugwa Dabeluchi
 * <p>
 * Date: December 27, 2017
 * <p>
 * Returns a double as the value of a function call.
 */

public class DoubleExecutor extends BaseExecutor<Double> {
    @Override
    protected String toString(double d) {
        return Double.toString(d);
    }

    @Override
    protected double toDouble(Double d) {
        return d;
    }
}
