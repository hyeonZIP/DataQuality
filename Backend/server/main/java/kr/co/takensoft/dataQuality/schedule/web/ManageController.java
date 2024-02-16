package kr.co.takensoft.dataQuality.schedule.web;

import kr.co.takensoft.dataQuality.schedule.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

@Controller
public class ManageController {

    @Autowired
    private ManageService manageService;

    /**
     * 총합 규칙 관리 select.
     *
     * @author 박민혁
     * @since 2024.01.23
     */
    @RequestMapping(value = "/selectManageRule.json", method = RequestMethod.POST)
    public ModelAndView selectManageRule(@RequestBody HashMap<String, Object> params) throws Exception {
        System.out.println("selectManageRule Controller");
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("manageList", manageService.selectManageRule(params));
        mav.addObject("count", manageService.selectManageRuleCount(params));
        return mav;
    }

    /*
     * 진단 규칙 실행
     *
     * @author 박민혁
     * @since 2024.02.02
     */
    @RequestMapping(value = "/execManageRule.json", method = RequestMethod.POST)
    public ModelAndView execManageRule (@RequestBody List<HashMap<String, Object>> paramList) throws Exception {
        System.out.println("execManageRule Controller");
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", manageService.execManageRule(paramList));
        return mav;
    }

}
