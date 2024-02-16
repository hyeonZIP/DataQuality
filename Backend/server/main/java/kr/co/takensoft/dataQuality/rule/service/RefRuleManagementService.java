package kr.co.takensoft.dataQuality.rule.service;

import kr.co.takensoft.dataQuality.rule.dao.RefRuleManagementDAO;
import kr.co.takensoft.dataQuality.schedule.dao.ManageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 *  데이터 규칙 관리
 *
 * @author 김준식
 * @since 2024.01.26
 */
@Service
public class RefRuleManagementService {

    @Autowired
    private RefRuleManagementDAO refRuleManagementDAO;

    @Autowired
    private ManageDAO manageDAO;

    /** 데이터규칙 관리 부분 **/
    /**
     * 검색조건에 필요한 DBMS명 검색
     *
     * @author 김준식
     * @since 2024.01.29
     */
    public List<HashMap<String, Object>> dbmsList(HashMap<String, Object> params){
        return refRuleManagementDAO.dbmsList(params);
    }

    /**
     * 검색조건에 필요한 스키마명 검색
     *
     * @author 김준식
     * @since 2024.01.29
     */
    public List<HashMap<String, Object>> schemaList(HashMap<String, Object> params){
        return refRuleManagementDAO.schemaList(params);
    }

    /**
     * 검색을 하였을 때 나오는 데이터 목록들
     *
     * @author 김준식
     * @since 2024.01.29
     */
    public List<HashMap<String, Object>> searchDataList(HashMap<String, Object> params){
        int currentPage = (int)params.get("currentPage");
        int perPage = (int)params.get("perPage");
        int startIdx = (currentPage - 1) * perPage;
        params.put("startIdx", startIdx);
        return refRuleManagementDAO.searchDataList(params);
    }

    /**
     * 검색을 하였을 때 나오는 데이터 목록들 개수
     *
     * @author 김준식
     * @since 2024.01.29
     */
    public int searchDataListCount(HashMap<String, Object> params){
        return refRuleManagementDAO.searchDataListCount(params);
    }

    /**
     * 조회된 목록들중 하나를 선택
     *
     * @author 김준식
     * @since 2024.01.29
     */
    public List<HashMap<String, Object>> oneSelectList(HashMap<String, Object> params){
        int currentPage = (int)params.get("currentPage");
        int perPage = (int)params.get("perPage");
        int startIdx = (currentPage - 1) * perPage;
        params.put("startIdx", startIdx);
        return refRuleManagementDAO.oneSelectList(params);
    }

    /**
     * 조회된 목록들중 하나를 선택한 후 부모 자식 관계 총 개수
     *
     * @author 김준식
     * @since 2024.01.29
     */
    public int oneSelectListCount(HashMap<String,Object> params){
        return refRuleManagementDAO.oneSelectListCount(params);
    }

    /********************************* 참조 무결설 추가 부분 *****************************************/
    /**
     * 부모테이블 검색 조건을 통해 데이터 조회
     *
     * @author 김준식
     * @since 2024.01.26
     */
    public List<HashMap<String, Object>> searchParentTable(HashMap<String, Object> params){
        int currentPage = (int)params.get("currentPage");
        int perPage = (int)params.get("perPage");
        int startIdx = (currentPage - 1) * perPage;
        params.put("startIdx", startIdx);
        return refRuleManagementDAO.searchParentTable(params);
    }

    /**
     * 참조 무결성 추가에서 부모테이블 검색한 데이터 총 개수
     *
     * @author 김준식
     * @since 2024.01.30
     */
    public int searchParentTableCount(HashMap<String, Object> params){
        return refRuleManagementDAO.searchParentTableCount(params);
    }

    /**
     * 자식테이블 데이터 조회
     *
     * @author 김준식
     * @since 2024.01.26
     */
    public List<HashMap<String, Object>> childTableList(HashMap<String, Object> params){
        return refRuleManagementDAO.childTableList(params);
    }

    /**
     * 참조무결성 등록
     *
     * @author 김준식
     * @since 2024.01.30
     */
    public int insertRef(List<HashMap<String, Object>> params){
        int result = 0;

        try{
            System.out.println("params" + params);
            for(HashMap<String, Object> param : params){
                refRuleManagementDAO.sameDeleteRef(param);
            }

            result = refRuleManagementDAO.insertRef(params);

            for(HashMap<String, Object> param : params) {
                param.put("rule_type", "ref_rule");
                param.put("rule_id", ((refRuleManagementDAO.selectRefRuleId(param)).get("ref_rule_id")));
                manageDAO.insertManageRule(param);
            }


        } catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 참조무결성 삭제
     *
     * @author 김준식
     * @since 2024.01.30
     */
    public int deleteRef(List<Integer> params) {
        int result = 0;
        try{
            HashMap<String, Object> change = new HashMap<String,Object>();
            change.put("params",params);
            result = refRuleManagementDAO.deleteRef(change);

            manageDAO.deleteManageRuleAll();
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
