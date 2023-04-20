/******************************************************************************
*  A Teaching GA					  Developed by Hal Stringer & Annie Wu, UCF
*  Version 2, January 18, 2004
*******************************************************************************/

import java.io.*;
import java.lang.reflect.Parameter;
import java.util.*;
import java.text.*;

public class Chromo
{
/*******************************************************************************
*                            INSTANCE VARIABLES                                *
*******************************************************************************/

	public ArrayList<Integer> chromo = new ArrayList<>();
	public double rawFitness;
	public double sclFitness;
	public double proFitness;
	public int n;
/*******************************************************************************
*                            INSTANCE VARIABLES                                *
*******************************************************************************/

	private static double randnum;


/*******************************************************************************
*                              CONSTRUCTORS                                    *
*******************************************************************************/

	

	public static int getRandomWithExclusion(Random rnd, int start, int end, ArrayList<Integer> exclude) {
		exclude.sort(Comparator.naturalOrder());
		int random = start + rnd.nextInt(end - start + 1 - exclude.size());
		for (int ex : exclude) {
			if (random < ex) {
				break;
			}
			random++;
		}
		return random;
	}

	public static String getChromo(ArrayList<Integer> arr){
		String temp = "";
		for(int i = 0 ; i < arr.size(); i++){
			temp += Integer.toString(arr.get(i)); 
			temp += "-";
		}

		return temp;
	}

	public Chromo(){
		//  Set gene values to a randum sequence of 1's and 0's
		// char geneBit;
		// chromo = "";
		ArrayList<Integer> used = new ArrayList<>();

		//need to generate chromosome more generall to be used with multiple problems
		// Have Gene size be the variable depending on the number of digits to represent each city
		//let num genes represent the number of cities. 
			//Inserting Introns
				//We can at average segments inject introns to get our wanted amount? --> prob best approach

		//Im sure to use get randome with exclsions the exclusion array must be sorted!!
		
		 this.n = Parameters.numGenes;
		if(Parameters.intronPercent != 0){														
			

			this.n += (int) (Parameters.numGenes * Parameters.intronPercent);

			
		}

		for (int i=0; i<this.n; i++){
			// for (int j=0; j<Parameters.geneSize; j++){
				
				

				
			// 	int randnumber = getRandomWithExclusion(Search.r, 0, 47, used);

			// 	// int a = 65;
			// 	// int b = a + 6;
			// 	// if(randnumber > 25){
					
			// 	// 	chromo += (char) (b+randnumber);
			// 	// }else{
			// 	// 	chromo += (char) (a+randnumber);
			// 	// }
			// 	String add = "";
			// 	if(randnumber < 10){
			// 		add += ('0' + Integer.toString(randnumber));
			// 	}else{
			// 		add += Integer.toString(randnumber);
			// 	}

			// 	// for(int val : used){
			// 	// 	int comp = Integer.parseInt(add);

			// 	// 	if(val == comp){
			// 	// 		System.out.println("Copy!");
			// 	// 	}
			// 	// }

			// 	used.add(randnumber);
			// 	used.sort(Comparator.naturalOrder());

				
			// 	chromo += add;
			// }
			
			//Generate a random number within the range of our cities
			int randnumber = getRandomWithExclusion(Search.r, 0, n-1, used);
			//add this to the list of used numbers
			used.add(randnumber);
			//sort since thats an assumption we make in our getRandomWithExclusionFunction
			
			
			//from here we take this string
			// String geneBit = Integer.toString(randnumber);
			
			// for(int j =0 ; j < Parameters.geneSize-geneBit.length(); j++){
			// 	//pad gene bit with zeroes as needed;
			// 	geneBit = '0' + geneBit;
			// }


			// this.chromo += geneBit;
		}
		// System.out.println("Start");
		// System.out.println(chromo);
		// System.out.println(chromo.length());
		// System.out.println("End");
		this.chromo = used;
		
		
		// System.out.println(getChromo(this.chromo));
		// System.out.println(this.chromo.size());
		this.rawFitness = -1;   //  Fitness not yet evaluated
		this.sclFitness = -1;   //  Fitness not yet scaled
		this.proFitness = -1;   //  Fitness not yet proportionalized
	}


/*******************************************************************************
*                                MEMBER METHODS                                *
*******************************************************************************/

	//  Get Alpha Represenation of a Gene **************************************

	// public String getGeneAlpha(int geneID){
	// 	int start = geneID * Parameters.geneSize;
	// 	int end = (geneID+1) * Parameters.geneSize;
	// 	String geneAlpha = this.chromo.substring(start, end);
	// 	return (geneAlpha);
	// }

	//  Get Integer Value of a Gene (Positive or Negative, 2's Compliment) ****

	// public int getIntGeneValue(int geneID){
	// 	String geneAlpha = "";
	// 	int geneValue;
	// 	char geneSign;
	// 	char geneBit;
	// 	geneValue = 0;
	// 	geneAlpha = getGeneAlpha(geneID);
	// 	for (int i=Parameters.geneSize-1; i>=1; i--){
	// 		geneBit = geneAlpha.charAt(i);
	// 		if (geneBit == '1') geneValue = geneValue + (int) Math.pow(2.0, Parameters.geneSize-i-1);
	// 	}
	// 	geneSign = geneAlpha.charAt(0);
	// 	if (geneSign == '1') geneValue = geneValue - (int)Math.pow(2.0, Parameters.geneSize-1);
	// 	return (geneValue);
	// }

	//  Get Integer Value of a Gene (Positive only) ****************************

	// public int getPosIntGeneValue(int geneID){
	// 	String geneAlpha = "";
	// 	int geneValue;
	// 	char geneBit;
	// 	geneValue = 0;
	// 	geneAlpha = getGeneAlpha(geneID);
	// 	for (int i=Parameters.geneSize-1; i>=0; i--){
	// 		geneBit = geneAlpha.charAt(i);
	// 		if (geneBit == '1') geneValue = geneValue + (int) Math.pow(2.0, Parameters.geneSize-i-1);
	// 	}
	// 	return (geneValue);
	// }

	//  Mutate a Chromosome Based on Mutation Type *****************************

	public void doMutation(){

		String mutChromo = "";
		char x;

		switch (Parameters.mutationType){

		case 1:     //  Replace with new random number

			// for (int j=0; j<(Parameters.geneSize * Parameters.numGenes); j++){
			// 	x = this.chromo.charAt(j);
			// 	randnum = Search.r.nextDouble();
			// 	if (randnum < Parameters.mutationRate){
			// 		if (x == '1') x = '0';
			// 		else x = '1';
			// 	}
			// 	mutChromo = mutChromo + x;
			// }

			

			// this.chromo = mutChromo;
			break;
		case 2: //random swap
			//implement random swap here
			int p1 = Search.r.nextInt(this.chromo.size());
			
			int p2 = Search.r.nextInt(this.chromo.size());
			
			while(p1 == p2){
				p2 = Search.r.nextInt(this.chromo.size());
			}

			

			int temp = this.chromo.get(p1);

			this.chromo.set(p1, this.chromo.get(p2));

			this.chromo.set(p2,temp);

			break;
		default:
			System.out.println("ERROR - No mutation method selected");
		}
	}

/*******************************************************************************
*                             STATIC METHODS                                   *
*******************************************************************************/

	//  Select a parent for crossover ******************************************

	public static int selectParent(){

		double rWheel = 0;
		int j = 0;
		int k = 0;

		switch (Parameters.selectType){

		case 1:     // Proportional Selection
			randnum = Search.r.nextDouble();
			for (j=0; j<Parameters.popSize; j++){
				rWheel = rWheel + Search.member[j].proFitness;
				if (randnum < rWheel) return(j);
			}
			break;

		case 3:     // Random Selection
			randnum = Search.r.nextDouble();
			j = (int) (randnum * Parameters.popSize);
			return(j);

		case 2:     //  Tournament Selection
			//k tournament size
			int k_tourn = 50;

			ArrayList<Integer> tourn_pop = new ArrayList<>();
			
			for(int i = 0 ; i < k_tourn; i++){
				tourn_pop.add(Search.r.nextInt(Parameters.popSize));
				
			}

			int best = tourn_pop.get(0);

			

			for(int i =1 ; i < k_tourn; i++){

				if(Search.member[tourn_pop.get(i)].rawFitness < Search.member[best].rawFitness)best = tourn_pop.get(i);
			}

			return best;

		default:
			System.out.println("ERROR - No selection method selected");
		}
	return(-1);
	}

	//  Produce a new child from two parents  **********************************

	public static ArrayList<Integer> order_one(int p1, int p2, Chromo parent1, Chromo parent2){
		
		ArrayList<Integer> partition = new ArrayList<>();

		

		ArrayList<Integer> newChromo = new ArrayList<>();
		

		
		for(int i = p1; i < p2; i++){
			partition.add(parent1.chromo.get(i));
		}
		int i = 0;

		while(newChromo.size() < p1){

			int val = parent2.chromo.get(i);

			Boolean exist = false;

			for(int j = 0; j < partition.size(); j++){
				if(val == partition.get(j)){
					exist = true;
				}
			}

			if(exist != true){
				newChromo.add(val);
			}
			i++;
		}

		newChromo.addAll(partition);

		while(newChromo.size() < parent1.n){

			int val = parent2.chromo.get(i);

			Boolean exist = false;

			for(int j = 0; j < partition.size(); j++){
				if(val == partition.get(j)){
					exist = true;
				}
			}

			if(exist != true){
				newChromo.add(val);
			}
			i++;
		}

		return newChromo;
	
	}

	public static void mateParents(int pnum1, int pnum2, Chromo parent1, Chromo parent2, Chromo child1, Chromo child2){

		int xoverPoint1;
		int xoverPoint2;

		switch (Parameters.xoverType){

		case 1:     //  Single Point Crossover

			// //  Select crossover point
			// xoverPoint1 = 1 + (int)(Search.r.nextDouble() * (Parameters.numGenes * Parameters.geneSize-1));

			// //  Create child chromosome from parental material
			// child1.chromo = parent1.chromo.substring(0,xoverPoint1) + parent2.chromo.substring(xoverPoint1);
			// child2.chromo = parent2.chromo.substring(0,xoverPoint1) + parent1.chromo.substring(xoverPoint1);
			
			break;

		case 2:     //  Two Point Crossover
			break;
		case 3:     //  Uniform Crossover
			
			break;
		case 4: //Edge Recombination
			//edge recombination based on characters in the chromo
			
		

			break;
		case 5:
			//this is where we will implement Order 1 Crossover
			
			xoverPoint1 = Search.r.nextInt(parent1.n);

			xoverPoint2 = Search.r.nextInt(parent1.n);

			while(xoverPoint1 == xoverPoint2){
				xoverPoint2 = Search.r.nextInt(parent1.chromo.size());
			}

			if(xoverPoint1 > xoverPoint2){
				int temp = xoverPoint2;
				xoverPoint2 = xoverPoint1;
				xoverPoint1 = temp;
			}

			child1.chromo = order_one(xoverPoint1, xoverPoint2, parent1, parent2);
			
			child2.chromo = order_one(xoverPoint1, xoverPoint2, parent2, parent1);

			break;
		default:
			System.out.println("ERROR - Bad crossover method selected");
		}

		//  Set fitness values back to zero
		child1.rawFitness = -1;   //  Fitness not yet evaluated
		child1.sclFitness = -1;   //  Fitness not yet scaled
		child1.proFitness = -1;   //  Fitness not yet proportionalized
		child2.rawFitness = -1;   //  Fitness not yet evaluated
		child2.sclFitness = -1;   //  Fitness not yet scaled
		child2.proFitness = -1;   //  Fitness not yet proportionalized
	}

	//  Produce a new child from a single parent  ******************************

	public static void mateParents(int pnum, Chromo parent, Chromo child){

		//  Create child chromosome from parental material
		child.chromo = parent.chromo;

		//  Set fitness values back to zero
		child.rawFitness = -1;   //  Fitness not yet evaluated
		child.sclFitness = -1;   //  Fitness not yet scaled
		child.proFitness = -1;   //  Fitness not yet proportionalized
	}

	//  Copy one chromosome to another  ***************************************

	public static void copyB2A (Chromo targetA, Chromo sourceB){

		targetA.chromo = sourceB.chromo;

		targetA.rawFitness = sourceB.rawFitness;
		targetA.sclFitness = sourceB.sclFitness;
		targetA.proFitness = sourceB.proFitness;
		return;
	}

}   // End of Chromo.java ******************************************************
