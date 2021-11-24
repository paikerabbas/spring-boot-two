package com.saien.springboottwo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
public class ProjectBeanConfiguration extends WebSecurityConfigurerAdapter{
	// we have extends this class by WebSecurityConfigurerAdapter to disable CSRF

	
    @Autowired
    private MyCustomAuthenticationProvider myCustomAuthenticationProvider;

   // ...
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(myCustomAuthenticationProvider);
    }
    
//    @Bean
//    public JdbcUserDetailsManager userDetailsService(){
//        return new JdbcUserDetailsManager(dataSource());
//    }
	

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//    	return new BCryptPasswordEncoder();
//    }
    
	@Bean
	public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/dev");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
	}
	
	@Bean
	public JdbcUserDetailsManager jdbcUserDetailsManager() {
		return new JdbcUserDetailsManager(dataSource());
	}

	
    @Bean
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        UserDetails dummyUser = User.withUsername("dummy_user")
                                    .password("1234")
                                    .authorities("read")
                                    .build();
        inMemoryUserDetailsManager.createUser(dummyUser);
        return inMemoryUserDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
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
