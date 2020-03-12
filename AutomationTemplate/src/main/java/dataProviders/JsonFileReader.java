package dataProviders;

import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * @author madhubabu
 * @date 12-Mar-2020
 * @desc TODO
 */
public class JsonFileReader 
{
	public static Set<JSONObject> readJSON(String jSONFilePath) throws Exception
	{
		Set<JSONObject> mainSet = new HashSet<JSONObject>();
		try 
		{
			JSONParser parser = new JSONParser();
	        Object obj = parser.parse(new FileReader(jSONFilePath));
	        JSONObject jsonObject =  (JSONObject) obj;
	        
	        mainSet.addAll(jsonObject.entrySet());
		}
		catch(Exception e)
		{}

		return mainSet;
	}

}
