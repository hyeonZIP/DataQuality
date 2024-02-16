package kr.co.takensoft.dataQuality.rule.web;

import kr.co.takensoft.dataQuality.rule.service.DomainRuleManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
public class DomainRuleManagementController {
    @Autowired
    private DomainRuleManagementService domainRuleManagementService;

    /**
     * 도메인 규칙 관리 select.
     *
     * @author 박민혁
     * @since 2024.01.23
     */
    @RequestMapping(value = "/selectDomainRule.json", method = RequestMethod.POST)
    public ModelAndView selectDomainRule(@RequestBody HashMap<String, Object> params) throws Exception {
        System.out.println("selectDomainRule Controller");
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("list", domainRuleManagementService.selectDomainRule(params));
        mav.addObject("listCount", domainRuleManagementService.selectDomainRuleCount(params));
        return mav;
    }

    /**
     * 도메인 규칙 관리 update.
     *
     * @author 박민혁
     * @since 2024.01.24
     */
    @RequestMapping(value = "/updateDomainRule.json", method = RequestMethod.POST)
    public ModelAndView updateDomainRule(@RequestBody HashMap<String, Object> params) throws Exception {
        System.out.println("updateDomainRule Controller");
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", domainRuleManagementService.updateDomainRule(params));
        return mav;
    }

    /**
     * 도메인 규칙 연결 컬럼 수 count select.
     *
     * @author 박민혁
     * @since 2024.01.23
     */
    @RequestMapping(value = "/selectDomainRuleCount.json", method = RequestMethod.POST)
    public ModelAndView selectDomainRuleCount(@RequestBody HashMap<String, Object> params) throws Exception {
        System.out.println("selectDomainRuleCount Controller");
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", domainRuleManagementService.selectDomainRuleCount(params));
        return mav;
    }

}
