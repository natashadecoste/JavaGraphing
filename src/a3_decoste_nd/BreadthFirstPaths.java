package a3_decoste_nd;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

public class BreadthFirstPaths {
	private Vertex source;
	private Graph g;

	public BreadthFirstPaths(Graph g, Vertex s){
		//edgeTo = new ArrayList<Vertex>();
		this.source  = s;
		this.g = g;
		bfs(g,s);

		
	}
	
	//the bfs function (changed a bit from the textbook)
	private void bfs(Graph g, Vertex s){
		Queue<Vertex> queue = new Queue<Vertex>();
		s.mark();
		queue.enqueue(s);
		while(!queue.isEmpty()){
			Vertex v = queue.dequeue();
			for(DirectedEdge w : v.getAdj()){
				if(!w.finish().isMarked()){
					w.finish().edgeTo(v);
					w.finish().mark();
					queue.enqueue(w.finish());
				}
			}
		}
	}
	
	//does it have a path to vertex x
	public boolean hasPathTo(Vertex x){
		return marked(x);
	}
	
	
	//has it been discovered 
	public boolean marked(Vertex x) {
		return x.isMarked();
	}
	

	//returning the path to the vertex v
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
	
	//writing the path to the output file
	public void write(String dest){

		
		String filename = "data/a3_out.txt";
		
		BufferedWriter output = null;
        try {
            File file = new File(filename);
            output = new BufferedWriter(new FileWriter(file));
            
            output.write("BFS: ");
            Iterable<Vertex> a = pathTo(g.getVertex(dest));
            
    		
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
