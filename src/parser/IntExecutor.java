package parser;
public class IntExecutor extends BaseExecutor<Integer> {
    @Override
    protected double toDouble(Integer integer) {
        return integer.doubleValue();
    }

    @Override
    protected String toString(double d) {
        return Integer.toString((int) d);
    }
}
