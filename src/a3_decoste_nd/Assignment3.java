package a3_decoste_nd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Assignment3 {

	public static void main(String[] args) {
		String start, finish;
		
		BufferedReader br = null; 
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
		

		
		//To write the output for BFS
		Graph p = new Graph();
		BreadthFirstPaths r = new BreadthFirstPaths(p, p.getVertex(start));
		r.write(finish);
		
		//To get the output for DFS
		Graph t =  new Graph();
		DepthFirstPaths n = new DepthFirstPaths(t, t.getVertex(start));
		n.write(finish);

		//To get the shortest path
		Graph shortestPath = new Graph();
		DijkstraSP shortestP = new DijkstraSP(shortestPath,shortestPath.getVertex(start));
		shortestP.write(finish);

	}

}
