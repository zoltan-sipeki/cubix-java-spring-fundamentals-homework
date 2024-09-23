package hu.cubix.hr.zoltan_sipeki.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="salary")
public class SalaryConfiguration {
	private double[] years;
	private int[] raisePercents;
	
	public SalaryConfiguration(double[] years, int[] raisePercents) {
		super();
		this.years = years;
		this.raisePercents = raisePercents;
	}
	public double[] getYears() {
		return years;
	}
	public void setYears(double[] years) {
		this.years = years;
	}
	public int[] getRaisePercents() {
		return raisePercents;
	}
	public void setRaisePercents(int[] raisePercents) {
		this.raisePercents = raisePercents;
	}
	
	
}
