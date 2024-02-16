package kr.co.takensoft.dataQuality.user.web;

import kr.co.takensoft.dataQuality.user.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 로그인
     *
     * @author 김준식
     * @since 2024.01.16
     */
    @RequestMapping(value = "/login.json", method = RequestMethod.POST)
    public ModelAndView login(@RequestBody HashMap<String, Object> params,
                              HttpServletResponse response, HttpServletRequest request) {
        System.out.println("LoginController - Login");
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", loginService.login(params, request, response));

        return modelAndView;
    }

    /**
     * 로그아웃
     *
     * @author 김준식
     * @since 2024.01.16
     */
    @RequestMapping(value = "/logout.json", method = RequestMethod.POST)
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("LoginController - logout");
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", loginService.logout(request, response));

        return modelAndView;
    }


}
