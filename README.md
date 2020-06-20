#                                                   Juzzy library extension  

### Implementation of Type-2 FLSs using Java.

The extended version of the Juzzy library contains new interval-valued aggregation functions (IvAF) which are modeled by pairs of N-dual IvAF, as conjunctive/disjunctive overlap aggregations.

Original library at
http://juzzy.wagnerweb.net/

C. Wagner, "Juzzy – A Java based Toolkit for Type-2 Fuzzy Logic", Proceedings of the IEEE Symposium Series on Computational Intelligence, Singapore, April 2013.


Note that there are two options, you can either download a precompiled version of Juzzy which you can test from the command line or you can download the complete project inclusive of the source code.

What you need:

1. Java JDK:
Please make sure you have a recent Java JDK installed on your system (not just the JRE).
The current JDK is available here: http://www.oracle.com/technetwork/java/javase/downloads/index.html.

2. Netbeans IDE (optional – alternatively use an IDE of your choice such as Eclipse, etc.):
Download it here: http://netbeans.org/.

3. Java Fuzzy Logic Toolkit (jar files – include jMathPlot jar):
Download it [here](http://juzzy.wagnerweb.net/Juzzy_V2/Juzzy_V2.zip).

4. Source Code, Javadoc and Visualisation library:
Download the source code [here](http://juzzy.wagnerweb.net/Juzzy_V2/Juzzy_V2_Source.zip).

Download the javadoc [here](http://juzzy.wagnerweb.net/Juzzy_V2/Juzzy_V2_javadoc.zip).

Download jmathplot.jar here: http://code.google.com/p/jmathplot/downloads/list

Note: after unzipping and setting up the project in Netbeans you will have to add the jmathplot.jar library to your project path (click  resolve references or Project properties )

To get started without looking at source code / without Netbeans:

1. Unzip the toolkit, creating a Juzzy directory.

2. Test your setup:
Open a command prompt (in Windows, run CMD) and navigate to your Juzzy directory.
Run one or more of the examples using the commands below. All examples are based on the standard "How much to tip a waiter?" problem and are based on two inputs (food quality & service level) and a single output: the amount of tip in percent. All examples will produce the output for sample calculations, the rulebase employed as well as visualisations of the control surface and all fuzzy sets. Some example contain multiple styles of visualisation for the fuzzy sets.

3. If the examples are running fine you should be good to go!

- java -jar Juzzy.jar                               Shows an overview of the example functionality.
- java -jar Juzzy.jar type1                     Executes a type-1 fuzzy system example.
- java -jar Juzzy.jar NStype1                     Executes a non-singleton type-1 fuzzy system example.
- java -jar Juzzy.jar type1-2outputs        Executes a type-1 fuzzy system example with 2 outputs.
- java -jar Juzzy.jar intervalIT2             Executes an interval type-2 fuzzy system example.
- java -jar Juzzy.jar type1NSintervalIT2             Executes a non-singleton type-1 interval type-2 fuzzy system example.
- java -jar Juzzy.jar IT2NSintervalIT2             Executes a non-singleton IT2 interval type-2 fuzzy system example.
- java -jar Juzzy.jar intervalIT2-2outputs             Executes an interval type-2 fuzzy system example with 2 outputs.
- java -jar Juzzy.jar zSlicesGT2            Executes a general type-2 fuzzy system example.
- java -jar Juzzy.jar type1NSzSlicesGT2            Executes a non-singleton type-1 general type-2 fuzzy system example.
- java -jar Juzzy.jar IT2NSzSlicesGT2            Executes a non-singleton IT2 general type-2 fuzzy system example.
- java -jar Juzzy.jar GT2NSzSlicesGT2            Executes a non-singleton GT2 general type-2 fuzzy system example.
- java -jar Juzzy.jar zSlicesGT2MC      Executes a multi-threaded / multi-core general type-2 fuzzy system example.
- java -jar Juzzy.jar zSlicesGT2MC-2outputs      Executes a multi-threaded / multi-core general type-2 fuzzy system example -with 2 outputs.

