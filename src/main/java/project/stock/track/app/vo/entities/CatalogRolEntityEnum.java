package project.stock.track.app.vo.entities;

public enum CatalogRolEntityEnum {

	ADMIN(1),
	DEMO(2);
	
	private int code;
	
	CatalogRolEntityEnum(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
}
