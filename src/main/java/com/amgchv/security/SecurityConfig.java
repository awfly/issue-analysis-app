package com.amgchv.security;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestOperations;

import java.net.URI;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().requestMatchers(
                PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // configure login
        http.formLogin()
                .loginPage("/login")
                .usernameParameter("account")
                .passwordParameter("password")
                .permitAll();
        // configure logout
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll()
                .invalidateHttpSession(true);
        // configure URL authorization
        http.authorizeRequests()
                .antMatchers("/login*").permitAll()
                .mvcMatchers("/public/**").permitAll()
                .mvcMatchers("/signup").permitAll()
                .mvcMatchers(HttpMethod.GET, "/issues/").hasAuthority("readIssue")
                .mvcMatchers(HttpMethod.GET, "/issue/new").hasAuthority("writeIssue")
                .mvcMatchers(HttpMethod.POST, "/issues/").hasAuthority("writeIssue")
                .mvcMatchers("/users").hasAuthority("manageUser")
                .anyRequest().authenticated();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    RestOperations restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .basicAuthentication("amgchv@gmail.com", "qocDYvxbSAux0iELFchUAC18")
                .rootUri("https://jira4cloud.atlassian.net")
                .build();
    }

    @Bean
    public JiraRestClient getJiraRestClient() {
        return new AsynchronousJiraRestClientFactory()
                .createWithBasicHttpAuthentication(
                        URI.create("https://jira4cloud.atlassian.net"),
                        "amgchv@gmail.com",
                        "qocDYvxbSAux0iELFchUAC18");
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }
}
