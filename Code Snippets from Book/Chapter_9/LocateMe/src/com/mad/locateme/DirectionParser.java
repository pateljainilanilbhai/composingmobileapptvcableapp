package com.mad.locateme;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

public class DirectionParser {
	public List<LatLng> parseDirections(String response) {
		try {
			JSONObject responseObject = new JSONObject(response);
			JSONArray routesArray = responseObject.getJSONArray("routes");
			JSONObject route = routesArray.getJSONObject(0); // fetching the
																// first route
			JSONObject overviewPolyline = route
					.getJSONObject("overview_polyline");
			String points = overviewPolyline.getString("points");
			return decodePolylines(points);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	private List<LatLng> decodePolylines(String encoded) {

		List<LatLng> points = new ArrayList<LatLng>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;

		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			LatLng point = new LatLng((((double) lat / 1E5)),
					(((double) lng / 1E5)));
			points.add(point);
		}

		return points;
	}
}
