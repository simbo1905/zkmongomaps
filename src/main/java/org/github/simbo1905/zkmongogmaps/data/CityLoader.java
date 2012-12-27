package org.github.simbo1905.zkmongogmaps.data;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.github.simbo1905.zkmongogmaps.app.City;


import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

/**
 * Class to load the demo file http://media.mongodb.org/zips.json
 * Note that the file is not strictly well formed as it has a well formed 
 * object per line but the overally file is not an array or other single 
 * structure
 */
public class CityLoader {
	Gson gson = new Gson();

	/**
	 * loads the each line as a separate json structure
	 */
	public List<City> readJsonStream(InputStream in) throws Exception {
		List<City> messages = new ArrayList<City>();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String str;
			while ((str = reader.readLine()) != null) {
				City city = process(str);
				messages.add(city);
			}
			reader.close();
		} catch (IOException e) {
		}

		return messages;
	}

	private City process(String str) throws Exception {
		JsonReader reader = new JsonReader(new InputStreamReader(new ByteArrayInputStream(str.getBytes("UTF-8")),
				"UTF-8"));
		City city = gson.fromJson(reader, City.class);
		return city;
	}
}
