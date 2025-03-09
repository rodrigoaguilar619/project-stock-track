package project.stock.track;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import project.stock.track.config.starter.AppStockTrack;

@ActiveProfiles("test")
@SpringBootTest(classes = AppStockTrack.class)
public abstract class ProjectIntegrationTest extends ProjectConfigFlywayTest {

	protected static String userName = "ADMIN";
}
