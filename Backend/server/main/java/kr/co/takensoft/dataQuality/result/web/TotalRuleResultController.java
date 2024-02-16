package kr.co.takensoft.dataQuality.result.web;

import kr.co.takensoft.dataQuality.result.servicee.TotalRuleResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
public class TotalRuleResultController {
    @Autowired
    private TotalRuleResultService totalRuleResultService;
    /**
     * 품질진단종합현황 메인 테이블 조회
     * @author 임재현
     * @since 2024.01.31
     */
    @RequestMapping(value = "/selectTotalResult.json", method = RequestMethod.POST)
    public ModelAndView selectTotalResult(@RequestBody HashMap<String, Object> params) throws Exception{
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("table", totalRuleResultService.selectTotalResult(params));
        return mav;
    }
}
