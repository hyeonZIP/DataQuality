package kr.co.takensoft.dataQuality.schedule.service;

import kr.co.takensoft.dataQuality.schedule.dao.ScheduleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleDAO scheduleDAO;

    /**
     * 검색 조건에 따른 메인 테이블 SELECT
     * @author 임재현
     * @since 2024.01.26
     */
    public List<HashMap<String, Object>> selectScheduleManager (HashMap<String, Object> params) throws Exception {
        int currentPage = (int) params.get("currentPage");
        int perPage = (int) params.get("perPage");
        int startIndex = (currentPage-1)*perPage;
        params.put("startIndex", startIndex);

        return scheduleDAO.selectScheduleManager(params);
    }
    /**
     * 검색 조건에 따른 메인 테이블 페이징
     * @author 임재현
     * @since 2024.01.26
     */
    public int countScheduleManager (HashMap<String,Object>params) throws Exception{
        return scheduleDAO.countScheduleManager(params);
    }

    /**
     * 선택 테이블 상세조회
     * @author 임재현
     * @since 2024.01.30
     */
    public List<HashMap<String, Object>> selectScheduleDetail (HashMap<String, Object> params) throws Exception {
        int currentPage = (int) params.get("currentPage");
        int perPage = (int) params.get("perPage");
        int startIndex = (currentPage-1)*perPage;
        params.put("startIndex", startIndex);

        return scheduleDAO.selectScheduleDetail(params);
    }
    /**
     * 선택 테이블 상세조회 테이블 페이징
     * @author 임재현
     * @since 2024.01.30
     */
    public int countScheduleDetail (HashMap<String,Object>params) throws Exception{
        return scheduleDAO.countScheduleDetail(params);
    }

    /**
     * 스케줄 결과 삭제
     * @author 임재현
     * @since 2024.01.30
     */
    public int deleteSchedule (HashMap<String,Object>params) throws Exception{
        return scheduleDAO.deleteSchedule(params);
    }
}
