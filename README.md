# Parser
A mathematical expression parser. Three steps is involved in parsing an expression
<ol>
   <li>Normalize expression to something that can be parsed.</li>
   <li>Execute function call withing expressions and replace function call with value returned by function</li>
   <li>Evaluate the expression to get the final value</li>
</ol>

<p>These processes are accomplished through the following interfaces.

<ol>
  <li>ExpressionResolver</li>
  <li>FunctionExecutor</li>
  <li>ExpressionEvaluator</li>
</ol>
  
<p> In other to create an instance of Parser. The interface mention above must be provided.The api provides 2 implementation of the interfaces. One for parsing integer expressions and the other for parsing double expressions. To add new functionality users can extend implementation provided by the api or create their own implementation from scratch by directly implementing the interfaces listed above.
</p>

<h3>Parsing Expression as Double</h3>

<pre>
String x = "cos(2*(pi))-1";
Parser<Double> p = new Parser.Builder<Double>()
                .defaultDouble()//use interface implementation provided by the api
                .build();

double s = p.parse(x);//parse the expression
System.out.println(s);
</pre>

<h3>Parsing Expression as Integer</h3>
<pre>
 String x = "10/2+4*2";
 Parser<Integer> p = new Parser.Builder<Integer>()
                .defaultInt()//use interface implementation provided by the api
                .build();

 int s = p.parse(x);//parse the expression
 System.out.println(s);
</pre>
