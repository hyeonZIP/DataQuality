package kr.co.takensoft.dataQuality.rule.service;

import kr.co.takensoft.dataQuality.rule.dao.ValRuleManagementDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ValRuleManagementService {

    @Autowired
    private ValRuleManagementDAO valRuleManagementDAO;

    /**
     * 검증룰 검색조건 중 검증유형 SELECT
     *
     * @author 임재현
     * @since 2024.01.17
     */
    public List<HashMap<String, Object>> selectDqiType (HashMap<String, Object> params) throws Exception {
        return valRuleManagementDAO.selectDqiType(params);
    }

    /**
     * 검증룰 테이블 조회
     *
     * @author 임재현
     * @since 2024.01.18
     */
    public List<HashMap<String, Object>> selectValRule (HashMap<String, Object> params) throws Exception {
        int currentPage = (int) params.get("currentPage");
        int perPage = (int) params.get("perPage");
        int startIndex = (currentPage - 1) * perPage;
        params.put("startIndex", startIndex);

        return valRuleManagementDAO.selectValRule(params);
    }

    /**
     * DB 목록 COUNT.
     *
     * @author 박민혁
     * @since 2024.01.19
     */
    public int dbSelectListCount(HashMap<String, Object> params) throws Exception{
        return valRuleManagementDAO.dbSelectListCount(params);
    }

    /**
     * 검증룰 추가
     *
     * @author 임재현
     * @since 2024.01.18
     */
    public int insertValRule(HashMap<String, Object> params) throws Exception {
        String valRuleMatch = "비매치";
        params.put("valRuleMatch", valRuleMatch);
        int result = valRuleManagementDAO.insertValRule(params);
        return result;
    }

    /**
     * 검증룰 삭제
     *
     * @author 임재현
     * @since 2024.01.18
     */
    public int updateValRule(HashMap<String, Object> params) throws Exception {
        int result = valRuleManagementDAO.updateValRule(params);
        return result;
    }

    /**
     * 선택된 검증룰 수정
     *
     * @author 임재현
     * @since 2024.01.18
     */
    public int deleteValRule(int[] params) throws Exception {
        int result = valRuleManagementDAO.deleteValRule(params);
        return result;
    }
}

