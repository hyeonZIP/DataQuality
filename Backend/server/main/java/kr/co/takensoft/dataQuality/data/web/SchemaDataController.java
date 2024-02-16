package kr.co.takensoft.dataQuality.data.web;

import kr.co.takensoft.dataQuality.data.service.SchemaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

@Controller
public class SchemaDataController {
    @Autowired
    private SchemaDataService schemaDataService;

    /**
     * 진단대상 DB 관리 스키마 조회
     *
     * @author 정다정
     * @since 2024.01.17
     */
    @RequestMapping(value = "/selectSchemaList.json", method = RequestMethod.POST)
    public ModelAndView selectSchemaList(@RequestBody HashMap<String,Object> params) throws Exception{
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("schemaList", schemaDataService.selectSchemaList(params));
        mav.addObject("schemaListCount", schemaDataService.selectSchemaListCount(params));
        return mav;
    };
    /**
     * 진단대상 DB 관리 스키마 수정
     *
     * @author 정다정
     * @since 2024.01.17
     */
    @RequestMapping(value = "/updateSchema.json", method = RequestMethod.POST)
    public ModelAndView updateSchema(@RequestBody List<HashMap<String,Object>> params) throws Exception{
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", schemaDataService.updateSchema(params));
        return mav;
    }

    /**
     * 진단대상 DB 관리 스키마 숫자 조회
     *
     * @author 박민혁
     * @since 2024.01.25
     */
    @RequestMapping(value = "/schemaListAllCount.json", method = RequestMethod.POST)
    public ModelAndView schemaListAllCount(@RequestBody HashMap<String,Object> params) throws Exception{
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", schemaDataService.selectSchemaListAllCount(params));
        return mav;
    };

}