package a3_decoste_nd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Menu {
	private String name;	//the name of the restaurant
	private ArrayList<FoodItem> items; //to hold the items 
	private FoodItem min;		//to store the minimum item so you only have to search once 


	
		
	
	/**
	 * 
	 * @param n	The Restaurant Name
	 * @param file	The csv file to get the menu items
	 */
	Menu(String n, String file){			//instantiating the menu
		this.name = n;
		this.items = new ArrayList<FoodItem>();	//instantiating empty ArrayList
		this.min = null;

		
		
		//to populate the ArrayLists with items containing their corresponding prices
		BufferedReader br = null; 
		String line = "";
		
		
		try{
			br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {

			    // use comma as separator (for CSV files)
				String[] item = line.split(",");
				if(item[0].equals(name)){	//if it is the right restaurant
					items.add(new FoodItem(item[1],item[2]));		//create the item and add it
				}
				line = br.readLine();	//to read the next line
				
			}		//end of while (end of reading lines in the CSV)
			
			br.close();
			
		}
		catch (Exception e){
			e.printStackTrace();
			System.err.println("Problem with the File."); 	//problems with the file
			
		}
		
	}
	
	
	
	
	/**
	 * Error if Menu is instantiated without parameters.
	 */
	Menu(){
		System.err.println("File Dataset was not parameterized for this Menu");
	}
	
	
	/**
	 * To get the restaurant name for the Menu
	 * @return String of the restaurant name for the menu.
	 */
	public String getRestaurant(){
		return name;
	}
	
	
	
	/**
	 * Prints out the items in the Menu.
	 */
	public void items(){
		for(int i=0; i<this.items.size(); i++){
			System.out.println(this.items.get(i));
		}
		
	}
	
	
	
	/**
	 * To return the minimum priced Item on the menu.
	 * @return a FoodItem Object that is the minimum price
	 */
	public FoodItem getMin(){	//to compare with other menu minimum items
		if(min!=null){			//we already found the minimum
			//System.out.println("WE FOUND MIN ALREADY YAY");
			return min;
		}
		else{
			if(this.items.size()==0){	//if there are no items then we just return null
				return null;
			}
			else{				//otherwise we find the minimum
				min = items.get(0);
				for(int i = 1; i< items.size(); i++){
					if(items.get(i).compareTo(min) == -1){
						min = this.items.get(i);
					}
					
				}
			}
			return min;	
			}
	}
	
	
	
	
	/**
	 * deleting the minimum Item.
	 */
	public void deleteMin(){
		items.remove(min);
		this.min = null;
		
	}
	
	
	
/*	public static void main(String []args){
		Menu f = new Menu();
		
		Menu m = new Menu("McDonald's","data/menu.csv");
		System.out.println("_____________ MENU________________");
		m.items();
		System.out.println("MIN: ");
		System.out.println(m.getMin());
		System.out.println(m.getMin().getPrice());
		m.deleteMin();
		System.out.println(" NEW MIN: ");
		System.out.println(m.getMin());
		System.out.println(m.getMin().getPrice());
	}*/

}
