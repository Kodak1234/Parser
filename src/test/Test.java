package test;


import parser.Parser;

public class Test {
    public static void main(String[] args) {
        Parser<Double> p = new Parser.Builder<Double>()
                .defaultDouble()
                .build();

        double s = p.parse("cos(2*(pi))-1");
        p.getLogger().dumpLog();
        System.out.println(s);
    }
}
