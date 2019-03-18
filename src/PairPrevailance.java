/* class that contains an ingredient pair and its prevalence
 * used for sorting ingredient pairs according to their prevalence
 */
public class PairPrevailance implements Comparable<PairPrevailance>{
	
	public Ingredient ingredient1;
	public Ingredient ingredient2;
	public double prevailance;
	
	/* constructor */
	public PairPrevailance(Ingredient ingredient1, Ingredient ingredient2, double prevailance) {
		super();
		this.ingredient1 = ingredient1;
		this.ingredient2 = ingredient2;
		this.prevailance = prevailance;
	}
	
	/* implementing the comparable interface for sorting according to prevalence */
	@Override
	public int compareTo(PairPrevailance o) {
		// TODO Auto-generated method stub
		return Double.compare(o.prevailance, this.prevailance);
	}
	
	/* method for printing */
	public String toString(){
		return "Ingredients: " + ingredient1.name + ", " + ingredient2.name + "\t Prevailance: " + String.format("%.4f", prevailance);
	}
}
