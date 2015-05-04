package com.enginsol.angular;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Path("/database")
@Produces("application/xml")
public class Database {

	//constants
	int PAGE_SIZE = 10;

	// "database" variable
	ArrayList<ArrayList<String>> DB = new ArrayList<ArrayList<String>>();

	public Database(){
		//fill in "database"
		createDB();
	}

	//returns a string of PAGE_SIZE elements, 
	//starting at idx (1-indexed), in a JSON array
	@GET
	@Produces("application/javascript")
	public String getMsg(
			@DefaultValue("1") @QueryParam("start") String start,
			@DefaultValue("A") @QueryParam("sort") String sort,
			@DefaultValue("asc") @QueryParam("direction") String direction,
			@QueryParam("callback") String callback){
		//sort array
		Collections.sort(DB, new DbComparator(sort, direction));

		//create data array object
		JsonArray arr = new JsonArray();

		//find array bounds
		int idx = Math.min(Math.max(Integer.valueOf(start)-1, 0), DB.size());
		int end = Math.min(idx+PAGE_SIZE, DB.size());

		//add each element in bounds to the output string
		for(ArrayList<String> sList : DB.subList(idx, end)){
			//JSON version
			JsonObject je = new JsonObject();
			je.addProperty("A", sList.get(0));
			je.addProperty("B", sList.get(1));
			arr.add(je);
		}

		//create data wrapper and store array and count data
		JsonObject dataWrapper = new JsonObject();
		dataWrapper.add("data", arr);
		dataWrapper.addProperty("count", PAGE_SIZE);
		dataWrapper.addProperty("total", DB.size());

		return (callback + "(" + dataWrapper.toString() + ")");
	}

	private class DbComparator implements Comparator<ArrayList<String>>{
		private String sort, direction;

		public DbComparator(String s, String d){
			this.sort = s;
			this.direction = d;
		}

		public int compare(ArrayList<String> a1, ArrayList<String> a2) {
			if(sort.equals("B")){
				if(direction.equals("desc")){
					return a2.get(1).compareTo(a1.get(1));
				} else{
					return a1.get(1).compareTo(a2.get(1));
				}
			} else{ //sort == A
				if(direction.equals("desc")){
					return a2.get(0).compareTo(a1.get(0));
				} else{
					return a1.get(0).compareTo(a2.get(0));
				}
			}
		}
	}

	private void createDB(){
		ArrayList<String> entry = new ArrayList<String>();
		entry.add("Entry 1");
		entry.add("51");
		DB.add(entry);
		entry = new ArrayList<String>();
		entry.add("Entry 2");
		entry.add("42");
		DB.add(entry);
		entry = new ArrayList<String>();
		entry.add("Entry 3");
		entry.add("33");
		DB.add(entry);
		entry = new ArrayList<String>();
		entry.add("Entry 4");
		entry.add("24");
		DB.add(entry);
		entry = new ArrayList<String>();
		entry.add("Entry 5");
		entry.add("15");
		DB.add(entry);
		entry = new ArrayList<String>();
		entry.add("Entry 6");
		entry.add("52");
		DB.add(entry);
		entry = new ArrayList<String>();
		entry.add("Entry 7");
		entry.add("43");
		DB.add(entry);
		entry = new ArrayList<String>();
		entry.add("Entry 8");
		entry.add("34");
		DB.add(entry);
		entry = new ArrayList<String>();
		entry.add("Entry 9");
		entry.add("25");
		DB.add(entry);
		entry = new ArrayList<String>();
		entry.add("Entry 10");
		entry.add("11");
		DB.add(entry);
		entry = new ArrayList<String>();
		entry.add("Entry 11");
		entry.add("41");
		DB.add(entry);
		entry = new ArrayList<String>();
		entry.add("Entry 12");
		entry.add("32");
		DB.add(entry);
		entry = new ArrayList<String>();
		entry.add("Entry 13");
		entry.add("23");
		DB.add(entry);
		entry = new ArrayList<String>();
		entry.add("Entry 14");
		entry.add("14");
		DB.add(entry);
		entry = new ArrayList<String>();
		entry.add("Entry 15");
		entry.add("55");
		DB.add(entry);
		entry = new ArrayList<String>();
		entry.add("Entry 16");
		entry.add("1");
		DB.add(entry);
		entry = new ArrayList<String>();
		entry.add("Entry 17");
		entry.add("2");
		DB.add(entry);
		entry = new ArrayList<String>();
		entry.add("Entry 18");
		entry.add("3");
		DB.add(entry);
		entry = new ArrayList<String>();
		entry.add("Entry 19");
		entry.add("4");
		DB.add(entry);
		entry = new ArrayList<String>();
		entry.add("Entry 20");
		entry.add("5");
		DB.add(entry);
		entry = new ArrayList<String>();
		entry.add("Entry 21");
		entry.add("6");
		DB.add(entry);
		entry = new ArrayList<String>();
		entry.add("Entry 22");
		entry.add("7");
		DB.add(entry);
		entry = new ArrayList<String>();
		entry.add("Entry 23");
		entry.add("8");
		DB.add(entry);
		entry = new ArrayList<String>();
		entry.add("Entry 24");
		entry.add("9");
		DB.add(entry);
		entry = new ArrayList<String>();
		entry.add("Entry 25");
		entry.add("10");
		DB.add(entry);
	}
}
