package kr.co.fastcompus.eatgo;

import kr.co.fastcompus.eatgo.filters.JwtAuthenticationFilter;
import kr.co.fastcompus.eatgo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.Filter;
import javax.validation.Valid;

@Configuration
@EnableWebSecurity // 웹 시큐리티를 사용하겠다라는 뜻
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.secret}")
    private String secret;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 사용자에게 받는 JWT를 확인하기 위한 필터
        Filter fileter = new JwtAuthenticationFilter(authenticationManager(), jwtUtil());

        // 기본적으로 설정해야될 것들
        http
                .cors().disable() // 보안에서 중요한 기능이나 RestApi에서 불필요한 기능
                .csrf().disable() // 보안에서 중요한 기능이나 RestApi에서 불필요한 기능
                .formLogin().disable() // 로그인 폼 페이지 미사용
                .headers().frameOptions().disable() // iframe 사용(h2 콘솔을 사용하기 위함)
                .and()
                .addFilter(fileter) // 필터 추가
                .sessionManagement() // 세션 메니져 설정
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // HTTP 세션은 사용하지 않음
         ;

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil(secret);
    }
}
