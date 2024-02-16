package kr.co.takensoft.dataQuality.rule.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ValRuleManagementDAO {

    /**
     * 검증룰 검색조건 중 검증유형 SELECT
     *
     * @author 임재현
     * @since 2024.01.16
     */
    public List<HashMap<String, Object>> selectDqiType (HashMap<String, Object> params) throws Exception;

    /**
     * DB 목록 COUNT.
     *
     * @author 박민혁
     * @since 2024.01.19
     */
    public int dbSelectListCount(HashMap<String, Object> params) throws Exception;

    /**
     * 검증룰 테이블 조회
     *
     * @author 임재현
     * @since 2024.01.16
     */
    public List<HashMap<String, Object>> selectValRule (HashMap<String, Object> params) throws Exception;

    /**
     * 검증룰 추가
     *
     * @author 임재현
     * @since 2024.01.16
     */
    public int insertValRule(HashMap<String,Object> params) throws Exception;

    /**
     * 검증룰 수정
     *
     * @author 임재현
     * @since 2024.01.16
     */
    public int updateValRule(HashMap<String,Object> params) throws Exception;

    /**
     * 검증룰 삭제
     *
     * @author 임재현
     * @since 2024.01.16
     */
    public int deleteValRule(int[] params) throws Exception;
}
