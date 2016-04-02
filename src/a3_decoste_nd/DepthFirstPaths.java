package a3_decoste_nd;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import edu.princeton.cs.algs4.Stack;

public class DepthFirstPaths {

	private int count;
	private Vertex source;
	private Graph g;

	//constructor
	public DepthFirstPaths(Graph g, Vertex x) {
		
		this.source = x; 
		this.g = g;
		dfs(g, x);	//x is the destination

	}

	//the depth first search function
	private void dfs(Graph g, Vertex x) {
		x.mark();
		count++;
		for (DirectedEdge v : x.getAdj()) {
			if (!v.finish().isMarked()) {
				v.finish().edgeTo(x);
				dfs(g, v.finish());
			}
		}

	}

	//is it marked (has it been discovered)
	public boolean marked(Vertex x) {
		return x.isMarked();
	}

	
	public int count() {
		return count;
	}

	//is there a path to this vertex
	public boolean hasPathTo(Vertex b) {
		return marked(b);
	}

	//creating a stack of the paths using the edgeTo within the Vertex
	public Iterable<Vertex> pathTo(Vertex v){
		if(!hasPathTo(v)){
			return null;
		}
		
		Stack<Vertex> path = new Stack<Vertex>();
		for(Vertex x = v; x != source; x = x.getEdgeTo()){
			path.push(x);
		}
		path.push(source);
		return path;
	}
	
	//to write the path to the output txt file
	public void write(String dest){

		
		String filename = "data/a3_out.txt";
		
		BufferedWriter output = null;
        try {
            File file = new File(filename);
            output = new BufferedWriter(new FileWriter(file,true));
            
            output.write("DFS: ");
            Iterable<Vertex> a = pathTo(g.getVertex(dest));
            
    		//print out the cities in order of the path
    		for(Vertex o: a){
    			output.write(o.toString() +", ");
    		}
            
    		output.write("\n");
            
            output.close();
        } catch (IOException e ) {
            e.printStackTrace();
        }
		
	}
	
}

