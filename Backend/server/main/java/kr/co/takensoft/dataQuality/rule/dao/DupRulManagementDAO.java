package kr.co.takensoft.dataQuality.rule.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface DupRulManagementDAO {
    /**
     * 컬럼 조회
     *
     * @author 정다정
     * @since 2024.01.26
     */
    public List<HashMap<String,Object>> selectColumn(HashMap<String,Object> params) throws Exception;

    /**
     * 컬럼 목록 COUNT
     *
     * @author 정다정
     * @since 2024.01.26
     */
    public int selectColumnCount(HashMap<String,Object> params) throws Exception;

    /**
     * 중복 분석 컬럼 저장
     *
     * @author 정다정
     * @since 2024.01.26
     */
    public int insertDupRule(HashMap<String,Object> params) throws Exception;

    /**
     * 중복 분석 컬럼 저장 취소
     *
     * @author 정다정
     * @since 2024.02.01
     */
     public int deleteDupRule(HashMap<String,Object> params) throws Exception;

    /**
     * 중복 분석 id 가져오기
     *
     * @author 박민혁
     * @since 2024.02.02
     */
    HashMap<String, Object> selectDuplRuleId(HashMap<String,Object> params) throws Exception;

    /**
     * 중복 검사 선택 대상 테이블 조회
     *
     * @author 임재현
     * @since 2024.02.06
     */
    List<HashMap<String,Object>> selectCheckedList(HashMap<String,Object>params) throws Exception;


    /**
     * 중복 검사 선택 대상 테이블 페이징
     *
     * @author 임재현
     * @since 2024.02.06
     */
    public int countCheckedList(HashMap<String,Object> params) throws Exception;

    /**
     * 중복 검사 선택 대상 테이블 컬럼명 조회 기능 (미완)
     *
     * @author 임재현
     * @since 2024.02.06
     */
    public String selectColumnNm(int params) throws Exception;
}
