/*
 * SimpleIT2FLSIntVideo.java
 *
 * Created on May 21st 2012
 *
 * Copyright 2012 Christian Wagner All Rights Reserved.
 */
package examples;

import generic.Input;
import generic.Output;
import generic.Tuple;
import intervalType2.sets.IntervalT2MF_Interface;
import intervalType2.sets.IntervalT2MF_Trapezoidal;
import intervalType2.system.IT2_Antecedent;
import intervalType2.system.IT2_Consequent;
import intervalType2.system.IT2_Rule;
import intervalType2.system.IT2_Rulebase;

import java.util.TreeMap;
import tools.JMathPlotter;
import type1.sets.T1MF_Trapezoidal;


/**
 * A simple example of an interval Type-2 FLS based on the "How much to tip the waiter"
 *  scenario.
 * The example is an extension of the Type-1 FLS example where we extend the MFs
 * and use the Interval Type-2 System classes. Note that in contrast to the type-1
 * case, here only two sets are used to model the service quality.
 * We have two inputs: food quality and service level and as an output we would
 * like to generate the applicable tip.
 * @author Christian Wagner
 */
public class SimpleIT2FLSIntVideo 
{
    Input BIAT, FIAT, RATIO;     //the inputs to the FLS
    Output VIDEO;             //the output of the FLS
    IT2_Rulebase rulebase;   //the rulebase captures the entire FLS
    
    public SimpleIT2FLSIntVideo()
    {
        //Define the inputs

    	BIAT = new Input("BIAT", new Tuple(0,10)); // CPU -> BIAT
        FIAT = new Input("FIAT", new Tuple(0,10)); // CC -> FIAT
        RATIO = new Input("RATIO PACKAGES", new Tuple(0,10)); // RAM -> RATIO

        
        VIDEO = new Output("VIDEO", new Tuple(0,10));   //  P -> VIDEO //a percentage for the tip

        // MF for Computational Power
        
        //MF1='limited':'itrapatype2',[-2.269 -0.9 0.45 2.4 -1.329 -0.01882 1.5 3.5 0.9]
        T1MF_Trapezoidal lowBiatUMF = new  T1MF_Trapezoidal("Upper MF for Limited BIAT", new double[] {-2.27, -0.9, 1.5, 3.5});   
        T1MF_Trapezoidal lowBiatLMF = new  T1MF_Trapezoidal("Lower MF for Limited BIAT", new double[] {-1.3, -0.2, 0.45, 2.4});
        IntervalT2MF_Trapezoidal lowBiatMF = new IntervalT2MF_Trapezoidal("IT2MF for limited BIAT",lowBiatUMF,lowBiatLMF);
        
        //MF2='reasonable':'itrapatype2',[1.6 3.8 5.4 7.4 2.6 4.6 6.3 8.4 0.9]
        T1MF_Trapezoidal reasonableBiatUMF = new  T1MF_Trapezoidal("Upper MF for Reasonable BIAT", new double[] {1.6, 3.8, 6.3, 8.4});  
        T1MF_Trapezoidal reasonableBiatLMF = new  T1MF_Trapezoidal("Lower MF for Reasonable BIAT", new double[] {2.6, 4.6, 5.4, 7.4});
        IntervalT2MF_Trapezoidal reasonableBiatMF = new IntervalT2MF_Trapezoidal("IT2MF for limited BIAT",reasonableBiatUMF,reasonableBiatLMF);
        
        //MF3='high':'itrapatype2',[6.65 7.65 10.19 12.34 7.65 8.5 11.19 13.34 0.9]
        T1MF_Trapezoidal highBiatUMF = new  T1MF_Trapezoidal("Upper MF for High BIAT", new double[] {6.65, 7.65, 11.19, 13.34});  
        T1MF_Trapezoidal highBiatLMF = new  T1MF_Trapezoidal("Lower MF for High BIAT", new double[] {7.65, 8.5, 10.19, 12.34});
        IntervalT2MF_Trapezoidal highBiatMF = new IntervalT2MF_Trapezoidal("IT2MF for limited BIAT",highBiatUMF,highBiatLMF);

        
        //plotMFs("Computational Power Membership Functions", new IntervalT2MF_Interface[]{limitedCPMF,reasonableCPMF, highCPMF}, 10);

        
        // até a l189
        // MF for Comunication Cost
        //MF1='small':'itrapatype2',[-2.27 -0.8 0.5 2.4 -1.327 -0.01682 1.35 3.4 0.9]
        T1MF_Trapezoidal smallFiatUMF = new  T1MF_Trapezoidal("Upper MF for Small FIAT", new double[] {-2.27, -0.8, 1.35, 3.4});   
        T1MF_Trapezoidal smallFiatLMF = new  T1MF_Trapezoidal("Lower MF for Small FIAT", new double[] {-1.3, -0.02, 0.5, 2.4});
        IntervalT2MF_Trapezoidal smallFiatMF = new IntervalT2MF_Trapezoidal("IT2MF for limited FIAT",smallFiatUMF,smallFiatLMF);
        
        //MF3='average':'itrapatype2',[1.5 4 5.2 7.5 2.5 4.8 6 8.5 0.9]
        T1MF_Trapezoidal averageFiatUMF = new  T1MF_Trapezoidal("Upper MF for Average FIAT", new double[] {1.5, 4, 6, 8.5});
        T1MF_Trapezoidal averageFiatLMF = new  T1MF_Trapezoidal("Lower MF for Average FIAT", new double[] {2.5, 4.8, 5.2, 7.5});
        IntervalT2MF_Trapezoidal averageFiatMF = new IntervalT2MF_Trapezoidal("IT2MF for Average FIAT",averageFiatUMF,averageFiatLMF);
        
        //MF2='big':'itrapatype2',[6.5 8.5 10 12 7.5 9.2 11.19 13.34 0.9]
        T1MF_Trapezoidal highFiatUMF = new  T1MF_Trapezoidal("Upper MF for Big FIAT", new double[] {6.5, 8.5, 11.19, 13.34}); 
        T1MF_Trapezoidal highFiatLMF = new  T1MF_Trapezoidal("Lower MF for Big FIAT", new double[] {7.5, 9.2, 10, 12});
        IntervalT2MF_Trapezoidal highFiatMF = new IntervalT2MF_Trapezoidal("IT2MF for Big FIAT",highFiatUMF,highFiatLMF);
        
        
        //plotMFs("Comunication Cost Membership Functions", new IntervalT2MF_Interface[]{reasonableFIATMF, averageCCMF, bigCCMF},10);
        
        // MF for RAM
        //MF1='small':'itrapatype2',[-2.27 -0.8 0.5 2.4 -1.327 -0.01682 1.35 3.4 0.9]
        T1MF_Trapezoidal lowRatioUMF = new  T1MF_Trapezoidal("Upper MF for Small RATIO", new double[] {-2.27, -0.8, 1.35, 3.4}); 
        T1MF_Trapezoidal lowRatioLMF = new  T1MF_Trapezoidal("Lower MF for Small RATIO", new double[] {-1.327, -0.01682, 0.5, 2.4});
        IntervalT2MF_Trapezoidal lowRatioMF = new IntervalT2MF_Trapezoidal("IT2MF for Small RATIO",lowRatioUMF,lowRatioLMF);
        
        //MF2='average':'itrapatype2',[1.5 4 5.2 7.5 2.5 4.8 6 8.5 0.9]
        T1MF_Trapezoidal reasonableRatioUMF = new  T1MF_Trapezoidal("Upper MF for Average RATIO", new double[] {1.5, 4, 6, 8.5});
        T1MF_Trapezoidal reasonableRatioLMF = new  T1MF_Trapezoidal("Lower MF for Average RATIO", new double[] {2.5, 4.8, 5.2, 7.5});
        IntervalT2MF_Trapezoidal reasonableRatioMF = new IntervalT2MF_Trapezoidal("IT2MF Average RATIO",reasonableRatioUMF, reasonableRatioLMF);
        
        //MF3='high':'itrapatype2',[6.5 8.5 10 12 7.5 9.2 11.19 13.34 0.9]
        T1MF_Trapezoidal highRatioUMF = new  T1MF_Trapezoidal("Upper MF for High RATIO", new double[] {6.5, 8.5, 11.19, 13.34});
        T1MF_Trapezoidal highRatioLMF = new  T1MF_Trapezoidal("Lower MF for High RATIO", new double[] {7.5, 9.2, 10, 12});
        IntervalT2MF_Trapezoidal highRatioMF = new IntervalT2MF_Trapezoidal("IT2MF for High RATIO",highRatioUMF,highRatioLMF);
        
        //plotMFs("RAM Membership Functions", new IntervalT2MF_Interface[]{smallRAMMF, averageRAMMF, highRAMMF}, 10);
        
        // MF for Priority (P)
        //MF1='low':'itrapatype2',[-1.74698039215686 -0.590980392156862 -0.569980392156862 3.99901960784314 -1.24678039215686 0.079019607843138 0.409019607843138 4.99901960784314 0.9]
        T1MF_Trapezoidal lowVideoUMF = new  T1MF_Trapezoidal("Upper MF for Low Video", new double[] {-1.75, -0.6, 0.41, 5});  
        T1MF_Trapezoidal lowVideoLMF = new  T1MF_Trapezoidal("Lower MF for Low Video", new double[] {-1.2, 0.08, -0.57, 4.0}); //
        IntervalT2MF_Trapezoidal lowVideoMF = new IntervalT2MF_Trapezoidal("IT2MF for Low Video ",lowVideoUMF,lowVideoLMF);
        
        //MF2='average':'itrapatype2',[0.5 4.5 4.83 8.5 1.5 5.16 5.5 9.5 0.9]
        T1MF_Trapezoidal averageVideoUMF = new  T1MF_Trapezoidal("Upper MF for Average Video", new double[] {0.5, 4.5, 5.5, 9.5}); 
        T1MF_Trapezoidal averageVideoLMF = new  T1MF_Trapezoidal("Lower MF for Average Video", new double[] {1.5, 5.16, 4.83, 8.5});
        IntervalT2MF_Trapezoidal averageVideoMF = new IntervalT2MF_Trapezoidal("IT2MF for Average Video ",averageVideoUMF,averageVideoLMF);
        
        //MF3='high':'itrapatype2',[5 9.61 9.94 11.3 6 10.3 10.7 11.8 0.9]
        T1MF_Trapezoidal highVideoUMF = new  T1MF_Trapezoidal("Upper MF for High Video", new double[] {5, 9.6, 10.7, 11.8});  
        T1MF_Trapezoidal highVideoLMF = new  T1MF_Trapezoidal("Lower MF for High Video", new double[] {6, 10.3, 9.94, 11.3});
        IntervalT2MF_Trapezoidal highVideoMF = new IntervalT2MF_Trapezoidal("IT2MF for High Video ", highVideoUMF, highVideoLMF);
        
        //plotMFs("Priority Membership Functions", new IntervalT2MF_Interface[]{lowPriorityMF, averagePriorityMF, highPriorityMF}, 10);

        //Set up the antecedents and consequents - note how the inputs are associated...
        IT2_Antecedent lowBIAT = new IT2_Antecedent("LowBIAT",  lowBiatMF, BIAT);
        IT2_Antecedent reasonableBIAT = new IT2_Antecedent("ReasonableBIAT", reasonableBiatMF, BIAT);
        IT2_Antecedent highBIAT = new IT2_Antecedent("HighBIAT", highBiatMF, BIAT);
        
        IT2_Antecedent lowFIAT = new IT2_Antecedent("LowFIAT", smallFiatMF,FIAT);
        IT2_Antecedent reasonableFIAT = new IT2_Antecedent("ReaonableFIAT", averageFiatMF,FIAT);
        IT2_Antecedent highFIAT = new IT2_Antecedent("HighFIAT", highFiatMF,FIAT);


        IT2_Antecedent smallRAM = new IT2_Antecedent("LowRatio", lowRatioMF,RATIO);
        IT2_Antecedent averageRAM = new IT2_Antecedent("ReasonableRatio",  reasonableRatioMF,RATIO);
        IT2_Antecedent highRAM = new IT2_Antecedent("HighRatio", highRatioMF,RATIO);
        
        IT2_Consequent lowPriority = new IT2_Consequent("LowVideo", lowVideoMF, VIDEO);
        IT2_Consequent averagePriority = new IT2_Consequent("AverageVideo", averageVideoMF, VIDEO);
        IT2_Consequent highPriority = new IT2_Consequent("AverageVideo", highVideoMF, VIDEO);
        
        
        // Set up the rulebase and add rules
        rulebase = new IT2_Rulebase(27);
        
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{lowBIAT, lowFIAT, smallRAM}, lowPriority));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{lowBIAT, lowFIAT, averageRAM}, lowPriority));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{lowBIAT, lowFIAT, highRAM}, lowPriority));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{lowBIAT, highFIAT , smallRAM}, lowPriority));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{lowBIAT, reasonableFIAT, averageRAM}, lowPriority));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{lowBIAT, reasonableFIAT, highRAM}, lowPriority));
        
        
        
        rulebase.setImplicationMethod((byte) 1);
        rulebase.setTypeIntersection("TL"); // default juzzy maxmin
        rulebase.setTypeUnion("TLN"); // default juzzy minmax
        
        
        //get some outputs
        // getPriority(inCP, inCC, inRAM);
        //getRating(8.0, 2.0, 2.0);
        getRating(1.0, 8.0, 1.0);
                
        //plot some sets, discretizing each input into 100 steps.
        plotMFs("BIAT Membership Functions", new IntervalT2MF_Interface[]{lowBiatMF, reasonableBiatMF, highBiatMF}, 10);
        plotMFs("FIAT Membership Functions", new IntervalT2MF_Interface[]{smallFiatMF, averageFiatMF, highFiatMF},10);
        plotMFs("RATIO Membership Functions", new IntervalT2MF_Interface[]{lowRatioMF, reasonableRatioMF, highRatioMF}, 10);
        plotMFs("VIDEO Membership Functions", new IntervalT2MF_Interface[]{lowVideoMF, averageVideoMF, highVideoMF}, 10);
        
        //plot control surface
        //do either height defuzzification (false) or centroid d. (true)
       // plotControlSurface(false, 100, 100);
        
        //print out the rules
        System.out.println("\n"+rulebase);
        System.out.println("\n Input BIAT: "+BIAT.getInput());
        
    }
    
    /**
     * Basic method that prints the output for a given set of inputs.
     * @param inCP
     * @param inCC
     * @param inRAM 
     */
    
    private void getRating(double inBiat, double inFiat, double inRatio)
    {
        //first, set the inputs
    	
    	BIAT.setInput(inBiat);
    	FIAT.setInput(inFiat);
    	RATIO.setInput(inRatio);
    	
        
        //now execute the FLS and print output
        System.out.println("The BIAT was: "+BIAT.getInput());
        System.out.println("The FIAT was: "+FIAT.getInput());
        System.out.println("The RATIO was: "+RATIO.getInput());
        System.out.println("Using center of sets type reduction, the IT2 FLS recommends a "
                + "priority of: "+rulebase.evaluate(0).get(VIDEO));  
        System.out.println("Using centroid type reduction, the IT2 FLS recommends a "
                + "rating of: "+rulebase.evaluate(1).get(VIDEO));
        
        
        //show the output of the raw centroids
        System.out.println("Centroid of the output for Priority (based on centroid type reduction):");
        TreeMap<Output, Object[]> centroid = rulebase.evaluateGetCentroid(1);
        Object[] centroidTip = centroid.get(VIDEO);
        Tuple centroidTipXValues = (Tuple)centroidTip[0];
        double centroidTipYValues = ((Double)centroidTip[1]);
            System.out.println(centroidTipXValues+" at y= "+centroidTipYValues);        
    }
    
    private void plotMFs(String name, IntervalT2MF_Interface[] sets, int discretizationLevel)
    {
        JMathPlotter plotter = new JMathPlotter();
        plotter.plotMF(sets[0].getName(), sets[0], discretizationLevel, null, false);
       
        for (int i=1;i<sets.length;i++)
        {
            plotter.plotMF(sets[i].getName(), sets[i], discretizationLevel, null, false);
        }
        plotter.show(name);
    }

    /*
    private void plotControlSurface(boolean useCentroidDefuzzification, int input1Discs, int input2Discs, int input3Discs)
    {
        double output;
        double[] x = new double[input1Discs];
        double[] y = new double[input2Discs];
        double[] j = new double[input3Discs];
        
        double[][][] z = new double[y.length][x.length][j.length];
        double incrX, incrY, incrJ;
        incrX = CP.getDomain().getSize()/(input1Discs-1.0);
        incrY = CC.getDomain().getSize()/(input2Discs-1.0);
        incrJ = RAM.getDomain().getSize()/(input3Discs-1.0);

        //first, get the values
        for(int currentX=0; currentX<input1Discs; currentX++)
        {
            x[currentX] = currentX * incrX;        
        }
        for(int currentY=0; currentY<input2Discs; currentY++)
        {
            y[currentY] = currentY * incrY;
        }
        
        for(int currentJ=0; currentJ<input3Discs; currentJ++)
        {
            j[currentJ] = currentJ * incrJ;
        }

        
        for(int currentX=0; currentX<input1Discs; currentX++)
        {
            CP.setInput(x[currentX]);
            for(int currentY=0; currentY<input2Discs; currentY++)
            {//System.out.println("Current x = "+currentX+"  current y = "+currentY);
                CC.setInput(y[currentY]);
                RAM.setInput(j[currentJ]);
                if(useCentroidDefuzzification)
                    output = rulebase.evaluate(1).get(tip);
                else
                    output = rulebase.evaluate(0).get(tip);
                z[currentY][currentX] = output;
            }    
        }
        
        //now do the plotting
        JMathPlotter plotter = new JMathPlotter();
        plotter.plotControlSurface("Control Surface",
                new String[]{food.getName(), service.getName(), "Tip"}, x, y, z, new Tuple(0.0,30.0), true); 
        plotter.show("Interval Type-2 Fuzzy Logic System Control Surface for Tipping Example");
    }
    */
    
    /*public static void main(String args[])
    {
        new SimpleIT2FLSIntFLBCC();
    }*/
}
