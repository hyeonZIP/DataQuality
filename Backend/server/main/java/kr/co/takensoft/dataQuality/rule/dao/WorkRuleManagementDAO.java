package kr.co.takensoft.dataQuality.rule.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * 업무 규칙 관리 DAO
 *
 * @author 김준식
 * @since 2024.01.23
 */

@Mapper
public interface WorkRuleManagementDAO {

    /**
     * 업무 규칙 관리에 필요한 DBMS명을 불러옴
     *
     * @author 김준식
     * @since 2024.01.23
     */
    public List<HashMap<String,Object>> dbmsList(HashMap<String, Object> params);

    /**
     * 업무 규칙 관리에 필요한 스키마명을 불러옴
     *
     * @author 김준식
     * @since 2024.01.24
     */
    public List<HashMap<String, Object>> schemaList(HashMap<String, Object> params);

    /**
     * 업무 규칙 관리에 필요한 테이블명을 불러옴
     *
     * @author 김준식
     * @since 2024.01.24
     */
    public List<HashMap<String, Object>> tableList(HashMap<String, Object> params);

    /**
     * 업무 규칙 관리에 필요한 컬럼명을 불러옴
     *
     * @author 김준식
     * @since 2024.01.24
     */
    public List<HashMap<String, Object>> columnList(HashMap<String, Object> params);

    /**
     * 업무 규칙 관리에 저장된 모든 데이터 항목을 불러옴
     *
     * @author 김준식
     * @since 2024.01.24
     */
    public List<HashMap<String, Object>> workRuleList(HashMap<String, Object> params);


    /**
     * 업무 규칙 관리 등록
     *
     * @author 김준식
     * @since 2024.01.24
     */
    public int insertWorkRule(HashMap<String, Object> params);

    /**
     * 업무 규칙 관리 수정
     *
     * @author 김준식
     * @since 2024.01.24
     */
    public int updateWorkRule(HashMap<String, Object> params);

    /**
     * 업무 규칙 관리 삭제
     *
     * @author 김준식
     * @since 2024.01.24
     */
    public int deleteWorkRule(HashMap<String, Object> params);

    /**
     * 검색조건에 따른 업무규칙 데이터의 총 개수
     *
     * @author 김준식
     * @since 2024.01.25
     */
    public int wrListCount(HashMap<String, Object> params);

    /**
     * 최신 업무 규칙 id 불러오기
     *
     * @author 박민혁
     * @since 2024.02.07
     */
    public HashMap<String, Object> selectWorkRuleId(HashMap<String, Object> params);

}
