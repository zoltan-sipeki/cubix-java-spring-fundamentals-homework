package hu.cubix.hr.zoltan_sipeki.configuration;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="salary")
public class SalaryConfiguration {
	private int defaultPercent;
	private Smart smart;

	public Smart getSmart() {
		return smart;
	}

	public void setSmart(Smart smart) {
		this.smart = smart;
	}

	public int getDefaultPercent() {
		return defaultPercent;
	}

	public void setDefaultPercent(int defaultPercent) {
		this.defaultPercent = defaultPercent;
	}

	public static class Smart {
		private List<SalaryLimit> limits;

		public List<SalaryLimit> getLimits() {
			return limits;
		}

		public void setLimits(List<SalaryLimit> limits) {
			this.limits = limits;
		}
	}

	public static class SalaryLimit {
		private double year;
		private int percent;

		public double getYear() {
			return year;
		}
		public void setYear(double year) {
			this.year = year;
		}
		public int getPercent() {
			return percent;
		}
		public void setPercent(int percent) {
			this.percent = percent;
		}
	}
}
