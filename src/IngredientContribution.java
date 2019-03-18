/* class that contains an ingredient and its contribution to Ns
 * used for sorting ingredients according to contribution to Ns
 */
public class IngredientContribution implements Comparable<IngredientContribution>{
	
	public Ingredient ingredient;
	public double prevailance;
	public double contribution;
	
	/* constructor */
	public IngredientContribution(Ingredient ingredient, double prevailance, double contribution) {
		super();
		this.ingredient = ingredient;
		this.prevailance = prevailance;
		this.contribution = contribution;
	}
	
	/* implementing the comparable interface for sorting according to contribution to Ns */
	@Override
	public int compareTo(IngredientContribution o) {
		// TODO Auto-generated method stub
		return Double.compare(o.contribution, this.contribution);
	}
	
	/* method for printing */
	public String toString(){
		return "Ingredient name: " + ingredient.name + "\t Contribution: " + String.format("%.4f", contribution);
	}
}
