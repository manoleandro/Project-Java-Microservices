package br.org.ons.bdt.test.data;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Utility for converting ResultSets into some Output formats
 */
public class Converter {
    /**
     * Convert a result set into a JSON Array
     * @param resultSet
     * @return a JSONArray
     * @throws Exception
     */
	
	// ISO8601
	private static SimpleDateFormat sdf;
	
	static {
		TimeZone tz = TimeZone.getTimeZone("America/Sao_Paulo");
		//sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSSZ");
		sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		sdf.setTimeZone(tz);
	}
	
	
	
    public static JSONArray convertToJSON(ResultSet resultSet,boolean mongoDBDate)
            throws Exception {
        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            int total_rows = resultSet.getMetaData().getColumnCount();
            JSONObject obj = new JSONObject();
            for (int i = 0; i < total_rows; i++) {
            	Object resultObj = resultSet.getObject(i + 1);
            	String label = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
            	if(resultObj instanceof java.sql.Timestamp) {
            		
            		if(mongoDBDate) {
	            		JSONObject obj2 = new JSONObject();
	            		JSONObject obj3 = new JSONObject();
	            		/***
	            		 * The MongoDB JSON parser currently does not support loading ISO-8601 strings representing dates prior to the Unix epoch. When formatting pre-epoch dates and dates past what your system’s time_t type can hold, the following format is used:
	
							fonte: https://docs.mongodb.com/manual/reference/mongodb-extended-json/
	            		 */
	            		Long millis = ((java.sql.Timestamp) resultObj).getTime();            		
            		
	            		obj3.put("$numberLong", millis.toString());
	            		obj2.put("$date", obj3);
	            		obj.put(label, obj2);
            		} else {
            			obj.put(label,(sdf.format(resultObj)));
            		}
            	} else {
	                obj.put(label, resultObj);
            	}
            }
            jsonArray.put(obj);
        }
        return jsonArray;
    }
    
    public static JSONArray concatArray(JSONArray... arrs)
            throws JSONException {
        JSONArray result = new JSONArray();
        for (JSONArray arr : arrs) {
            for (int i = 0; i < arr.length(); i++) {
                result.put(arr.get(i));
            }
        }
        return result;
    }
    /**
     * Convert a result set into a XML List
     * @param resultSet
     * @return a XML String with list elements
     * @throws Exception if something happens
     */
    public static String convertToXML(ResultSet resultSet)
            throws Exception {
        StringBuffer xmlArray = new StringBuffer("<results>");
        while (resultSet.next()) {
            int total_rows = resultSet.getMetaData().getColumnCount();
            xmlArray.append("<result ");
            for (int i = 0; i < total_rows; i++) {
                xmlArray.append(" " + resultSet.getMetaData().getColumnLabel(i + 1)
                        .toLowerCase() + "='" + resultSet.getObject(i + 1) + "'"); }
            xmlArray.append(" />");
        }
        xmlArray.append("</results>");
        return xmlArray.toString();
    }
}