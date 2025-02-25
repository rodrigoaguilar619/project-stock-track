package project.stock.track.app.vo.entities;

public enum CatalogTypeCurrencyEnum {

	MXN(1),
    USD(2);

    private final Integer value;

    CatalogTypeCurrencyEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
