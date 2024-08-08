package project.stock.track;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;
import org.h2.tools.Server;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class ProjectConfigFlywayTest {

	private static Connection connection;
	protected String userName = "ADMIN";
	
	@Value("${spring.datasource.username}")
	private String dbUserName;
	
	@Value("${spring.datasource.password}")
	private String dbPassword;
	
	@Value("${spring.datasource.url}")
	private String dbUrl;
	
	private static boolean isInitLoaded = false;
	
	@BeforeEach
	public void initTestClass() throws SQLException, IOException {
		
		if (isInitLoaded) {
			return;
		}
		
		isInitLoaded = true;
		
		Server.createTcpServer("-tcpAllowOthers", "-tcpPort", "8085").start();
	    Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8086").start();
	    
	    connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
        connection.setAutoCommit(true);
        
        Flyway flyway = Flyway.configure().dataSource(dbUrl, dbUserName, dbPassword)
				.locations("classpath:db/migration")
        		.load();
        
        for(Location location : flyway.getConfiguration().getLocations()) {
			System.out.println("location: " + location);
		}

        flyway.migrate();
        
        connection.setAutoCommit(false);
	}
}