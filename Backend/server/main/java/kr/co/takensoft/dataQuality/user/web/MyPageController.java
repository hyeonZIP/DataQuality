package kr.co.takensoft.dataQuality.user.web;


import kr.co.takensoft.dataQuality.user.service.MyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
public class MyPageController {
    @Autowired
    private MyPageService myPageService;

    /**
     * 사용자 비밀 번호 변경
     *
     * @author 정다정
     * @since 2024.01.22
     */
    @RequestMapping(value="/updateUserPw.json", method = RequestMethod.POST)
    public ModelAndView updateUserPw(@RequestBody HashMap<String,Object> params) throws Exception{
        System.out.println("updatePW Controller");
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", myPageService.updateUserPw(params));
        return mav;
    }

    /**
     * 사용자 이메일 가져 오기
     *
     * @author 정다정
     * @since 2024.02.01
     */
    @RequestMapping(value = "/getUserEmail.json",method = RequestMethod.POST)
    public ModelAndView getUserEmail(@RequestBody HashMap<String,Object> params) throws Exception{
        System.out.println("getUserEmail Controller");
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", myPageService.getUserEmail(params));
        return mav;
    }
}
