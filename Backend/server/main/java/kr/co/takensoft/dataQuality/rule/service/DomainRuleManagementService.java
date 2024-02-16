package kr.co.takensoft.dataQuality.rule.service;

import kr.co.takensoft.dataQuality.rule.dao.DomainRuleManagementDAO;
import kr.co.takensoft.dataQuality.schedule.dao.ManageDAO;
import kr.co.takensoft.dataQuality.result.dao.ManageResultDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class DomainRuleManagementService {
    @Autowired
    DomainRuleManagementDAO domainRuleManagementDAO;

    @Autowired
    ManageDAO manageDAO;

    @Autowired
    ManageResultDAO manageResultDAO;

    /**
     * 도메인 규칙 select.
     *
     * @author 박민혁
     * @since 2024.01.23
     */
    public List<HashMap<String, Object>> selectDomainRule (HashMap<String, Object> params) throws Exception{
        int currentPage = (int) params.get("currentPage");
        int perPage = (int) params.get("perPage");
        int startIndex = (currentPage - 1) * perPage;
        params.put("startIndex", startIndex);

        return domainRuleManagementDAO.selectDomainRule(params);
    }
    /**
     * 도메인 규칙 count select.
     *
     * @author 박민혁
     * @since 2024.01.23
     */
    public int selectDomainRuleCount (HashMap<String, Object> params) throws Exception{
        return domainRuleManagementDAO.selectDomainRuleCount(params);
    }

    /**
     * 도메인 규칙 update.
     *
     * @author 박민혁
     * @since 2024.01.24
     */
    public int updateDomainRule(HashMap<String, Object> params) throws Exception{
        if (params.get("code_id") != null) {
            params.put("rule_type", "domain_code");
        } else if (params.get("val_rule_id") != null){
            params.put("rule_type", "domain_val");
        }
        HashMap<String, Object> manage_id = manageDAO.selectManageId(params);

        if (manage_id != null) {
            Object manageRuleIdObj = manage_id.get("manage_rule_id");

            if (manageRuleIdObj != null) {
                Integer manageRuleId = (Integer) manageRuleIdObj;
                params.put("manage_rule_id", manageRuleId);

                if (params.get("code_id") == null && params.get("val_rule_id") == null) {
                    System.out.println("delete 실행");
                    manageDAO.deleteManageRule(params);
                } else {
                    System.out.println("update 실행");
                    manageDAO.updateManageRule(params);
                    manageResultDAO.deleteManageResult(params);
                }
            }
        }
        else {
            if(params.get("code_id") != null || params.get("val_rule_id") != null) {
                System.out.println("insert 실행");
                manageDAO.insertManageRule(params);
            }
        }

        return domainRuleManagementDAO.updateDomainRule(params);
    }
}

