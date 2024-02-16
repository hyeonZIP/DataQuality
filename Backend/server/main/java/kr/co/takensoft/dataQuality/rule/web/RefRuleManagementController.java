package kr.co.takensoft.dataQuality.rule.web;

import kr.co.takensoft.dataQuality.rule.service.RefRuleManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

/**
 *  데이터 규칙 관리
 *
 * @author 김준식
 * @since 2024.01.26
 */
@Controller
public class RefRuleManagementController {

    @Autowired
    private RefRuleManagementService refRuleManagementService;

    /** 데이터규칙 관리 부분 **/
    /**
     * 검색조건에 필요한 DBMS명 검색
     *
     * @author 김준식
     * @since 2024.01.29
     */
    @RequestMapping(value = "/getDbmsNameList.json", method = RequestMethod.POST)
    public ModelAndView getDbmsNameList(@RequestBody HashMap<String, Object> params) {
        System.out.println("refRuleManagementController - dbmsList");
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("dbmsList", refRuleManagementService.dbmsList(params));

        return modelAndView;
    }

    /**
     * 검색조건에 필요한 스키마명 검색
     *
     * @author 김준식
     * @since 2024.01.29
     */
    @RequestMapping(value = "/getSchemaNameList.json", method = RequestMethod.POST)
    public ModelAndView getSchemaNameList(@RequestBody HashMap<String, Object> params){
        System.out.println("refRuleManagementController - getSchemaNameList");
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("schemaList", refRuleManagementService.schemaList(params));

        return modelAndView;
    }

    /**
     * 검색을 하였을 때 나오는 데이터 목록들
     *
     * @author 김준식
     * @since 2024.01.29
     */
    @RequestMapping(value = "/searchDataList.json", method = RequestMethod.POST)
    public ModelAndView searchDataList(@RequestBody HashMap<String, Object> params){
        System.out.println("refRuleManagementController - searchDataList");
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("searchDataList", refRuleManagementService.searchDataList(params));
        modelAndView.addObject("searchDataListCount", refRuleManagementService.searchDataListCount(params));

        return modelAndView;
    }

    /**
     * 조회된 목록들중 하나를 선택
     *
     * @author 김준식
     * @since 2024.01.29
     */
    @RequestMapping(value = "/oneSelectList.json", method = RequestMethod.POST)
    public ModelAndView oneSelectList(@RequestBody HashMap<String, Object> params){
        System.out.println("refRuleManagementController - oneSelectList");
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("selectList", refRuleManagementService.oneSelectList(params));
        modelAndView.addObject("oneListDataCount", refRuleManagementService.oneSelectListCount(params));

        return modelAndView;
    }

    /********************* 참조 무결설 추가 부분 *************************************/
    /**
     * 부모테이블 검색 조건을 통해 데이터 조회
     *
     * @author 김준식
     * @since 2024.01.26
     */
    @RequestMapping(value = "/searchParentTable.json", method = RequestMethod.POST)
    public ModelAndView searchParentTable(@RequestBody HashMap<String, Object> params) {
        System.out.println("RefRuleManagement - searchParentTable");
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("searchList", refRuleManagementService.searchParentTable(params));
        modelAndView.addObject("searchListCount", refRuleManagementService.searchParentTableCount(params));

        return modelAndView;
    }

    /**
     * 자식테이블 데이터 조회
     *
     * @author 김준식
     * @since 2024.01.26
     */
    @RequestMapping(value = "/childTableList.json", method = RequestMethod.POST)
    public ModelAndView childTableList(@RequestBody HashMap<String, Object> params){
        System.out.println("RefRuleManagement - childTableList");
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("childTableList", refRuleManagementService.childTableList(params));

        return modelAndView;
    }

    /**
     * 참조무결성 등록
     *
     * @author 김준식
     * @since 2024.01.30
     */
    @RequestMapping(value = "/insertRef.json", method = RequestMethod.POST)
    public ModelAndView registerRef(@RequestBody List<HashMap<String, Object>> params){
        System.out.println("RefRuleManagement - insertRef");
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", refRuleManagementService.insertRef(params));

        return modelAndView;
    }

    /**
     * 참조무결성 삭제
     *
     * @author 김준식
     * @since 2024.01.30
     */
    @RequestMapping(value = "/deleteRef.json", method = RequestMethod.POST)
    public ModelAndView deleteRef(@RequestBody List<Integer> params) {
        System.out.println("RefRuleManagement - deleteRef");
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", refRuleManagementService.deleteRef(params));

        return modelAndView;
    }
}
