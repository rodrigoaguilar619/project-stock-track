package project.stock.track.app.vo.entities;

public enum CatalogSectorEnum {

	FINANCIALS(1),
    TECHNOLOGY(2),
    HEALTHCARE(3),
    MATERIALS(4),
    REAL_ESTATE(5),
    CONSUMER_STAPLES(6),
    CONSUMER_DISCRETIONARY(7),
    UTILITIES(8),
    ENERGY(9),
    INDUSTRIALS(10),
    CONSUMER_SERVICES(11),
    CONSUMER_CYCLICAL(12),
    CONSUMER_DEFENSIVE(13);

    private final Integer value;

    CatalogSectorEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
