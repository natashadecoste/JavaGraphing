package a3_decoste_nd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Graph {
	private int E;	//the number of edges
	private int V;	//the number of vertices

	private ArrayList<String[]> cities = new ArrayList<String[]>();
	private ArrayList<String[]> gas = new ArrayList<String[]>();
	private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	private ArrayList<String[]> connections = new ArrayList<String[]>(); 
	public ArrayList<Restaurant> restos = new ArrayList<Restaurant>();
	


	public Graph(){
		this.E = 0;
		this.V = 0;
		getGasPrices();
		getAllCities();
		addVertices();
		addEdges();
		restos.add(new Restaurant("Wendy's"));
		restos.add(new Restaurant("McDonald's"));
		restos.add(new Restaurant("Burger King"));
		

	}



	private void getAllCities(){
		BufferedReader br = null; 
		String line = "";

		try{
			
			br = new BufferedReader(new FileReader("data/zips1990.csv"));
			while ((line = br.readLine()) != null) {

				// use comma as separator (for CSV files)
				String[] item = line.split(",");
				//left side is one city, right side is another
				cities.add(item);

			}		//end of while (end of reading lines in the CSV)
			br.close();
			System.out.println("Add cities done.");
		}
		catch(Exception e){
			System.out.println("EXCEPTION");
			e.printStackTrace();
		}
	}


	private void addVertices(){

		BufferedReader br = null; 
		String line = "";

		try{
			br = new BufferedReader(new FileReader("data/connectedCities.txt"));
			while ((line = br.readLine()) != null) {

				// use comma as separator (for CSV files)
				String[] item = line.split("\\),");

				//left side is one city, right side is another (item[0] = left, item[1] = right)
				
				String connectOne = item[0].split(" \\(")[0];
				String connectTwo = item[1].split(" \\(")[0].substring(1);
				this.connections.add(new String[]{connectOne,connectTwo});
/*				System.out.println(Arrays.toString(connections.get(0)));
				System.out.println(connections.get(0)[1]);*/
				
				int p = 0;
				while(p!=2){
				if(p == 1){
					item[p] = item[p].substring(1);
				}
				
				String[] r = item[p].split("\\(");
				//System.out.println(Arrays.toString(r));  < the array of the city name and the states

				//r[0] is the city name
				//r[1] are the states

				
				ArrayList<String> states = new ArrayList<String>();
				String [] temp = r[1].split(",");
				for(int i  = 0; i< temp.length; i++){
					states.add(temp[i].replace(" ","").replaceAll("\\p{P}",""));
				}
				
				String n = r[0].replace(" ","").replace(".", "");
				
				
				if (n.length() > 6){
					if(n.substring(0,7).equalsIgnoreCase("newyork")){
						n = n.substring(0,7);
					}
				}


				String[] info = search(n,states);
				//the city name without any spaces

				
				if(info != null){
					if(r[0].endsWith(" ")){
						r[0] = r[0].substring(0,r[0].length()-1);
					}
					Vertex v = new Vertex(r[0],states,info[5],info[4]);
					//System.out.println("NEW VERTEX: " + v);

					int count = 0;
					double gasPrice = 0;
					for(int i = 0; i< states.size(); i++){
						gasPrice += lookUpGas(states.get(i));
						count ++;

					}

					gasPrice = gasPrice/count;
					v.addPrice(gasPrice);
					
					if(!vertices.contains(v)){

						//System.out.println("adding");
						vertices.add(v);
					}

				}
			p++;	
			}
			}		//end of while (end of reading lines in the CSV)
			br.close();
			this.V = vertices.size();
		}
		catch(Exception e){
			System.out.println("EXCEPTION 2");
			e.printStackTrace();
		}
	}

	
	
	

	private void getGasPrices(){
		BufferedReader br = null; 
		String line = "";

		try{
			br = new BufferedReader(new FileReader("data/StateGasPrice.csv"));
			while ((line = br.readLine()) != null) {
				// use comma as separator (for CSV files)
				String[] item = line.split(",");
				//left side is one city, right side is another
				gas.add(item);
			}	//end of while (end of reading lines in the CSV)

			br.close();
			System.out.println("Add gas prices added done.");
		}
		catch(Exception e){
			System.out.println("Gas EXCEPTION");
			e.printStackTrace();
		}
	}
	
	private void addEdges(){
		//need to read from connectedCities and add directed weighted edges from each city to the other and the same the other direction
		for(int i = 0; i< connections.size();i++){
			//System.out.println(Arrays.toString(connections.get(i)));
			//System.out.println(getVertex(connections.get(i)[0]));
			//System.out.println(getVertex(connections.get(i)[1]));
			getVertex(connections.get(i)[0]).addAdj(getVertex(connections.get(i)[1]));
			getVertex(connections.get(i)[1]).addAdj(getVertex(connections.get(i)[0]));
			E+=2;
		}
	}
	
	public Vertex getVertex(String x){
		//System.out.println("HERE");
		for(int i=0;i<vertices.size();i++){
			//System.out.println(vertices.get(i));
			if(x.equals(vertices.get(i).toString())){
				//System.out.println("found " + vertices.get(i));
				return vertices.get(i);
			}
		}
		return null;
		
	}
	
	private double lookUpGas(String s){
		for(int i = 0; i<gas.size();i++){
			if (s.equalsIgnoreCase(gas.get(i)[0])){
				return Double.parseDouble(gas.get(i)[1]);
			}
		}
		return 0.0;
	}

	
	private String[] search(String name, ArrayList<String> states){
		for(int i = 0; i< cities.size(); i++){
			if(name.equalsIgnoreCase(cities.get(i)[3].replace(" ", ""))){
				if (states.contains(cities.get(i)[2])){
					return cities.get(i);
				}
			}
		}
		return null;

	}

	public ArrayList<Vertex> getVertices(){
		return this.vertices;
	}
	

}
