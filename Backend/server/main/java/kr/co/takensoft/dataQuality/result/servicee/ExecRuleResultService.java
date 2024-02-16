package kr.co.takensoft.dataQuality.result.servicee;

import kr.co.takensoft.dataQuality.result.dao.ExecRuleResultDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ExecRuleResultService {
    @Autowired
    private ExecRuleResultDAO execRuleResultDAO;

    /**
     * 진단항목별 실행 페이지
     * 메인 테이블 조회
     * @author 임재현
     * @since 2024.01.30
     */
    public List<HashMap<String, Object>> selectExecResult (HashMap<String, Object> params) throws Exception {
        int perPage = (int)params.get("perPage");
        int currentPage = (int)params.get("currentPage");
        int startIndex = (currentPage-1)*perPage;
        params.put("startIndex", startIndex);

        return execRuleResultDAO.selectExecResult(params);
    }
    /**
     * 메인 테이블 COUNT
     * @author 임재현
     * @since 2024.01.30
     */
    public int countExecResult (HashMap<String, Object> params) throws Exception{
        return execRuleResultDAO.countExecResult(params);
    }


}
