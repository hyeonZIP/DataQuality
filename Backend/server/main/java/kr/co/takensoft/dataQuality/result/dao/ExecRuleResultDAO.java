package kr.co.takensoft.dataQuality.result.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ExecRuleResultDAO {
    /**
     * 메인 테이블 조회
     * @author 임재현
     * @since 2024.01.30
     */
    public List<HashMap<String, Object>> selectExecResult (HashMap<String, Object> params) throws Exception;
    /**
     * 메인 테이블 COUNT
     * @author 임재현
     * @since 2024.01.30
     */
    public int countExecResult  (HashMap<String,Object> params) throws Exception;
}
