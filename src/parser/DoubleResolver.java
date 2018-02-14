package parser;
/**
 * Author: Umeugwa Dabeluchi
 * <p>
 * Date: December 27, 2017
 * <p>
 * pi = Math.PI;
 * <p>
 * .xx = 0.xx
 */

public class DoubleResolver extends BaseResolver {
    private Logger logger;

    @Override
    public Parser.Info resolve(char l, char c, char r, int index) throws RuntimeException {
        Parser.Info info = super.resolve(l, c, r, index);

        if (info == null) {
            //pi = Math.PI
            if (l == 'p' && c == 'i') {
                info = set(Double.toString(Math.PI));
                logger.log("change pi = " + info.s);
            }

            //.xx = 0.xx
            else if (!Character.isDigit(l) && c == '.' && Character.isDigit(r)) {
                info = new Parser.Info();
                info.s = "0.";
                info.start = index;
                info.end = index;

                logger.log("change ." + r + " = " + "0." + r);
            }
        }

        return info;
    }

    @Override
    public void setLogger(Logger logger) {
        super.setLogger(logger);
        this.logger = logger;
    }
}
