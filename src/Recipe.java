import java.util.ArrayList;
import java.util.HashMap;
 
/* recipe class */
class Recipe{
	
	HashMap<String, Ingredient> ingredients;
	
	/* constructor */
	public Recipe() {
		super();
		ingredients = new HashMap<String, Ingredient>();
	}
	
	/* method for adding an ingredient to the Recipe */
	public void addIngredient(Ingredient i){
		ingredients.put(i.name, i);
	}
	
	/*method for peinting the recipe */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Recipe: ");
		if (ingredients.size() > 0)
			for (String key : ingredients.keySet())
				sb.append(key + " ");
		sb.append("\n");
		return sb.toString();
	}
	
	/* returns number of ingredients in the recipe */
	public int count(){
		return ingredients.size();
	}
	
	/* checks if an ingredient is present in the recipe */
	public boolean containsIngredient(String ingredientName){
		return ingredients.containsKey(ingredientName);
	}
	
	/* returns the Ns measure for the ingredient (mean number of compounds shared) */
	public double getNs(){
		if (this.count() < 2)
			return 0;
		int count = 0;
		ArrayList<String> keys = new ArrayList<String>();
		keys.addAll(ingredients.keySet());
		for (int i=0; i< keys.size(); i++)
			for (int j=0; j<keys.size(); j++){
				if (i != j && ingredients.get(keys.get(i)).similarIngredients.containsKey(keys.get(j))){
					count += ingredients.get(keys.get(i)).similarIngredients.get(keys.get(j));
				}
			}
		return (double)count / (this.count() * (this.count() - 1));
	}
	
	/* checks if an ingredient pair is present in the recipe */
	public boolean containsPair (String ingredient1, String ingredient2){
		return ingredients.containsKey(ingredient1) && ingredients.containsKey(ingredient2);
	}
	
	/* chekcs if an ingredient triplet is present in the recipe */
	public boolean containsTriplet (String ingredient1, String ingredient2, String ingredient3){
		return ingredients.containsKey(ingredient1) && ingredients.containsKey(ingredient2) && ingredients.containsKey(ingredient3);
	}
	
	/* removes a specific ingredient from the recipe */
	public void removeIngredient(String ingredientName){
		ingredients.remove(ingredientName);
	}
	
	/* returns a clone of the recipe */
	public Recipe clone(){
		Recipe clone = new Recipe();
		for (String key : this.ingredients.keySet())
			clone.ingredients.put(key, this.ingredients.get(key));
		return clone;
	}
}
