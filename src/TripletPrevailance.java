/* class that contains an ingredient triplet and its prevalence
 * used for sorting ingredient triplets according to their prevalence
 */
public class TripletPrevailance implements Comparable<TripletPrevailance>{
	
	public Ingredient ingredient1;
	public Ingredient ingredient2;
	public Ingredient ingredient3;
	public double prevailance;
	
	/* constructor */
	public TripletPrevailance(Ingredient ingredient1, Ingredient ingredient2, Ingredient ingredient3,  double prevailance) {
		super();
		this.ingredient1 = ingredient1;
		this.ingredient2 = ingredient2;
		this.ingredient3 = ingredient3;
		this.prevailance = prevailance;
	}
	
	/* implementing the comparable interface for sorting according to prevalence */
	@Override
	public int compareTo(TripletPrevailance o) {
		// TODO Auto-generated method stub
		return Double.compare(o.prevailance, this.prevailance);
	}
	
	/* method for printing */
	public String toString(){
		return "Ingredients: " + ingredient1.name + ", " + ingredient2.name + ", " + ingredient3.name + "\t Prevailance: " + String.format("%.4f", prevailance);
	}
}
