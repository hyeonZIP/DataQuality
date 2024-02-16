package kr.co.takensoft.dataQuality.schedule.web;

import kr.co.takensoft.dataQuality.schedule.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    /**
     * 스케줄 실행 결과 조회 페이지
     * 검색 조건에 따른 메인 테이블 SELECT
     * @author 임재현
     * @since 2024.01.26
     */
    @RequestMapping(value = "/selectScheduleManager.json", method = RequestMethod.POST)
    public ModelAndView selectScheduleManager(@RequestBody HashMap<String, Object> params) throws Exception {
        System.out.println("selectScheduleManager Controller");
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("table", scheduleService.selectScheduleManager(params));
        mav.addObject("count", scheduleService.countScheduleManager(params));
        return mav;
    }

    /**
     * 스케줄 실행 결과 조회 페이지
     * 선택 테이블 상세조회
     * @author 임재현
     * @since 2024.01.30
     */
    @RequestMapping(value = "/selectScheduleDetail.json", method = RequestMethod.POST)
    public ModelAndView selectScheduleDetail(@RequestBody HashMap<String, Object> params) throws Exception {
        System.out.println("selectScheduleDetail Controller");
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("table", scheduleService.selectScheduleDetail(params));
        mav.addObject("count", scheduleService.countScheduleDetail(params));
        return mav;
    }

    /**
     * 스케줄 실행 결과 조회 페이지
     * 스케줄 결과 삭제
     * @author 임재현
     * @since 2024.01.30
     */
    @RequestMapping(value = "/deleteSchedule.json", method = RequestMethod.POST)
    public ModelAndView deleteSchedule(@RequestBody HashMap<String, Object> params) throws Exception {
        System.out.println("deleteSchedule Controller");
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("table", scheduleService.deleteSchedule(params));
        return mav;
    }
}
