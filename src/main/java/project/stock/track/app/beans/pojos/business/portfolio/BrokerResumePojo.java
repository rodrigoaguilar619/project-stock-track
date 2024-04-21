package project.stock.track.app.beans.pojos.business.portfolio;

import java.math.BigDecimal;

public class BrokerResumePojo {
	
	private Integer idBroker;
	
	private String broker;

	private BigDecimal totalDeposits;
	
	private BigDecimal totalSecuritiesValue;
	
	private BigDecimal totalSecuritiesValueMxn;
	
	private BigDecimal totalYield;
	
	private Integer idTypeCurrency;

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
		return totalYield;
	}

	public void setYield(BigDecimal totalYield) {
		this.totalYield = totalYield;
	}

	public Integer getIdTypeCurrency() {
		return idTypeCurrency;
	}

	public void setIdTypeCurrency(Integer idTypeCurrency) {
		this.idTypeCurrency = idTypeCurrency;
	}
	
}
