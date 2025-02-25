package project.stock.track.app.vo.entities;

public enum CatalogStatusIssueEnum {

	ACTIVE(1),
    INACTIVE(2);

    private final Integer value;

    CatalogStatusIssueEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
