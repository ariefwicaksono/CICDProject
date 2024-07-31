package data;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.nio.charset.*;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;



public class DataReader {
	
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
		//read json to string
		Charset utf8 = StandardCharsets.UTF_8;
		String jsonContent = FileUtils.readFileToString(new File(filePath,
				StandardCharsets.UTF_8.name()));
		//String to hasmap jakson databind
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
		});
		return data;
	}
}
