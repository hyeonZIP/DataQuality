package kr.co.takensoft.dataQuality.result.servicee;

import kr.co.takensoft.dataQuality.result.dao.TotalRuleResultDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class TotalRuleResultService {

    @Autowired
    private TotalRuleResultDAO totalRuleResultDAO;

    /**
     * 품질진단종합현황 페이지
     * 메인 테이블 조회
     * @author 임재현
     * @since 2024.01.31
     */
    public List<HashMap<String, Object>> selectTotalResult (HashMap<String, Object> params) throws Exception {
        return totalRuleResultDAO.selectTotalResult(params);
    }
}
