package kr.co.takensoft.dataQuality.result.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface TotalRuleResultDAO {
    /**
     * 품질진단종합현황 페이지
     * 메인 테이블 조회
     * @author 임재현
     * @since 2024.01.31
     */
    public List<HashMap<String, Object>> selectTotalResult (HashMap<String,Object> params) throws Exception;
}
