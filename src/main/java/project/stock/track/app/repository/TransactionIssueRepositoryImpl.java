package project.stock.track.app.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.SingularAttribute;
import lib.base.backend.utils.DataParseUtil;
import lib.base.backend.utils.JpaUtil;
import project.stock.track.app.beans.entity.CatalogBrokerEntity;
import project.stock.track.app.beans.entity.CatalogBrokerEntity_;
import project.stock.track.app.beans.entity.CatalogIssuesEntity_;
import project.stock.track.app.beans.entity.CatalogTypeCurrencyEntity;
import project.stock.track.app.beans.entity.CatalogTypeCurrencyEntity_;
import project.stock.track.app.beans.entity.DollarHistoricalPriceEntity;
import project.stock.track.app.beans.entity.IssuesLastPriceTmpEntity_;
import project.stock.track.app.beans.entity.IssuesManagerEntity_;
import project.stock.track.app.beans.entity.TransactionIssueEntity;
import project.stock.track.app.beans.entity.TransactionIssueEntity_;
import project.stock.track.app.beans.pojos.business.portfolio.PorfolioIssuePojo;
import project.stock.track.app.beans.pojos.business.transaction.TransactionIssueNotSoldPojo;
import project.stock.track.app.beans.pojos.tuple.IssueTransactionResumeTuplePojo;
import project.stock.track.app.beans.pojos.tuple.IssueTransactionsByDateTuplePojo;
import project.stock.track.app.utils.CalculatorUtil;
import project.stock.track.app.vo.catalogs.CatalogsEntity.CatalogTypeCurrency;
import project.stock.track.app.vo.catalogs.CatalogsStaticData;

@Repository
public class TransactionIssueRepositoryImpl {

	EntityManager em;
	DollarHistoricalPriceRepositoryImpl dollarHistoricalPriceRepository;
	
	private JpaUtil jpaUtil = new JpaUtil();
	private CalculatorUtil calculatorUtil = new CalculatorUtil();
	private DataParseUtil dataParseUtil = new DataParseUtil();
	
	public TransactionIssueRepositoryImpl(EntityManager em, DollarHistoricalPriceRepositoryImpl dollarHistoricalPriceRepository) {
		this.em = em;
		this.dollarHistoricalPriceRepository = dollarHistoricalPriceRepository;
	}
	
	private Expression<Object> getSelectedPriceTotal(CriteriaBuilder cb, Root<TransactionIssueEntity> root, boolean isSell) {
		
		SingularAttribute<TransactionIssueEntity, BigDecimal> priceTotalUsd = isSell ? TransactionIssueEntity_.priceTotalSell : TransactionIssueEntity_.priceTotalBuy;
		SingularAttribute<TransactionIssueEntity, BigDecimal> priceTotalMxn = isSell ? TransactionIssueEntity_.priceTotalSellMxn : TransactionIssueEntity_.priceTotalBuyMxn;
		
		return cb.selectCase()
				.when(cb.equal(root.get(TransactionIssueEntity_.catalogBrokerEntity).get(CatalogBrokerEntity_.idTypeCurrency), CatalogTypeCurrency.USD), root.get(priceTotalUsd))
				.when(cb.equal(root.get(TransactionIssueEntity_.catalogBrokerEntity).get(CatalogBrokerEntity_.idTypeCurrency), CatalogTypeCurrency.MXN), root.get(priceTotalMxn))
				.otherwise(root.get(priceTotalUsd));
	}
	
	private Expression<Object> getSelectedPrice(CriteriaBuilder cb, Root<TransactionIssueEntity> root, boolean isSell) {
		
		SingularAttribute<TransactionIssueEntity, BigDecimal> priceTotalUsd = isSell ? TransactionIssueEntity_.priceSell : TransactionIssueEntity_.priceBuy;
		SingularAttribute<TransactionIssueEntity, BigDecimal> priceTotalMxn = isSell ? TransactionIssueEntity_.priceSellMxn : TransactionIssueEntity_.priceBuyMxn;
		
		return cb.selectCase()
				.when(cb.equal(root.get(TransactionIssueEntity_.catalogBrokerEntity).get(CatalogBrokerEntity_.idTypeCurrency), CatalogTypeCurrency.USD), root.get(priceTotalUsd))
				.when(cb.equal(root.get(TransactionIssueEntity_.catalogBrokerEntity).get(CatalogBrokerEntity_.idTypeCurrency), CatalogTypeCurrency.MXN), root.get(priceTotalMxn))
				.otherwise(root.get(priceTotalUsd));
	}
	
	public TransactionIssueEntity findTransactionIssueBuy(Integer idUser, Integer idIssue, Integer idBroker, LocalDateTime date) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TransactionIssueEntity> cq = cb.createQuery(TransactionIssueEntity.class);
		Root<TransactionIssueEntity> root = cq.from(TransactionIssueEntity.class);
		
		Predicate predicateAnd = cb.and( 
			cb.equal(root.get(TransactionIssueEntity_.idIssue), idIssue),
			cb.equal(root.get(TransactionIssueEntity_.idUser), idUser),
			cb.equal(root.get(TransactionIssueEntity_.catalogBrokerEntity).get(CatalogBrokerEntity_.id), idBroker),
			cb.equal(root.get(TransactionIssueEntity_.buyDate).as(LocalDateTime.class), date));
		
		cq.where( predicateAnd );
		
		List<TransactionIssueEntity> issuesFundsTransactionsEntities = em.createQuery(cq).setMaxResults(1).getResultList();
		if (!issuesFundsTransactionsEntities.isEmpty())
			return issuesFundsTransactionsEntities.get(0);
		else
			return null;
	}
	
	public TransactionIssueEntity findTransactionIssueSell(Integer idUser, Integer idIssue, int idBroker, LocalDateTime date) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TransactionIssueEntity> cq = cb.createQuery(TransactionIssueEntity.class);
		Root<TransactionIssueEntity> root = cq.from(TransactionIssueEntity.class);
		
		Predicate predicateAnd = cb.and( 
			cb.equal(root.get(TransactionIssueEntity_.idIssue), idIssue),
			cb.equal(root.get(TransactionIssueEntity_.idUser), idUser),
			cb.equal(root.get(TransactionIssueEntity_.catalogBrokerEntity).get(CatalogBrokerEntity_.id), idBroker),
			cb.equal(root.get(TransactionIssueEntity_.sellDate).as(LocalDateTime.class), date));
		
		cq.where( predicateAnd );
		
		List<TransactionIssueEntity> transactionIssueEntities = em.createQuery(cq).setMaxResults(1).getResultList();
		if (!transactionIssueEntities.isEmpty())
			return transactionIssueEntities.get(0);
		else
			return null;
	}

	public List<IssueTransactionsByDateTuplePojo> findIssueTransactionsBuys(Integer idUser, Integer idIssue, LocalDateTime startDate) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<IssueTransactionsByDateTuplePojo> cq = cb.createQuery(IssueTransactionsByDateTuplePojo.class);
		Root<TransactionIssueEntity> root = cq.from(TransactionIssueEntity.class);

		List<Predicate> predicatesAnd = new ArrayList<>();

		predicatesAnd.add(cb.equal(root.get(TransactionIssueEntity_.idIssue), idIssue));
		predicatesAnd.add(cb.equal(root.get(TransactionIssueEntity_.idUser), idUser));
		predicatesAnd.add(cb.greaterThanOrEqualTo(root.get(TransactionIssueEntity_.buyDate), startDate));
		
		Expression<Object> selectedPriceBuy = getSelectedPrice(cb, root, false);
		Expression<Object> selectedPriceSell = getSelectedPrice(cb, root, true);
		Expression<Object> selectedPriceTotalBuy = getSelectedPriceTotal(cb, root, false);
		Expression<Object> selectedPriceTotalSell = getSelectedPriceTotal(cb, root, true);
		
		
		cq.select(cb.construct(IssueTransactionsByDateTuplePojo.class,
				cb.prod(cb.function(CatalogsStaticData.StaticSql.UNIX_TIMESTAMP,Long.class, root.get(TransactionIssueEntity_.buyDate)), 1000L),
				cb.prod(cb.function(CatalogsStaticData.StaticSql.UNIX_TIMESTAMP,Long.class, root.get(TransactionIssueEntity_.sellDate)), 1000L),
				cb.sum(root.get(TransactionIssueEntity_.totalShares)),
				selectedPriceBuy,
				cb.sum(selectedPriceTotalBuy.as(BigDecimal.class)),
				selectedPriceSell,
				cb.sum(selectedPriceTotalSell.as(BigDecimal.class)),
				root.get(TransactionIssueEntity_.catalogBrokerEntity).get(CatalogBrokerEntity_.acronym),
				root.get(TransactionIssueEntity_.catalogBrokerEntity).get(CatalogBrokerEntity_.catalogTypeCurrencyEntity).get(CatalogTypeCurrencyEntity_.description)));

		cq.groupBy(root.get(TransactionIssueEntity_.idBroker), root.get(TransactionIssueEntity_.buyDate), root.get(TransactionIssueEntity_.sellDate), root.get(TransactionIssueEntity_.priceBuy), root.get(TransactionIssueEntity_.priceSell));
		cq.orderBy(cb.asc(root.get(TransactionIssueEntity_.buyDate)));

		cq.where(predicatesAnd.toArray(new Predicate[0]));

		return em.createQuery(cq).getResultList();
	}

	public List<IssueTransactionResumeTuplePojo> findIssueTransactionsResume(Integer idUser, Integer idIssue) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> cq = cb.createTupleQuery();
		Root<TransactionIssueEntity> root = cq.from(TransactionIssueEntity.class);

		Join<TransactionIssueEntity, CatalogBrokerEntity> joinBroker = root.join(TransactionIssueEntity_.catalogBrokerEntity, JoinType.LEFT);
		Join<CatalogBrokerEntity, CatalogTypeCurrencyEntity> joinTypeCurrency = joinBroker.join(CatalogBrokerEntity_.catalogTypeCurrencyEntity, JoinType.LEFT);
		
		Expression<Object> selectedPriceTotalBuy = getSelectedPriceTotal(cb, root, false);
		Expression<Object> selectedPriceTotalSell = getSelectedPriceTotal(cb, root, true);

		cq.multiselect(joinBroker.get(CatalogBrokerEntity_.id), joinBroker.get(CatalogBrokerEntity_.acronym),
				joinTypeCurrency.get(CatalogTypeCurrencyEntity_.id),
				joinTypeCurrency.get(CatalogTypeCurrencyEntity_.description),
				cb.sum(root.get(TransactionIssueEntity_.totalShares)), selectedPriceTotalBuy,
				cb.sum(selectedPriceTotalBuy.as(BigDecimal.class)),
				cb.prod(cb.function(CatalogsStaticData.StaticSql.UNIX_TIMESTAMP, Long.class, root.get(TransactionIssueEntity_.buyDate)), 1000L),
				selectedPriceTotalSell,
				cb.sum(selectedPriceTotalSell.as(BigDecimal.class)),
				cb.prod(cb.function(CatalogsStaticData.StaticSql.UNIX_TIMESTAMP, Long.class, root.get(TransactionIssueEntity_.sellDate)), 1000L),
				root.get(TransactionIssueEntity_.sellGainLossPercentage),
				root.get(TransactionIssueEntity_.sellGainLossTotal));

		List<Predicate> predicatesAnd = new ArrayList<>();

		predicatesAnd.add(cb.equal(root.get(TransactionIssueEntity_.idIssue), idIssue));
		predicatesAnd.add(cb.equal(root.get(TransactionIssueEntity_.idUser), idUser));

		cq.groupBy(joinBroker.get(CatalogBrokerEntity_.id), root.get(TransactionIssueEntity_.buyDate),
				root.get(TransactionIssueEntity_.priceTotalBuy), root.get(TransactionIssueEntity_.sellDate),
				root.get(TransactionIssueEntity_.priceTotalSell));
		cq.orderBy(cb.asc(root.get(TransactionIssueEntity_.buyDate)));

		cq.where(predicatesAnd.toArray(new Predicate[0]));

		List<Tuple> tuples = em.createQuery(cq).getResultList();

		List<IssueTransactionResumeTuplePojo> issueTransactionResumeTuplePojos = new ArrayList<>();

		for (Tuple tuple : tuples) {

			IssueTransactionResumeTuplePojo issueTransactionResumeTuplePojo = new IssueTransactionResumeTuplePojo();

			issueTransactionResumeTuplePojo.setIdBroker((Integer) tuple.get(0));
			issueTransactionResumeTuplePojo.setDescriptionBroker((String) tuple.get(1));
			issueTransactionResumeTuplePojo.setIdTypeCurrency((Integer) tuple.get(2));
			issueTransactionResumeTuplePojo.setDescriptionTypeCurrency((String) tuple.get(3));
			issueTransactionResumeTuplePojo.setTotalShares((BigDecimal) tuple.get(4));
			issueTransactionResumeTuplePojo.setPriceBuy((BigDecimal) tuple.get(5));
			issueTransactionResumeTuplePojo.setSumPriceBuy((BigDecimal) tuple.get(6));
			issueTransactionResumeTuplePojo.setBuyDate((Long) tuple.get(7));
			issueTransactionResumeTuplePojo.setPriceSell((BigDecimal) tuple.get(8));
			issueTransactionResumeTuplePojo.setSumPriceSell((BigDecimal) tuple.get(9));
			issueTransactionResumeTuplePojo.setSellDate((Long) tuple.get(10));
			issueTransactionResumeTuplePojo.setSellGainLossPercentage((BigDecimal) tuple.get(11));
			issueTransactionResumeTuplePojo.setSellGainLossTotal((BigDecimal) tuple.get(12));

			issueTransactionResumeTuplePojos.add(issueTransactionResumeTuplePojo);
		}

		return issueTransactionResumeTuplePojos;
	}
	
	public BigDecimal findTotalBuys(int idBroker, int idTypeCurrency, int idUser, boolean isSold) {
		
		SingularAttribute<TransactionIssueEntity, BigDecimal> priceTotalBuy = null;
		SingularAttribute<TransactionIssueEntity, BigDecimal> priceTotalSell = null;
		
		if (idTypeCurrency == CatalogTypeCurrency.USD) {
			priceTotalBuy = TransactionIssueEntity_.priceTotalBuy;
			priceTotalSell = TransactionIssueEntity_.priceTotalSell;
		}
		else if (idTypeCurrency == CatalogTypeCurrency.MXN) {
			priceTotalBuy = TransactionIssueEntity_.priceTotalBuyMxn;
			priceTotalSell = TransactionIssueEntity_.priceTotalSellMxn;
		}
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> cq = cb.createQuery(BigDecimal.class);
		Root<TransactionIssueEntity> root = cq.from(TransactionIssueEntity.class);
		cq.select(cb.sum(root.<BigDecimal>get(priceTotalBuy)));
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(root.get(TransactionIssueEntity_.idBroker), idBroker));
		predicatesAnd.add(cb.equal(root.get(TransactionIssueEntity_.idUser), idUser));
		
		if (isSold) {
			predicatesAnd.add(cb.isNotNull(root.get(priceTotalSell)));
		}
		else {
			predicatesAnd.add(cb.isNull(root.get(priceTotalSell)));
		}

		cq.where(predicatesAnd.toArray(new Predicate[0]));

		TypedQuery<BigDecimal> typedQuery = em.createQuery(cq);
		return typedQuery.getSingleResult() == null ? BigDecimal.ZERO : typedQuery.getSingleResult();
	}
	
	public BigDecimal findTotalSells(int idBroker, int idTypeCurrency, int idUser, boolean isSold) {
		
		SingularAttribute<TransactionIssueEntity, BigDecimal> priceTotalSell = null;
		BigDecimal dollarPrice = new BigDecimal(1);
		
		if (idTypeCurrency == CatalogTypeCurrency.USD) {
			priceTotalSell = TransactionIssueEntity_.priceTotalSell;
		}
		else if (idTypeCurrency == CatalogTypeCurrency.MXN) {
			priceTotalSell = TransactionIssueEntity_.priceTotalSellMxn;
		}
		
		if (idTypeCurrency == CatalogTypeCurrency.MXN && !isSold) {
			DollarHistoricalPriceEntity dollarHistoricalPriceEntity = dollarHistoricalPriceRepository.findLastRecord();
			dollarPrice = dollarHistoricalPriceEntity.getPrice();
		}

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> cq = cb.createQuery(BigDecimal.class);
		Root<TransactionIssueEntity> root = cq.from(TransactionIssueEntity.class);
		
		if (isSold) {
			cq.select(cb.sum(root.<BigDecimal>get(priceTotalSell)));
		}
		else {
			cq.select(cb.sum(root.get(TransactionIssueEntity_.issuesLastPriceTmpEntity).get(IssuesLastPriceTmpEntity_.last)));
		}

		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(root.get(TransactionIssueEntity_.idBroker), idBroker));
		predicatesAnd.add(cb.equal(root.get(TransactionIssueEntity_.idUser), idUser));
		
		if (isSold) {
			predicatesAnd.add(cb.isNotNull(root.get(priceTotalSell)));
		}
		else {
			predicatesAnd.add(cb.isNull(root.get(priceTotalSell)));
		}

		cq.where(predicatesAnd.toArray(new Predicate[0]));

		TypedQuery<BigDecimal> typedQuery = em.createQuery(cq);
		return typedQuery.getSingleResult() == null ? BigDecimal.ZERO : typedQuery.getSingleResult().multiply(dollarPrice);
	}
	
	@SuppressWarnings("unchecked")
	public List<PorfolioIssuePojo> findPortfolioIssues(Integer idUser, Integer idBroker, int idTypeCurrency, boolean isSold) {
		
		BigDecimal dollarPrice = new BigDecimal(1);
		String priceTotalBuy = null;
		String priceTotalSell = null;
		
		if (idTypeCurrency == CatalogTypeCurrency.USD) {
			priceTotalBuy = TransactionIssueEntity_.priceTotalBuy.getName();
			priceTotalSell = TransactionIssueEntity_.priceTotalSell.getName();
		}
		else if (idTypeCurrency == CatalogTypeCurrency.MXN) {
			priceTotalBuy = TransactionIssueEntity_.priceTotalBuyMxn.getName();
			priceTotalSell = TransactionIssueEntity_.priceTotalSellMxn.getName();
		}
		
		if (idTypeCurrency == CatalogTypeCurrency.MXN && !isSold) {
			
			DollarHistoricalPriceEntity dollarHistoricalPriceEntity = dollarHistoricalPriceRepository.findLastRecord();
			dollarPrice = dollarHistoricalPriceEntity.getPrice();
		}
		
		final String JOIN_TRANSACTION_ISSUE = " LEFT JOIN transactionIssue.";
		
		List<String> parameterslist = Arrays.asList(
			"transactionIssue." + TransactionIssueEntity_.idIssue.getName(),
			"catalogIssues." + CatalogIssuesEntity_.initials.getName(),
			"catalogBroker." + CatalogBrokerEntity_.idTypeCurrency.getName(),
			"SUM(transactionIssue." + TransactionIssueEntity_.totalShares.getName() + ")",
			"SUM(transactionIssue." + priceTotalBuy + ") / SUM(transactionIssue." + TransactionIssueEntity_.totalShares.getName() + ") AS costAverageBuy",
			"SUM(CASE WHEN :isSold = true THEN transactionIssue." + priceTotalSell + 
	        " ELSE (issuesLastPriceTmp." + IssuesLastPriceTmpEntity_.last.getName() + ") * CAST(:dollarPrice as DOUBLE) END) / SUM(transactionIssue." + TransactionIssueEntity_.totalShares.getName() + ") AS costAverageSell",
			"SUM(transactionIssue." + priceTotalBuy + ") AS costTotalBuy",
			"SUM(CASE WHEN :isSold = true THEN transactionIssue." + priceTotalSell + 
	        " ELSE (issuesLastPriceTmp." + IssuesLastPriceTmpEntity_.last.getName() + ") * CAST(:dollarPrice as DOUBLE) END) AS costTotalSell",
			"issuesLastPriceTmp." + IssuesLastPriceTmpEntity_.timestamp.getName()
		);
		
		List<String> parametersGroupBylist = Arrays.asList(
				"transactionIssue." + TransactionIssueEntity_.idIssue.getName(),
				"catalogBroker." + CatalogBrokerEntity_.idTypeCurrency.getName()
			);
		
		Query query = em.createQuery(StringEscapeUtils.escapeSql("SELECT " + String.join(", ", parameterslist) +
    		" FROM " + jpaUtil.getTableMetaModel(TransactionIssueEntity_.class) + " transactionIssue" +
    		JOIN_TRANSACTION_ISSUE + TransactionIssueEntity_.issuesLastPriceTmpEntity.getName() + " issuesLastPriceTmp" +
    		JOIN_TRANSACTION_ISSUE + TransactionIssueEntity_.issuesManagerEntity.getName() + " managerIssues" +
    		JOIN_TRANSACTION_ISSUE + TransactionIssueEntity_.catalogBrokerEntity.getName() + " catalogBroker" +
    		" LEFT JOIN managerIssues." + IssuesManagerEntity_.catalogIssueEntity.getName() + " catalogIssues" +
    		" WHERE transactionIssue." + TransactionIssueEntity_.priceTotalSell.getName() + (isSold ? " IS NOT NULL" : " IS NULL") +
    		" AND transactionIssue." + TransactionIssueEntity_.idUser.getName() + " = :idUser" +
    		" AND transactionIssue." + TransactionIssueEntity_.catalogBrokerEntity.getName() + "." + CatalogBrokerEntity_.id.getName() + " = :idBroker GROUP BY " + String.join(", ", parametersGroupBylist)));
		
		query.setParameter("idBroker", idBroker);
		query.setParameter("isSold", isSold);
		query.setParameter("dollarPrice", dollarPrice);
		query.setParameter("idUser", idUser);
		
		List<Object[]> results = query.getResultList();
		
		List<PorfolioIssuePojo> issuePorfolioEntityPojos = new ArrayList<>();
		
		for (Object[] result: results) {
			
			PorfolioIssuePojo porfolioIssueEntityPojo = new PorfolioIssuePojo();
			
			porfolioIssueEntityPojo.setIdIssue(Integer.parseInt(result[0].toString()));
			porfolioIssueEntityPojo.setInitials(result[1].toString());
			porfolioIssueEntityPojo.setIdTypeCurrency(dataParseUtil.parseInteger(result[2]));
			porfolioIssueEntityPojo.setTitles(dataParseUtil.parseBigDecimal(result[3]));
			porfolioIssueEntityPojo.setCostAvgBuyPerTitle(dataParseUtil.parseBigDecimal(result[4]));
			porfolioIssueEntityPojo.setCostAvgSellPerTitle(dataParseUtil.parseBigDecimal(result[5]));
			porfolioIssueEntityPojo.setCostTotalBuy(dataParseUtil.parseBigDecimal(result[6]));
			porfolioIssueEntityPojo.setCostTotalSell(dataParseUtil.parseBigDecimal(result[7]));
			porfolioIssueEntityPojo.setLastUpdate(dataParseUtil.parseDate(result[8]));
			porfolioIssueEntityPojo.setTotalGainLoss(porfolioIssueEntityPojo.getCostTotalSell() != null ? porfolioIssueEntityPojo.getCostTotalSell().subtract(porfolioIssueEntityPojo.getCostTotalBuy()) : BigDecimal.ZERO);
			porfolioIssueEntityPojo.setTotalYield(calculatorUtil.calculatePercentageUpDown(porfolioIssueEntityPojo.getCostTotalBuy(), porfolioIssueEntityPojo.getCostTotalSell()));
			
			issuePorfolioEntityPojos.add(porfolioIssueEntityPojo);
		}
		
		return issuePorfolioEntityPojos;
	}
	
	@SuppressWarnings("unchecked")
	public List<TransactionIssueNotSoldPojo> findTransactionIssuesNotSoldList(Integer idUser) {
		
		List<String> parameterslist = Arrays.asList(
			"ift." + TransactionIssueEntity_.idIssue.getName(),
			"ift." + TransactionIssueEntity_.buyDate.getName(),
			"ift." + TransactionIssueEntity_.priceBuy.getName(),
			"ift." + TransactionIssueEntity_.commisionPercentage.getName(),
			"ift." + TransactionIssueEntity_.priceTotalBuy.getName(),
			"ift." + TransactionIssueEntity_.idBroker.getName(),
			"catalogIssue." + CatalogIssuesEntity_.initials.getName()
		);
		
		Query query = em.createQuery(StringEscapeUtils.escapeSql("SELECT " + String.join(", ", parameterslist) + ", COUNT(*) AS totalTitles "
	        			+ "FROM " + jpaUtil.getTableMetaModel(TransactionIssueEntity_.class) + " ift"
	        			+ " LEFT JOIN ift." + TransactionIssueEntity_.issuesManagerEntity.getName() + " managerIssue"
	        			+ " LEFT JOIN managerIssue." + IssuesManagerEntity_.catalogIssueEntity.getName() + " catalogIssue"
	        			+ " WHERE ift." + TransactionIssueEntity_.idUser.getName() + " = :idUser"
	        			+ " AND ift." + TransactionIssueEntity_.priceSell.getName() + " IS NULL GROUP BY " + String.join(", ", parameterslist)));
		
		query.setParameter("idUser", idUser);
		
		List<Object[]> results = query.getResultList();
		
		List<TransactionIssueNotSoldPojo> transactionsNotSoldCustomPojos = new ArrayList<>();
		
		for (Object[] object: results) {
			
			TransactionIssueNotSoldPojo transactionsNotSoldCustomPojo = new TransactionIssueNotSoldPojo();
			transactionsNotSoldCustomPojo.setIdIssue((int) object[0]);
			transactionsNotSoldCustomPojo.setIdDate((LocalDateTime) object[1]);
			transactionsNotSoldCustomPojo.setPriceBuy((BigDecimal) object[2]);
			transactionsNotSoldCustomPojo.setCommisionPercentage((BigDecimal) object[3]);
			transactionsNotSoldCustomPojo.setPriceTotalBuy((BigDecimal) object[4]);
			transactionsNotSoldCustomPojo.setIdBroker(Integer.parseInt(object[5].toString()));
			transactionsNotSoldCustomPojo.setIssue(object[6].toString());
			transactionsNotSoldCustomPojo.setTotalTitles((Long) object[7]);
			
			transactionsNotSoldCustomPojos.add(transactionsNotSoldCustomPojo);
		}
		
		return transactionsNotSoldCustomPojos;
	}
	
	public List<TransactionIssueEntity> findTransactionIssueShortSells(Integer idUser, Integer idIssue, int idBroker) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TransactionIssueEntity> cq = cb.createQuery(TransactionIssueEntity.class);
		Root<TransactionIssueEntity> root = cq.from(TransactionIssueEntity.class);
		
		Predicate predicateAnd = cb.and( 
			cb.equal(root.get(TransactionIssueEntity_.idIssue), idIssue),
			cb.equal(root.get(TransactionIssueEntity_.idUser), idUser),
			cb.equal(root.get(TransactionIssueEntity_.catalogBrokerEntity).get(CatalogBrokerEntity_.id), idBroker),
			cb.equal(root.get(TransactionIssueEntity_.isShortSell), true),
			cb.isNull(root.get(TransactionIssueEntity_.priceBuy)));
		
		cq.where( predicateAnd );
		
		return em.createQuery(cq).getResultList();
	}
	
	public List<TransactionIssueEntity> findTransactionIssuesNotSoldLower(Integer idUser, Integer idIssue, int idBroker, boolean isSlice) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TransactionIssueEntity> cq = cb.createQuery(TransactionIssueEntity.class);
		Root<TransactionIssueEntity> root = cq.from(TransactionIssueEntity.class);
		
		Predicate predicateAnd = cb.and( 
			cb.equal(root.get(TransactionIssueEntity_.idIssue), idIssue),
			cb.equal(root.get(TransactionIssueEntity_.idUser), idUser),
			cb.equal(root.get(TransactionIssueEntity_.catalogBrokerEntity).get(CatalogBrokerEntity_.id), idBroker),
			cb.equal(root.get(TransactionIssueEntity_.isSlice), isSlice),
			cb.isNull(root.get(TransactionIssueEntity_.sellDate)));
		
		cq.where( predicateAnd );
		cq.orderBy(cb.asc(root.get(TransactionIssueEntity_.priceBuy)));
		
		return em.createQuery(cq).getResultList();
	}
}
