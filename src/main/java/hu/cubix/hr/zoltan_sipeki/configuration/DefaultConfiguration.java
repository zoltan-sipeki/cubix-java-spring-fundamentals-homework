package hu.cubix.hr.zoltan_sipeki.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import hu.cubix.hr.zoltan_sipeki.service.DefaultEmployeeService;

@Configuration
@Profile("default")
public class DefaultConfiguration {
	@Bean
	public DefaultEmployeeService defaultEmployeeService() {
		return new DefaultEmployeeService();
	}
}
