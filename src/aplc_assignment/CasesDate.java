package aplc_assignment;

import java.time.LocalDate;

public class CasesDate {

	private final LocalDate casesDate;
	private final int casesCount;

	public CasesDate(LocalDate casesDate, int casesCount) {
		this.casesDate = casesDate;
		this.casesCount = casesCount;
	}

	public LocalDate getCasesDate() {
		return casesDate;
	}

	public int getCasesCount() {
		return casesCount;
	}
}
