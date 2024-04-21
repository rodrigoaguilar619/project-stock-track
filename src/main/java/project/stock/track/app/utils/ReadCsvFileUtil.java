package project.stock.track.app.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

public class ReadCsvFileUtil {
	
	public List<List<String>> readCsvFile(byte[] fileBytes) throws IOException {
		
		return readCsvFile(new ByteArrayInputStream(fileBytes));
	}

	public List<List<String>> readCsvFile(InputStream inputStream) throws IOException {
		
		List<List<String>> records = new ArrayList<>();
		CSVParser csvParser = null;
		
		try(BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
		
			csvParser = new CSVParser(br, CSVFormat.DEFAULT.withDelimiter(','));
			
			for (CSVRecord csvRecord: csvParser) {
				
				List<String> rowData = new ArrayList<>();
				for (String value: csvRecord) {
					rowData.add(value);
				}
				records.add(rowData);
			}
			
		}
		finally {
			if (csvParser != null)
				csvParser.close();
		}
		return records;
	}
	
	public List<List<String>> readCsvFile(String fileBase64) throws IOException {
		
		byte[] fileBytes = Base64.getDecoder().decode(fileBase64);
		return readCsvFile(fileBytes);
	}
	
	public List<List<String>> readCsvFile(MultipartFile file) throws IOException {
		return readCsvFile(file.getInputStream());
	}
}
