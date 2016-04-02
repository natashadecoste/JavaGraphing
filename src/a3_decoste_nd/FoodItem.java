package a3_decoste_nd;

public class FoodItem {
	private String name;
	private double price;
	
	
	/**
	 * The Constructor for the FoodItem class.
	 * @param s	The name of the food.
	 * @param p	The price, as a String.
	 */
	FoodItem(String s, String p){
		this.name = s;
		
		String x = p.replace("$","");
		this.price = Double.parseDouble(x);
	}
	
	
	/**
	 * Comparing to other food items using the price double.
	 * @param x		The other FoodItem Object to compare to.
	 * @return		An integer representing the relationship of the price being less than, equal to or greater than the item in comparison.
	 */
	public int compareTo(FoodItem x){
		if(this.price < x.price){
			return -1;
		}
		else if(this.price == x.price){
			return 0;
		}
		else{
			return 1;
		}
		
	}
	
	
	/**
	 * The String representation of the FoodItem
	 */
	public String toString(){
		return this.name;
	}
	
	/**
	 * To get the Price of the item.
	 * @return float of the price
	 */
	public double getPrice(){
		return this.price;
	}
	
	
	
/*	public static void main(String []args){
		FoodItem f = new FoodItem("Big Mac","$5.67");
		System.out.println(f);
		System.out.println(f.getPrice());
		
		System.out.println();
		
		FoodItem p = new FoodItem("McChicken","$7.67");
		System.out.println(p);
		System.out.println(p.getPrice());
		
		System.out.println();
		
		
		System.out.println(f.compareTo(p));		//should return -1
		System.out.println(p.compareTo(f));		//should return 1
		
		
	}*/

}
