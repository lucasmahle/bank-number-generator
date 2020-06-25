package repository;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import entity.Settings;

public class SettingsRepository {
	private Sql2o connectionFactory;

	public SettingsRepository(Sql2o sql2o) {
		this.connectionFactory = sql2o;
	}

	public Settings getSettings() {
		try (Connection conn = connectionFactory.open()) {
			Settings settings = conn.createQuery("select * from settings")
					.executeAndFetchFirst(Settings.class);

			return settings;
		}
	}

	public void setCurrentPreferentialQueue(int number) {
		try (Connection conn = connectionFactory.beginTransaction()) {
			conn.createQuery("update settings set current_preferential_queue = :value")
			.addParameter("value", number)
			.executeUpdate();

			conn.commit();
		}
	}

	public void setCurrentNormalQueue(int number) {
		try (Connection conn = connectionFactory.beginTransaction()) {
			conn.createQuery("update settings set current_normal_queue = :value")
			.addParameter("value", number)
			.executeUpdate();

			conn.commit();
		}
	}
}
