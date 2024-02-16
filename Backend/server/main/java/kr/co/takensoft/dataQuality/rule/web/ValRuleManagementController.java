package kr.co.takensoft.dataQuality.rule.web;

import kr.co.takensoft.dataQuality.rule.service.ValRuleManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
public class ValRuleManagementController {

    @Autowired
    private ValRuleManagementService valRuleListService;
    /**
     * 검증룰 검색조건 중 검증유형 SELECT
     *
     * @author 임재현
     * @since 2024.01.17
     */
    @RequestMapping(value = "/selectDqiType.json", method = RequestMethod.POST)
    public ModelAndView search_DQI_type(@RequestBody HashMap<String, Object> params) throws Exception {
        System.out.println("selectDqiType Controller");
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", valRuleListService.selectDqiType(params));
        return mav;
    }

    /**
     * 검증룰 테이블 조회
     * selectValRule
     *
     * @author 임재현
     * @since 2024.01.18
     */
    @RequestMapping(value = "/selectValRule.json", method = RequestMethod.POST)
    public ModelAndView selectValRule(@RequestBody HashMap<String, Object> params) throws Exception {
        System.out.println("selectValRule Controller");
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("valRuleList", valRuleListService.selectValRule(params));
        mav.addObject("count", valRuleListService.dbSelectListCount(params));
        return mav;
    }

    /**
     * 검증룰 추가
     * insertValRule
     *
     * @author 임재현
     * @since 2024.01.18
     */
    @RequestMapping(value = "/insertValRule.json", method = RequestMethod.POST)
    public ModelAndView insertValRule(@RequestBody HashMap<String, Object> params) throws Exception {
        System.out.println("insertValRule Controller");
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", valRuleListService.insertValRule(params));
        return mav;
    }

    /**
     * 검증룰 삭제
     * deleteValRule
     *
     * @author 임재현
     * @since 2024.01.18
     */
    @RequestMapping(value = "/deleteValRule.json", method = RequestMethod.POST)
    public ModelAndView deleteValRule(@RequestBody int[] params) throws Exception {
        System.out.println("deleteValRule Controller");
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", valRuleListService.deleteValRule(params));
        return mav;
    }

    /**
     * 선택된 검증룰 수정
     * updateValRule
     *
     * @author 임재현
     * @since 2024.01.18
     */
    @RequestMapping(value = "/updateValRule.json", method = RequestMethod.POST)
    public ModelAndView updateValRule(@RequestBody HashMap<String, Object> params) throws Exception {
        System.out.println("updateValRule Controller");
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", valRuleListService.updateValRule(params));
        return mav;
    }
}





