package project.stock.track.app.vo.entities;

public enum CatalogBrokerEnum {

	GBM_HOMBROKER(1),
    CHARLES_SCHWAB(2);

    private final Integer value;

    CatalogBrokerEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
