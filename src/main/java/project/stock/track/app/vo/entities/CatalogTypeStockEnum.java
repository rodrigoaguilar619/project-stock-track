package project.stock.track.app.vo.entities;

public enum CatalogTypeStockEnum {

	ISSUE(1),
    CRYPTO(2);

    private final Integer value;

    CatalogTypeStockEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
