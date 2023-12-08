package aplc_assignment;

import java.util.List;
import java.util.Optional;

public class Covid19Cases {

	private final String country;
	private final String state;
	private final double latitude;
	private final double longitude;
	private final List<CasesDate> dailyCases;

	public Covid19Cases(String state, String country, double latitude, double longitude, List<CasesDate> casesDate) {
		this.state = state;
		this.country = country;
		this.latitude = latitude;
		this.longitude = longitude;
		this.dailyCases = casesDate;
	}

	public String getCountry() {
		return country;
	}

	public Optional<String> getState() {
		return Optional.ofNullable(state);
	}

	public Optional<Double> getLatitude() {
		return Optional.ofNullable(latitude);
	}

	public Optional<Double> getLongitude() {
		return Optional.ofNullable(longitude);
	}

	public List<CasesDate> getDailyCases() {
		return dailyCases;
	}
}
