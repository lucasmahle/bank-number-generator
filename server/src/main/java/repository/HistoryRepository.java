package repository;

import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import entity.History;
import entity.NumberType;
import entity.Queue;

public class HistoryRepository {
	private Sql2o connectionFactory;

	public HistoryRepository(Sql2o sql2o) {
		this.connectionFactory = sql2o;
	}

	public void addNumber(int number, int type) {
		try (Connection conn = connectionFactory.beginTransaction()) {
			conn.createQuery("insert into history (id, number, type) VALUES (DEFAULT, :number, :type)")
			.addParameter("number", number)
			.addParameter("type", type)
			.executeUpdate();
			conn.commit();
		}
	}

	public History getLastNumber() {
		try (Connection conn = connectionFactory.open()) {
			History data = conn.createQuery("select * from history order by id desc")
					.executeAndFetchFirst(History.class);

			return data;

		}
	}

	public List<History> getHistoricNumbers() {
		try (Connection conn = connectionFactory.open()) {
			List<History> data = conn.createQuery("select * from history order by id desc LIMIT 5 OFFSET 1")
					.executeAndFetch(History.class);

			return data;

		}
	}
}
