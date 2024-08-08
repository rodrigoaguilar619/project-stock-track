package project.stock.track;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import org.h2.tools.Server;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class ProjectConfigScriptTest {

	public static boolean isInitLoaded = false;

	private static Connection connection;
	protected String userName = "ADMIN";
	
	@BeforeEach
	public void initTestClass() throws SQLException, IOException {
		
		if (isInitLoaded) {
			return;
		}
		
		isInitLoaded = true;
		
		Server.createTcpServer("-tcpAllowOthers", "-tcpPort", "8085").start();
	    Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8086").start();
	    
	    connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;Mode=MySQL", "sa", "password");
        connection.setAutoCommit(true);
        
        List<String> sqlFiles = Arrays.asList( 
        		"src/test/resources/db/migration/test/V1__create_tables.sql",
				"src/test/resources/db/migration/test/V2__modify_columns_active_to_bit.sql",
				"src/test/resources/db/migration/test/V3__modify_columns_user_config_control.sql",
				"src/test/resources/db/migration/test/V4__insert_catalogs.sql",
				"src/test/resources/db/migration/test/V5__insert_data.sql",
				"src/test/resources/db/migration/test/V6__remove_columns.sql"
        		);
        
        loadSqlScripts(sqlFiles);
        connection.setAutoCommit(false);
	}
	
	private static String loadSqlFromFile(String filePath) throws IOException {
        StringBuilder sqlBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sqlBuilder.append(line).append("\n");
            }
        }
        return sqlBuilder.toString();
    }
	
	private static void loadSqlScripts(List<String> sqlFiles) throws SQLException, IOException {
		
		for (String file : sqlFiles) {
            String sql = loadSqlFromFile(file);
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(sql);
            }
        }
	}
}