package kr.co.takensoft.dataQuality.rule.service;

import kr.co.takensoft.dataQuality.rule.dao.DupRulManagementDAO;
import kr.co.takensoft.dataQuality.schedule.dao.ManageDAO;
import org.slf4j.helpers.SubstituteLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class DupRulManagementService {
    @Autowired
    private DupRulManagementDAO dupRulManagementDAO;

    @Autowired
    ManageDAO manageDAO;

    /**
     * 컬럼 조회
     *
     * @author 정다정
     * @since 2024.01.26
     */
    public List<HashMap<String,Object>> selectColumn(HashMap<String,Object> params) throws Exception{
        int currentPage = (int) params.get("currentPage");
        int perPage = (int) params.get("perPage");
        int startIndex = (currentPage - 1) * perPage;
        params.put("startIndex", startIndex);
        return dupRulManagementDAO.selectColumn(params);
    }

    /**
     * 컬럼 목록 COUNT
     *
     * @author 정다정
     * @since 2024.01.26
     */
    public int selectColumnCount(HashMap<String,Object> params) throws Exception{
        return dupRulManagementDAO.selectColumnCount(params);
    }

    /**
     * 중복 분석 컬럼 저장
     *
     * @author 정다정
     * @since 2024.01.26
     */
    public int insertDupRule(HashMap<String,Object> params) throws Exception {

        System.out.println("params = " + params);

        List<Integer> columnList = (List<Integer>) params.get("columnList");

        params.put("mainColumnId", columnList.get(0));
        params.put("columnListId", columnList.toString().replaceAll("[\\[\\]]",""));

        dupRulManagementDAO.insertDupRule(params);

        HashMap<String, Object> duplRuleId = dupRulManagementDAO.selectDuplRuleId(params);

        params.put("rule_type", "work_rule");
        params.put("rule_id", duplRuleId.get("dupl_rule_id"));
        params.put("rule_type", "dupl_rule");

        manageDAO.insertManageRule(params);
        return 1;
    }

    /**
     * 중복 분석 컬럼 저장 취소
     *R
     * @author 정다정
     * @since 2024.02.01
     */
    public int deleteDupRule(HashMap<String,Object> params) throws Exception{

        dupRulManagementDAO.deleteDupRule(params);

        List<Integer> duplRuleList = (List<Integer>) params.get("ruleIdList");

        for(int rule_id : duplRuleList){
            manageDAO.deleteManageRuleId(rule_id);
        }

//        List<HashMap<String, Object>> duplRuleIdList = dupRulManagementDAO.selectDuplRuleId(params);
//
//        for (HashMap<String, Object> row : duplRuleIdList) {
//            Integer duplRuleId = (Integer) row.get("dupl_rule_id");
//            params.put("rule_id", duplRuleId);
//            HashMap<String, Object> manage_id = manageDAO.selectManageId(params);
//            Integer manageRuleId = (Integer) manage_id.get("manage_rule_id");
//            params.put("manage_rule_id", manageRuleId);
//            manageDAO.deleteManageRule(params);
//        }

        return 1;
    }
    /**
     * 중복 분석 선택 대상 테이블 조회
     * 컬럼명 조회 기능
     *
     * @author 임재현
     * @since 2024.02.06
     */
    public List<HashMap<String,Object>> selectCheckedList(HashMap<String,Object> params) throws Exception{
        int currentPage = (int) params.get("curPage");
        int perPage = (int) params.get("perPage");
        int startIndex = (currentPage - 1) * perPage;
        params.put("startIndex", startIndex);

        List<HashMap<String,Object>> countTable = dupRulManagementDAO.selectCheckedList(params);
        for(HashMap<String, Object> count : countTable)
        {
            //str > 컬럼명 저장
            String str = "";
            //s > dupl_rule DB 에 있는 column_id_list 문자열 저장
            String s = (String) count.get("column_id_list");
            //문자열 s를 콤마를 기준으로 분리하여 정수형 배열에 저장, 문자열 id에서 정수형 id로 변환 후 selectColumnNm 에서 정수형id를 통해 컬럼명을 반환함
            int[] a = Arrays.stream(s.split(", ")).mapToInt(Integer::parseInt).toArray();

            //각각의 정수형 id에 대해 컬럼명을 반환 후  str에 이어서 저장
            for(int j = 0; j<a.length; j++)
            {
                str += dupRulManagementDAO.selectColumnNm(a[j]) + ", ";
            }
            //str 문자열의 마지막 콤마와 공백 제거
            str = str.substring(0,str.length()-2);

            //한 행의 컬럼명을 전부 반환 했으면 put, 프론트로 보냄
            count.put("code_list_nm", str);
        }
        return countTable;
    }


    public int countCheckedList(HashMap<String,Object> params) throws Exception{
        return dupRulManagementDAO.countCheckedList(params);
    }
}
