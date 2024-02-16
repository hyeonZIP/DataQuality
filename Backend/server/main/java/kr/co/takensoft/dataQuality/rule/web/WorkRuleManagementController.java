package kr.co.takensoft.dataQuality.rule.web;

import kr.co.takensoft.dataQuality.rule.service.WorkRuleManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

/**
 * 업무 규칙 관리 Controller
 *
 * @author 김준식
 * @since 2024.01.23
 */
@Controller
public class WorkRuleManagementController {
    @Autowired
    private WorkRuleManagementService wrManagementService;

    /**
     * 업무 규칙 관리에 필요한 DBMS명을 불러옴
     *
     * @author 김준식
     * @since 2024.01.23
     */
    @RequestMapping(value = "/getDbmsList.json", method = RequestMethod.POST)
    public ModelAndView dbmsList(@RequestBody HashMap<String, Object> params) {
        System.out.println("WorkRuleManagementController - getDbmsList");
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("dbmsList",wrManagementService.dbmsList(params));

        return modelAndView;
    }

    /**
     * 업무 규칙 관리에 필요한 스키마명을 불러옴
     *
     * @author 김준식
     * @since 2024.01.24
     */
    @RequestMapping(value = "/getSchemaList.json", method = RequestMethod.POST)
    public ModelAndView schemaList(@RequestBody HashMap<String, Object> params){
        System.out.println("WorkRuleManagementController - getSchemaList");
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("schemaList", wrManagementService.schemaList(params));

        return modelAndView;
    }

    /**
     * 업무 규칙 관리에 필요한 테이블명을 불러옴
     *
     * @author 김준식
     * @since 2024.01.24
     */
    @RequestMapping(value = "/getTableList.json", method = RequestMethod.POST)
    public ModelAndView tableList(@RequestBody HashMap<String, Object> params){
        System.out.println("WorkRuleManagementController - getTableList");
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("tableList", wrManagementService.tableList(params));

        return modelAndView;
    }

    /**
     * 업무 규칙 관리에 필요한 컬럼명을 불러옴
     *
     * @author 김준식
     * @since 2024.01.24
     */
    @RequestMapping(value = "/getColumnList.json", method = RequestMethod.POST)
    public ModelAndView columnList(@RequestBody HashMap<String, Object> params){
        System.out.println("WorkRuleManagementController - getColumnList");
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("columnList", wrManagementService.columnList(params));

        return modelAndView;
    }

    /**
     * 업무 규칙 관리에 저장된 모든 데이터 항목을 불러옴
     *
     * @author 김준식
     * @since 2024.01.24
     */
    @RequestMapping(value = "/allWorkRuleList.json", method = RequestMethod.POST)
    public ModelAndView allWorkRuleList(@RequestBody HashMap<String, Object> params){
        System.out.println("WorkRuleManagementController - allWorkRuleList");
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("workRuleList", wrManagementService.workRuleList(params));
        modelAndView.addObject("wrListCount", wrManagementService.wrListCount(params));

        return modelAndView;
    }

    /**
     * 업무 규칙 관리 등록
     *
     * @author 김준식
     * @since 2024.01.24
     */
    @RequestMapping(value = "/insertWorkRule.json", method = RequestMethod.POST)
    public ModelAndView insertWorkRule(@RequestBody HashMap<String, Object> params){
        System.out.println("WorkRuleManagementController - insertWorkRule");
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", wrManagementService.insertWorkRule(params));

        return modelAndView;
    }

    /**
     * 업무 규칙 관리 수정
     *
     * @author 김준식
     * @since 2024.01.24
     */
    @RequestMapping(value = "/updateWorkRule.json", method = RequestMethod.POST)
    public ModelAndView updateWorkRule(@RequestBody HashMap<String, Object> params){
        System.out.println("WorkRuleManagementController - updateWorkRule");
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", wrManagementService.updateWorkRule(params));

        return modelAndView;
    }

    /**
     * 업무 규칙 관리 삭제
     *
     * @author 김준식
     * @since 2024.01.24
     */
    @RequestMapping(value = "/deleteWorkRule.json", method = RequestMethod.POST)
    public ModelAndView deleteWorkRule(@RequestBody List<Integer> params) {
        System.out.println("WorkRuleManagementController - deleteWorkRule");
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", wrManagementService.deleteWorkRule(params));

        return modelAndView;
    }

    /**
     * 업무 규칙 관리에 필요한 dbms명, 스키마명, 테이블명, 컬럼명을 한번에 불러옴
     *
     * @author 김준식
     * @since 2024.01.24
     */
    @RequestMapping(value = "/getSelectList.json", method = RequestMethod.POST)
    public ModelAndView getSelectList(@RequestBody HashMap<String, Object> params){
        System.out.println("WorkRuleManagementController - getSelectList");
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("schemaList", wrManagementService.schemaList(params));
        modelAndView.addObject("tableList", wrManagementService.tableList(params));
        modelAndView.addObject("columnList",wrManagementService.columnList(params));

        return modelAndView;
    }
}
