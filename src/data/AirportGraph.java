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
		
		HashMap<Airport, Float>   d = new HashMap<Airport, Float>();
		HashMap<Airport, Airport> p = new HashMap<Airport, Airport>();
		
		HashMap<Airport, Float> S = new HashMap<Airport, Float>();
		
		ArrayList<Airport> R = new ArrayList<Airport>();
		
		S.put(start, 0.0f);
		p.put(start, null);
		
		ArrayList<Airport> start_con = new ArrayList<Airport>();
		for(Airport apt : routes_.get(start)) start_con.add(apt);
		
		for(Airport apt : airports_){
			if(!apt.code().equals(start.code())){
				int ind;
				if((ind = start_con.indexOf(apt)) != -1){
					R.add(apt);
					d.put(apt, dists_.get(start)[ind]);
				}
				else{
					R.add(apt);
					d.put(apt, Float.POSITIVE_INFINITY);
				}
			}
		}
			
		
		while(!R.isEmpty()){
			Airport[] lowest_dist = new Airport[]{d.keySet().iterator().next()};
			BiConsumer<Airport, Float> bc0 = (x, y) -> {
				if(d.get(x) < d.get(lowest_dist[0])){
					lowest_dist[0] = x;
				}
			};
			d.forEach(bc0);
			R.remove(lowest_dist[0]);
			
			if(R.contains(lowest_dist[0])){
				System.out.println(R.contains(lowest_dist[0]));
			}
			
			S.put(lowest_dist[0], d.get(lowest_dist[0]));
			
			int index = 0;
			for(Airport apt : routes_.get(lowest_dist[0])){
				if(R.contains(apt)){
					if(d.get(lowest_dist[0]) + dists_.get(lowest_dist[0])[index] < d.get(apt)){
						d.put(apt, d.get(lowest_dist[0]) + dists_.get(lowest_dist[0])[index]);
						p.put(apt, lowest_dist[0]);
					}
				}
				++index;
			}
		}
		
		ArrayList<Airport> route = new ArrayList<Airport>();
		Airport current = end;
		while(current != null){
			route.add(current);
			current = p.get(current);
		}
		
		Airport[] ret = new Airport[route.size()];
		for(int i = 0; i < route.size(); ++i){
			ret[i] = route.get(route.size() - i - 1);
			// TODO REMOVE
			System.out.println(route.get(route.size() - i - 1));
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
