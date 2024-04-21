package project.stock.track;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import project.stock.track.config.starter.AppStockTrack;

@SpringBootTest(classes = AppStockTrack.class)
public abstract class ProjectMainTest {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	protected String userName = "ADMIN";
	
}
