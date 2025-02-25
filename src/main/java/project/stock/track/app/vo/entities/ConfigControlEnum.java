package project.stock.track.app.vo.entities;

public enum ConfigControlEnum {

	API_STOCK_TIINGO_TOKEN_ONE("api.stock.tiingo.token.one"),
    API_STOCK_TIINGO_TOKEN_TWO("api.stock.tiingo.token.two"),
    API_PRICE_DOLLAR_BANXICO_TOKEN("api.pricedollar.bancomexico.token"),
    API_PRICE_DOLLAR_BANXICO_URI("api.pricedollar.bancomexico.price"),
    API_PRICE_DOLLAR_CURRENT_LAYER_TOKEN("api.pricedollar.currentlayer.token"),
    API_PRICE_DOLLAR_CURRENT_LAYER_URI("api.pricedollar.currentlayer.price"),
    API_STOCK_TIINGO_IEX("api.stock.tiingo.iex"),
    API_STOCK_TIINGO_HISTORICAL_ISSUE("api.stock.tiingo.finantial.historical.issue"),
    API_STOCK_TIINGO_HISTORICAL_CRYPTO("api.stock.tiingo.finantial.historical.crypto"),
    API_STOCK_ALPHA_TOKEN("api.stock.alpha.token"),
    API_STOCK_ALPHA_URI("api.stock.alpha.finantial.historical.issue"),
    SCHEDULE_ISSUE_LAST_PRICE_UPDATE("api.schedule.issue.lastprice.update.enabled");

    private final String value;

    ConfigControlEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
