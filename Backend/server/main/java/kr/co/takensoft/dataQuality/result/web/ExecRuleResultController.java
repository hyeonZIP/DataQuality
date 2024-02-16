package kr.co.takensoft.dataQuality.result.web;

import kr.co.takensoft.dataQuality.result.servicee.ExecRuleResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
public class ExecRuleResultController {

    @Autowired
    ExecRuleResultService execRuleResultService;
    /**
     * 진단상세결과현황 메인 테이블 조회
     * @author 임재현
     * @since 2024.01.30
     */
    @RequestMapping(value = "/selectExecResult.json", method = RequestMethod.POST)
    public ModelAndView selectExecResult(@RequestBody HashMap<String, Object> params) throws Exception{
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("table", execRuleResultService.selectExecResult(params));
        mav.addObject("count", execRuleResultService.countExecResult(params));
        return mav;
    }
}
