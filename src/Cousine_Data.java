import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/* main class of the project */
public class Cousine_Data {
	
	/* collection of all ingredients in the flavor network */
	static HashMap<String, Ingredient> ingredients;
	/* collection of all cuisines in the flavor network */
	static HashMap<String, Cousine> cousines;
	
	/* main methond */
	public static void main(String[] args) {
		
		/* read the flavor network (or backbone) */
		readData();
		System.out.println("Number of ingredients in largest recipe: " + getLargestRecipeLength());
		System.out.println("Number of ingredients in network: " + ingredients.size());
		System.out.println("Average number of ingredients per recipe: " + getAvgNumIngredientsPerRecipe());
		
		/* save plot data in a file for the recipe size distributions and ingredient frequencies for each cuisine */
		writeRecipeSizeDist();
		writeIngredientFreqPerCousine();
		
		/* generate a random cuisine */
		Cousine randomCousine = generateRandomCousineFromExistingRecepies(10000);
		System.out.println("Ns measure of random cuisine: " + randomCousine.getNs());
		
		/* calculate delta Ns for each cuisine, sort cuisines according to this measure and print plot data */
		ArrayList<Cousine> list = new ArrayList();
		for (String key : cousines.keySet())
			list.add(cousines.get(key));
		Collections.sort(list);
		for (Cousine c : list)
			System.out.print(c.getNs() - randomCousine.getNs() + ",");
		System.out.println();
		for (Cousine c : list)
			System.out.print("'" + c.name + "'" + ",");
		
		/* print the maximal number of shared compounds between any two ingredients in a cuisine */
		for (String key : cousines.keySet())
			System.out.println(key + ": " + getMaxWeight(cousines.get(key).getIngredients()));
		
		/* print plot data for the regression graphs */
		writePRForCousine(cousines.get("NorthAmerican"));
		writePRForCousine(cousines.get("EastAsian"));
		
		/* print the authenticity tables for all cuisines (6 most authentic ingredients, pairs and triplets) */
		printCousineAuthenticity(cousines.get("NorthAmerican"), 6);
		printCousineAuthenticity(cousines.get("WesternEuropean"), 6);
		printCousineAuthenticity(cousines.get("SouthernEuropean"), 6);
		printCousineAuthenticity(cousines.get("LatinAmerican"), 6);
		printCousineAuthenticity(cousines.get("EastAsian"), 6);
		
		Cousine NA = cousines.get("NorthAmerican");
		Cousine clone = NA.clone();
		System.out.println(NA.getIngredients().size());
		
		/* test whether clone is a deep copy */
		clone.removeIngredient("egg");
		System.out.println(NA.getIngredients().size());
		System.out.println(clone.getIngredients().size());
				
		ArrayList<IngredientContribution> list = getIngredientContributions(NA);
		
		/* print plot data for the ingredient contribution bubble chart */
		System.out.print("cont = [");
		for (int i=0; i<list.size()-1; i++)
			System.out.print(list.get(i).contribution + ",");
		System.out.print(list.get(list.size()-1).contribution + "];");
		System.out.println();
		System.out.print("prev = [");
		for (int i=0; i<list.size()-1; i++)
			System.out.print(((list.get(i).prevailance*100 > 10)? list.get(i).prevailance*100 : 10) + ",");
		System.out.print(((list.get(list.size()-1).prevailance*100 > 10)? list.get(list.size()-1).prevailance*100 : 10) + "];");
		System.out.println();
		System.out.print("freq = [");
		for (int i=0; i<list.size()-1; i++)
			System.out.print(((double)NA.numRecipesContainingIngredient(list.get(i).ingredient.name)/NA.count()) + ",");
		System.out.print(((double)NA.numRecipesContainingIngredient(list.get(list.size()-1).ingredient.name)/NA.count()) + "];");
		System.out.println();
		System.out.print("names = [");
		for (int i=0; i<list.size()-1; i++)
			System.out.print("'" + list.get(i).ingredient.name + "',");
		System.out.println("'" + list.get(list.size()-1).ingredient.name + "'];");
		/* Reverse the collection for East Asia */
		Collections.reverse(list);
		
		/* print plot data for the change of Ns after removing ingredients from the cuisine */
		printNsChangeAfterRemovingIngredients(35, list, NA);
	}
	
	/* returns the number of ingredients in the largest recipe */
	public static int getLargestRecipeLength(){
		int max = 0;
		for (String key : cousines.keySet()){
			if (max < cousines.get(key).largestRecipeLength());
				max = cousines.get(key).largestRecipeLength();
		}
		return max;
	}
	
	/* prints the recipe size distributions in a file */
	public static void writeRecipeSizeDist(){
		File file = new File("Recipe Size Distributions.csv");
		try {
			FileOutputStream fout = new FileOutputStream(file);
			PrintWriter pw = new PrintWriter(fout);
			int recipeLength = getLargestRecipeLength();
			for (String key : cousines.keySet()){
				String line = key + ",";
				Cousine c = cousines.get(key);
				for (int i=0; i<recipeLength-1; i++)
					line += (double)c.numRecipesOfLenX(i)/c.count() + ","; 
				line += (double)c.numRecipesOfLenX(recipeLength-1)/c.count(); 
				pw.println(line);	
			}
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* print the ingredient frequencies (per cuisine) in a file */
	public static void writeIngredientFreqPerCousine(){
		File file = new File("Ingredient frequency per cousine.txt");
		try {
			FileOutputStream fout = new FileOutputStream(file);
			PrintWriter pw = new PrintWriter(fout);
			Set<String> keys = ingredients.keySet();
			for (String key : cousines.keySet()){
				String line = key + " = [";
				Cousine c = cousines.get(key);
				ArrayList<Double> freq = new ArrayList<Double>();
				for (String ingredientName : keys)
					freq.add((double)c.numRecipesContainingIngredient(ingredientName)/c.count()); 
				Collections.sort(freq);
				Collections.reverse(freq);
				for (int i=0; i<freq.size()-1; i++)
					line += freq.get(i) + ",";
				line += freq.get(freq.size()-1) + "];"; 
				pw.println(line);	
			}
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* prints plot data for the fraction of pairs that share a given number of compounds that belong to
	 * at least one recipe within a cuisine in a file
	 */
	public static void writePRForCousine (Cousine c){
		HashMap<Integer, Integer> allPairs = c.getAllPairWeights(), recipePairs = c.getRecipePairWeights();
		ArrayList<Integer> keys = new ArrayList<Integer>();
		keys.addAll(allPairs.keySet());
		Collections.sort(keys);
		StringBuilder Ns = new StringBuilder(), Pr = new StringBuilder();
		for (int i=0; i< keys.size()-1; i++){
			if (allPairs.get(keys.get(i)) > 5){
				Ns.append(keys.get(i) + ",");
				if (recipePairs.containsKey(keys.get(i)))
					Pr.append((double)recipePairs.get(keys.get(i)) / allPairs.get(keys.get(i)) + ",");
				else
					Pr.append(0 + ",");
			}
		}
		/* to remove noise consider points containing more than 5 pairs */
		if (allPairs.get(keys.get(keys.size()-1)) > 5){
			Ns.append(keys.get(keys.size()-1) + ",");
			if (recipePairs.containsKey(keys.get(keys.size()-1)))
				Pr.append((double)recipePairs.get(keys.get(keys.size()-1)) / allPairs.get(keys.get(keys.size()-1)) + ",");
			else
				Pr.append(0 + ",");
		}
		File file = new File(c.name + "Pr.csv");
		try {
			FileOutputStream fout = new FileOutputStream(file);
			PrintWriter pw = new PrintWriter(fout);
			pw.println(Ns.toString());
			pw.println(Pr.toString());
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* read the flavor network input file (or backbone) and the recipe input file */
	public static void readData() {
		
		ingredients = new HashMap<String, Ingredient>();
		cousines = new HashMap<String, Cousine>();
		
		/* Choose whether to read entire flavor network or backbone */
		File network = new File("Network without labels.csv");
		//File network = new File("backbone.csv");

		try {
			FileInputStream fin = new FileInputStream(network);
			Scanner in = new Scanner(fin);
			while (in.hasNextLine()){
				String[] line = in.nextLine().split(",");
				Ingredient ingredient1 = (ingredients.containsKey(line[0]))? ingredients.get(line[0]) : new Ingredient(line[0]);
				Ingredient ingredient2 = (ingredients.containsKey(line[1]))? ingredients.get(line[1]) : new Ingredient(line[1]);
				ingredient1.addIngredient(ingredient2, Integer.parseInt(line[2]));
				ingredients.put(line[0], ingredient1);
				ingredients.put(line[1], ingredient2);			
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/* read recipes from file */
		File recipeFile = new File("Recipes.csv");
		try {
			FileInputStream fin = new FileInputStream(recipeFile);
			Scanner in = new Scanner(fin);
			while (in.hasNextLine()){
				String[] line = in.nextLine().split(",");
				Cousine cousine = (cousines.containsKey(line[0]))? cousines.get(line[0]) : new Cousine(line[0]);
				Recipe recipe = new Recipe();
				boolean hasAllIngredients = true;
				for (int i=1; i<line.length; i++){					
					if (ingredients.containsKey(line[i])){
						recipe.addIngredient(ingredients.get(line[i]));
					}
					else {
						hasAllIngredients = false;
						recipe.addIngredient(new Ingredient(line[i]));
					}
				}
				/* uncomment this line if reading from backbone */
				//if (!hasAllIngredients) continue;
				cousine.addRecipe(recipe);
				cousines.put(line[0], cousine);
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/* returns a randomly generated cuisine with the Poisson approach (less effective) */	
	public static Cousine generateRandomCousine(int numRecepies, double poissonLambda) {
        System.out.println("generating random Cousine...");
        Cousine randomCousine = new Cousine("randomCousine");
        ArrayList<String> ings = new ArrayList<String>();
        ings.addAll(ingredients.keySet());
        Random rand = new Random();
        for(int i=0;i<numRecepies;i++) {
            int numIngredinets = getPoisson(poissonLambda);
            Recipe recipe = new Recipe();
            for(int j=0;j<numIngredinets;j++) {
                Ingredient ing = ingredients.get(ings.get(rand.nextInt(ings.size())));
                recipe.addIngredient(ing);
            }
            randomCousine.addRecipe(recipe);
        }
        return randomCousine;
    }
	
	/* generates a random cuisine from existing recipes (more effective) */
	public static Cousine generateRandomCousineFromExistingRecepies(int numRecepies) {
		System.out.println("generating random Cousine...");
        ArrayList<String> keys = new ArrayList<String>();
        keys.addAll(cousines.keySet());
        Cousine randomCousine = new Cousine("randomCousine");
        Random rand = new Random();
        for(int i=0;i<numRecepies;i++) {
            Cousine cousine = cousines.get(keys.get(rand.nextInt(keys.size())));
            randomCousine.addRecipe(cousine.recipes.get(rand.nextInt(cousine.recipes.size())));
        }
        return randomCousine;
    }
	
	/* generate random variable according to Poisson distribution */
	public static int getPoisson(double lambda) {
        double L = Math.exp(-lambda);
        double p = 1.0;
        int k = 0;
        do {
            k++;
            p *= Math.random();
        } while (p > L);
        return k - 1;
    }
	
	/* returns average number of ingredients per recipe */
	public static double getAvgNumIngredientsPerRecipe(){
		int numIng = 0;
		int numRec = 0;
		for (String key : cousines.keySet()){
			for (Recipe r : cousines.get(key).recipes){
				numIng += r.count();
			}
			numRec += cousines.get(key).count();
		}
		return (double)numIng / numRec;
	}
	
	/* returns the maximal weight (number of shared compounds) between any two ingredients within a given collection */
	public static int getMaxWeight(HashMap<String, Ingredient> ingredients){
		int max = 0;
		for (String ingredient : ingredients.keySet()){
			for (String similarIngredient : ingredients.get(ingredient).similarIngredients.keySet()){
				int weight = ingredients.get(ingredient).similarIngredients.get(similarIngredient);
				if (max < weight)
					max = weight;
			}
		}
		return max;
	}
	
	/* calculate ingredient relative prevalence (authenticity) for an ingredient within a cuisine */
	public static double getIngredientRelativePrevailance(Ingredient i, Cousine c){
		double prevailance = 0;
		for (String key : cousines.keySet()){
			if (!key.equals(c.name))
				prevailance += cousines.get(key).getIngredientPrevalence(i.name);
		}
		return c.getIngredientPrevalence(i.name) - (prevailance / (c.count() - 1));
	}
	
	/* calculate ingredient relative prevalence (authenticity) for an ingredient pair within a cuisine */
	public static double getPairRelativePrevailance(Ingredient i1, Ingredient i2, Cousine c){
		double prevailance = 0;
		for (String key : cousines.keySet()){
			if (!key.equals(c.name)){
				prevailance += cousines.get(key).getPairPrevalence(i1.name, i2.name);
			}
		}
		return c.getPairPrevalence(i1.name, i2.name) - (prevailance / (c.count() - 1));
	}
	
	/* calculate ingredient relative prevalence (authenticity) for an ingredient triplet within a cuisine */
	public static double getTripletRelativePrevailance(Ingredient i1, Ingredient i2, Ingredient i3, Cousine c){
		double prevailance = 0;
		for (String key : cousines.keySet()){
			if (!key.equals(c.name))
				prevailance += cousines.get(key).getTripletPrevalence(i1.name, i2.name, i3.name);
		}
		return c.getTripletPrevalence(i1.name, i2.name, i3.name) - (prevailance / (c.count() - 1));
	}
	
	/* get a list of ingredients that is sortable according to their prevalence */
	public static ArrayList<IngredientPrevailance> getIngredientPrevailances(Cousine c){		
		HashMap<String,Ingredient> ingredients = c.getIngredients();
		ArrayList<IngredientPrevailance> list = new ArrayList<IngredientPrevailance>();
		for (String key : ingredients.keySet()){			
			IngredientPrevailance ingredientPrevailance = new IngredientPrevailance(ingredients.get(key), getIngredientRelativePrevailance(ingredients.get(key), c));
			list.add(ingredientPrevailance);
		}
		Collections.sort(list);
		return list;
	}
	
	/* get a list of ingredient pairs that is sortable according to their prevalence */
	public static ArrayList<PairPrevailance> getPairPrevailances(Cousine c, ArrayList<String> keys){		
		ArrayList<PairPrevailance> list = new ArrayList<PairPrevailance>();
		HashMap<String, Ingredient> ingredients = c.getIngredients();
		if (keys.size()<2)
			return list;
		for (int i=0; i<keys.size()-1; i++){			
			for (int j=i+1; j<keys.size(); j++){
				PairPrevailance pairPrevailance = new PairPrevailance(ingredients.get(keys.get(i)), ingredients.get(keys.get(j)), getPairRelativePrevailance(ingredients.get(keys.get(i)), ingredients.get(keys.get(j)), c));
				list.add(pairPrevailance);
			}
			
		}
		Collections.sort(list);
		return list;
	}
	
	/* get a list of ingredient triplets that is sortable according to their prevalence */
	public static ArrayList<TripletPrevailance> getTripletPrevailances(Cousine c, ArrayList<String> keys){		
		ArrayList<TripletPrevailance> list = new ArrayList<TripletPrevailance>();
		HashMap<String, Ingredient> ingredients = c.getIngredients();
		if (keys.size()<3)
			return list;
		for (int i=0; i<keys.size()-2; i++){			
			for (int j=i+1; j<keys.size()-1; j++){
				for (int k=j+1; k<keys.size(); k++){
					TripletPrevailance tripletPrevailance = new TripletPrevailance(ingredients.get(keys.get(i)), ingredients.get(keys.get(j)), ingredients.get(keys.get(k)), getTripletRelativePrevailance(ingredients.get(keys.get(i)), ingredients.get(keys.get(j)), ingredients.get(keys.get(k)), c));
					list.add(tripletPrevailance);
				}				
			}
			
		}
		Collections.sort(list);
		return list;
	}
	
	/* get a list of ingredients that is sortable according to their contribution to Ns of the cuisine c */
	public static ArrayList<IngredientContribution> getIngredientContributions(Cousine c){		
		HashMap<String,Ingredient> ingredients = c.getIngredients();
		ArrayList<IngredientContribution> list = new ArrayList<IngredientContribution>();
		int i=0;
		for (String key : ingredients.keySet()){			
			IngredientContribution ingredientContribution = new IngredientContribution(ingredients.get(key), 
					getIngredientRelativePrevailance(ingredients.get(key), c), c.calculateIngredientContribution(key));
			list.add(ingredientContribution);
		}
		Collections.sort(list);
		return list;
	}
	
	/* prints the authenticity tables for the top x authentic ingredients, pairs and triplets within a cuisine */
	public static void printCousineAuthenticity(Cousine c, int x){		
		ArrayList<IngredientPrevailance> ingredientPrevailace = getIngredientPrevailances(c);		
		System.out.println("Most authentic ingredients in " + c.name + " cousine:");
		for (int i=0; i < ((ingredientPrevailace.size() < x)? ingredientPrevailace.size() : x); i++)
			System.out.println(ingredientPrevailace.get(i));
		
		ArrayList<String> top50 = new ArrayList<String>();
		for (int i=0; i<((ingredientPrevailace.size() < 50)? ingredientPrevailace.size() : 50); i++)
			top50.add(ingredientPrevailace.get(i).ingredient.name);

		ArrayList<PairPrevailance> pairPrevailace = getPairPrevailances(c, top50);
		System.out.println("Most authentic pairs of ingredients in " + c.name + " cousine:");
		for (int i=0; i < ((pairPrevailace.size() < x)? pairPrevailace.size() : x); i++)
			System.out.println(pairPrevailace.get(i));
		
		ArrayList<TripletPrevailance> tripletPrevailace = getTripletPrevailances(c, top50);
		System.out.println("Most authentic triplets of ingredients in " + c.name + " cousine:");
		for (int i=0; i < ((tripletPrevailace.size() < x)? tripletPrevailace.size() : x); i++)
			System.out.println(tripletPrevailace.get(i));
	}
	
	/* prints plot data for the change of Ns of the cuisine after removing ingredients one by one from the cuisine */ 
	public static void printNsChangeAfterRemovingIngredients(int numIngredients, ArrayList<IngredientContribution> list, Cousine c){
		if (numIngredients > list.size()){
			System.out.println("Cousine has less than " + numIngredients + " ingredients!");
			return;
		}
		System.out.print("removed = [");
		for (int i=0; i<numIngredients; i++)
			System.out.print(i + ",");
		System.out.println(numIngredients + "];");
		Cousine clone = c.clone();
		System.out.print("Ns = [" + clone.getNs() + ",");
		for (int i=0;i<numIngredients-1;i++){
			clone.removeIngredient(list.get(i).ingredient.name);
			System.out.print(clone.getNs() + ",");
		}
		clone.removeIngredient(list.get(numIngredients-1).ingredient.name);
		System.out.println(clone.getNs() + "];");
	}
}
