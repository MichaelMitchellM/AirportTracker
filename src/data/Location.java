package data;

public class Location {
	
	private String city_;
	private String country_;
	private float  timezone_;
	private String tz_oslon_;
	private String dst_;
	private float  latitude_;
	private float  longitude_;
	private float  altitude_feet_;
	
	public Location(
			String city,    String country,
			float timezone, String tz_oslon, String dst,
			float latitude, float longitude, float altitude_feet
			)
	{
		city_          = city;
		country_       = country;
		timezone_      = timezone;
		tz_oslon_      = tz_oslon;
		dst_           = dst;
		latitude_      = latitude;
		longitude_     = longitude;
		altitude_feet_ = altitude_feet;
	}
	
	// getters
	public String city()         { return city_;          }
	public String country()      { return country_;       }
	public float  timezone()     { return timezone_;      }
	public String tz_oslon()     { return tz_oslon_;      }
	public String dst()          { return dst_;           }
	public float  latitude()     { return latitude_;      }
	public float  longitude()    { return longitude_;     }
	public float  altitude_feet(){ return altitude_feet_; }
	
}
