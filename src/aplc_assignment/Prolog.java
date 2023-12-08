package aplc_assignment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import org.jpl7.Query;
import org.jpl7.Term;

public class Prolog {

	public static void main(String[] args) {
		
//		CONNECT TO CONFIRMED COVID CASES KNOWLEDGE BASE FILE
		String CONFIRMED_CASES_KBF = "total_confirm_cases_kbf.pl";
		connectKBF.accept(CONFIRMED_CASES_KBF);
		
//		GENERATE SORTED TOTAL CONFIRMED CASES (ASCENDING)
		String SORT_CASES_ASC = "sortedCasesASC.txt";
		sortTotalConfirmedCaseASC.accept(SORT_CASES_ASC);
		
//		GENERATE SORTED TOTAL CONFIRMED CASES (DESCENDING)
		String SORT_CASES_DSC = "sortedCasesDSC.txt";
		sortTotalConfirmedCaseDSC.accept(SORT_CASES_DSC);
	}
	
//	CONNECT TO CONFIRMED COVID CASES KNOWLEDGE BASE FILE
	public static Consumer<String> connectKBF = KBF -> {
		String consult = "consult('"+KBF+"')";
		Query query = new Query(consult);
		System.out.print(KBF + ": " );
		System.out.println(query.hasSolution() ? "Successfully Connected" : "Connection Failed");
    };
    
//	GENERATE SORTED TOTAL CONFIRMED CASES (ASCENDING)
    public static Consumer<String> sortTotalConfirmedCaseASC = filePath -> {
		
		String ruleASC = "getAscSort(Sorted)";
		Query query = new Query(ruleASC);
		
		Term sortedCase = query.oneSolution().get("Sorted");
		Term[] sortCaseArr = sortedCase.toTermArray();
		
		IntStream.range(0, sortCaseArr.length).forEach(cases -> {
			
			try {
				FileWriter writer = new FileWriter(filePath, true);
	            BufferedWriter bufferedWriter = new BufferedWriter(writer);
	            
	            bufferedWriter.write(sortCaseArr[cases].toString());
	            bufferedWriter.newLine();
	            bufferedWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		System.out.println("sortedCasesASC.txt file has been created successfully!");
	};
	
//	GENERATE SORTED TOTAL CONFIRMED CASES (DESCENDING)
    public static Consumer<String> sortTotalConfirmedCaseDSC = filePath -> {
		
		String ruleDSC = "getDscSort(Sorted)";
		Query query = new Query(ruleDSC);
		
		Term sortedCase = query.oneSolution().get("Sorted");
		Term[] sortCaseArr = sortedCase.toTermArray();
		
		IntStream.range(0, sortCaseArr.length).forEach(cases -> {
			
			try {
				FileWriter writer = new FileWriter(filePath, true);
	            BufferedWriter bufferedWriter = new BufferedWriter(writer);
	            
	            bufferedWriter.write(sortCaseArr[cases].toString());
	            bufferedWriter.newLine();
	            bufferedWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		System.out.println("sortedCasesDSC.txt file has been created successfully!");
	};
}
