package data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;

public class Route {
	
	String start_airport_code_;
	String end_airport_code_;
	
	String airline_code_;
	String equipment_;
	
	public Route(
			String start_airport_code, String end_airport_code,
			String airline_code,       String equipment
			){
		start_airport_code_ = start_airport_code;
		end_airport_code_   = end_airport_code;
		
		airline_code_ = airline_code;
		equipment_    = equipment;
	}
	
	/**
	 * Make HashMap of routes between airports from the supplied route data file
	 * @param file_name
	 * @return HashMap of array of routes
	 * @throws IOException
	 */
	public static HashMap<String, Route[]> LoadRoutes(String file_name) throws IOException{
		ArrayList<String> raw_lines;
		HashMap<String, ArrayList<Route>> routes;
		
		routes = new HashMap<String, ArrayList<Route>>();
		raw_lines = (ArrayList<String>) Files.readAllLines(Paths.get(file_name));
		
		String[] data;
		
		String airline_code;
		String start_airport_code;
		String end_airport_code;
		int    num_stops;
		String equipment;
		
		// parse lines from file into variables
		for(String s : raw_lines){
			data = s.split(",");
			
			airline_code       = data[0];
			start_airport_code = data[2];
			end_airport_code   = data[4];
			num_stops          = Integer.parseInt(data[7]);
			if(data.length == 9) equipment = data[8];
			else equipment = "";
			
			// routes should only have direct flights
			if(num_stops == 0){
				// if key does not exist, add it and give value of new arraylist of routes
				if(!routes.containsKey(start_airport_code))
					routes.put(start_airport_code, new ArrayList<Route>());
				// place new route in arraylist of routes for that airport
				routes.get(start_airport_code).add(
						new Route(
								start_airport_code, end_airport_code,
								airline_code, equipment
								)
						);
			}
		}
		
		HashMap<String, Route[]> ret = new HashMap<String, Route[]>();
		
		BiConsumer<String, ArrayList<Route>> bc = (k, v) -> {
			Route[] rs = new Route[v.size()];
			int index = 0;
			for(Route r : v) rs[index++] = r;
			ret.put(k, rs);
		};
		
		routes.forEach(bc);
		
		return ret;
	}
	
}
