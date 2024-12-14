package project.stock.track.modules.business.files.transactions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.utils.NumberDataUtil;
import lib.base.backend.utils.date.DateFormatUtil;
import project.stock.track.ProjectUnitTest;
import project.stock.track.app.beans.pojos.business.transaction.TransactionIssueFilePojo;

class ReadCsvTransactionIssuesBrokerGbmTest extends ProjectUnitTest {

	@Mock
    private DateFormatUtil dateFormatUtil;

    @Mock
    private NumberDataUtil numberDataUtil;

    @InjectMocks
    private ReadCsvTransactionIssuesBrokerGbm readCsvTransactionIssuesBrokerGbm;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReadCsvFile() throws ParseException, IOException, BusinessException {
    	
    	List<List<String>> records = Arrays.asList(
    		    Arrays.asList("\"Emisora\"", "Fecha", "Hora", "Descripción", "Títulos", "Precio", "Tasa", "Plazo", "Intereses", "Impuestos", "Comisión", "Importe", "Saldo"),
    		    Arrays.asList("CCL", "09/mar/2022", "09:54:08", "Compra de Acciones.", "4", "$1234.56", "0.00", "0", "$0.00", "$1.42", "$8.86", "$3,552.27", "-$56,003.70")
    		);

        when(dateFormatUtil.formatLocalDateTime(anyString(), anyString())).thenReturn(LocalDateTime.of(2023, 11, 14, 22, 13, 20));
        when(numberDataUtil.hasFractionalPart(anyDouble())).thenReturn(false);

        List<TransactionIssueFilePojo> result = readCsvTransactionIssuesBrokerGbm.readCsvFileIssues(records);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("CCL", result.get(0).getIssue());
        assertEquals(ReadCsvTransactionIssuesBrokerGbm.TYPE_TRANSACTION_BUY, result.get(0).getTypeTransaction());
        assertEquals(BigDecimal.valueOf(1234.56), result.get(0).getPrice());
    }

}
