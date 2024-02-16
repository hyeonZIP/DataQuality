package kr.co.takensoft.dataQuality.data.web;

import kr.co.takensoft.dataQuality.data.service.ColumnDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
public class ColumnDataController {
    @Autowired
    private ColumnDataService columnManagementService;

    /**
     * 검색 DBMS 선택
     *
     * @author 임재현
     * @since 2024.01.24
     */
    @RequestMapping(value = "/selectDbms.json", method = RequestMethod.POST)
    public ModelAndView selectDbms(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", columnManagementService.selectDbms(params));
        return mav;
    }
    /**
     * 검색 스키마 선택
     *
     * @author 임재현
     * @since 2024.01.24
     */
    @RequestMapping(value = "/selectSchema.json", method = RequestMethod.POST)
    public ModelAndView selectSchema(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", columnManagementService.selectSchema(params));
        return mav;
    }


    /**
     * 검색결과 테이블 조회
     *
     * @author 임재현
     * @since 2024.01.24
     */
    @RequestMapping(value = "/selectSearchResult.json", method = RequestMethod.POST)
    public ModelAndView selectSearchResult(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("table", columnManagementService.selectSearchResult(params));
        mav.addObject("count", columnManagementService.searchResultCount(params));
        return mav;
    }


    /**
     * 테이블컬럼 조회
     *
     * @author 임재현
     * @since 2024.01.25
     */
    @RequestMapping(value = "/selectTableColumn.json", method = RequestMethod.POST)
    public ModelAndView selectTableColumn(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("table1", columnManagementService.selectTableColumn(params));
        mav.addObject("count1", columnManagementService.tableColumnCount(params));
        return mav;
    }

    /**
     * 컬럼 분석
     *
     * @author 박민혁
     * @since 2024.01.26
     */
    @RequestMapping(value = "/analyzeColumn.json", method = RequestMethod.POST)
    public ModelAndView analyzeColumn(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", columnManagementService.analyzeColumn(params));
        return mav;
    }

    /**
     * 컬럼 분석 select
     *
     * @author 박민혁
     * @since 2024.01.26
     */
    @RequestMapping(value = "/selectColumnAnl.json", method = RequestMethod.POST)
    public ModelAndView selectColumnAnl(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", columnManagementService.selectColumnAnl(params));
        return mav;
    }
}
