package data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Airport {
	
	private String   name_;
	private Location loc_;
	private String   code_;  // FAA code if in USA or IATA for all others
	
	public Airport(String name, Location loc, String code){
		name_      = name;
		loc_       = loc;
		code_      = code;
	}
	
	// getters
	public String   name()     { return name_;      }
	public Location loc()      { return loc_;       }
	public String   code()     { return code_;      }
	
	/**
	 * Make an array of airports from the supplied airport data file
	 * @param file_name
	 * @return array of airport objects
	 * @throws IOException
	 */
	public static Airport[] LoadAirports(String file_name) throws IOException{
		ArrayList<Airport> ports = null;
		ArrayList<String> raw_lines = null;
		
		raw_lines = (ArrayList<String>) Files.readAllLines(Paths.get(file_name));
		
		ports = new ArrayList<Airport>();
		
		String[] data = null;
		
		Location loc = null;
		
		String name      = "";
		String city      = "";
		String country   = "";
		String code      = "";
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
			latitude  = Float.parseFloat(data[6]);
			longitude = Float.parseFloat(data[7]);
			altitude  = Float.parseFloat(data[8]);
			timezone  = Float.parseFloat(data[9]);
			dst       = data[10].substring(1, data[10].length() - 1);
			tz_oslon  = data[11].substring(1, data[11].length() - 1);
			
			
			
			// only store airporst that have a designation
			if(code.length() != 0){
				loc = new Location(
						city,     country,
						timezone, tz_oslon,  dst,
						latitude, longitude, altitude
						);
				ports.add(new Airport(name, loc, code));	
			}
				
			
			
		}
		
		Airport[] ret = new Airport[ports.size()];
		int index = 0;
		for(Airport a : ports) ret[index++] = a;
		return ret;
	}
	
}
