package a3_decoste_nd;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import edu.princeton.cs.algs4.Stack;

public class DijkstraSP {
	private Vertex source;
	private ArrayList<Vertex> pq;
	private Graph g;
	private double totalTripCost;
	
	//constructor for the shortest path class
	DijkstraSP(Graph g, Vertex s){
		this.source = s;
		this.g= g;
		
		pq = new ArrayList<Vertex>();
		s.dist(0.0);
		pq.add(s);
		//relaxing all the edges in the graph
		while(!pq.isEmpty()){
			relax(g,delMin());
		}
	}
	
	 
	private Vertex delMin(){
		if(pq.size()>=1){
			Vertex temp = pq.get(0);
			for(int i = 1; i < pq.size(); i++){
				if(pq.get(i).distTo() < temp.distTo()){
					temp = pq.get(i);
				}
			}
			//System.out.println("temp : " + temp + "    dist to: " + temp.distTo());
			pq.remove(temp);
			//System.out.println("returning temp");
			return temp;
		}
		//System.out.println("returning null");
		return null;
		
	}
	
	//updating the distTo (it is within the given vertex)
	public double distTo(Vertex v){
		return v.distTo();

	}
	
	//if a path exists to a vertex v
	public boolean hasPathTo(Vertex v){
		return distTo(v) < Double.POSITIVE_INFINITY;
	}
	
	//to get the path to a vertex v
	public Stack<Vertex> pathTo(Vertex v){
		if(!hasPathTo(v)){ return null;}

		
		Stack<Vertex> path = new Stack<Vertex>();
		for(Vertex x = v; x != source; x = x.getEdgeTo()){
			path.push(x);
		}

		//path.push(source);
		return path;
	}
	
	
	//relaxing the whole graph
	private void relax(Graph g, Vertex v){
		for(DirectedEdge e: v.getAdj()){
			Vertex w = e.finish();
			if(w.distTo() > v.distTo() + e.weight()){
				w.dist(v.distTo()+e.weight());
				w.edgeTo(e.start());
				if(!pq.contains(w)){
					pq.add(w);
				}	
			}
		}

			

		
	}
	
	
	//writing to the output file
	public void write(String dest){
		String filename = "data/a3_out.txt";
		
		BufferedWriter output = null;
        try {
            File file = new File(filename);
            output = new BufferedWriter(new FileWriter(file,true));
            
            String formatStr = "%-20s %-40s %-15s %-15s %-15s%n";
            
            
            output.write("******************************");
            output.write("\n");
            output.write("Shortest Path (Cheapest Route)");
            output.write("\n"+ "\n");
            
            
            Iterable<Vertex> a = pathTo(g.getVertex(dest));
            //output.write("City				" + "Meal Choice			         " + "Cost of Meal               	" + "Cost of Fuel	        " + "Total Cost        	");
            output.write(String.format(formatStr, "City", "Meal Choice", "Cost of Meal", "Cost of Fuel", "Total City Cost"));
            
            output.write("\n");
            //output.write(source + "             " + "No Meal                          " + "$0.00       "+ "0.0             " + " 0.0                ");
            output.write(String.format(formatStr, source, "No Meal" , "$0.00", "$0.00", "$0.00"));
            Vertex last = source;
    		for(Vertex o: a){
    			Restaurant min = null;
    			double cityCost = 0.0;
    			for(Restaurant r: g.restos){
    				//System.out.println(o);
    				//System.out.println("LONGITUDE: " + o.getLongitude());
    				//System.out.println("LATITUDE: " + o.getLatitude());
    				
    				if((r.range(o.getLongitude()-0.5,o.getLongitude()+0.5, o.getLatitude())).size()!= 0){
    					if(min == null){ 	//that restaurant's smallest item will be the min
    						min = r;
    					}
    					else{//compare to min, if it is smaller then reset the min, or do nothing
    						if(r.menu.getMin().getPrice() < min.menu.getMin().getPrice()){
    							min = r;
    						}
    					}
    					//System.out.println(min);
    					
    				}
    				
    			}
    			FoodItem m = min.menu.getMin();
    			totalTripCost += m.getPrice() + o.getEdgeWeight(last);
    			cityCost += m.getPrice() + o.getEdgeWeight(last);
    			min.menu.deleteMin();
    			output.write("\n");
    			
    			//writing to the output file
    			output.write(String.format(formatStr, o.toString(), "$"+m.toString(), "$"+Double.toString(m.getPrice()), "$"+(String.format("%.2f", o.getEdgeWeight(last)).toString()), String.format("%.2f",cityCost)));
    			
    			last = o;
    		}
            
    		
    		output.write("\n");
    		output.write("                                                                                            Total Cost: $" + String.format("%.2f",totalTripCost) + "\n");
    		output.write("************************************************");
            
            output.close();
        } catch (IOException e ) {
            e.printStackTrace();
        }
		
	}


}
