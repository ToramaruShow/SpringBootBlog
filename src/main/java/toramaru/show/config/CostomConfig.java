package toramaru.show.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource(value="classpath:blog.properties")
public class CostomConfig {
	static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
	return new PropertySourcesPlaceholderConfigurer();
	}
}
