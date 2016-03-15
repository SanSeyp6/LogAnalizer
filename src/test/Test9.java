package test;

import java.io.IOException;
import java.io.Writer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import util.JSONWriter;
import util.JsonReadWriteUtils;

/**
 * Пытаемся сделать pretty-print
 * 
 * @author aaovchinnikov
 *
 */
public class Test9 {

	public static void main(String[] args) {
		JSONArray ar = new JSONArray();
		JSONArray array = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONObject resultJson = new JSONObject();

		ar.add("first");
		ar.add(new Integer(100));

		obj.put("one", "two");
		obj.put("three", "four");

		resultJson.put("paramsArray", ar);
		resultJson.put("paramsObj", obj);
		resultJson.put("paramsStr", "some string");
		array.add(resultJson);
		array.add(resultJson);

		try {
			Writer writer = new JSONWriter(); // this writer adds indentation
			resultJson.writeJSONString(writer);
			System.out.println(writer.toString());
			
			System.out.println("Write to file");
			JsonReadWriteUtils.saveJsonArrayToFile(array, "save_json.json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
