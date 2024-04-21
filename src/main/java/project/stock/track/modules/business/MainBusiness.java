package project.stock.track.modules.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import lib.base.backend.modules.security.jwt.repository.UserRepositoryImpl;
import lib.base.backend.persistance.GenericPersistence;
import lib.base.backend.utils.DataUtil;
import project.stock.track.app.utils.MapEntityToPojoUtil;
import project.stock.track.app.utils.MapPojoToEntityUtil;

public class MainBusiness {

	@SuppressWarnings("rawtypes")
	@Autowired
	protected GenericPersistence genericPersistance;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	@Qualifier("customPersistance")
	protected GenericPersistence genericCustomPersistance;
	
	@Autowired
	protected UserRepositoryImpl userRepository;
	
	@Autowired
	protected DataUtil dataUtil;
	
	@Autowired
	protected MapEntityToPojoUtil mapEntityToPojoUtil;
	
	@Autowired
	protected MapPojoToEntityUtil mapPojoToEntityUtil;
}
