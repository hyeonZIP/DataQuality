package kr.co.takensoft.dataQuality.rule.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 *  데이터 규칙 관리
 *
 * @author 김준식
 * @since 2024.01.26
 */
@Mapper
public interface RefRuleManagementDAO {

    /** 데이터규칙 관리 부분 **/
    /**
     * 검색조건에 필요한 DBMS명 검색
     *
     * @author 김준식
     * @since 2024.01.29
     */
    public List<HashMap<String, Object>> dbmsList(HashMap<String, Object> params);

    /**
     * 검색조건에 필요한 스키마명 검색
     *
     * @author 김준식
     * @since 2024.01.29
     */
    public List<HashMap<String, Object>> schemaList(HashMap<String, Object> params);

    /**
     * 검색을 하였을 때 나오는 데이터 목록들
     *
     * @author 김준식
     * @since 2024.01.29
     */
    public List<HashMap<String, Object>> searchDataList(HashMap<String, Object> params);

    /**
     * 검색을 하였을 때 나오는 데이터 목록들 개수
     *
     * @author 김준식
     * @since 2024.01.29
     */
    public int searchDataListCount(HashMap<String, Object> params);

    /**
     * 조회된 목록들중 하나를 선택
     *
     * @author 김준식
     * @since 2024.01.29
     */
    public List<HashMap<String, Object>> oneSelectList(HashMap<String, Object> params);

    /**
     * 조회된 목록들중 하나를 선택한 후 부모 자식 관계 총 개수
     *
     * @author 김준식
     * @since 2024.01.29
     */
    public int oneSelectListCount(HashMap<String, Object> params);

    /************************************* 참조 무결설 추가 부분 ***********************************************/
    /**
     * 부모테이블 검색 조건을 통해 데이터 조회
     *
     * @author 김준식
     * @since 2024.01.26
     */
    public List<HashMap<String, Object>> searchParentTable(HashMap<String, Object> params);

    /**
     * 참조 무결성 추가에서 부모테이블 검색한 데이터 총 개수
     *
     * @author 김준식
     * @since 2024.01.30
     */
    public int searchParentTableCount(HashMap<String, Object> params);

    /**
     * 자식테이블 데이터 조회
     *
     * @author 김준식
     * @since 2024.01.26
     */
    public List<HashMap<String, Object>> childTableList(HashMap<String, Object> params);

    /**
     * 참조무결성 등록
     *
     * @author 김준식
     * @since 2024.01.30
     */
    public int insertRef(List<HashMap<String, Object>> params);

    /**
     * 참조무결성 삭제
     *
     * @author 김준식
     * @since 2024.01.30
     */
    public int deleteRef(HashMap<String, Object> params);

    /**
     * 참조무결성에 이미 데이터가 존재 했을 경우 삭제
     *
     * @author 김준식
     * @since 2024.01.30
     */
    public void sameDeleteRef(HashMap<String, Object> params);

    /**
     * ref_rule_id 가져오기
     *
     * @author 박민혁
     * @since 2024.02.07
     */
    public HashMap<String, Object> selectRefRuleId(HashMap<String, Object> params);
}
