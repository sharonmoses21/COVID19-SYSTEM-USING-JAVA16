package aplc_assignment;

import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;

public class Covid19Reader {

//	TO READ AND PARSE THE DATA FROM THE GIVEN DATASETS FILE INTO THE DESIRED FORMAT
	public Function<String, List<Covid19Cases>> readDataLineByLine = file -> {
		
		try {
//			READ THE SELECTED OF A FILE
			FileReader fileReader = new FileReader(file);

//			READ ALL THE CONTENTS OF THE SELECTED CSV FILE		
			CSVReader csvReader = new CSVReaderBuilder(fileReader)
					.withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_SEPARATORS).build();
			List<String[]> covidData = csvReader.readAll();

//			TO RETREIVED AND GENERATE A LIST OF DATES FROM THE GIVEN CSV DATASETS FILE
			List<LocalDate> datesData = Arrays.asList(Arrays.copyOfRange(covidData.get(0), 4, covidData.get(0).length))
					.stream().map(d -> {
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy", Locale.ENGLISH);
						LocalDate date = LocalDate.parse(d, formatter);
						return date;
					}).collect(Collectors.toList());

//			TO GENERATE A LIST OF CasesDate OBJECT
			BiFunction<String[], List<LocalDate>, List<CasesDate>> casesDates = (data, dates) -> {
				HashMap<LocalDate, Integer> datesToCases = new HashMap<LocalDate, Integer>();

				List<Integer> cases = Arrays.stream(data, 4, data.length).mapToInt(Integer::parseInt).boxed()
						.collect(Collectors.toList());

				IntStream.range(0, cases.size()).boxed().sorted(Collections.reverseOrder())
						.forEach(i -> i = i != 0 && cases.get(i) != 0
								? datesToCases.put(dates.get(i), cases.get(i) - cases.get(i - 1))
								: datesToCases.put(dates.get(i), cases.get(i)));

				List<CasesDate> casesDate = datesToCases.entrySet().stream().map(i -> {
					return new CasesDate(i.getKey(), i.getValue());
				}).collect(Collectors.toList());

				return casesDate;
			};

//			TO GENERATE A LIST OF Covid19Cases OBJECT
			List<Covid19Cases> confirmedCases = covidData.stream().skip(1).map(row -> {
				return new Covid19Cases(row[0], row[1], Double.parseDouble(Optional.ofNullable(row[2]).orElse("-1")),
						Double.parseDouble(Optional.ofNullable(row[3]).orElse("-1")), casesDates.apply(row, datesData));
			}).collect(Collectors.toList());
			return confirmedCases;
		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return null;
	};
}
