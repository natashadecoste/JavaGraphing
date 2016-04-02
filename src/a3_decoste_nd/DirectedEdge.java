package a3_decoste_nd;

import java.util.ArrayList;

public class DirectedEdge {
	private Vertex a;
	private Vertex b;
	private double weight;

	
	DirectedEdge(Vertex a, Vertex b){
		this.a = a;
		this.b = b;

		
		//calculate the distances between, also calculate the price based on the gas price of a (state you are coming from)
		double distance = a.getDistance(b);
		//System.out.println("distance is " + distance + " and gas price is: " + a.getGasPrice());
		//System.out.println("A gas price: " + a.getGasPrice());
		this.weight = a.getGasPrice()*distance*(0.082)/100;
	}
	
	public double weight(){
		return this.weight;
	}
	

	
	public Vertex start(){
		return this.a;
	}
	
	public Vertex finish(){
		return this.b;
	}
	
	public String toString(){
		return "Edge from " + a + " to " + b + " with cost " + this.weight;
	}
	
	
	public static void main(String[] args) {
		
		ArrayList<String> h = new ArrayList<String>();
		h.add("IL");
		h.add("OH");
		Vertex x = new Vertex("Louisiana",h,"19.8","67.8");
		x.addPrice(56.0);
		Vertex y = new Vertex("NYC",h,"78.8","23.5");
		DirectedEdge e = new DirectedEdge(x,y);
		System.out.println(e);

	}

}
