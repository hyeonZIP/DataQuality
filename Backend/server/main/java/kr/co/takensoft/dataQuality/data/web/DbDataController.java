package kr.co.takensoft.dataQuality.data.web;

import kr.co.takensoft.dataQuality.data.service.DbDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

@Controller
public class DbDataController {

    @Autowired
    private DbDataService dbDataService;

    /**
     * DB 데이터 select.
     *
     * @author 박민혁
     * @since 2024.01.17
     */
    @RequestMapping(value = "/selectDbList.json", method = RequestMethod.POST)
    public ModelAndView selectDbData(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("dbList", dbDataService.selectDbList(params));
        mav.addObject("dbListCount", dbDataService.selectDbCount(params));
        return mav;
    }

    /**
     * DB 데이터 insert.
     *
     * @author 박민혁
     * @since 2024.01.17
     */
    @RequestMapping(value = "/insertDbData.json", method = RequestMethod.POST)
    public ModelAndView insertDbData(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", dbDataService.insertDbData(params));
        return mav;
    }
    /**
     * DB 연결.
     *
     * @author 박민혁
     * @since 2024.01.18
     */
    @RequestMapping(value = "/connectDb.json", method = RequestMethod.POST)
    public ModelAndView connectDb(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", dbDataService.connectDb(params));
        return mav;
    }
    /**
     * DB 스키마 수집.
     *
     * @author 박민혁
     * @since 2024.01.18
     */
    @RequestMapping(value = "/collectSchema.json", method = RequestMethod.POST)
    public ModelAndView collectSchema(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", dbDataService.collectSchema(params));
        return mav;
    }

    /**
     * DB 데이터 delete하며 동시에 연결된 스키마 data도 delete.
     *
     * @author 박민혁
     * @since 2024.01.18
     */
    @RequestMapping(value = "/deleteDbData.json", method = RequestMethod.POST)
    public ModelAndView deleteDbData(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", dbDataService.deleteDbData(params));
        return mav;
    }

    /**
     * DB 데이터 update.
     *
     * @author 박민혁
     * @since 2024.01.22
     */
    @RequestMapping(value = "/updateDbData.json", method = RequestMethod.POST)
    public ModelAndView updateDbData(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", dbDataService.updateDbData(params));
        return mav;
    }


}
