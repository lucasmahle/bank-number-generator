package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sql2o.Sql2o;

import application.Connection;
import application.Util;
import entity.History;
import entity.NumberType;
import entity.Queue;
import entity.Settings;
import repository.HistoryRepository;
import repository.QueueRepository;
import repository.SettingsRepository;

import spark.Request;
import spark.Response;
import spark.Route;
import websocket.NumberWebSocket;

public class NumberController {
	
	public static class ResetHandler implements Route {
		public Object handle(Request request, Response response) throws Exception {
			Map<String, Object> responseObject = new HashMap<>();
			try {
				Sql2o conn = Connection.getConnection(); 
				SettingsRepository settingsRepo = new SettingsRepository(conn);

				settingsRepo.setCurrentPreferentialQueue(0);
				settingsRepo.setCurrentNormalQueue(0);

				responseObject.put("success", true);
			} catch (Exception e) {
				responseObject.put("success", false);
				responseObject.put("message", "Ocorreu uma falha ao verificar status da aplicação");
			}
			return responseObject;
		}
	}

	public static class GenerateHandler implements Route {
		public Object handle(Request request, Response response) throws Exception {
			Map<String, Object> responseObject = new HashMap<>();
			try {
				Map<String, Object> data = new HashMap<>();

				Sql2o conn = Connection.getConnection(); 
				SettingsRepository settingsRepo = new SettingsRepository(conn);
				QueueRepository queueRepo = new QueueRepository(conn);

				Settings setting = settingsRepo.getSettings();
				boolean isPreferentialNumber = request.params("type").equals("1");
				int nextNumber = 0;
				int numberType;

				if(isPreferentialNumber) {
					nextNumber = setting.current_preferential_queue + 1;
					numberType = NumberType.Preferential;
				} else {
					nextNumber = setting.current_normal_queue + 1;		
					numberType = NumberType.Normal;			
				}

				queueRepo.addNumber(nextNumber, numberType);

				if(isPreferentialNumber) {
					settingsRepo.setCurrentPreferentialQueue(nextNumber);
				} else {
					settingsRepo.setCurrentNormalQueue(nextNumber);
				}

				data.put("number", nextNumber);
				data.put("type", numberType);
				data.put("formatted", Util.formatQueueNumber(numberType, nextNumber));

				responseObject.put("success", true);
				responseObject.put("data", data);
			} catch (Exception e) {
				responseObject.put("success", false);
				responseObject.put("message", "Ocorreu uma falha ao gerar o número");
			}
			return responseObject;
		}
	}

	public static class NextHandler implements Route {
		public Object handle(Request request, Response response) throws Exception {
			Map<String, Object> responseObject = new HashMap<>();
			try {
				Map<String, Object> data = new HashMap<>();

				Sql2o conn = Connection.getConnection(); 
				HistoryRepository historyRepo = new
						HistoryRepository(conn); 
				QueueRepository queueRepo = new
						QueueRepository(conn);

				Queue nextNumber = queueRepo.getNextNumber(); 
				if(nextNumber == null) {
					responseObject.put("success", true); 
					responseObject.put("message", "Não há números na fila"); 
					return responseObject; 
				}

				historyRepo.addNumber(nextNumber.number, nextNumber.type);

				queueRepo.deleteNumber(nextNumber.id);

				data.put("number", nextNumber.number); 
				data.put("type", nextNumber.type);
				data.put("formatted", Util.formatQueueNumber(nextNumber.type, nextNumber.number));
				NumberWebSocket.broadcast("new-number");

				responseObject.put("success", true);
				responseObject.put("data", data);
			} catch (Exception e) {
				responseObject.put("success", false);
				responseObject.put("message", "Ocorreu uma falha ao obter o próximo número");
			}
			return responseObject;
		}
	}

	public static class CurrentHandler implements Route {
		public Object handle(Request request, Response response) throws Exception {
			Map<String, Object> responseObject = new HashMap<>();
			try {
				Map<String, Object> data = new HashMap<>();

				Sql2o conn = Connection.getConnection(); 
				HistoryRepository historyRepo = new HistoryRepository(conn);

				History lastNumber = historyRepo.getLastNumber();
				if(lastNumber == null) {					
					responseObject.put("success", false);
					responseObject.put("message", "Não há números na fila");
					return responseObject;
				}

				data.put("number", lastNumber.number);
				data.put("type", lastNumber.type);
				data.put("formatted", Util.formatQueueNumber(lastNumber.type, lastNumber.number));

				responseObject.put("success", true);
				responseObject.put("data", data);
			} catch (Exception e) {
				responseObject.put("success", false);
				responseObject.put("message", "Ocorreu uma falha ao obter o número atual");
			}
			return responseObject;
		}
	}

	public static class LatestHandler implements Route {
		public Object handle(Request request, Response response) throws Exception {
			Map<String, Object> responseObject = new HashMap<>();
			try {
				List<HashMap<String,Object>> historycData = new ArrayList<HashMap<String,Object>>();

				// Get last + 1 and 6 others items from history table
				Sql2o conn = Connection.getConnection(); 
				HistoryRepository historyRepo = new HistoryRepository(conn);

				List<History> historic = historyRepo.getHistoricNumbers();
				for (History history : historic) {
					HashMap<String, Object> data = new HashMap<>();
					data.put("number", history.number);
					data.put("type", history.type);
					data.put("formatted", Util.formatQueueNumber(history.type, history.number));

					historycData.add(data);
				}


				responseObject.put("success", true);
				responseObject.put("data", historycData);
			} catch (Exception e) {
				responseObject.put("success", false);
				responseObject.put("message", "Ocorreu uma falha ao obter o histórico de números");
			}
			return responseObject;
		}
	}
}
