package com.saien.springboottwo;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
public class ProjectBeanConfiguration extends WebSecurityConfigurerAdapter{
	// we have extends this class by WebSecurityConfigurerAdapter to disable CSRF

	
    @Bean
    public JdbcUserDetailsManager userDetailsService(){
        return new JdbcUserDetailsManager(dataSource());
    }
	

    @Bean
    public PasswordEncoder passwordEncoder(){
    	return new BCryptPasswordEncoder();
    }
    
	@Bean
	public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/dev");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
	}
	
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();

        http.csrf().disable(); // Disable CSRF token protection

        http.authorizeRequests()
                .mvcMatchers("/user/**").permitAll() // permits all the requests to the /users path
                .anyRequest().authenticated(); // others path will be protected
    }
}
