package kr.co.takensoft.dataQuality.rule.service;

import kr.co.takensoft.dataQuality.rule.dao.WorkRuleManagementDAO;
import kr.co.takensoft.dataQuality.schedule.dao.ManageDAO;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 업무 규칙 관리 Service
 *
 * @author 김준식
 * @since 2024.01.23
 */
@Service
public class WorkRuleManagementService {
    @Autowired
    private WorkRuleManagementDAO wrManagementDAO;

    @Autowired
    private ManageDAO manageDAO;

    /**
     * 업무 규칙 관리에 필요한 DBMS명을 불러옴
     *
     * @author 김준식
     * @since 2024.01.23
     */
    public List<HashMap<String, Object>> dbmsList(HashMap<String, Object> params) {
        return wrManagementDAO.dbmsList(params);
    }

    /**
     * 업무 규칙 관리에 필요한 스키마명을 불러옴
     *
     * @author 김준식
     * @since 2024.01.24
     */
    public List<HashMap<String, Object>> schemaList(HashMap<String, Object> params) {
        return wrManagementDAO.schemaList(params);
    }

    /**
     * 업무 규칙 관리에 필요한 테이블명을 불러옴
     *
     * @author 김준식
     * @since 2024.01.24
     */
    public List<HashMap<String, Object>> tableList(HashMap<String, Object> params){
        return wrManagementDAO.tableList(params);
    }

    /**
     * 업무 규칙 관리에 필요한 컬럼명을 불러옴
     *
     * @author 김준식
     * @since 2024.01.24
     */
    public List<HashMap<String, Object>> columnList(HashMap<String, Object> params){
        return wrManagementDAO.columnList(params);
    }

    /**
     * 업무 규칙 관리에 저장된 모든 데이터 항목을 불러옴
     *
     * @author 김준식
     * @since 2024.01.24
     */
    public List<HashMap<String, Object>> workRuleList(HashMap<String, Object> params){
        int currentPage = (int)params.get("currentPage");
        int perPage = (int)params.get("perPage");
        int startIdx = (currentPage - 1) * perPage;
        params.put("startIdx", startIdx);
        return wrManagementDAO.workRuleList(params);
    }

    /**
     * 업무 규칙 관리 등록
     *
     * @author 김준식
     * @since 2024.01.24
     */
    public int insertWorkRule(HashMap<String, Object> params){
        int result = 0;
        try{
            String cnt = params.get("work_rule_cnt").toString(); // 건수 sql
            String sql = params.get("work_rule_sql").toString(); // 분석 sql
            // SQL 문 파싱
            Statement cnt_statement = CCJSqlParserUtil.parse(cnt);
            Statement sql_statement = CCJSqlParserUtil.parse(sql);
            // SQL 문이 SELECT 문인지 확인
            boolean compare = sql_statement instanceof Select && cnt_statement instanceof Select && sql.toUpperCase().contains("FROM") && cnt.toUpperCase().contains("FROM");
            if(compare) {
                result = wrManagementDAO.insertWorkRule(params);

                HashMap<String, Object> workRuleId = wrManagementDAO.selectWorkRuleId(params);
                params.put("rule_type", "work_rule");
                params.put("rule_id", workRuleId.get("work_rule_id"));
                manageDAO.insertManageRule(params);
            }
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("sql 파싱 오류 : " + e.getMessage());
        }

        return result;
    }

    /**
     * 업무 규칙 관리 수정
     *
     * @author 김준식
     * @since 2024.01.24
     */
    public int updateWorkRule(HashMap<String, Object> params){
        int result = 0;
        try{
            String sql = params.get("work_rule_sql").toString();
            String cnt = params.get("work_rule_cnt").toString();
            //SQL 문 파싱
            Statement sql_statement = CCJSqlParserUtil.parse(sql);
            Statement cnt_statement = CCJSqlParserUtil.parse(cnt);
            // SQL 문이 SELECT 문인지 확인
            boolean compare = sql_statement instanceof Select && cnt_statement instanceof Select && sql.toUpperCase().contains("FROM") && cnt.toUpperCase().contains("FROM");
            if(compare) {
                result = wrManagementDAO.updateWorkRule(params);
            }
        } catch (JSQLParserException e){
            System.out.println("sql 파싱 오류 : " + e.getMessage());
        }

        return result;
    }

    /**
     * 업무 규칙 관리 삭제
     *
     * @author 김준식
     * @since 2024.01.24
     */
    public int deleteWorkRule(List<Integer> params){
        int result = 0;
        try{
            HashMap<String, Object> change = new HashMap<String,Object>();
            change.put("params",params);
            result = wrManagementDAO.deleteWorkRule(change);

            manageDAO.deleteManageRuleAll();
        } catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 검색조건에 따른 업무규칙 데이터의 총 개수
     *
     * @author 김준식
     * @since 2024.01.25
     */
    public int wrListCount(HashMap<String, Object> params){
        return wrManagementDAO.wrListCount(params);
    }
}
