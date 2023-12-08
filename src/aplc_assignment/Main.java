package aplc_assignment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.function.TriFunction;

public class Main {

	public static List<Covid19Cases> confirmedCases;
	public static List<Covid19Cases> deathCases;
	public static List<Covid19Cases> recoveredCases;

	public static void main(String[] args) {

		Covid19Reader covid19Reader = new Covid19Reader();

		confirmedCases = covid19Reader.readDataLineByLine.apply(
				"C:\\Users\\Bryan Kee Jia Xian\\eclipse-workspace\\APLC_Assignment\\dataset\\time_series_covid19_confirmed_global.csv");

		deathCases = covid19Reader.readDataLineByLine.apply(
				"C:\\Users\\Bryan Kee Jia Xian\\eclipse-workspace\\APLC_Assignment\\dataset\\time_series_covid19_deaths_global.csv");

		recoveredCases = covid19Reader.readDataLineByLine.apply(
				"C:\\Users\\Bryan Kee Jia Xian\\eclipse-workspace\\APLC_Assignment\\dataset\\time_series_covid19_recovered_global.csv");

		
		
		
		
		
		
//		Q1 - Display the total confirmed Covid-19 cases according to country
		String countryName = "Afghanistan";
		String stateName = null;
//		int totalCases = getTotalCases.apply(recoveredCases, countryName, stateName);
//		System.out.printf("%s\n",
//				"Total cases for country " + countryName + (stateName == null ? " are " + totalCases + " cases."
//						: ", " + stateName + " are " + totalCases + " cases."));

//		get the total cases of 
		List<Integer> totalCasesAll = confirmedCases.stream().map(row -> row.getDailyCases()).map(a -> {
			return a.stream().map(row -> row.getCasesCount()).collect(Collectors.toList()).stream().reduce(0,
					Integer::sum);
		}).collect(Collectors.toList());
		
		for(int i = 0; i < confirmedCases.size(); i++) {
			String test = "confirmedCases('" + confirmedCases.get(i).getState().orElse("No State") + "','" + confirmedCases.get(i).getCountry() + "'," + totalCasesAll.get(i) + ").";
			
			try {
	            FileWriter writer = new FileWriter("kbf.pl", true);
	            BufferedWriter bufferedWriter = new BufferedWriter(writer);
	 
	            bufferedWriter.write(test);
	            bufferedWriter.newLine();
	            bufferedWriter.close();
	        } catch (IOException e1) {
	            e1.printStackTrace();
	        }
		}
		System.out.println("Done");
		


		int testcm = totalCasesAll.stream().reduce(0, Integer::sum);
//		System.out.println(testcm);

//				confirmedCases.stream().flatMap(row -> row.getDailyConfirmedCases().stream()).getDailyCases().stream()
//				.map(row -> row.getCasesCount())
//				.collect(Collectors.toList()).stream().reduce(0, Integer::sum);

//		List<CasesDate> test2 = confirmedCases.stream().flatMap(row -> row.getDailyConfirmedCases().stream())
//				.collect(Collectors.toList());

		List<Integer> totalCasesAll1 = deathCases.stream().map(row -> row.getDailyCases()).map(a -> {
			return a.stream().map(row -> row.getCasesCount()).collect(Collectors.toList()).stream().reduce(0,
					Integer::sum);
		}).collect(Collectors.toList());

		List<Integer> totalCasesAll2 = recoveredCases.stream().map(row -> row.getDailyCases()).map(a -> {
			return a.stream().map(row -> row.getCasesCount()).collect(Collectors.toList()).stream().reduce(0,
					Integer::sum);
		}).collect(Collectors.toList());

		List<Integer> newTotal = new ArrayList<>();

		for (int i = 40; i < 55; i++) {
			totalCasesAll2.add(i, null);
		}

//		System.out.println(totalCasesAll.size());
//		System.out.println(totalCasesAll2.size());

//		System.out.println(recoveredCases.get(39).getCountry());

//		System.out.println(totalCasesAll.size());
//		System.out.println(totalCasesAll1.size());
//		System.out.println(totalCasesAll2.size());
//		System.out.println("State\t\t\tCountry\t\t\tConfirmed\t\t\tDeath\t\t\tRecovered");
		for (int i = 0; i < confirmedCases.size(); i++) {

//			Optional<String> text = confirmedCases.get(i).getState();
//			
//			if (confirmedCases.get(i).getState().isEmpty()) {
//				text = Optional.ofNullable("No State");
//			}
//
//			System.out.println(
//					confirmedCases.get(i).getState().orElse("No State") + "\t" + confirmedCases.get(i).getCountry()
//							+ "\t\t" + totalCasesAll.get(i) + "\t\t" + totalCasesAll1.get(i) + totalCasesAll2.get(i));

//			if (confirmedCases.get(i).getCountry().equals("Canada")) {
//				System.out.println(confirmedCases.get(i).getState().orElse("No State") + "\t" + "Canada1" + "\t\t"
//						+ totalCasesAll.get(i) + "\t\t" + totalCasesAll1.get(i) + "\t" + totalCasesAll2.get(39));
//			} else {
//				System.out.println(confirmedCases.get(i).getState().orElse("No State") + "\t"
//						+ confirmedCases.get(i).getCountry() + "\t\t" + totalCasesAll.get(i) + "\t\t"
//						+ totalCasesAll1.get(i) + totalCasesAll2.get(i));
//			}

		}

//		.flatMap(row -> row.getDailyCases().stream())
//		.map(row -> row.getCasesCount()).collect(Collectors.toList()).stream().reduce(0, Integer::sum);

//		confirmedCases.stream().forEach(e -> System.out.println(e.getCountry()));

//		Q2 - Compute the sum of confirmed cases by week and month for each country
		List<Integer> totalConfirmedCases = confirmedCases.stream().map(row -> row.getDailyCases()).map(cases -> {
			return cases.stream().map(row -> row.getCasesCount()).collect(Collectors.toList()).stream().reduce(0,
					Integer::sum);
		}).collect(Collectors.toList());

//		System.out.println(recoveredCases.get(40).getCountry());

		for (int i = 40; i < 55; i++) {
			recoveredCases.add(i, null);
		}

//		System.out.println(recoveredCases.get(39).getCountry());
//		System.out.println(recoveredCases.get(55).getCountry());

		List<List<String>> monththCovid = new ArrayList<>();
		for (int i = 0; i < confirmedCases.size(); i++) {
			List<String> hi = new ArrayList<>();

			hi.add(confirmedCases.get(i).getState().orElse("N/A"));
			hi.add(confirmedCases.get(i).getCountry());

//			GET MONTHLY CONFIRMED CASES
			int cm = confirmedCases.get(i).getDailyCases().stream().filter(a -> a.getCasesDate().getYear() == 2020)
					.filter(a -> a.getCasesDate().getMonthValue() == 1).map(row -> row.getCasesCount())
					.collect(Collectors.toList()).stream().reduce(0, Integer::sum);

			int dm = deathCases.get(i).getDailyCases().stream().filter(a -> a.getCasesDate().getYear() == 2020)
					.filter(a -> a.getCasesDate().getMonthValue() == 1).map(row -> row.getCasesCount())
					.collect(Collectors.toList()).stream().reduce(0, Integer::sum);

			int rm;
			if (i >= 40 && i < 55) {
				rm = 0;
			} else {
				rm = recoveredCases.get(i).getDailyCases().stream().filter(a -> a.getCasesDate().getMonthValue() == 1)
						.map(row -> row.getCasesCount()).collect(Collectors.toList()).stream().reduce(0, Integer::sum);
			}

			hi.add(String.valueOf(cm));
			hi.add(String.valueOf(dm));
			hi.add(String.valueOf(rm));
			monththCovid.add(hi);
		}
//		System.out.println(monththCovid);

//		System.out.println(confirmedCases.get(55).getCountry());
//		System.out.println(recoveredCases.get(55).getCountry());

		int rm = recoveredCases.get(55).getDailyCases().stream().filter(a -> a.getCasesDate().getMonthValue() == 1)
				.map(row -> row.getCasesCount()).collect(Collectors.toList()).stream().reduce(0, Integer::sum);

//		System.out.println(rm);

		List<CasesDate> monthlyConfirmedCases = confirmedCases.stream().flatMap(o -> o.getDailyCases().stream())
				.collect(Collectors.toList());

//		System.out.println(monthlyConfirmedCases.get(0).getCasesDate());
		List<Integer> month = new ArrayList<>();

//		GET MONTHLY CASES
		IntStream.range(1, 13).forEach(b -> {
			int ans = monthlyConfirmedCases.stream().filter(a -> a.getCasesDate().getYear() == 2020)
					.filter(a -> a.getCasesDate().getMonthValue() == b).map(row -> row.getCasesCount())
					.collect(Collectors.toList()).stream().reduce(0, Integer::sum);
			month.add(ans);
		});

//		System.out.println(month);

//		GET WEEKLY CASES

		Consumer<List<Covid19Cases>> weekCases = i -> {
			List<CasesDate> test2 = i.stream().flatMap(o -> o.getDailyCases().stream()).collect(Collectors.toList());

//			System.out.println(test2);

			int ans = test2.stream().filter(a -> a.getCasesDate().getMonthValue() == 2).map(row -> row.getCasesCount())
					.collect(Collectors.toList()).stream().reduce(0, Integer::sum);

//			i.stream().forEach(p -> System.out.println("The total cases of " + p.getCountry() + " in month is " + ans));
		};

//		weekCases.accept(confirmedCases);

//		System.out.println(confirmedCases.get(0).getDailyCases().get(0).getCasesDate().getDayOfWeek());

		List<CasesDate> test2 = confirmedCases.stream().filter(a -> a.getCountry().equals("Afghanistan"))
				.flatMap(o -> o.getDailyCases().stream()).collect(Collectors.toList());
		int ans = test2.stream().filter(a -> a.getCasesDate().getYear() == 2020)
				.filter(a -> a.getCasesDate().getMonthValue() == 1).map(row -> row.getCasesCount())
				.collect(Collectors.toList()).stream().reduce(0, Integer::sum);
//		System.out.println(test2.get(0).getCasesCount());
//		System.out.println(ans);

		List<CasesDate> he = test2.stream().filter(i -> i.getCasesDate().getYear() == 2020)
				.collect(Collectors.toList());
//
//		test2.stream().filter(i -> i.getCasesDate().getYear() == 2020)
//				.filter(a -> a.getCasesDate().getMonthValue() == 1).map(i -> i.getCasesDate())
//				.forEach(System.out::println);
////
//		System.out.println("");
//
//		test2.stream().filter(i -> i.getCasesDate().getYear() == 2020).map(i -> i.getCasesCount())
//				.forEach(i -> System.out.print(i + "           "));

		List<List<String>> weeklyCovid = new ArrayList<>();
		for (int i = 0; i < confirmedCases.size(); i++) {
			List<String> hi = new ArrayList<>();

			String province = confirmedCases.get(i).getState().orElse("N/A");
			String country = confirmedCases.get(i).getCountry();

			hi.add(province);
			hi.add(country);

			List<CasesDate> weeklyConfirmedCasesDate = confirmedCases.get(i).getDailyCases();

			Map<Integer, Integer> weeklyConfirmedCases = weeklyConfirmedCasesDate.stream()
					.collect(Collectors.groupingBy(a -> {
						TemporalField woy = WeekFields.of(Locale.UK).weekOfWeekBasedYear();
						int weekNumber = a.getCasesDate().get(woy);
						return weekNumber;
					}, Collectors.mapping(a -> a.getCasesCount(), Collectors.reducing(0, Integer::sum))));

			List<CasesDate> weeklyDeathCasesDate = deathCases.get(i).getDailyCases();

			Map<Integer, Integer> weeklyDeathCases = weeklyDeathCasesDate.stream().collect(Collectors.groupingBy(a -> {
				TemporalField woy = WeekFields.of(Locale.UK).weekOfWeekBasedYear();
				int weekNumber = a.getCasesDate().get(woy);
				return weekNumber;
			}, Collectors.mapping(a -> a.getCasesCount(), Collectors.reducing(0, Integer::sum))));

			hi.add(String.valueOf(weeklyConfirmedCases.get(1)));
			hi.add(String.valueOf(weeklyDeathCases.get(1)));

			if (i >= 40 && i < 55) {
				hi.add("0");
			} else {

				List<CasesDate> weeklyRecoveredCasesDate = recoveredCases.get(i).getDailyCases();

				Map<Integer, Integer> weeklyRecoveredCases = weeklyRecoveredCasesDate.stream()
						.collect(Collectors.groupingBy(a -> {
							TemporalField woy = WeekFields.of(Locale.UK).weekOfWeekBasedYear();
							int weekNumber = a.getCasesDate().get(woy);
							return weekNumber;
						}, Collectors.mapping(a -> a.getCasesCount(), Collectors.reducing(0, Integer::sum))));

				hi.add(String.valueOf(weeklyRecoveredCases.get(1)));
			}
			weeklyCovid.add(hi);
		}
//		System.out.println(weeklyCovid);

		Map<Integer, Integer> yes = test2.stream().collect(Collectors.groupingBy(a -> {
			TemporalField woy = WeekFields.of(Locale.UK).weekOfWeekBasedYear();
			int weekNumber = a.getCasesDate().get(woy);
//			System.out.println(weekNumber + " ");
			return weekNumber;
		}, Collectors.mapping(i -> i.getCasesCount(), Collectors.reducing(0, Integer::sum))));

		System.out.println("");
//		System.out.print(yes);

//		System.out.println(he.stream().collect(Collectors.groupingBy(a -> {
//			TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
//			int weekNumber = a.getCasesDate().get(woy);
//			return weekNumber;
//		}, Collectors.counting())));

//		for (int i = 0; i < confirmedCases.get(0).getDailyCases().size(); i++) {
//			System.out.println(confirmedCases.get(0).getDailyCases().get(i).getCasesDate());
//
//		}

//		List<CasesDate> test2 = confirmedCases.stream().flatMap(row -> row.getDailyConfirmedCases().stream())
//				.collect(Collectors.toList());

//		int ans = test2.stream().filter(i -> i.getCasesDate().getMonthValue() == 2).map(row -> row.getCasesCount())
//				.collect(Collectors.toList()).stream().reduce(0, Integer::sum);
//
//		System.out.println(ans);
//		List<LocalDate> hehe = test2.stream().map(o -> {
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy", Locale.ENGLISH);
//			LocalDate date = LocalDate.parse(o.getCasesDate(), formatter);
//			return date;
//		}).collect(Collectors.toList());

//		hehe.stream().map(i -> i.getMonth()).forEach(System.out::println);

//		Q4 - Search/locate the country for Covid-19 cases covering confirmed, death 
//		and recovered cases.

	}

//	Q4 - Search/locate the country for Covid-19 cases covering confirmed, death and recovered cases.
	static TriFunction<List<Covid19Cases>, String, String, Integer> searchCountryCases = (cases, country, state) -> {

		List<Covid19Cases> countryCases = cases.stream()
				.filter(row -> row.getCountry().equals(country) && row.getState().equals(Optional.ofNullable(state)))
				.collect(Collectors.toList());

		int totalCases = countryCases.stream().flatMap(row -> row.getDailyCases().stream())
				.map(row -> row.getCasesCount()).collect(Collectors.toList()).stream().reduce(0, Integer::sum);

		return totalCases;
	};

}
