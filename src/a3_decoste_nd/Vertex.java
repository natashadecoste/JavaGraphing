package a3_decoste_nd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import edu.princeton.cs.algs4.Bag;

public class Vertex {
	private String cityName;	//the name of the city
	private ArrayList<String> states;	//the state(s) it is in, used to find the gas price
	private double longitude;	//the longitude off the dataset
	private double latitude;	//latitude of city
	private double gPrice;		//the gas price in this city
	private boolean marked;		//the marked boolean used to find the paths using bfs and dfs
	private Vertex edgeTo;		//for BFS and DFS and Dijkstra's Algorithm
	private Bag<DirectedEdge> adj = new Bag<DirectedEdge>(); 	//to hold the adjacent vertices
	private double distTo;	//for dijkstra's algorithm
	
	
	Vertex(String name, ArrayList<String> states, String lon, String lat){	//constructor for the Vertex
		this.cityName = name;
		this.states = states;
		this.longitude = Double.parseDouble(lon);
		this.latitude = Double.parseDouble(lat);
		this.gPrice = 0.0;	//added in later
		this.marked = false;	
		this.edgeTo = null;
		this.distTo = Double.POSITIVE_INFINITY;

		
	}
	
	
	
	public void addPrice(double t){	//to assign the gas price from the other csv file
		this.gPrice = t;
		
	}
	
	public String toString(){
		return this.cityName ; //the string representation for the Vertex
	}
	
	public double distTo(){		//used to retrieve the variable distTo (used in finding the shortest path)
		return this.distTo;
	}
	
	public void dist(double s){	//to assign the distTo variable for the shortest path algorithms
		this.distTo = s;
	}
	
	public void addAdj(Vertex b){	//adding an adjacent edge connecting this vertex to another
		adj.add(new DirectedEdge(this,b));
		//System.out.println("Just added edge from "+ this + " to "+ b);
		//printAdj();
		
	}
	
	public double getEdgeWeight(Vertex p){	//getting the edge between two vertices 
		//System.out.println("getEdgeBetween "+ this + " ADJ SIZE: "+ this.adj.size());
		for(DirectedEdge v: p.adj){
			//System.out.println(v.start().getEdgeTo());
			if(v.finish().equals(this)){
				return v.weight();
			}
			
		}
		return (Double) null;
	}
	
	/**
	 * To get the DirectedEdges to the adjacent Vertices.
	 * @return Bag<DirectedEdge> of the adjacent Vertices.
	 */
	public Bag<DirectedEdge> getAdj(){
		return this.adj;
	}
	
	public void edgeTo(Vertex x){	//assigning the edge that is pointing to this edge
		this.edgeTo = x;
		
	}
	
	public Vertex getEdgeTo(){		//to return the Vertex that points to this vertex
		return this.edgeTo;
	}
	
	public void printAdj(){		//to print out the adjacent edges from this city.
		System.out.println("Adjacent to "+ this.cityName + ": ");
		for(DirectedEdge s: this.adj){
			System.out.println(s);
		}
	}
	
	public boolean isMarked(){		//returns whether or not the boolean is marked (when looking for paths)
		return this.marked;
	}
	
	public void unMark(){			//to change the marked boolean and unmark it
		this.marked = false;
	}
	
	public void mark(){				//to mark the boolean 
		this.marked = true;
	}
	
	
	public double getDistance(Vertex b){		//to get the distance between 2 cities using longitude and latitude coordinates
		return dist(this.latitude,this.longitude, b.latitude,b.longitude);
	}
	
	
	private double dist(double lat1, double long1, double lat2, double long2){		//using the Haversine formula (found online in JavaScript)
		double tlat = Math.toRadians(lat2-lat1);
		double tlong = Math.toRadians(long2-long1);
		double a = Math.pow(Math.sin(tlat/2),2) + Math.cos(Math.toRadians(lat1))*Math.cos(Math.toRadians(lat2))*Math.pow(Math.sin(tlong/2),2);
		double c = 2* Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
		double R = 6373;	//radius of the earth in KM
		double d = R *c;
		return d;

	}
	
	public double getGasPrice(){		//to return the gas price
		return this.gPrice;
	}

	
	//Overriding the equals for this object so it just checks the name.
    @Override
    public boolean equals(Object obj){
        if ( !(obj instanceof Vertex) ) {
            return false;
        }
        return Objects.equals(((Vertex)obj).cityName, this.cityName);
    }
    
    public double getLongitude(){
    	return this.longitude;
    }
    
    public double getLatitude(){
    	return this.latitude;
    }


}
