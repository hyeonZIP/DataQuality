package kr.co.takensoft.dataQuality.rule.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * 코드관리 가져오기 위한 DAO 입니다.
 *
 * @author 김준식
 * @since 2024.01.18
 */
@Mapper
public interface CodeManagementDAO {

    /**
     * 코드 관리 데이터 목록 조회(검색 포함).
     *
     * @author 김준식
     * @since 2024.01.18
     */
    public List<HashMap<String, Object>> selectCodeList(HashMap<String, Object> params);

    /**
     * 코드 관리 데이터 추가.
     *
     * @author 김준식
     * @since 2024.01.18
     */
    public int insertCode(HashMap<String, Object> params);

    /**
     * 코드 관리 데이터 수정.
     *
     * @author 김준식
     * @since 2024.01.18
     */
    public int updateCode(HashMap<String, Object> params);

    /**
     * 코드 관리 모든 항목 삭제.
     *
     * @author 김준식
     * @since 2024.01.22
     */
    public int allCodeDelete(HashMap<String, Object> params);

    /**
     * 선택한 코드 관리 데이터 삭제.
     *
     * @author 김준식
     * @since 2024.01.18
     */
    public int deleteCode(HashMap<String, Object> params);

    /**
     * 목록들 중에서 하나의 코드 관리 데이터 조회.
     *
     * @author 김준식
     * @since 2024.01.18
     */
    public HashMap<String, Object> oneSelectCode(HashMap<String, Object> params);

    /**
     * 검색조건(DBMS) 데이터들을 조회.
     *
     * @author 김준식
     * @since 2024.01.18
     */
    public List<HashMap<String, Object>> getDBDataName(HashMap<String, Object> params);

    /**
     * 코드관리 데이터 총 개수.
     *
     * @author 김준식
     * @since 2024.01.22
     */
    public int cmListCount(HashMap<String, Object> params);

    /**
     * 검색조건을 반영한 데이터 총 개수.
     *
     * @author 김준식
     * @since 2024.01.22
     */
    public int cmSearchCount(HashMap<String, Object> params);

}
