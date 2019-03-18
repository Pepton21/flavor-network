import java.util.HashMap;

/* Ingredient class */
public class Ingredient {
	
	String name;
	/* collection of neighbors in the flavor network (maps ingredients that
	 * share compounds with this ingredient to the number of shared compounds
	 */
	HashMap<String, Integer> similarIngredients;
	
	/* constructor */
	public Ingredient(String name) {
		super();
		this.name = name;
		similarIngredients = new HashMap<String, Integer>();
	}
	
	/* method for adding a neighbor from the flavor network graph */
	public void addIngredient (Ingredient i, int weight){
		this.similarIngredients.put(i.name, weight);
		i.similarIngredients.put(this.name, weight);
	}
	
	/* method for printing */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Similiar ingredients to " + name + ":\n");
		for (String key : similarIngredients.keySet()){
			sb.append("Ingredient: " + key + ", Common compounds: " + similarIngredients.get(key) + "\n");
		}
		return sb.toString();
	}
}
