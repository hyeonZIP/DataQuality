package kr.co.takensoft.dataQuality.user.service;

import kr.co.takensoft.dataQuality.user.dao.UserDAO;
import kr.co.takensoft.dataQuality.user.service.encrypt.SaltEncrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Service
public class LoginService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private SaltEncrypt saltEncrypt;

    /**
     * 로그인
     *
     * @author 김준식
     * @since 2024.01.16
     */
    public int login(HashMap<String,Object> params, HttpServletRequest request, HttpServletResponse response) {
        int result = 0;

        try{
            result = userDAO.idCheck(params);
            // 데이터베이스에 id가 없을 경우
            if (result == 0) {
                return 0;
            }

            String userPw = userDAO.getPassword(params);

            // 로그인 시 입력한 기존의 패스워드와 해시된 패스워드를 비교하여 false이면 비밀번호가 일치하지 않음을 의미.
            if(saltEncrypt.isMatch(params.get("user_password").toString(), userPw) == false) {
                result = 0;
                return result;
            }
            String userId = params.get("user_id").toString();

            HttpSession session = request.getSession();
            session.setAttribute("loginUser", userId);
            session.setMaxInactiveInterval(30*60); // 30분
            // 세션을 쿠키로 전달
            cookieCreate(response, "JSESSIONID", session.getId());

            // 사용자 이름을 쿠키로 전달
            String user_name = userDAO.getUserName(params);
            cookieCreate(response, "USER", user_name);

            // 사용자 ID를 쿠키로 전달
            cookieCreate(response, "USER_ID", userId);

            result = 1;
        } catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }
    /**
     * 쿠키 생성
     *
     * @author 김준식
     * @since 2024.01.17
     */
    public void cookieCreate(HttpServletResponse response, String cookieName, String cookieValue ) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(30*60);
        response.addCookie(cookie);
    }


    /**
     * 로그아웃
     *
     * @author 김준식
     * @since 2024.01.16
     */
    public int logout(HttpServletRequest request, HttpServletResponse response) {
        int result = 0;

        HttpSession session = request.getSession(false);
        if (session != null) {
            // 세션 무효화
            session.invalidate();
            // 쿠키 삭제
            cookieDelete(request, response);
            result = 1;
        }
        return result;
    }

    /**
     * 쿠키 삭제
     *
     * @author 김준식
     * @since 2024.01.16
     */
    public void cookieDelete(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for( Cookie cookie : cookies) {
                if(cookie.getName().equals("JSESSIONID") || cookie.getName().equals("USER") || cookie.getName().equals("USER_ID")){
                    // 쿠키에 JSESSIONID 또는 USER 또는 USER_ID가 있을 경우
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }

}
