package project.stock.track.app.beans.pojos.business.portfolio;

import java.math.BigDecimal;

public class PortfolioResumePojo {
	
	private Integer idBroker;
	
	private String broker;

	private BigDecimal totalDeposits;
	
	private BigDecimal totalSecuritiesValue;
	
	private BigDecimal totalSecuritiesValueMxn;
	
	private BigDecimal yield;
	
	private Integer idTypeCurrency;
	
	private String typeCurrency;

	public Integer getIdBroker() {
		return idBroker;
	}

	public void setIdBroker(Integer idBroker) {
		this.idBroker = idBroker;
	}

	public String getBroker() {
		return broker;
	}

	public void setBroker(String broker) {
		this.broker = broker;
	}

	public BigDecimal getTotalDeposits() {
		return totalDeposits;
	}

	public void setTotalDeposits(BigDecimal totalDeposits) {
		this.totalDeposits = totalDeposits;
	}

	public BigDecimal getTotalSecuritiesValue() {
		return totalSecuritiesValue;
	}

	public void setTotalSecuritiesValue(BigDecimal totalSecuritiesValue) {
		this.totalSecuritiesValue = totalSecuritiesValue;
	}

	public BigDecimal getTotalSecuritiesValueMxn() {
		return totalSecuritiesValueMxn;
	}

	public void setTotalSecuritiesValueMxn(BigDecimal totalSecuritiesValueMxn) {
		this.totalSecuritiesValueMxn = totalSecuritiesValueMxn;
	}

	public BigDecimal getYield() {
		return yield;
	}

	public void setYield(BigDecimal yield) {
		this.yield = yield;
	}

	public Integer getIdTypeCurrency() {
		return idTypeCurrency;
	}

	public void setIdTypeCurrency(Integer idTypeCurrency) {
		this.idTypeCurrency = idTypeCurrency;
	}

	public String getTypeCurrency() {
		return typeCurrency;
	}

	public void setTypeCurrency(String typeCurrency) {
		this.typeCurrency = typeCurrency;
	}
	
}
