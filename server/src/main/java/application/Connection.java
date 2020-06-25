package application;

import org.sql2o.Sql2o;
import org.sql2o.quirks.PostgresQuirks;

public class Connection {
	private static String dbHost = "localhost";
	private static String dbPort = "5432";
	private static String database = "interview";
	private static String dbUsername = "USER";
	private static String dbPassword = "PASSWORD";
	
	public static Sql2o getConnection() {
		Sql2o sql2o = new Sql2o("jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + database,
	            dbUsername, dbPassword, new PostgresQuirks() {
	        {
	            // make sure we use default UUID converter.
	            // converters.put(UUID.class, new UUIDConverter());
	        }
	    });	
		
		return sql2o;
	}
}
