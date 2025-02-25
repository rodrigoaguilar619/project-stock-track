package project.stock.track.app.vo.entities;

public enum CatalogTypeMovementEnum {

	DEPOSIT(1),
    WITHDRAW(2),
    DIVIDEND(3),
    TAX(4),
    CASH_IN_LIEU(5),
    ISSUE_FEE(6),
    BANK_INTEREST(7),
    BANK_FEE(8);

    private final Integer value;

    CatalogTypeMovementEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
