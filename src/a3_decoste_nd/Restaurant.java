package a3_decoste_nd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Restaurant {
	private String name;
	private Node root;
	public Menu menu;

	/**
	 * The Constructor of the Node subclass.
	 * 
	 *
	 */
	private class Node{
		private String location;	//Key
		private double longitude;	//the Val
		private double latitude;
		private Node left, right;	
		private int N; //nodes in subtree
		
		
		/**
		 * The Constructor of the Node subclass.
		 * @param loc	The String representation of the location of one of the restaurant chains.
		 * @param y		The Longitude of the location.
		 * @param x		The Latitude of the location.
		 * @param n		The number of Nodes in this Node's subtree.
		 */
		public Node(String loc,double x,double y,int n){ 
			this.location = loc;
			this.longitude = y;
			this.latitude  = Math.abs(x);
			this.N = n;

		}
		
		/**
		 * To get the Longitude of the Node.
		 * @return The Node's Longitude.
		 */
		public double longitude(){
			return this.longitude;
		}
		
		/**
		 * To get the Latitude of the Node.
		 * @return the Node's Latitude.
		 */
		public double latitude(){
			return this.latitude;
		}
		
		/**
		 * To get the Location of the Node.
		 * @return	The String representation of the Node's Location.
		 */
		public String location(){
			return this.location;
		}
		
		/**
		 * Specifying to compare Nodes using the Longitude.
		 * @param x		The Node to compare to.
		 * @return		An integer representing a less than, greater than or equal to relationship.
		 */
		public int compareTo(Node x){
			if (this.longitude < x.longitude){
				return -1;
			}
			else if(this.longitude == x.longitude){
				return 0;
			}
			else {
				return 1;
			}
		}
		
		
		/**
		 * The String representation of a Node object.
		 */
		public String toString(){
			return "Located at: " + this.location() + " with Longitude: " + this.longitude + " and Latitude: " + this.latitude;
		}
		
		
	}
	
	/**
	 * The constructor of the Restaurant Object. 
	 * @param n		The name of the restaurant chain.
	 */
	Restaurant(String n){
		this.name = n;
		menu = new Menu(name,"data/menu.csv");


		
		String filename = "data/"+(this.name.replace(" ", "").replace("'","").toLowerCase()) + ".csv";
		//System.out.println("CSV NAME:  " + filename);
		
		//to populate the ArrayLists with locations
				BufferedReader br = null; 
				String line = "";
				
				
				try{
					br = new BufferedReader(new FileReader(filename));
					while ((line = br.readLine()) != null) {

					    // use comma as separator (for CSV files)
						String[] item = line.split(",");

						//we need to make nodes out of them and add it to the BST
						
						put(item[2],Double.parseDouble(item[0]),Double.parseDouble(item[1]));
						//System.out.println(item[0]);
						

						
					}		//end of while (end of reading lines in the CSV)
					
					br.close();
					
				}
				catch (Exception e){
					e.printStackTrace();
					System.err.println("Problem with the File."); 	//problems with the file
					
				}
		
		
	}
	
	/**
	 * Returns an integer that is the size of the Binary Tree.
	 * @return 		An integer representation of the size of the Data Structure.
	 */
	public int size(){
		return size(root);
	}
	
	/**
	 * Function to get the Size of the subtree at a certain Node.
	 * @param x		The Node to calculate the subtree size of.
	 * @return		Returns an integer representation of the size of the Node's subtree.
	 */
	private int size(Node x){
		if(x == null) return 0;
		else return x.N;
	}
	
	/**
	 * The function to add a Location of a restaurant to the Data Structure.
	 * @param loc	The String representation of the location.
	 * @param y		The Longitude of the restaurant location.
	 * @param x		The Latitude of the restaurant location.
	 */
	public void put(String loc,double x,double y){
		root = put(root, loc, x, y);
	}
	
	
	public boolean getRes(String n){
		if(n.equals(this.name)){
			return true;
		}
		return false;
	}
	
	
	/**
	 * The function to add Nodes to the Data Structure to create the Binary Search Tree.
	 * @param n		The Node to recursively search in order to add the new Node as a Leaf Node.
	 * @param loc	The String representation of the Location to add to the Node.
	 * @param y		The Longitude of the Node.
	 * @param x		The Latitude of the Node.
	 * @return		Returns the root Node (of the Binary Tree).
	 */
	private Node put(Node n, String loc, double x, double y){
			if(n == null) return new Node(loc, x, y, 1);
			if(y < n.longitude){ n.left = put(n.left, loc, x, y);}
			else if(y > n.longitude){ n.right = put(n.right, loc, x, y);}
			else{ n.location = loc; n.longitude = y; n.latitude = x;}
			n.N = size(n.left) + size(n.right)+ 1;
			return n;
	}
	
	
	/**
	 * To get the restaurant locations that in the the accepted range of the Longitude and Latitude.
	 * @param lo	The lower bound of the Longitude range.
	 * @param hi	The higher bound of the Longitude range.
	 * @param lat	The Latitude to check the range for.
	 * @return		An ArrayList of Nodes that are in the acceptable range.
	 */
	public ArrayList<Node> range(double lo, double hi, double lat){	//longitude lower bound and higher bound and latitude
		ArrayList<Node> q = new ArrayList<Node>();
		//System.out.println("first function");
		range(root, q, lo, hi, lat);
		return q;
		
	}
	
	
	/**
	 * To compute the range of Nodes in the acceptable range from the Longitude and Latitude using a Binary Search.
	 * @param x		The Node to recursively search children of (starts with root node).
	 * @param q		The ArrayList to populate with acceptable Nodes
	 * @param lo	The Lower bound of the Longitude.
	 * @param hi	The Upper bound of the Longitude.
	 * @param lat	The Latitude to check the acceptable range of.
	 */
	private void range(Node x, ArrayList<Node> q, double lo, double hi, double lat){
		//System.out.println("Second function");
		if(x == null) {return;}
		if (lo < x.longitude) range(x.left, q, lo, hi, lat);
		if (lo <= x.longitude && rangeLat(lat,x)) q.add(x);
		if (hi > x.longitude) range(x.right,q,lo,hi,lat);
	}
	
	
	/**
	 * To find out whether or not the Node's Latitude is in the accepted range.
	 * @param lat	The Latitude defining the range (0.5 degree margin) 
	 * @param x		The Node in which we are checking the Latitude.
	 * @return		Boolean whether or not the Node's Latitude is in the of the given Latitude.
	 */
	private boolean rangeLat(double lat, Node x){
		if(x.latitude >= (lat-0.5)){
			if(x.latitude <= lat+0.5){
				return true;
			}
			return false;
		}
		return false;
/*		if((lat-0.5 <= x.latitude) && (x.latitude <= lat+0.5)){
			return true;
		}
		else {return false;}*/
	}
	

	/**
	 * To find the distance between 2 points using their coordinates given in Longitude and Latitudes.
	 * @param lat1		The Latitude of the first point.
	 * @param long1		The Longitude of the first point.
	 * @param lat2		The Latitude of the first point.
	 * @param long2		The Longitude of the first point.
	 * @return			The distance in kilometers from the first point to the second point.
	 */
	private double dist(double lat1, double long1, double lat2, double long2){		//using the Haversine formula
		double tlat = Math.toRadians(lat2-lat1);
		double tlong = Math.toRadians(long2-long1);
		double a = Math.pow(Math.sin(tlat/2),2) + Math.cos(Math.toRadians(lat1))*Math.cos(Math.toRadians(lat2))*Math.pow(Math.sin(tlong/2),2);
		double c = 2* Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
		double R = 6373;	//radius of the earth in KM
		double d = R *c;
		return d;

	}
	
	/**
	 * Finds the closest restaurant to the given longitude and latitude that are within 0.5 degree range.
	 * @param lon	The degree representation of the Longitude
	 * @param lat	The degree representation of the Latitude
	 */
	public Node closestRestaurant(double lon, double lat){
		ArrayList<Node> options = range(lon-0.5, lon+0.5, lat);
		//System.out.println("OPTIONS SIZE : " + options.size());
		
		
		//System.out.println(options.get(0));
		
		if(options.size() == 0){
			return null;
		}

		double distance = dist(options.get(0).latitude, options.get(0).longitude,lat,lon);
		//System.out.println("START DIST: "+ distance);
		Node closest = options.get(0);
		
		for(int i = 1; i< options.size();i++){
			if(dist(options.get(i).latitude, options.get(i).longitude,lat,lon) < distance){
				closest = options.get(i);
				distance = dist(options.get(i).latitude, options.get(i).longitude, lat, lon);
			}
			
		}
		
		//System.out.println("CLOSEST IS: " + closest);
		return closest;
		
	}
	
	/**
	 * The String representation of the Restaurant Item.
	 */
	public String toString(){
		return "Restaurants: " + this.name;
	}
	

}
