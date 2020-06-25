package controller;

import java.util.HashMap;
import java.util.Map;

import spark.Request;
import spark.Response;
import spark.Route;

public class IndexController {
	public static class IndexHandler implements Route {
		public Object handle(Request request, Response response) throws Exception {
			Map<String, Object> responseData = new HashMap<>();
	        responseData.put("status", true);

			return responseData;
        }
    }
}