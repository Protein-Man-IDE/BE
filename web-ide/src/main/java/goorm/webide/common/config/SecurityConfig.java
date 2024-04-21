package goorm.webide.common.config;

import goorm.webide.common.jwt.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                // JWT 방식은 세션이 stateless해서 csrf를 방어할 필요 X
                .csrf((auth) -> auth.disable())
                // 스프링에서 제공하는 로그인 form disable
                .formLogin((auth) -> auth.disable())
                // HTTP 헤더에 사용자 이름과 비밀번호를 포함하여 요청을 보내는 간단한 인증 방식 disable
                .httpBasic((auth) -> auth.disable())
                // 경로별 인가
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/", "/user/register", "/user/login").permitAll()
                        .anyRequest().authenticated()
                )
                // 커스텀한 LoginFilter를 기존자리에 적용
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration)),
                        UsernamePasswordAuthenticationFilter.class)
                // 세션설정, stateless하도록 설정**
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}