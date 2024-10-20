package project.stock.track.app.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lib.base.backend.utils.DataParseUtil;
import lib.base.backend.utils.JpaUtil;
import project.stock.track.app.beans.entity.CatalogBrokerEntity;
import project.stock.track.app.beans.entity.CatalogBrokerEntity_;
import project.stock.track.app.beans.entity.CatalogIssuesEntity_;
import project.stock.track.app.beans.entity.CatalogTypeCurrencyEntity_;
import project.stock.track.app.beans.entity.IssuesLastPriceTmpEntity;
import project.stock.track.app.beans.entity.IssuesLastPriceTmpEntity_;
import project.stock.track.app.beans.entity.IssuesManagerEntity_;
import project.stock.track.app.beans.entity.TransactionIssueEntity;
import project.stock.track.app.beans.entity.TransactionIssueEntity_;
import project.stock.track.app.beans.pojos.business.portfolio.PorfolioIssuePojo;
import project.stock.track.app.beans.pojos.business.transaction.TransactionIssueNotSoldPojo;
import project.stock.track.app.beans.pojos.tuple.IssueTransactionResumeTuplePojo;
import project.stock.track.app.beans.pojos.tuple.IssueTransactionsByDateTuplePojo;
import project.stock.track.app.utils.CalculatorUtil;
import project.stock.track.app.vo.catalogs.CatalogsEntity;
import project.stock.track.app.vo.catalogs.CatalogsEntity.CatalogTypeCurrency;
import project.stock.track.app.vo.catalogs.CatalogsStaticData;

@Repository
public class TransactionIssueRepositoryImpl {

	EntityManager em;
	
	private JpaUtil jpaUtil = new JpaUtil();
	private CalculatorUtil calculatorUtil = new CalculatorUtil();
	private DataParseUtil dataParseUtil = new DataParseUtil();
	
	@Autowired
	public TransactionIssueRepositoryImpl(EntityManager em) {
		this.em = em;
	}
	
	public TransactionIssueEntity findTransactionIssueBuy(Integer idUser, Integer idIssue, Integer idBroker, Date date) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TransactionIssueEntity> cq = cb.createQuery(TransactionIssueEntity.class);
		Root<TransactionIssueEntity> root = cq.from(TransactionIssueEntity.class);
		
		Predicate predicateAnd = cb.and( 
			cb.equal(root.get(TransactionIssueEntity_.idIssue), idIssue),
			cb.equal(root.get(TransactionIssueEntity_.idUser), idUser),
			cb.equal(root.get(TransactionIssueEntity_.catalogBrokerEntity).get(CatalogBrokerEntity_.ID), idBroker),
			cb.equal(root.get(TransactionIssueEntity_.idDate).as(Date.class), date));
		
		cq.where( predicateAnd );
		
		List<TransactionIssueEntity> issuesFundsTransactionsEntities = em.createQuery(cq).setMaxResults(1).getResultList();
		if (!issuesFundsTransactionsEntities.isEmpty())
			return issuesFundsTransactionsEntities.get(0);
		else
			return null;
	}
	
	public TransactionIssueEntity findTransactionIssueSell(Integer idUser, Integer idIssue, int idBroker, Date date) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TransactionIssueEntity> cq = cb.createQuery(TransactionIssueEntity.class);
		Root<TransactionIssueEntity> root = cq.from(TransactionIssueEntity.class);
		
		Predicate predicateAnd = cb.and( 
			cb.equal(root.get(TransactionIssueEntity_.idIssue), idIssue),
			cb.equal(root.get(TransactionIssueEntity_.idUser), idUser),
			cb.equal(root.get(TransactionIssueEntity_.catalogBrokerEntity).get(CatalogBrokerEntity_.ID), idBroker),
			cb.equal(root.get(TransactionIssueEntity_.sellDate).as(Date.class), date));
		
		cq.where( predicateAnd );
		
		List<TransactionIssueEntity> transactionIssueEntities = em.createQuery(cq).setMaxResults(1).getResultList();
		if (!transactionIssueEntities.isEmpty())
			return transactionIssueEntities.get(0);
		else
			return null;
	}

	public List<IssueTransactionsByDateTuplePojo> findIssueTransactionsBuys(Integer idUser, Integer idIssue, Date startDate) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<IssueTransactionsByDateTuplePojo> cq = cb.createQuery(IssueTransactionsByDateTuplePojo.class);
		Root<TransactionIssueEntity> root = cq.from(TransactionIssueEntity.class);

		List<Predicate> predicatesAnd = new ArrayList<>();

		predicatesAnd.add(cb.equal(root.get(TransactionIssueEntity_.idIssue), idIssue));
		predicatesAnd.add(cb.equal(root.get(TransactionIssueEntity_.idUser), idUser));
		predicatesAnd.add(cb.greaterThanOrEqualTo(root.get(TransactionIssueEntity_.idDate), startDate));
		
		cq.select(cb.construct(IssueTransactionsByDateTuplePojo.class,
				cb.prod(cb.function(CatalogsStaticData.StaticSql.UNIX_TIMESTAMP,Long.class, root.get(TransactionIssueEntity_.idDate)), 1000L),
				cb.prod(cb.function(CatalogsStaticData.StaticSql.UNIX_TIMESTAMP,Long.class, root.get(TransactionIssueEntity_.sellDate)), 1000L),
				cb.sum(root.get(TransactionIssueEntity_.totalShares)),
				root.get(TransactionIssueEntity_.priceTotalBuy),
				root.get(TransactionIssueEntity_.priceTotalSell),
				root.get(TransactionIssueEntity_.catalogBrokerEntity).get(CatalogBrokerEntity_.acronym)));

		cq.groupBy(root.get(TransactionIssueEntity_.idDate), root.get(TransactionIssueEntity_.sellDate), root.get(TransactionIssueEntity_.priceTotalBuy), root.get(TransactionIssueEntity_.priceTotalSell));
		cq.orderBy(cb.asc(root.get(TransactionIssueEntity_.idDate)));

		cq.where(predicatesAnd.toArray(new Predicate[0]));

		return em.createQuery(cq).getResultList();
	}

	public List<IssueTransactionResumeTuplePojo> findIssueTransactionsResume(Integer idUser, Integer idIssue) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> cq = cb.createTupleQuery();
		Root<TransactionIssueEntity> root = cq.from(TransactionIssueEntity.class);

		Join<TransactionIssueEntity, CatalogBrokerEntity> joinBroker = root
				.join(TransactionIssueEntity_.CATALOG_BROKER_ENTITY, JoinType.LEFT);
		Join<CatalogBrokerEntity, CatalogTypeCurrency> joinTypeCurrency = joinBroker
				.join(CatalogBrokerEntity_.CATALOG_TYPE_CURRENCY_ENTITY, JoinType.LEFT);

		cq.multiselect(joinBroker.get(CatalogBrokerEntity_.ID), joinBroker.get(CatalogBrokerEntity_.ACRONYM),
				joinTypeCurrency.get(CatalogTypeCurrencyEntity_.ID),
				joinTypeCurrency.get(CatalogTypeCurrencyEntity_.DESCRIPTION),
				cb.sum(root.get(TransactionIssueEntity_.totalShares)), root.get(TransactionIssueEntity_.priceTotalBuy),
				cb.sum(root.get(TransactionIssueEntity_.priceTotalBuy)),
				cb.prod(cb.function(CatalogsStaticData.StaticSql.UNIX_TIMESTAMP, Long.class, root.get(TransactionIssueEntity_.idDate)), 1000L),
				root.get(TransactionIssueEntity_.priceTotalSell),
				cb.sum(root.get(TransactionIssueEntity_.priceTotalSell)),
				cb.prod(cb.function(CatalogsStaticData.StaticSql.UNIX_TIMESTAMP, Long.class, root.get(TransactionIssueEntity_.sellDate)), 1000L),
				root.get(TransactionIssueEntity_.SELL_GAIN_LOSS_PERCENTAGE),
				root.get(TransactionIssueEntity_.SELL_GAIN_LOSS_TOTAL));

		List<Predicate> predicatesAnd = new ArrayList<>();

		predicatesAnd.add(cb.equal(root.get(TransactionIssueEntity_.idIssue), idIssue));
		predicatesAnd.add(cb.equal(root.get(TransactionIssueEntity_.idUser), idUser));

		cq.groupBy(joinBroker.get(CatalogBrokerEntity_.ID), root.get(TransactionIssueEntity_.idDate),
				root.get(TransactionIssueEntity_.priceTotalBuy), root.get(TransactionIssueEntity_.sellDate),
				root.get(TransactionIssueEntity_.priceTotalSell));
		cq.orderBy(cb.asc(root.get(TransactionIssueEntity_.idDate)));

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

	// query made from the cost of buy
	public BigDecimal findBuyValueBuysNotSold(int idBroker, int idUser) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> cq = cb.createQuery(BigDecimal.class);
		Root<TransactionIssueEntity> root = cq.from(TransactionIssueEntity.class);
		cq.select(cb.sum(root.<BigDecimal>get(TransactionIssueEntity_.priceTotalBuy)));

		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(root.get(TransactionIssueEntity_.ID_BROKER), idBroker));
		predicatesAnd.add(cb.equal(root.get(TransactionIssueEntity_.ID_USER), idUser));
		predicatesAnd.add(cb.isNull(root.get(TransactionIssueEntity_.priceTotalSell)));

		cq.where(predicatesAnd.toArray(new Predicate[0]));

		TypedQuery<BigDecimal> typedQuery = em.createQuery(cq);
		return typedQuery.getSingleResult() == null ? BigDecimal.ZERO : typedQuery.getSingleResult();
	}

	// query made from the cost of current issue value
	public BigDecimal findCurrentValueBuysNotSold(int idBroker, int idUser) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> cq = cb.createQuery(BigDecimal.class);
		Root<TransactionIssueEntity> root = cq.from(TransactionIssueEntity.class);

		Join<TransactionIssueEntity, IssuesLastPriceTmpEntity> joinIssuesLastPriceTmp = root
				.join(TransactionIssueEntity_.issuesLastPriceTmpEntity, JoinType.LEFT);

		cq.select(cb.sum(joinIssuesLastPriceTmp.<BigDecimal>get(IssuesLastPriceTmpEntity_.last)));

		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(root.get(TransactionIssueEntity_.idBroker), idBroker));
		predicatesAnd.add(cb.equal(root.get(TransactionIssueEntity_.ID_USER), idUser));
		predicatesAnd.add(cb.isNull(root.get(TransactionIssueEntity_.priceTotalSell)));

		cq.where(predicatesAnd.toArray(new Predicate[0]));

		TypedQuery<BigDecimal> typedQuery = em.createQuery(cq);
		return typedQuery.getSingleResult() == null ? BigDecimal.ZERO : typedQuery.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<PorfolioIssuePojo> findPortfolioIssues(Integer idUser, Integer idBroker, BigDecimal dollarPrice/*, Integer statusIssue*/) {
		
		final String JOIN_TRANSACTION_ISSUE = " LEFT JOIN transactionIssue.";
		
		List<String> parameterslist = Arrays.asList(
			"transactionIssue." + TransactionIssueEntity_.ID_ISSUE,
			"catalogBroker." + CatalogBrokerEntity_.ID_TYPE_CURRENCY,
			"COUNT(transactionIssue." + TransactionIssueEntity_.ID_ISSUE + ")",
			"SUM(transactionIssue." + TransactionIssueEntity_.PRICE_TOTAL_BUY + ") / COUNT(transactionIssue." + TransactionIssueEntity_.ID_ISSUE + ") AS costAverageBuy",
			"SUM(issuesLastPriceTmp. " + IssuesLastPriceTmpEntity_.LAST + ") / COUNT(transactionIssue." + TransactionIssueEntity_.ID_ISSUE + ") AS costAverageSell",
			"SUM(issuesLastPriceTmp." + IssuesLastPriceTmpEntity_.LAST + ") * :dollarPrice / COUNT(transactionIssue." + TransactionIssueEntity_.ID_ISSUE + ") AS costAverageSellMxn",
			"SUM(transactionIssue." + TransactionIssueEntity_.PRICE_TOTAL_BUY + ") AS costTotalBuy, SUM(issuesLastPriceTmp." + IssuesLastPriceTmpEntity_.LAST +") AS costTotalSell",
			"SUM(issuesLastPriceTmp." + IssuesLastPriceTmpEntity_.LAST + ") * :dollarPrice AS costTotalSellMxn, issuesLastPriceTmp." + IssuesLastPriceTmpEntity_.TIMESTAMP,
			"catalogIssues." + CatalogIssuesEntity_.INITIALS
		);
		
		List<String> parametersGroupBylist = Arrays.asList(
				"transactionIssue." + TransactionIssueEntity_.ID_ISSUE,
				"catalogBroker." + CatalogBrokerEntity_.ID_TYPE_CURRENCY
			);
		
		Query query = em.createQuery(StringEscapeUtils.escapeSql("SELECT " + String.join(", ", parameterslist) +
    		" FROM " + jpaUtil.getTableMetaModel(TransactionIssueEntity_.class) + " transactionIssue" +
    		JOIN_TRANSACTION_ISSUE + TransactionIssueEntity_.ISSUES_LAST_PRICE_TMP_ENTITY + " issuesLastPriceTmp" +
    		JOIN_TRANSACTION_ISSUE + TransactionIssueEntity_.ISSUES_MANAGER_ENTITY + " managerIssues" +
    		JOIN_TRANSACTION_ISSUE + TransactionIssueEntity_.CATALOG_BROKER_ENTITY + " catalogBroker" +
    		" LEFT JOIN managerIssues." + IssuesManagerEntity_.CATALOG_ISSUES_ENTITY + " catalogIssues" +
    		" WHERE transactionIssue." + TransactionIssueEntity_.PRICE_TOTAL_SELL + " IS NULL" +
    		" AND transactionIssue." + TransactionIssueEntity_.ID_USER + " = :idUser" +
    		" AND transactionIssue." + TransactionIssueEntity_.CATALOG_BROKER_ENTITY + "." + CatalogBrokerEntity_.ID + " = :idBroker GROUP BY " + String.join(", ", parametersGroupBylist)));
		
		query.setParameter("idBroker", idBroker);
		query.setParameter("dollarPrice", dollarPrice);
		query.setParameter("idUser", idUser);
		
		List<Object[]> results = query.getResultList();
		
		List<PorfolioIssuePojo> issuePorfolioEntityPojos = new ArrayList<>();
		
		for (Object[] result: results) {
			
			PorfolioIssuePojo porfolioIssueEntityPojo = new PorfolioIssuePojo();
			
			porfolioIssueEntityPojo.setIdIssue(Integer.parseInt(result[0].toString()));
			porfolioIssueEntityPojo.setIdTypeCurrency(dataParseUtil.parseInteger(result[1]));
			porfolioIssueEntityPojo.setTitles(Integer.valueOf(result[2].toString()));
			porfolioIssueEntityPojo.setCostAvgBuyPerTitle(dataParseUtil.parseBigDecimal(result[3]));
			porfolioIssueEntityPojo.setCostAvgSellPerTitle(dataParseUtil.parseBigDecimal(result[4]));
			porfolioIssueEntityPojo.setCostAvgSellPerTitleMxn(dataParseUtil.parseBigDecimal(result[5]));
			porfolioIssueEntityPojo.setCostTotalBuy(dataParseUtil.parseBigDecimal(result[6]));
			porfolioIssueEntityPojo.setCostTotalSell(dataParseUtil.parseBigDecimal(result[7]));
			porfolioIssueEntityPojo.setCostTotalSellMxn(dataParseUtil.parseBigDecimal(result[8]));
			porfolioIssueEntityPojo.setLastUpdate(dataParseUtil.parseDate(result[9]));
			porfolioIssueEntityPojo.setInitials(result[10].toString());
			
			BigDecimal costAvgSellPerTitle = porfolioIssueEntityPojo.getIdTypeCurrency().equals(CatalogsEntity.CatalogTypeCurrency.MXN) ? porfolioIssueEntityPojo.getCostAvgSellPerTitleMxn() : porfolioIssueEntityPojo.getCostAvgSellPerTitle();
			porfolioIssueEntityPojo.setTotalYield(calculatorUtil.calculatePercentageUpDown(porfolioIssueEntityPojo.getCostAvgBuyPerTitle(), costAvgSellPerTitle ));
			
			issuePorfolioEntityPojos.add(porfolioIssueEntityPojo);
		}
		
		return issuePorfolioEntityPojos;
	}
	
	@SuppressWarnings("unchecked")
	public List<TransactionIssueNotSoldPojo> findTransactionIssuesNotSoldList(Integer idUser) {
		
		List<String> parameterslist = Arrays.asList(
			"ift." + TransactionIssueEntity_.ID_ISSUE,
			"ift." + TransactionIssueEntity_.ID_DATE,
			"ift." + TransactionIssueEntity_.PRICE_BUY,
			"ift." + TransactionIssueEntity_.COMMISION_PERCENTAGE,
			"ift." + TransactionIssueEntity_.PRICE_TOTAL_BUY,
			"ift." + TransactionIssueEntity_.ID_BROKER,
			"catalogIssue." + CatalogIssuesEntity_.INITIALS
		);
		
		Query query = em.createQuery(StringEscapeUtils.escapeSql("SELECT " + String.join(", ", parameterslist) + ", COUNT(*) AS totalTitles "
	        			+ "FROM " + jpaUtil.getTableMetaModel(TransactionIssueEntity_.class) + " ift"
	        			+ " LEFT JOIN ift." + TransactionIssueEntity_.ISSUES_MANAGER_ENTITY + " managerIssue"
	        			+ " LEFT JOIN managerIssue." + IssuesManagerEntity_.CATALOG_ISSUES_ENTITY + " catalogIssue"
	        			+ " WHERE ift." + TransactionIssueEntity_.ID_USER + " = :idUser"
	        			+ " AND ift." + TransactionIssueEntity_.PRICE_SELL + " IS NULL GROUP BY " + String.join(", ", parameterslist)));
		
		query.setParameter("idUser", idUser);
		
		List<Object[]> results = query.getResultList();
		
		List<TransactionIssueNotSoldPojo> transactionsNotSoldCustomPojos = new ArrayList<>();
		
		for (Object[] object: results) {
			
			TransactionIssueNotSoldPojo transactionsNotSoldCustomPojo = new TransactionIssueNotSoldPojo();
			transactionsNotSoldCustomPojo.setIdIssue((int) object[0]);
			transactionsNotSoldCustomPojo.setIdDate((Date) object[1]);
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
			cb.equal(root.get(TransactionIssueEntity_.catalogBrokerEntity).get(CatalogBrokerEntity_.ID), idBroker),
			cb.isNull(root.get(TransactionIssueEntity_.priceBuy)));
		
		cq.where( predicateAnd );
		
		return em.createQuery(cq).getResultList();
	}
	
	public List<TransactionIssueEntity> findTransactionIssuesNotSoldLower(Integer idUser, Integer idIssue, int idBroker) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TransactionIssueEntity> cq = cb.createQuery(TransactionIssueEntity.class);
		Root<TransactionIssueEntity> root = cq.from(TransactionIssueEntity.class);
		
		Predicate predicateAnd = cb.and( 
			cb.equal(root.get(TransactionIssueEntity_.idIssue), idIssue),
			cb.equal(root.get(TransactionIssueEntity_.idUser), idUser),
			cb.equal(root.get(TransactionIssueEntity_.catalogBrokerEntity).get(CatalogBrokerEntity_.ID), idBroker),
			cb.isNull(root.get(TransactionIssueEntity_.sellDate)));
		
		cq.where( predicateAnd );
		cq.orderBy(cb.asc(root.get(TransactionIssueEntity_.priceBuy)));
		
		return em.createQuery(cq).getResultList();
	}
}
