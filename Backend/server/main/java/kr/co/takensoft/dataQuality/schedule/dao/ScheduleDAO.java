package kr.co.takensoft.dataQuality.schedule.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ScheduleDAO {

    /**
     * 검색 조건에 따른 메인 테이블 SELECT
     * @author 임재현
     * @since 2024.01.26
     */
    public List<HashMap<String,Object>> selectScheduleManager(HashMap<String,Object> params) throws Exception;

    /**
     * 검색 조건에 따른 메인 테이블 페이징
     * @author 임재현
     * @since 2024.01.26
     */
    public int countScheduleManager(HashMap<String,Object> params) throws Exception;

    /**
     * 선택 테이블 상세조회
     * @author 임재현
     * @since 2024.01.26
     */
    public List<HashMap<String,Object>> selectScheduleDetail(HashMap<String,Object> params) throws Exception;

    /**
     * 선택 테이블 상세조회 테이블 페이징
     * @author 임재현
     * @since 2024.01.26
     */
    public int countScheduleDetail(HashMap<String,Object> params) throws Exception;


    /**
     * 선택 테이블 상세조회 테이블 페이징
     * @author 임재현
     * @since 2024.01.26
     */
    public int deleteSchedule(HashMap<String,Object> params) throws Exception;
}
