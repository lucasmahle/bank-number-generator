package repository;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import entity.NumberType;
import entity.Queue;

public class QueueRepository {
	private Sql2o connectionFactory;

	public QueueRepository(Sql2o sql2o) {
		this.connectionFactory = sql2o;
	}

	public void addNumber(int nextNumber, int numberType) {
		try (Connection conn = connectionFactory.beginTransaction()) {
			conn.createQuery("insert into queue (id, number, type) VALUES (DEFAULT, :number, :type)")
			.addParameter("number", nextNumber)
			.addParameter("type", numberType)
			.executeUpdate();
			conn.commit();
		}
	}

	public Queue getNextNumber() {
		try (Connection conn = connectionFactory.open()) {
			Queue data = conn.createQuery("select * from queue order by type, id ASC")
					.executeAndFetchFirst(Queue.class);

			return data;

		}
	}

	public void deleteNumber(long id) {
		try (Connection conn = connectionFactory.beginTransaction()) {
			conn.createQuery("delete from queue where id = :id")
			.addParameter("id", id)
			.executeUpdate();
			conn.commit();
		}

	}
}
