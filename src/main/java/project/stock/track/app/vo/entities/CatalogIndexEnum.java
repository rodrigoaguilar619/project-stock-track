package project.stock.track.app.vo.entities;

public enum CatalogIndexEnum {

	SP500(1),
    NASDAQ(2),
    OTHER(3);

    private final Integer value;

    CatalogIndexEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
