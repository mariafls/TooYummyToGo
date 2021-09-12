package pt.tooyummytogo.facade.dto;

import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.GeodesicData;

public class CoordinatesPosition {
	
	private double latitude;
	private double longitude;

	public CoordinatesPosition(double lat, double lng) {
		this.latitude = lat;
		this.longitude = lng;
	}
	
	
	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}
	
	public double distanceInMeters(CoordinatesPosition o) {
		GeodesicData g = Geodesic.WGS84.Inverse(this.latitude, this.longitude, o.latitude, o.longitude);
        return g.s12;  // distance in meters
	}
	
}
