package imena.uisrael.docsmanagement.security;

import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
// import org.springframework.security.web.SecurityFilterChain;

// @EnableWebSecurity
@Configuration
public class WebSecurityConfig {
    
    // @Bean
    // public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
    //     http//.authorizeHttpRequests(
    //             // (authorize) -> authorize.requestMatchers("/login").permitAll())
    //             //.httpBasic(Customizer.withDefaults())
                
    //             .authorizeHttpRequests(authorize -> authorize.requestMatchers("/login","/assets/**")
    //                                     .permitAll()
    //                                     .anyRequest()
    //                                     .authenticated()
    //             )
    //             .formLogin(form -> form.loginProcessingUrl("/login")
    //                             .loginPage("/login")
    //                             .defaultSuccessUrl("/success", true)
                                
    //                             .failureUrl("/login?error=true")
    //             )
    //             .logout(logout -> logout.logoutUrl("/logout")
    //                              .logoutSuccessUrl("/login?logout=true")
    //                              .deleteCookies("JSESSIONID")
    //             )
    //             .csrf(csrf -> csrf.disable())
    //                         ;
        
    //     return http.build();
    // }
    
    // @Bean
    // public UserDetailsService userDetailsService () {
    //     UserDetails userDetails = User.withDefaultPasswordEncoder()
    //                                   .username("user")
    //                                   .password("1234")
    //                                   .roles("USER")
    //                                   .build();
        
    //     return new InMemoryUserDetailsManager(userDetails);
    // }
    
}
