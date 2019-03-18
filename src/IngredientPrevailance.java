/* class that contains an ingredient and its prevalence
 * used for sorting ingredients according to prevalence
 */
public class IngredientPrevailance implements Comparable<IngredientPrevailance>{
	
	public Ingredient ingredient;
	public double prevailance;
	
	/* constructor */
	public IngredientPrevailance(Ingredient ingredient, double prevailance) {
		super();
		this.ingredient = ingredient;
		this.prevailance = prevailance;
	}
	
	/* implementing the comparable interface for sorting according to prevalence */
	@Override
	public int compareTo(IngredientPrevailance o) {
		// TODO Auto-generated method stub
		return Double.compare(o.prevailance, this.prevailance);
	}
	
	/* method for printing */
	public String toString(){
		return "Ingredient name: " + ingredient.name + "\t Prevailance: " + String.format("%.4f", prevailance);
	}
}
