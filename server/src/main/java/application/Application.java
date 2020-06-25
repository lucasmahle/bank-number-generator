package application;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.port;
import static spark.Spark.after;
import static spark.Spark.options;
import static spark.Spark.webSocket;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jetty.websocket.api.*;

import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;

import controller.IndexController;
import controller.NumberController;
import websocket.NumberWebSocket;

public class Application {
	public static List<Session> wsConnectionMap = new ArrayList();

	public static void main(String[] args) {
		Gson gson = new Gson();
		Injector injector = Guice.createInjector();

		port(4567);

		webSocket("/websocket", NumberWebSocket.class);

		options("/*", (request, response) -> {
			String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
			if (accessControlRequestHeaders != null) {
				response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
			}

			String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
			if (accessControlRequestMethod != null) {
				response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
			}

			return "OK";
		});


		after((req, res) -> {
			res.header("Access-Control-Allow-Origin", "*");
			res.header("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, PATCH, DELETE");
			res.header("Access-Control-Allow-Credentials", "false");
			res.header("Access-Control-Allow-Headers", "Origin, X-Request-Width, Content-Type, Accept, Authorization, token");
			res.type("application/json");
		});

		get("/", injector.getInstance(IndexController.IndexHandler.class), gson::toJson);

		post("/number/reset", injector.getInstance(NumberController.ResetHandler.class), gson::toJson);

		post("/number/next", injector.getInstance(NumberController.NextHandler.class), gson::toJson);

		get("/number/current", injector.getInstance(NumberController.CurrentHandler.class), gson::toJson);

		get("/number/latest", injector.getInstance(NumberController.LatestHandler.class), gson::toJson);

		post("/number/:type", injector.getInstance(NumberController.GenerateHandler.class), gson::toJson);
	}

}
