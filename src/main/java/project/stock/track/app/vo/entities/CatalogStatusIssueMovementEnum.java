package project.stock.track.app.vo.entities;

public enum CatalogStatusIssueMovementEnum {

	ACTIVE(1),
    INACTIVE(2);

    private final Integer value;

    CatalogStatusIssueMovementEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
