package kr.co.takensoft.dataQuality.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * 세션 및 쿠키 유효기간 재설정
 *
 * @author 김준식
 * @since 2024.01.16
 *
 */
public class SessionInterceptor implements HandlerInterceptor {

    /**
     * 클라이언트에서 요청이 들어올때마다 세션 검사 및 세션, 쿠키 만료시간 재설정
     *
     * @author 김준식
     * @since 2024.01.16
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        System.out.println("SessionInterceptor 실행");
        if(session != null && !(session.isNew())) {
            // 세션이 새로 생성되지 않았고 세션이 존재하였으면 세션과 쿠키 만료시간 갱신
            session.setMaxInactiveInterval(30 * 60);
            Cookie[] cookies = request.getCookies();

            // 기존 쿠키를 찾아서 변경
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals("JSESSIONID") || cookie.getName().equals("USER") || cookie.getName().equals("USER_ID") ) {
                    cookie.setMaxAge(30 * 60);
                    response.addCookie(cookie);
                }
            }
        }

        return true;
    }
}
