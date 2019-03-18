import java.util.ArrayList;
import java.util.HashMap;

/* Cousine class */
class Cousine implements Comparable<Cousine>{
	
	String name;
	ArrayList<Recipe> recipes;
	
	/* Constructor */
	public Cousine(String name) {
		super();
		this.name = name;
		recipes = new ArrayList<Recipe>();
	}
	
	public void addRecipe(Recipe r){
		recipes.add(r);
	}
	
	/* Printing methof */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for (Recipe r : recipes){
			sb.append(name + " " + r.toString() + "\n");
		}
		return sb.toString();
	}
	
	/* returns number of recipes in cuisine */
	public int count() {
		return recipes.size();
	}
	
	/* returns number of recipes of largest recipe */
	public int largestRecipeLength(){
		int max = recipes.get(0).count();
		for (int i=1; i<recipes.size(); i++)
			if (max < recipes.get(i).count())
				max = recipes.get(i).count();
		return max;
	}
	
	/* returns number of recipes with x ingredients */
	public int numRecipesOfLenX(int x){
		int count = 0;
		for (Recipe r : recipes){
			if (r.count() == x)
				count++;
		}
		return count;
	}
	
	/* returns number of recipes containing a specific ingredient */
	public int numRecipesContainingIngredient(String ingredientName){
		int count = 0;
		for (Recipe r : recipes){
			if (r.containsIngredient(ingredientName))
				count++;
		}
		return count;
	}
	
	/* returns number of recipes containing a specific ingredient pair */
	public int numRecipesContainingPair(String ingredient1, String ingredient2){
		int count = 0;
		for (Recipe r : recipes){
			if (r.containsPair(ingredient1, ingredient2))
				count++;
		}
		return count;
	}
	
	/* returns number of recipes containing a specific ingredient triplet */
	public int numRecipesContainingTriplet(String ingredient1, String ingredient2, String ingredient3){
		int count = 0;
		for (Recipe r : recipes){
			if (r.containsTriplet(ingredient1, ingredient2, ingredient3))
				count++;
		}
		return count;
	}
	
	/* returns prevalence of the ingredient (fraction of recipes containing the ingredient) */
	public double getIngredientPrevalence(String ingredientName){
		return (double)numRecipesContainingIngredient(ingredientName) / this.count();
	}
	
	/* returns prevalence of the ingredient pair (fraction of recipes containing the ingredient pair) */
	public double getPairPrevalence(String ingredient1, String ingredient2){
		return (double)numRecipesContainingPair(ingredient1, ingredient2) / this.count();
	}
	
	/* returns prevalence of the ingredient triplet (fraction of recipes containing the ingredient triplet) */
	public double getTripletPrevalence(String ingredient1, String ingredient2, String ingredient3){
		return (double)numRecipesContainingTriplet(ingredient1, ingredient2, ingredient3) / this.count();
	}
	
	/* returns the Ns measure for the cuisine (mean number of compound shared by ingredients)*/
	public double getNs(){
		double count = 0;
		for (Recipe r : recipes){
			count += r.getNs();
		}
		return count / this.count();
	}
	
	/* returns a collection of all ingredients in the cuisine */
	public HashMap<String, Ingredient> getIngredients(){
		HashMap<String, Ingredient> ingredients = new HashMap<String, Ingredient>();
		for (Recipe r : recipes){
			ingredients.putAll(r.ingredients);
		}
		return ingredients;
	}
	
	/* checks whether an ingredient pair is present in the cuisine */
	public boolean containsPair (String ingredient1, String ingredient2){
		for (Recipe r : recipes)
			if (r.containsPair(ingredient1, ingredient2))
				return true;
		return false;
	}
	
	/* returns a collection that maps the number of shared compounds to total number of ingredient pairs 
	 * that share that amount of compounds
	 */
	public HashMap<Integer, Integer> getAllPairWeights(){
		HashMap<Integer, Integer> pairWeights = new HashMap<Integer, Integer>();
		HashMap<String, Ingredient> ingredients = getIngredients();
		ArrayList<String> keys = new ArrayList<String>();
		keys.addAll(ingredients.keySet());
		for (int i=0; i<keys.size()-1; i++)
			for (int j=i+1; j<keys.size(); j++){
				if (ingredients.get(keys.get(i)).similarIngredients.containsKey(keys.get(j))){
					int weight = ingredients.get(keys.get(i)).similarIngredients.get(keys.get(j));
					if (pairWeights.containsKey(weight))
						pairWeights.put(weight, pairWeights.get(weight) + 1);
					else
						pairWeights.put(weight, 1);
				}
				else{
					if (pairWeights.containsKey(0))
						pairWeights.put(0, pairWeights.get(0) + 1);
					else
						pairWeights.put(0, 1);
				}
			}
		return pairWeights;
	}
	
	/* returns a collection that maps the number of shared compounds to the number of ingredient pairs 
	 * that share that amount of compounds which belong to some recipe within the cuisine
	 */
	public HashMap<Integer, Integer> getRecipePairWeights(){
		HashMap<Integer, Integer> pairWeights = new HashMap<Integer, Integer>();
		HashMap<String, Ingredient> ingredients = getIngredients();
		ArrayList<String> keys = new ArrayList<String>();
		keys.addAll(ingredients.keySet());
		for (int i=0; i<keys.size()-1; i++)
			for (int j=i+1; j<keys.size(); j++){
				if (containsPair(keys.get(i), keys.get(j))){
					if (ingredients.get(keys.get(i)).similarIngredients.containsKey(keys.get(j))){
						int weight = ingredients.get(keys.get(i)).similarIngredients.get(keys.get(j));
						if (pairWeights.containsKey(weight))
							pairWeights.put(weight, pairWeights.get(weight) + 1);
						else
							pairWeights.put(weight, 1);
					}
					else{
						if (pairWeights.containsKey(0))
							pairWeights.put(0, pairWeights.get(0) + 1);
						else
							pairWeights.put(0, 1);
					}
				}
			}
		return pairWeights;
	}
	
	/* returns a clone of the cuisine */
	public Cousine clone(){
		Cousine cousine = new Cousine(this.name);
		for (Recipe r : this.recipes)
			cousine.addRecipe(r.clone());
		return cousine;
	}
	
	/* removes an ingredient form all of the recipes in the cuisine */
	public void removeIngredient(String ingredientName){
		for (int i=0; i< recipes.size(); i++){			
			recipes.get(i).removeIngredient(ingredientName);
		}			
	}
	
	/* calculate the ingredient contribution to the Ns measure of the cuisine for a specific ingredient*/
	public double calculateIngredientContribution (String ingredientName){
		Cousine clone = this.clone();		
		double originalNs = clone.getNs();
		clone.removeIngredient(ingredientName);
		return originalNs - clone.getNs();
	}

	/* implementing the comparable interface to sort cuisines according to their Ns measures */
	@Override
	public int compareTo(Cousine o) {
		// TODO Auto-generated method stub
		return (Double.compare(o.getNs(), this.getNs()));
	}
}