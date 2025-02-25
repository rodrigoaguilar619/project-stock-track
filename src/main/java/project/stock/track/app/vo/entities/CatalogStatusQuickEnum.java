package project.stock.track.app.vo.entities;

public enum CatalogStatusQuickEnum {

	ACTIVE(1),
    INACTIVE(2);

    private final Integer value;

    CatalogStatusQuickEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
