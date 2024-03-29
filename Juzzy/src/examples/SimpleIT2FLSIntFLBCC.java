/*
 * SimpleT1FLS.java
 *
 * Created on May 21st 2012
 *
 * Copyright 2012 Christian Wagner All Rights Reserved.
 */
package examples;

import java.util.TreeMap;

import generic.Input;
import generic.Output;
import generic.Tuple;
import intervalType2.sets.IntervalT2MF_Interface;
import intervalType2.sets.IntervalT2MF_Trapezoidal;
import intervalType2.system.IT2_Antecedent;
import intervalType2.system.IT2_Consequent;
import intervalType2.system.IT2_Rule;
import intervalType2.system.IT2_Rulebase;
import tools.JMathPlotter;
import type1.sets.T1MF_Trapezoidal;
import util.DaoMembershipDegreesOfType2FuzzySets;
import util.ExperimetName;
import util.MembershipDegreesOfType2FuzzySets;

/**
 * A simple example of an interval Type-2 FLS based on the "How much to tip the
 * waiter" scenario. The example is an extension of the Type-1 FLS example where
 * we extend the MFs and use the Interval Type-2 System classes. Note that in
 * contrast to the type-1 case, here only two sets are used to model the service
 * quality. We have two inputs: food quality and service level and as an output
 * we would like to generate the applicable tip.
 * 
 * @author Christian Wagner
 */
public class SimpleIT2FLSIntFLBCC {
	Input CP, CC, RAM, EC; // the inputs to the FLS
	Output U; // the output of the FLS
	IT2_Rulebase rulebase; // the rulebase captures the entire FLS

	private double OutputXValue;
	private double OutputYValue;
	private double xPonctual;

	// private final byte CENTEROFSETS = 0;
	// private final byte CENTROID = 1;

	private MembershipDegreesOfType2FuzzySets degreesOfMembershipFunctions = new MembershipDegreesOfType2FuzzySets();
	private DaoMembershipDegreesOfType2FuzzySets daoMembershipDegreesOfType2FuzzySets = new DaoMembershipDegreesOfType2FuzzySets();

	public SimpleIT2FLSIntFLBCC() {
		// Define the inputs
		// food = new Input("Food Quality", new Tuple(0,10));
		CP = new Input("Compatational Power", new Tuple(0, 10));
		CC = new Input("Communication Cost", new Tuple(0, 10));
		RAM = new Input("Available RAM", new Tuple(0, 10));
		// EC = new Input("Energy Cost", new Tuple(0,10));

		U = new Output("Utilization", new Tuple(0, 10)); // a percentage for the tip

		// MF for Computational Power

		// MF1='limited':'itrapatype2',[-2.269 -0.9 0.45 2.4 -1.329 -0.01882 1.5 3.5
		// 0.9]
		T1MF_Trapezoidal limitedCPUMF = new T1MF_Trapezoidal("Upper MF for Limited CP",	new double[] { -2.27, -0.9, 1.5, 3.5 });
		T1MF_Trapezoidal limitedCPLMF = new T1MF_Trapezoidal("Lower MF for Limited CP",	new double[] { -1.3, -0.2, 0.45, 2.4 });
		IntervalT2MF_Trapezoidal limitedCPMF = new IntervalT2MF_Trapezoidal("IT2MF for limited CP", limitedCPUMF, limitedCPLMF);

		// MF2='reasonable':'itrapatype2',[1.6 3.8 5.4 7.4 2.6 4.6 6.3 8.4 0.9]
		T1MF_Trapezoidal reasonableCPUMF = new T1MF_Trapezoidal("Upper MF for Reasonable CP", new double[] { 1.6, 3.8, 6.3, 8.4 });
		T1MF_Trapezoidal reasonableCPLMF = new T1MF_Trapezoidal("Lower MF for Reasonable CP", new double[] { 2.6, 4.6, 5.4, 7.4 });
		IntervalT2MF_Trapezoidal reasonableCPMF = new IntervalT2MF_Trapezoidal("IT2MF for reasonable CP", reasonableCPUMF,	reasonableCPLMF);

		// MF3='high':'itrapatype2',[6.65 7.65 10.19 12.34 7.65 8.5 11.19 13.34 0.9]
		T1MF_Trapezoidal highCPUMF = new T1MF_Trapezoidal("Upper MF for High CP",	new double[] { 6.65, 7.65, 11.19, 13.34 });
		T1MF_Trapezoidal highCPLMF = new T1MF_Trapezoidal("Lower MF for High CP",  new double[] { 7.65, 8.5, 10.19, 12.34 });
		IntervalT2MF_Trapezoidal highCPMF = new IntervalT2MF_Trapezoidal("IT2MF for high CP", highCPUMF, highCPLMF);

		// plotMFs("Computational Power Membership Functions", new
		// IntervalT2MF_Interface[]{limitedCPMF,reasonableCPMF, highCPMF}, 10);

		// até a l189
		// MF for Comunication Cost
		// MF1='small':'itrapatype2',[-2.27 -0.8 0.5 2.4 -1.327 -0.01682 1.35 3.4 0.9]
		T1MF_Trapezoidal smallCCUMF = new T1MF_Trapezoidal("Upper MF for Small CC",	new double[] { -2.27, -0.8, 1.35, 3.4 });
		T1MF_Trapezoidal smallCCLMF = new T1MF_Trapezoidal("Lower MF for Small CC",	new double[] { -1.3, -0.02, 0.5, 2.4 });
		IntervalT2MF_Trapezoidal smallCCMF = new IntervalT2MF_Trapezoidal("IT2MF for small CC", smallCCUMF, smallCCLMF);

		// MF3='average':'itrapatype2',[1.5 4 5.2 7.5 2.5 4.8 6 8.5 0.9]
		T1MF_Trapezoidal averageCCUMF = new T1MF_Trapezoidal("Upper MF for Average CC", new double[] { 1.5, 4, 6, 8.5 });
		T1MF_Trapezoidal averageCCLMF = new T1MF_Trapezoidal("Lower MF for Average CC",	new double[] { 2.5, 4.8, 5.2, 7.5 });
		IntervalT2MF_Trapezoidal averageCCMF = new IntervalT2MF_Trapezoidal("IT2MF for average CC", averageCCUMF,	averageCCLMF);

		// MF2='big':'itrapatype2',[6.5 8.5 10 12 7.5 9.2 11.19 13.34 0.9]
		T1MF_Trapezoidal bigCCUMF = new T1MF_Trapezoidal("Upper MF for Big CC",	new double[] { 6.5, 8.5, 11.19, 13.34 });
		T1MF_Trapezoidal bigCCLMF = new T1MF_Trapezoidal("Lower MF for Big CC", new double[] { 7.5, 9.2, 10, 12 });
		IntervalT2MF_Trapezoidal bigCCMF = new IntervalT2MF_Trapezoidal("IT2MF for Big CC", bigCCUMF, bigCCLMF);

		// plotMFs("Comunication Cost Membership Functions", new
		// IntervalT2MF_Interface[]{smallCCMF, averageCCMF, bigCCMF},10);

		// MF for RAM
		// MF1='small':'itrapatype2',[-2.27 -0.8 0.5 2.4 -1.327 -0.01682 1.35 3.4 0.9]
		T1MF_Trapezoidal smallRAMUMF = new T1MF_Trapezoidal("Upper MF for Small RAM", new double[] { -2.27, -0.8, 1.35, 3.4 });
		T1MF_Trapezoidal smallRAMLMF = new T1MF_Trapezoidal("Lower MF for Small RAM", new double[] { -1.327, -0.01682, 0.5, 2.4 });
		IntervalT2MF_Trapezoidal smallRAMMF = new IntervalT2MF_Trapezoidal("IT2MF for Small RAM", smallRAMUMF,	smallRAMLMF);

		// MF2='average':'itrapatype2',[1.5 4 5.2 7.5 2.5 4.8 6 8.5 0.9]
		T1MF_Trapezoidal averageRAMUMF = new T1MF_Trapezoidal("Upper MF for Average RAM", new double[] { 1.5, 4, 6, 8.5 });
		T1MF_Trapezoidal averageRAMLMF = new T1MF_Trapezoidal("Lower MF for Average RAM", new double[] { 2.5, 4.8, 5.2, 7.5 });
		IntervalT2MF_Trapezoidal averageRAMMF = new IntervalT2MF_Trapezoidal("IT2MF Average RAM", averageRAMUMF, averageRAMLMF);

		// MF3='high':'itrapatype2',[6.5 8.5 10 12 7.5 9.2 11.19 13.34 0.9]
		T1MF_Trapezoidal highRAMUMF = new T1MF_Trapezoidal("Upper MF for High RAM",	new double[] { 6.5, 8.5, 11.19, 13.34 });
		T1MF_Trapezoidal highRAMLMF = new T1MF_Trapezoidal("Lower MF for High RAM", new double[] { 7.5, 9.2, 10, 12 });
		IntervalT2MF_Trapezoidal highRAMMF = new IntervalT2MF_Trapezoidal("IT2MF for High RAM", highRAMUMF, highRAMLMF);

		// plotMFs("RAM Membership Functions", new IntervalT2MF_Interface[]{smallRAMMF,
		// averageRAMMF, highRAMMF}, 10);

		// MF for Priority (P)
		// MF1='low':'itrapatype2',[-1.74698039215686 -0.590980392156862
		// -0.569980392156862 3.99901960784314 -1.24678039215686 0.079019607843138
		// 0.409019607843138 4.99901960784314 0.9]
		T1MF_Trapezoidal lowUtilizationUMF = new T1MF_Trapezoidal("Upper MF for Low Utilization", new double[] { -1.75, -0.6, 0.41, 5 });
		T1MF_Trapezoidal lowUtilizationLMF = new T1MF_Trapezoidal("Lower MF for Low Utilization",new double[] { -1.2, 0.08, -0.57, 4.0 }); //
		IntervalT2MF_Trapezoidal lowUtilizationMF = new IntervalT2MF_Trapezoidal("IT2MF for Low Utilization ",
				lowUtilizationUMF, lowUtilizationLMF);

		// MF2='average':'itrapatype2',[0.5 4.5 4.83 8.5 1.5 5.16 5.5 9.5 0.9]
		T1MF_Trapezoidal averageUtilizationUMF = new T1MF_Trapezoidal("Upper MF for Average Utilization", new double[] { 0.5, 4.5, 5.5, 9.5 });
		T1MF_Trapezoidal averageUtilizationLMF = new T1MF_Trapezoidal("Lower MF for Average Utilization", new double[] { 1.5, 5.16, 4.83, 8.5 });
		IntervalT2MF_Trapezoidal averageUtilizationMF = new IntervalT2MF_Trapezoidal("IT2MF for Average Utilization ", averageUtilizationUMF, averageUtilizationLMF);

		// MF3='high':'itrapatype2',[5 9.61 9.94 11.3 6 10.3 10.7 11.8 0.9]
		T1MF_Trapezoidal highUtilizationUMF = new T1MF_Trapezoidal("Upper MF for High Utilization",	new double[] { 5, 9.6, 10.7, 11.8 });
		T1MF_Trapezoidal highUtilizationLMF = new T1MF_Trapezoidal("Lower MF for High Utilization",	new double[] { 6, 10.3, 9.94, 11.3 });
		IntervalT2MF_Trapezoidal highUtilizationMF = new IntervalT2MF_Trapezoidal("IT2MF for High Utilization ", highUtilizationUMF, highUtilizationLMF);

		// plotMFs("Priority Membership Functions", new
		// IntervalT2MF_Interface[]{lowPriorityMF, averagePriorityMF, highPriorityMF},
		// 10);

		// Set up the antecedents and consequents - note how the inputs are
		// associated...
		IT2_Antecedent limitedCP = new IT2_Antecedent("LimitedCP", limitedCPMF, CP);
		IT2_Antecedent reasonableCP = new IT2_Antecedent("ReasonableCP", reasonableCPMF, CP);
		IT2_Antecedent highCP = new IT2_Antecedent("HighCP", highCPMF, CP);

		IT2_Antecedent smallCC = new IT2_Antecedent("SmallCC", smallCCMF, CC);
		IT2_Antecedent averageCC = new IT2_Antecedent("AverageCC", averageCCMF, CC);
		IT2_Antecedent bigCC = new IT2_Antecedent("BigCC", bigCCMF, CC);

		IT2_Antecedent smallRAM = new IT2_Antecedent("SmallRAM", smallRAMMF, RAM);
		IT2_Antecedent averageRAM = new IT2_Antecedent("AverageRAM", averageRAMMF, RAM);
		IT2_Antecedent highRAM = new IT2_Antecedent("HighRAM", highRAMMF, RAM);

		IT2_Consequent lowUtilization = new IT2_Consequent("LowUtilization", lowUtilizationMF, U);
		IT2_Consequent averageUtilization = new IT2_Consequent("AverageUtilization", averageUtilizationMF, U);
		IT2_Consequent highUtilization = new IT2_Consequent("HighUtilization", highUtilizationMF, U);

		// Set up the rulebase and add rules
		rulebase = new IT2_Rulebase(27);

		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[] { limitedCP, smallCC, smallRAM }, lowUtilization));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[] { limitedCP, smallCC, averageRAM }, lowUtilization));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[] { limitedCP, smallCC, highRAM }, lowUtilization));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[] { limitedCP, bigCC, smallRAM }, lowUtilization));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[] { limitedCP, bigCC, averageRAM }, lowUtilization));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[] { limitedCP, bigCC, highRAM }, lowUtilization));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[] { limitedCP, averageCC, smallRAM }, lowUtilization));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[] { limitedCP, averageCC, averageRAM }, lowUtilization));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[] { limitedCP, averageCC, highRAM }, lowUtilization));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[] { reasonableCP, smallCC, smallRAM }, lowUtilization));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[] { reasonableCP, smallCC, averageRAM }, averageUtilization));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[] { reasonableCP, smallCC, highRAM }, highUtilization));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[] { reasonableCP, bigCC, smallRAM }, lowUtilization));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[] { reasonableCP, bigCC, averageRAM }, lowUtilization));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[] { reasonableCP, bigCC, highRAM }, lowUtilization));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[] { reasonableCP, averageCC, smallRAM }, lowUtilization));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[] { reasonableCP, averageCC, averageRAM }, averageUtilization));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[] { reasonableCP, averageCC, highRAM }, averageUtilization));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[] { highCP, smallCC, smallRAM }, lowUtilization));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[] { highCP, smallCC, averageRAM }, highUtilization));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[] { highCP, smallCC, highRAM }, highUtilization));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[] { highCP, bigCC, smallRAM }, lowUtilization));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[] { highCP, bigCC, averageRAM }, lowUtilization));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[] { highCP, bigCC, highRAM }, lowUtilization));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[] { highCP, averageCC, smallRAM }, averageUtilization));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[] { highCP, averageCC, averageRAM }, averageUtilization));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[] { highCP, averageCC, highRAM }, averageUtilization));

		// get some outputs
		// getPriority(inCP, inCC, inRAM);

		double inCP = 4.278754501165007; //8.0; //1.0; 
		double inCC = 2.0; //8.0; 
		double inRam = 0.04425048828125; //9.0; //3.0; 

		rulebase.setImplicationMethod((byte) 7);
		// default value empty max(xInf, yInf), min(xSup, ySup)
		//                              1     2      3      4      5    6     7     8      9     10      11    12    13      14    15
		//String[] typeIntersection = { "o2", "om", "odb", "om3", "o2db", "a", "tl", "o2", "om", "odb", "om3", "o2db", "a", "tl", "maxmin" };
		String[] typeIntersection = { "TLK" };
		// rulebase.setTypeIntersection("om"); //TL // default juzzy maxmin

		// default value empty min(xInf, yInf), max(xSup, ySup)
		//                        1          2         3        4         5         6          7       8      9      10      11       12     13    14       15
		//String[] typeUnion = { "minmax", "minmax", "minmax", "minmax", "minmax", "minmax", "minmax", "o2n", "omn", "odbn", "om3n", "o2dbn", "an", "tln", "minmax" };
		String[] typeUnion = { "SLK"};
		// rulebase.setTypeUnion("omn"); //TLN default juzzy minmax

		for (int i = 0; i < typeIntersection.length; i++) {
			
			// seta os agregadores
			rulebase.setTypeIntersection(typeIntersection[i]);
			rulebase.setTypeUnion(typeUnion[i]);
			
            // seta os valores de entrada
			getLevelOfUse(inCP, inCC, inRam);

			// getLevelOfUse(8.0, 2.0, 9.0);
			// getLevelOfUse(1.0, 8.0, 3.0); // valores que está ocorrendo erros

			// obtem o nome do experimento para compartilhar no DaoMembershipDegrees
			ExperimetName experimetNamePuplico = new ExperimetName();
			experimetNamePuplico.setExperimentName(
					"intersection_" + rulebase.getTypeIntersection() + "_union_" + rulebase.getTypeUnion());

			this.degreesOfMembershipFunctions.setInCP(inCP);
			this.degreesOfMembershipFunctions.setInCC(inCC);
			this.degreesOfMembershipFunctions.setInRam(inRam);

			this.degreesOfMembershipFunctions.setxPonctual(this.xPonctual);
			this.degreesOfMembershipFunctions.setxInf(this.OutputXValue);
			this.degreesOfMembershipFunctions.setxSup(this.OutputYValue);

			this.degreesOfMembershipFunctions
					.setLowUtilizationDegreeOfMembershipLowerBound(lowUtilizationLMF.getFS(this.xPonctual));
			this.degreesOfMembershipFunctions
					.setLowUtilizationDegreeOfMembershipUpperBound(lowUtilizationUMF.getFS(this.xPonctual));

			this.degreesOfMembershipFunctions
					.setAverageUtilizationDegreeOfMembershipLowerBound(averageUtilizationLMF.getFS(this.xPonctual));
			this.degreesOfMembershipFunctions
					.setAverageUtilizationDegreeOfMembershipUpperBound(averageUtilizationUMF.getFS(this.xPonctual));

			this.degreesOfMembershipFunctions
					.setHighUtilizationDegreeOfMembershipLowerBound(highUtilizationLMF.getFS(this.xPonctual)); 
			this.degreesOfMembershipFunctions
					.setHighUtilizationDegreeOfMembershipLowerBound(highUtilizationUMF.getFS(this.xPonctual)); 

			daoMembershipDegreesOfType2FuzzySets.adicionar(this.degreesOfMembershipFunctions);

		}

		// plot some sets, discretizing each input into 100 steps.
		plotMFs("Computational Power Membership Functions",
				new IntervalT2MF_Interface[] { limitedCPMF, reasonableCPMF, highCPMF }, 10);
		plotMFs("Comunication Cost Membership Functions",
				new IntervalT2MF_Interface[] { smallCCMF, averageCCMF, bigCCMF }, 10);
		plotMFs("RAM Membership Functions", new IntervalT2MF_Interface[] { smallRAMMF, averageRAMMF, highRAMMF }, 10);
		plotMFs("Utilization Membership Functions",
				new IntervalT2MF_Interface[] { lowUtilizationMF, averageUtilizationMF, highUtilizationMF }, 10);

		// plot control surface
		// do either height defuzzification (false) or centroid d. (true)
		// plotControlSurface(false, 100, 100);

		// print out the rules
		System.out.println("\n" + rulebase);

	}

	/**
	 * Basic method that prints the output for a given set of inputs.
	 * 
	 * @param inCP
	 * @param inCC
	 * @param inRAM
	 */

	private void getLevelOfUse(double inCP, double inCC, double inRAM) {
		// first, set the inputs

		CP.setInput(inCP);
		CC.setInput(inCC);
		RAM.setInput(inRAM);

		// now execute the FLS and print output
		System.out.println("The CP was: " + CP.getInput());
		System.out.println("The CC was: " + CC.getInput());
		System.out.println("The RAM was: " + RAM.getInput());

		// private final byte CENTEROFSETS = 0;
		// private final byte CENTROID = 1;

		//System.out.println("Using center of sets type reduction, the IT2 FLS recommends a " + "utilization of: "
		//		+ rulebase.evaluate(0).get(U));
		//System.out.println("Using centroid type reduction, the IT2 FLS recommends a " + "utilization of: "
		//		+ rulebase.evaluate(1).get(U));

		// show the output of the raw centroids
		System.out.println("Centroid of the output for Utilization (based on centroid type reduction):");
		TreeMap<Output, Object[]> centroid = rulebase.evaluateGetCentroid(1);
		Object[] centroidTip = centroid.get(U);
		Tuple centroidTipXValues = (Tuple) centroidTip[0];
		double centroidTipYValues = ((Double) centroidTip[1]);
		System.out.println(centroidTipXValues + " at y= " + centroidTipYValues);

		this.OutputXValue = centroidTipXValues.getLeft();
		this.OutputYValue = centroidTipXValues.getRight();

		// private final byte CENTEROFSETS = 0;
		this.xPonctual = rulebase.evaluate(1).get(U);

	}

	private void plotMFs(String name, IntervalT2MF_Interface[] sets, int discretizationLevel) {
		JMathPlotter plotter = new JMathPlotter();
		plotter.plotMF(sets[0].getName(), sets[0], discretizationLevel, null, false);

		for (int i = 1; i < sets.length; i++) {
			plotter.plotMF(sets[i].getName(), sets[i], discretizationLevel, null, false);
		}
		plotter.show(name);
	}

	/*
	 * private void plotControlSurface(boolean useCentroidDefuzzification, int
	 * input1Discs, int input2Discs, int input3Discs) { double output; double[] x =
	 * new double[input1Discs]; double[] y = new double[input2Discs]; double[] j =
	 * new double[input3Discs];
	 * 
	 * double[][][] z = new double[y.length][x.length][j.length]; double incrX,
	 * incrY, incrJ; incrX = CP.getDomain().getSize()/(input1Discs-1.0); incrY =
	 * CC.getDomain().getSize()/(input2Discs-1.0); incrJ =
	 * RAM.getDomain().getSize()/(input3Discs-1.0);
	 * 
	 * //first, get the values for(int currentX=0; currentX<input1Discs; currentX++)
	 * { x[currentX] = currentX * incrX; } for(int currentY=0; currentY<input2Discs;
	 * currentY++) { y[currentY] = currentY * incrY; }
	 * 
	 * for(int currentJ=0; currentJ<input3Discs; currentJ++) { j[currentJ] =
	 * currentJ * incrJ; }
	 * 
	 * 
	 * for(int currentX=0; currentX<input1Discs; currentX++) {
	 * CP.setInput(x[currentX]); for(int currentY=0; currentY<input2Discs;
	 * currentY++)
	 * {//System.out.println("Current x = "+currentX+"  current y = "+currentY);
	 * CC.setInput(y[currentY]); RAM.setInput(j[currentJ]);
	 * if(useCentroidDefuzzification) output = rulebase.evaluate(1).get(tip); else
	 * output = rulebase.evaluate(0).get(tip); z[currentY][currentX] = output; } }
	 * 
	 * //now do the plotting JMathPlotter plotter = new JMathPlotter();
	 * plotter.plotControlSurface("Control Surface", new String[]{food.getName(),
	 * service.getName(), "Tip"}, x, y, z, new Tuple(0.0,30.0), true); plotter.
	 * show("Interval Type-2 Fuzzy Logic System Control Surface for Tipping Example"
	 * ); }
	 */

	/*
	 * public static void main(String args[]) { new SimpleIT2FLSIntFLBCC(); }
	 */
}
