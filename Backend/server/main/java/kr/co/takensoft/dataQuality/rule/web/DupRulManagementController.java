package kr.co.takensoft.dataQuality.rule.web;

import kr.co.takensoft.dataQuality.rule.service.DupRulManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

@Controller
public class DupRulManagementController {
    @Autowired
    private DupRulManagementService dupRulManagementService;
    /**
     * 컬럼 조회
     *
     * @author 정다정
     * @since 2024.01.26
     */
    @RequestMapping(value = "/selectDupRuleColumn.json", method = RequestMethod.POST)
    public ModelAndView selectTableList(@RequestBody HashMap<String,Object> params) throws Exception{
        System.out.println("컬럼 조회 컨트롤러(params)");
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("columnList", dupRulManagementService.selectColumn(params));
        mav.addObject("columnListCount", dupRulManagementService.selectColumnCount(params));
        return mav;
    }

    /**
     * 중복 분석 컬럼 저장
     *
     * @author 정다정
     * @since 2024.01.26
     */
    @RequestMapping(value = "/insertDupRuleColumn.json", method = RequestMethod.POST)
    public ModelAndView insertDupRule(@RequestBody HashMap<String,Object> params) throws Exception {
        System.out.println("컬럼 저장 컨트롤러(params)");
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", dupRulManagementService.insertDupRule(params));
        return mav;
    }

    /**
     * 중복 분석 컬럼 저장 취소
     *
     * @author 정다정
     * @since 2024.02.01
     */
    @RequestMapping(value = "/deleteDupRuleColumn.json", method = RequestMethod.POST)
    public ModelAndView deleteDupRule(@RequestBody HashMap<String,Object> params) throws Exception {
        System.out.println("컬럼 삭제 컨트롤러(params)");
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", dupRulManagementService.deleteDupRule(params));
        return mav;
    }

    /**
     * 중복 분석 선택 대상 테이블 조회, 중복검사 선택 대상 테이블 페이징
     *
     * @author 임재현
     * @since 2024.02.06
     */
    @RequestMapping(value = "/selectCheckedList.json", method = RequestMethod.POST)
    public ModelAndView selectCheckedList(@RequestBody HashMap<String,Object> params) throws Exception {
        System.out.println("selectCheckedList");
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("table", dupRulManagementService.selectCheckedList(params));
        mav.addObject("count", dupRulManagementService.countCheckedList(params));
        return mav;
    }
}
