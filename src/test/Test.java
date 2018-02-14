package test;


import parser.Parser;

public class Test {
    public static void main(String[] args) {
        String x = "10/2+4*2";
        Parser<Double> p = new Parser.Builder<Double>()
                .defaultDouble()//use interface implementation provided by the api
                .build();

        double s = p.parse(x);//parse the expression
        p.getLogger().dumpLog();
        System.out.println(s);
    }
}
