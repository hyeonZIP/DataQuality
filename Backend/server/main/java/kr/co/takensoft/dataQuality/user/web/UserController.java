package kr.co.takensoft.dataQuality.user.web;

import kr.co.takensoft.dataQuality.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    /**
     * users 데이터 조회
     *
     * @author 박민혁
     * @since 2024.01.16
     */
    @RequestMapping(value = "/selectUsers.json", method = RequestMethod.POST)
    public ModelAndView selectUsers(@RequestBody HashMap<String, Object> params) throws Exception {
        System.out.println("selectUsers Controller");
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", userService.selectUsers(params));
        return mav;
    }

    /**
     * users 데이터 등록
     *
     * @author 박민혁
     * @since 2024.01.16
     */
    @RequestMapping(value = "/insertUsers.json", method = RequestMethod.POST)
    public ModelAndView insertUsers(@RequestBody HashMap<String, Object> params) throws Exception {
        System.out.println("insertUsers Controller");
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", userService.insertUsers(params));
        return mav;
    }

}
