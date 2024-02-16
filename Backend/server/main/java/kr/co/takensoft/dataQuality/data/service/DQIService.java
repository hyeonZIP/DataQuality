package kr.co.takensoft.dataQuality.data.service;

import kr.co.takensoft.dataQuality.data.dao.DQIDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class DQIService {

    @Autowired
    private DQIDAO dqiDAO;

    /**
     * 품질지표조회
     *
     * @author 임재현
     * @since 2024.01.17
     */
    public List<HashMap<String, Object>> selectDqiTable (HashMap<String, Object> params) throws Exception {
        return dqiDAO.selectDqiTable(params);
    }
}
