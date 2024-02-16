package kr.co.takensoft.dataQuality.data.web;

import kr.co.takensoft.dataQuality.data.service.DQIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
public class DQIController {

    @Autowired
    private DQIService DQIService;
    /**
     * 품질지표조회
     *
     * @author 임재현
     * @since 2024.01.16
     */

    @RequestMapping(value = "/selectDqiTable.json", method = RequestMethod.POST)
    public ModelAndView selectDqiTable(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", DQIService.selectDqiTable(params));
        return mav;
    }
}