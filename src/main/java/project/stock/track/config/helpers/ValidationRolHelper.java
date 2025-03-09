package project.stock.track.config.helpers;

import org.springframework.stereotype.Component;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.modules.security.jwt.repository.UserRolRepositoryImpl;
import project.stock.track.app.vo.entities.CatalogRolEntityEnum;

@Component
public class ValidationRolHelper {

	private UserRolRepositoryImpl userRolRepository;
	
	public ValidationRolHelper(UserRolRepositoryImpl userRolRepository) {
		this.userRolRepository = userRolRepository;
	}
	
	public void validateOperationUserAsAdmin(String userName) throws BusinessException {
		
		if (!userRolRepository.validateUserHasRol(userName, CatalogRolEntityEnum.ADMIN.getCode()))
			throw new BusinessException("Operation only available for admin users");
	}
}
