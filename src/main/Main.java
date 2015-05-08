package main;

import java.io.IOException;
import java.util.HashMap;

import data.Airport;
import data.AirportGraph;
import data.Route;
import display.Window;

public class Main {
	
	public static Airport start_airport = null;
	public static Airport end_airport   = null;
	
	public static Window window = null;
	
	public static AirportGraph airport_graph = null;
	
	public static void main(String[] args) {
		
		Airport[] airports = null;
		HashMap<String, Route[]> routes = null;;
		
		try {
			airports = Airport.LoadAirports("res/data/airports.dat");
			routes   = Route.LoadRoutes("res/data/routes.dat");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// make graph of airports and routes
		airport_graph = AirportGraph.GenerateGraph(airports, routes);
		
		// make display
		window = new Window();
		
	}
	
}
