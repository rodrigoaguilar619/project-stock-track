package project.stock.track.app.vo.entities;

public enum CatalogStatusTradingEnum {

	ACTIVE(1),
    INACTIVE(2);

    private final Integer value;

    CatalogStatusTradingEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
