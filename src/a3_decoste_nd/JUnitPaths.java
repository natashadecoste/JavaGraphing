package a3_decoste_nd;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.Stack;

public class JUnitPaths {
	String start, finish;
	BufferedReader br; 

	@Before
	public void setUp() throws Exception {
		
		br = null;
		String line = "";
		ArrayList<String> input = new ArrayList<String>();
		
		try{
			br = new BufferedReader(new FileReader("data/a3_in.txt"));
			while ((line = br.readLine()) != null) {

			    // use comma as separator (for CSV files)
				input.add(line);

				//we need to make nodes out of them and add it to the BST
				
				

				
			}		//end of while (end of reading lines in the CSV)
			
			br.close();
			
		}
		catch (Exception e){
			e.printStackTrace();
			System.err.println("Problem with the File."); 	//problems with the file
			
		}
		start = input.get(0);
		finish = input.get(1);

		
	}

	@After
	public void tearDown() throws Exception {
		br.close();
	}

	@Test
	public void testBFS(){
		Graph p = new Graph();
		BreadthFirstPaths r = new BreadthFirstPaths(p, p.getVertex(start));
		
		Iterable<Vertex> path = r.pathTo(p.getVertex(finish));
		
		Iterator<Vertex> iter = path.iterator();
		List<Vertex> copy = new ArrayList<Vertex>();
		while (iter.hasNext())
		    copy.add(iter.next());
		
		Vertex a;
		for(int i = 1; i<copy.size(); i++){
			a = copy.get(i-1);
			assert(copy.get(i).getEdgeWeight(a) != (Double) null);	//if there is a connection between the cities then the edge weight wont return as (Double) null
			}
			


	}
	
	@Test
	public void testDFS(){
		Graph t =  new Graph();
		DepthFirstPaths n = new DepthFirstPaths(t, t.getVertex(start));
		
		Iterable<Vertex> path = n.pathTo(t.getVertex(finish));
		
		Iterator<Vertex> iter = path.iterator();
		List<Vertex> copy = new ArrayList<Vertex>();
		while (iter.hasNext())
		    copy.add(iter.next());
		
		Vertex a;
		for(int i = 1; i<copy.size(); i++){
			a = copy.get(i-1);
			assert(copy.get(i).getEdgeWeight(a) != (Double) null);
			}
			
		
		
	}

}
