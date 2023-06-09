package com.example.javaspringboot.Security;

import static org.springframework.http.HttpMethod.GET;

import com.example.javaspringboot.Security.Request.AuthEntryPointJwt;
import com.example.javaspringboot.Security.Request.AuthTokenFilter;
import com.example.javaspringboot.Security.Service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        // securedEnabled = true,
        // jsr250Enabled = true,
        prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception { return super.authenticationManagerBean(); }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .authorizeRequests()
            .antMatchers("/Auth/signup", "/Auth/whoami", "/Auth/signin","/Auth/signout").permitAll() // TODO: FIGURE OUT WHY THE FUCK NOT WORK

            .antMatchers(
                "/Users/initialRegister/**" ,"/Users/getUserProfileAndStatsById/**",
                "/Modules/dto", "/Modules/dto/byCode/**",
                "/Updates", "/Updates/Recent", "/Test/all", "/Extras/newestOrder-hideHidden", "/Extras/containingTitle-hideHidden/*" ,"/Extras/viewed/*", "/ContactForms/add"
                ).permitAll()

//add GETTING ACTIVITIES FOR ALL USERS IN HERE

                .antMatchers(GET, "/Modules", "/Modules/**", "/Modules/**/**","/Modules/**/**/**", "/Users/**", "/Auth/whoami")
                .hasAnyAuthority("ROLE_STUDENT","ROLE_UNDEFINED", "ROLE_STAFF", "ROLE_ADMIN")

                .antMatchers( "/SubmittedPropagates/add", "/SubmittedPropagates/vote/*/*",
                        "/SubmittedQuizzes/add","/SubmittedQuizzes/vote/*/*",
                        "/SubmittedSwipes/add","/SubmittedSwipes/vote/*/*",
                        "/SubmittedHangmen/add","/SubmittedHangmen/vote/*/*",
                        "/SubmittedShifter/add","/SubmittedShifter/vote/*/*")
                .hasAnyAuthority("ROLE_STUDENT","ROLE_UNDEFINED", "ROLE_STAFF", "ROLE_ADMIN")

                .antMatchers("/Users/getUserProfileByEmail", "/QuickNotes", "/QuickNotes/**", "/QuickNotes/**/**",
                "/Test/user"
                ).hasAnyAuthority("ROLE_STUDENT","ROLE_UNDEFINED", "ROLE_STAFF", "ROLE_ADMIN")
//                .hasAnyAuthority("STUDENT","UNDEFINED", "STAFF", "ADMIN")

                .antMatchers("/**", "/**/**", "/**/**/**", "/Test/admin"
//                        "/User", "/ContactForms", "/Extras", "/Feedbacks", "/Modules", "/QuickNotes", "/Updates",
//                        "/Hangmen", "/Matches", "/Propagates", "/Quizzes", "/Swipes",
//                        "/SubmittedHangmen", "/SubmittedMatches","SubmittedPropagates","/SubmittedQuestion","/SubmittedQuizzes","/SubmittedSwipes",
//                        "/Test/staff"
                ).hasAnyAuthority("ROLE_STAFF", "ROLE_ADMIN", "ROLE_SYSTEM");

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
