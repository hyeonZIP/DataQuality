package kr.co.takensoft.dataQuality.data.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import kr.co.takensoft.dataQuality.data.service.TableDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
public class TableDataController {
    @Autowired
    private TableDataService tableDataService;

    /**
     * 검색용 DBMS 명 조회
     *
     * @author 정다정
     * @since 2024.01.23
     */
    @RequestMapping(value = "/selectDbmsName.json", method = RequestMethod.POST)
    public ModelAndView selectDbName(@RequestBody HashMap<String,Object> params) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", tableDataService.selectDbmsName(params));
        return mav;
    }

    /**
     * 검색용 스키마명 조회
     *
     * @author 정다정
     * @since 2024.01.23
     */
    @RequestMapping(value = "/selectSchemaName.json", method = RequestMethod.POST)
    public ModelAndView selectSchemaName(@RequestBody HashMap<String,Object> params) throws Exception{
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", tableDataService.selectSchemaName(params));
        return mav;
    }

    /**
     * 테이블 조회
     *
     * @author 정다정
     * @since 2024.01.23
     */
    @RequestMapping(value = "/selectTableList.json", method = RequestMethod.POST)
    public ModelAndView selectTableList(@RequestBody HashMap<String,Object> params) throws Exception{
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("tableList", tableDataService.selectTableList(params));
        mav.addObject("tableListCount", tableDataService.tableListCount(params));
        mav.addObject("result", tableDataService.selectTableList(params));
        return mav;
    }

    /**
     * 테이블 제외 여부 및 사유 저장
     *
     * @author 정다정
     * @since 2024.01.24
     */
    @RequestMapping(value = "/updateTableData.json", method = RequestMethod.POST)
    public ModelAndView updateTableData(@RequestBody HashMap<String,Object> params) throws Exception{
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", tableDataService.updateTableData(params));
        return mav;
    }

    /**
     * 메인화면용 테이블 수 select
     *
     * @author 박민혁
     * @since 2024.01.25
     */
    @RequestMapping(value = "/tableListAllCount.json", method = RequestMethod.POST)
    public ModelAndView tableListAllCount(@RequestBody HashMap<String,Object> params) throws Exception{
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", tableDataService.selectTableListAllCount(params));
        return mav;
    }

}