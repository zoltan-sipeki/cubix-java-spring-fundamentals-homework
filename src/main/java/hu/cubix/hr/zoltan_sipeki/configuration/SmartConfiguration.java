package hu.cubix.hr.zoltan_sipeki.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import hu.cubix.hr.zoltan_sipeki.service.SmartEmployeeService;

@Configuration
@Profile("smart")
public class SmartConfiguration {
	
	@Bean
	public SmartEmployeeService smartEmployeeService() {
		return new SmartEmployeeService();
	}
}
