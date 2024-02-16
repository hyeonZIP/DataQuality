package kr.co.takensoft.dataQuality.rule.service;

import kr.co.takensoft.dataQuality.rule.dao.CodeManagementDAO;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class CodeManagementService {

    @Autowired
    private CodeManagementDAO codeMgDAO;

    /**
     * 코드 관리 데이터 목록 조회(검색 포함).
     *
     * @author 김준식
     * @since 2024.01.18
     */
    public List<HashMap<String, Object>> selectCodeList(HashMap<String, Object> params) {
        int currentPage = (int)params.get("currentPage");
        int perPage = (int)params.get("perPage");
        int startIdx = (currentPage - 1) * perPage; // 시작 인덱스 번호 (예, 현재 페이지가 2이면 (2-1)*5=5 인덱스5번부터 시작.)
        params.put("startIdx", startIdx);

        return codeMgDAO.selectCodeList(params);
    }

    /**
     * 코드 관리 데이터 추가.
     *
     * @author 김준식
     * @since 2024.01.18
     */
    public int insertCode(HashMap<String, Object> params) {
        int result = 0;
        try {
            String sql = params.get("cr_code_sql").toString();
            // SQL 문 파싱
            Statement statement = CCJSqlParserUtil.parse(sql);

            // SQL 문이 SELECT 문인지 확인
            if (statement instanceof Select && sql.toUpperCase().contains("FROM")) {
                result = codeMgDAO.insertCode(params);
            }
        } catch (JSQLParserException e) {
            // SQL 문 파싱 오류 처리
            System.out.println("SQL 문 파싱 오류: " + e.getMessage());
        }
        return result;
    }

    /**
     * 코드 관리 데이터 수정.
     *
     * @author 김준식
     * @since 2024.01.18
     */
    public int updateCode(HashMap<String, Object> params) {
        int result = 0;
        try {
            String sql = params.get("cr_code_sql").toString();
            // SQL 문 파싱
            Statement statement = CCJSqlParserUtil.parse(sql);

            // SQL 문이 SELECT 문인지 확인
            if (statement instanceof Select && sql.toUpperCase().contains("FROM")) {
                result = codeMgDAO.updateCode(params);
            }
        } catch (JSQLParserException e) {
            // SQL 문 파싱 오류 처리
            System.out.println("SQL 문 파싱 오류: " + e.getMessage());
        }

        return  result;
    }

    /**
     * 선택한 코드 관리 데이터 삭제.
     *
     * @author 김준식
     * @since 2024.01.18
     */
    public int deleteCode(List<Integer> params) {
        int result = 0;
        try{
            HashMap<String, Object> changeParams = new HashMap<String, Object>();
            changeParams.put("params", params);
            result = codeMgDAO.deleteCode(changeParams);
        } catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 코드 관리 모든 항목 삭제.
     *
     * @author 김준식
     * @since 2024.01.22
     */
    public int allCodeDelete(HashMap<String, Object> params) {
        int result = 0;

        try{
            result = codeMgDAO.allCodeDelete(params);
        } catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 목록들 중에서 하나의 코드 관리 데이터 조회.
     *
     * @author 김준식
     * @since 2024.01.18
     */
    public HashMap<String, Object> oneSelectCode(HashMap<String, Object> params) {
        return codeMgDAO.oneSelectCode(params);
    }

    /**
     * 검색조건(DBMS) 데이터들을 조회.
     *
     * @author 김준식
     * @since 2024.01.18
     */
    public List<HashMap<String, Object>> getDBDataName(HashMap<String, Object> params) {
        return codeMgDAO.getDBDataName(params);
    }

    /**
     * 코드관리 데이터 총 개수.
     *
     * @author 김준식
     * @since 2024.01.22
     */
    public int cmListCount(HashMap<String, Object> params) {
        return codeMgDAO.cmListCount(params);
    }

    /**
     * 검색조건을 반영한 데이터 총 개수.
     *
     * @author 김준식
     * @since 2024.01.22
     */
    public int cmSearchCount(HashMap<String, Object> params) {
        return codeMgDAO.cmSearchCount(params);
    }

    /**
     * 데이터의 총 개수를 반환 OR 검색조건에 따른 데이터의 개수를 반환.
     *
     * @author 김준식
     * @since 2024.01.22
     */
    public int cmTotalCount(HashMap<String, Object> params) {
        if(params.get("dbms_id") == null && params.get("code_type") == null ) {
            //  검색조건을 선택하지 않았을 때
            return cmListCount(params);
        } else{
            return cmSearchCount(params);
        }
    }
}
