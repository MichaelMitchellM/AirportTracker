package data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Airport {
	
	private String   name_;
	private Location loc_;
	private String   code_;  // FAA code if in USA or IATA for all others
	private String   icao_code_;
	
	public Airport(String name, Location loc, String code, String icao_code){
		name_      = name;
		loc_       = loc;
		code_      = code;
		icao_code_ = icao_code;
	}
	
	// getters
	public String   name()     { return name_;      }
	public Location loc()      { return loc_;       }
	public String   code()     { return code_;      }
	public String   icao_code(){ return icao_code_; }
	
	/**
	 * Make an array of airports from the supplied airport data file
	 * @param file_name
	 * @return array of airport objects
	 * @throws IOException
	 */
	public static Airport[] LoadAirports(String file_name) throws IOException{
		int arr_index = 0;
		Airport[] ports = null;
		ArrayList<String> raw_lines = null;
		
		raw_lines = (ArrayList<String>) Files.readAllLines(Paths.get(file_name));
		
		ports = new Airport[raw_lines.size()];
		
		String[] data = null;
		
		Location loc = null;
		
		String name      = "";
		String city      = "";
		String country   = "";
		String code      = "";
		String icao_code = "";
		float  latitude  = 0.0f;
		float  longitude = 0.0f;
		float  altitude  = 0.0f;
		float  timezone  = 0.0f;
		String dst       = "";
		String tz_oslon  = "";
		
		for(String s : raw_lines){
			data = s.split(",(?=[^ ])+");
			name      = data[1].substring(1, data[1].length() - 1);
			city      = data[2].substring(1, data[2].length() - 1);
			country   = data[3].substring(1, data[3].length() - 1);
			code      = data[4].substring(1, data[4].length() - 1);
			icao_code = data[5].substring(1, data[5].length() - 1);
			latitude  = Float.parseFloat(data[6]);
			longitude = Float.parseFloat(data[7]);
			altitude  = Float.parseFloat(data[8]);
			timezone  = Float.parseFloat(data[9]);
			dst       = data[10].substring(1, data[10].length() - 1);
			tz_oslon  = data[11].substring(1, data[11].length() - 1);
			
			loc = new Location(
					city, country,
					timezone, tz_oslon, dst,
					latitude, longitude, altitude
					);
			
			ports[arr_index++] = new Airport(name, loc, code, icao_code);
			
		}
		
		return ports;
	}
	
}
