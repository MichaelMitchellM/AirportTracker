package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;

public class AirportGraph {
	
	private Airport[] airports_;
	private HashMap<Airport, Airport[]> routes_;
	private HashMap<Airport, float[]> dists_;
	
	
	private AirportGraph(Airport[] airports){
		airports_ = airports;
		routes_ = new HashMap<Airport, Airport[]>();
		dists_  = new HashMap<Airport, float[]>();
	}
	
	// getters
	public HashMap<Airport, Airport[]> routes(){ return routes_; }
	public HashMap<Airport, float[]>   dists() { return dists_;  }
	
	public Airport[] CalculateRoute(Airport start, Airport end){
		if(start.code().equals(end.code())) return new Airport[]{start};
		
		// set up containers to hold nodes
		
		// d holds the distance from the start node to each node
		HashMap<Airport, Float>   d = new HashMap<Airport, Float>();
		
		// p holds the previous node in the path for each node 
		HashMap<Airport, Airport> p = new HashMap<Airport, Airport>();
		
		// S holds already searched nodes
		ArrayList<Airport> S = new ArrayList<Airport>();
		
		// R holds nodes that still need to be pathed to
		ArrayList<Airport> R = new ArrayList<Airport>();
		
		// put the starting node in S
		S.add(start);
		
		// put the starting node in p and set its previous node to null
		p.put(start, null);
		
		// get all the nodes that connect to the starting node
		ArrayList<Airport> start_con = new ArrayList<Airport>();
		for(Airport apt : routes_.get(start)) start_con.add(apt);
		
		// initialize R with all nodes that are not the starting node
		// if the current node being iterated on is connected to the starting node
		//   put that node in d and set the distance equal to the edge between it and the starting node
		// else
		//   put the current node in d but set its distance to the starting node to pos infinity
		for(Airport apt : airports_){
			if(!apt.code().equals(start.code())){
				int ind;
				R.add(apt);
				if((ind = start_con.indexOf(apt)) != -1){
					d.put(apt, dists_.get(start)[ind]);
				}
				else{
					d.put(apt, Float.POSITIVE_INFINITY);
				}
			}
		}
		
		// while there are nodes to search over
		while(!R.isEmpty()){
			
			// find the node with the lowest distance from the starting node
			Airport lowest_dist = R.get(0);
			for(Airport apt : R)
				if(d.get(apt) < d.get(lowest_dist))
					lowest_dist = apt;
			
			// remove lowest_dist from S (nodes to search)
			R.remove(lowest_dist);
			
			// print out the number of nodes left to search
			//System.out.println(R.size());
			
			// add lowest_dist to S (already searched nodes)
			S.add(lowest_dist);
			
			// loop over nodes that are connected to lowest_dist
			// who are also still in S
			int index = 0;
			for(Airport apt : routes_.get(lowest_dist)){
				if(R.contains(apt)){
					// IF the distance from starting node to apt (current node)
					//    is shorter if you go through lowest_dist than it is currently
					// THEN
					//	set the distance of apt to the shorter distance, place in d
					//  set the previous node for apt to lowest_dist
					// ELSE continue loop
					if(d.get(lowest_dist) + dists_.get(lowest_dist)[index] < d.get(apt)){
						d.put(apt, d.get(lowest_dist) + dists_.get(lowest_dist)[index]);
						p.put(apt, lowest_dist);
					}
				}
				++index;
			}
		}
		
		// build arraylist that contains the path from end node to starting node
		ArrayList<Airport> route = new ArrayList<Airport>();
		Airport current = end;
		while(current != null){
			route.add(current);
			current = p.get(current);
		}
		
		// put route into array where starting node is index 0
		// and end node is last index
		Airport[] ret = new Airport[route.size()];
		for(int i = 0; i < route.size(); ++i){
			ret[i] = route.get(route.size() - i - 1);
		}
		
		return ret;
	}
	
	public static AirportGraph GenerateGraph(Airport[] airports, HashMap<String, Route[]> routes){
		AirportGraph graph = new AirportGraph(airports);
		ArrayList<Airport> aps;
		ArrayList<Float>   dists;
		Route[]   rts;
		Airport[] ar_arr;
		float[]   dst_arr;
		
		for(Airport ap : airports){
			rts = routes.get(ap.code());
			if(rts != null){
				aps   = new ArrayList<Airport>();
				dists = new ArrayList<Float>();
				for(Route r : rts){
					for(Airport a : airports){
						if(r.end_airport_code_.equals(a.code())){
							aps.add(a);
							
							
							float a0 = ap.loc().latitude();
							float a1 = a.loc().latitude();
							
							float d0 = a1 - a0;
							float d1 = a.loc().longitude() - ap.loc().longitude();
							
							float c0 = (float) (Math.sin(d0 / 2.0f) * Math.sin(d0 / 2.0f)
									+ Math.cos(a0) * Math.cos(a1)
									* Math.sin(d1 / 2.0f) * Math.sin(d1 / 2.0f));
							
							float c1 = (float) (2.0f * Math.atan2(Math.sqrt(c0), Math.sqrt(1.0f - c0)));
							
							float dist = 3958.75f * c1;
							
							dists.add(dist);
						}
					}
				}
				
				ar_arr = new Airport[aps.size()];
				int index = 0;
				for(Airport a : aps) ar_arr[index++] = a;
				
				dst_arr = new float[aps.size()];
				index = 0;
				for(float f : dists) dst_arr[index++] = f;
				
				graph.routes_.put(ap, ar_arr);
				graph.dists_.put(ap, dst_arr);
			}
		}
		
		return graph;
	}
	
}
