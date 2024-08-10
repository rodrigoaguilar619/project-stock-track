package project.stock.track.modules.business.files.transactions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import lib.base.backend.utils.NumberDataUtil;
import lib.base.backend.utils.date.DateFormatUtil;
import project.stock.track.ProjectUnitTest;
import project.stock.track.app.beans.pojos.business.transaction.TransactionIssueFilePojo;

class ReadCsvTransactionIssuesBrokerSchwabTest extends ProjectUnitTest {

	@Mock
    private DateFormatUtil dateFormatUtil;

    @Mock
    private NumberDataUtil numberDataUtil;

    @InjectMocks
    private ReadCsvTransactionIssuesBrokerSchwab readCsvTransactionIssuesBrokerSchwab;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReadCsvFile() throws ParseException, IOException {
    	
    	List<List<String>> records = Arrays.asList(
    		    Arrays.asList("Date", "Action", "Symbol", "Description", "Quantity", "Price", "Fees & Comm", "Amount"),
    		    Arrays.asList("05/20/2024", "Buy", "ALK", "ALASKA AIR GROUP INC", "2", "$43.9199", "", "-$87.84")
    		);

        when(dateFormatUtil.formatDate(anyString(), anyString())).thenReturn(new java.util.Date(1700000000000L));
        when(numberDataUtil.hasFractionalPart(anyDouble())).thenReturn(false);

        List<TransactionIssueFilePojo> result = readCsvTransactionIssuesBrokerSchwab.readCsvFile(records);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ALK", result.get(0).getIssue());
        assertEquals(ReadCsvTransactionIssuesBrokerSchwab.TYPE_TRANSACTION_BUY, result.get(0).getTypeTransaction());
        assertEquals(BigDecimal.valueOf(43.9199), result.get(0).getPrice());
    }

}
