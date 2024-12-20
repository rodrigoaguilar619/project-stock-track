package project.stock.track.app.beans.pojos.business.portfolio;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PortfolioResumePojo {
	
	private Integer idBroker;
	
	private String broker;

	private BigDecimal totalDeposits;
	
	private BigDecimal totalDividends;
	
	private BigDecimal totalDividendTaxes;
	
	private BigDecimal totalCashInLieu;
	
	private BigDecimal totalFees;
	
	private BigDecimal totalBankInterests;
	
	private BigDecimal totalGainLoss;
	
	private BigDecimal totalCash;
	
	private BigDecimal yield;
	
	private Integer idTypeCurrency;
	
	private String typeCurrency;
}
