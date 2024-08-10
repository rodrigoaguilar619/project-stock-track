package project.stock.track.modules.business;

import lib.base.backend.utils.DataUtil;
import lib.base.backend.utils.date.DateFormatUtil;
import lib.base.backend.utils.date.DateUtil;
import project.stock.track.app.utils.MapEntityToPojoUtil;
import project.stock.track.app.utils.MapPojoToEntityUtil;

public class MainBusiness {
	
	protected MapEntityToPojoUtil mapEntityToPojoUtil = new MapEntityToPojoUtil();
	protected MapPojoToEntityUtil mapPojoToEntityUtil = new MapPojoToEntityUtil();
	protected DateFormatUtil dateFormatUtil = new DateFormatUtil();
	protected DateUtil dateUtil = new DateUtil();
	protected DataUtil dataUtil = new DataUtil();
}
