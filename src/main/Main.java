package main;

import java.io.IOException;

import data.Airport;

public class Main {
	
	public static void main(String[] args) {
		
		
		try {
			Airport[] ap = Airport.LoadAirports("res/data/airports.dat");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
}
