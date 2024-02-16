package kr.co.takensoft.dataQuality.rule.web;

import kr.co.takensoft.dataQuality.rule.service.CodeManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

@Controller
public class CodeManagementController {
    @Autowired
    private CodeManagementService codeMgService;


    /**
     * 코드 관리 데이터 목록 조회(검색 포함).
     *
     * @author 김준식
     * @since 2024.01.18
     */
    @RequestMapping(value = "/selectCodeList.json" , method = RequestMethod.POST)
    public ModelAndView selectCodeList(@RequestBody HashMap<String, Object> params){
        System.out.println("CodeManagementController - selectCodeList");
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("cmList", codeMgService.selectCodeList(params));
        modelAndView.addObject("cmListCount", codeMgService.cmTotalCount(params));

        return modelAndView;
    }

    /**
     * 코드 관리 데이터 추가.
     *
     * @author 김준식
     * @since 2024.01.18
     */
    @RequestMapping(value = "/insertCode.json", method = RequestMethod.POST)
    public ModelAndView insertCode(@RequestBody HashMap<String, Object> params) {
        System.out.println("CodeManagementController - insertCode");
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", codeMgService.insertCode(params));

        return modelAndView;
    }

    /**
     * 코드 관리 데이터 수정.
     *
     * @author 김준식
     * @since 2024.01.18
     */
    @RequestMapping(value = "/updateCode.json", method = RequestMethod.POST)
    public ModelAndView updateCode(@RequestBody HashMap<String, Object> params) {
        System.out.println("CodeManagementController - updateCode");
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", codeMgService.updateCode(params));

        return modelAndView;
    }

    /**
     * 선택한 코드 관리 데이터 삭제.
     *
     * @author 김준식
     * @since 2024.01.18
     */
    @RequestMapping(value = "/deleteCode.json", method = RequestMethod.POST)
    public ModelAndView deleteCode(@RequestBody List<Integer> params) {
        System.out.println("CodeManagementController - deleteCode");
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", codeMgService.deleteCode(params));

        return modelAndView;
    }

    /**
     * 코드 관리 모든 항목 삭제.
     *
     * @author 김준식
     * @since 2024.01.22
     */
    @RequestMapping(value = "/allCodeDelete.json", method = RequestMethod.POST)
    public ModelAndView allCodeDelete(@RequestBody HashMap<String, Object> params) {
        System.out.println("CodeManagementController - allCodeDelete");
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", codeMgService.allCodeDelete(params));

        return modelAndView;
    }

    /**
     * 목록들 중에서 하나의 코드 관리 데이터 조회.
     *
     * @author 김준식
     * @since 2024.01.18
     */
    @RequestMapping(value = "/oneSelectCode.json", method = RequestMethod.POST)
    public ModelAndView oneSelectCode(@RequestBody HashMap<String, Object> params){
        System.out.println("CodeManagementController - oneSelectCode");
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result",codeMgService.oneSelectCode(params));

        return modelAndView;
    }

    /**
     * 검색조건(DBMS 이름, 코드유형) 데이터들을 조회.
     *
     * @author 김준식
     * @since 2024.01.18
     */
    @RequestMapping(value = "/getOptionValue.json", method = RequestMethod.POST)
    public ModelAndView getOptionValue(@RequestBody HashMap<String, Object> params) {
        System.out.println("CodeManagementController - getOptionValue");
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("DB_name", codeMgService.getDBDataName(params));

        return modelAndView;
    }

}
