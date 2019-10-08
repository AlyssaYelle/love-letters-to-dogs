package dogletters.demo.Config;


import dogletters.demo.Models.Person;
import dogletters.demo.Config.JwtRequestFilter;
import dogletters.demo.Services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private dogletters.demo.Config.JwtRequestFilter jwtRequestFilter;

    @Autowired
    public PersonService personService;

    @Bean("encoder")
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/signup/**", "/login/**").permitAll()
                .antMatchers("/user/**", "/profile/**", "/post/**", "/comment/**").authenticated()
                //.antMatchers("/user/**", "/profile/**", "/dog/**").permitAll()
                //.antMatchers("/role/**").hasRole("DBA")
                .antMatchers("/role/**").permitAll()
                .and()
                .httpBasic();

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{

        User.UserBuilder person = User.withDefaultPasswordEncoder();
        auth.inMemoryAuthentication().withUser(person.username("test").password("test").roles("ADMIN"));
        auth.inMemoryAuthentication().withUser(person.username("dba").password("dba").roles("DBA"));
    }
}
