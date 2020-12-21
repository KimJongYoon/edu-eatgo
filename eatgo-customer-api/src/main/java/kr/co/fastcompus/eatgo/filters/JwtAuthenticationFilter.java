package kr.co.fastcompus.eatgo.filters;

import io.jsonwebtoken.Claims;
import kr.co.fastcompus.eatgo.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {


    private JwtUtil jwtUtil;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        // JWT에 대한 분석 작업을 함
        Authentication authentication = getAuthentication(request);

        if(authentication != null){
            SecurityContext context = SecurityContextHolder.getContext(); // 현재 사용하고 있는 컨텍스트를 설정
            context.setAuthentication(authentication);
        }

        chain.doFilter(request,response);
    }

    private Authentication getAuthentication(HttpServletRequest request){

        // 헤더에서 토큰을 가져옴
        String token = request.getHeader("Authorization");
        log.info("token: {}",token);
        if(token == null){
            return null;
        }

        // (Authorization : Bearer Token) 와 같은 형식으로 오기 때문에 앞에 'Bearer '(띄어쓰기 포함) 을 날려준다.
//        Claims claims = jwtUtil.getClamils(token.substring("Bearer ".length()));
        Claims claims = jwtUtil.getClamils(token);

        // 보통 첫번째 id, 두번째 비밀번호 셋팅하는데 여기서는 첫번째에 클레임즈를 넣는다.
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(claims, null);
        return authentication;
    }
}
