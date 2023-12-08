package aplc_assignment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GenerateKBF {
	
	public static List<Covid19Cases> confirmedCases;

	public static void main(String[] args) {
		
		Covid19Reader covid19Reader = new Covid19Reader();
		String CONFIRMED_CASES_FILE = "C:\\Users\\Bryan Kee Jia Xian\\eclipse-workspace\\APLC_Assignment\\dataset\\time_series_covid19_confirmed_global.csv";
		confirmedCases = covid19Reader.readDataLineByLine.apply(CONFIRMED_CASES_FILE);
		
		String TOTAL_CONFIRMED_CASES_KBF_FILE = "total_confirm_cases_kbf.pl";
		
//		GENERATE KNOWLEDGEBASE FILE OF TOTAL CONFIRMED CASES
		generateKBF.accept(confirmedCases, TOTAL_CONFIRMED_CASES_KBF_FILE);
	}
	
//	GENERATE KNOWLEDGEBASE FILE OF TOTAL CONFIRMED CASES
	public static BiConsumer<List<Covid19Cases>, String> generateKBF = (confirmedCases, kbfPath) -> {
		
//		GENERATE FACTS
		List<Integer> totalConfirmedCases = confirmedCases.stream().map(row -> row.getDailyCases()).map(cases -> {
			return cases.stream().map(row -> row.getCasesCount()).collect(Collectors.toList()).stream().reduce(0,
					Integer::sum);
		}).collect(Collectors.toList());
		
		Path path = Paths.get(kbfPath);
		if(!Files.exists(path)) {
			try {
				Files.createFile(path);
				
				FileWriter writer = new FileWriter(kbfPath, true);
	            BufferedWriter bufferedWriter = new BufferedWriter(writer);
	 
	            bufferedWriter.write("% FACTS OF TOTAL CONFIRMED CASES");
	            bufferedWriter.newLine();
	            bufferedWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			IntStream.range(0, confirmedCases.size()).forEach(i -> {
				
				String state = confirmedCases.get(i).getState().orElse("No State");
				String country = confirmedCases.get(i).getCountry();
				int totalConfirmedCase = totalConfirmedCases.get(i);
				
				String fact = "confirmedCases('" + state + "','" + country.replace("'", "") + "'," + totalConfirmedCase + ").";
				
				try {
		            FileWriter writer = new FileWriter(kbfPath, true);
		            BufferedWriter bufferedWriter = new BufferedWriter(writer);
		 
		            bufferedWriter.write(fact);
		            bufferedWriter.newLine();
		            bufferedWriter.close();
		        } catch (IOException e1) {
		            e1.printStackTrace();
		        }
			});
			
			
//			GENERATE RULES OF TOTAL CONFIRMED CASES
			try {
	            FileWriter writer = new FileWriter(kbfPath, true);
	            BufferedWriter bufferedWriter = new BufferedWriter(writer);
	            
//	            SorceCode: https://stackoverflow.com/questions/47810970/sort-descending-order-in-a-list-of-tuple-in-prolog/47811774
	            String getAllConfirmedCases = "getAllCases(List) :- findall([Cases,State,Country],confirmedCases(State,Country,Cases),List).";
	            String getAscSort = "getAscSort(Sorted) :- getAllCases(Unsorted),sort(1,@=<,Unsorted,Sorted).";
	            String getDscSort = "getDscSort(Sorted) :- getAllCases(Unsorted),sort(1,@>=,Unsorted,Sorted).";
	 
	            bufferedWriter.write("% RULES OF TOTAL CONFIRMED CASES");
	            bufferedWriter.newLine();
	            bufferedWriter.write("% GET ALL THE COUNTRIES");
	            bufferedWriter.newLine();
	            bufferedWriter.write(getAllConfirmedCases);
	            bufferedWriter.newLine();
	            bufferedWriter.write("% SORTING IN ASCENDING ORDER");
	            bufferedWriter.newLine();
	            bufferedWriter.write(getAscSort);
	            bufferedWriter.newLine();
	            bufferedWriter.write("% SORTING IN DESCENDING ORDER ");
	            bufferedWriter.newLine();
	            bufferedWriter.write(getDscSort);
	            bufferedWriter.newLine();
	            bufferedWriter.close();
	        } catch (IOException e1) {
	            e1.printStackTrace();
	        }
			
		} else {
			System.out.println("File existed.");
		}
	};

}
