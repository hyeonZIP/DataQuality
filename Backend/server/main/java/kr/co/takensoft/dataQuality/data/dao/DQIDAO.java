package kr.co.takensoft.dataQuality.data.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface DQIDAO {

    /**
     * 품질지표조회
     *
     * @author 임재현
     * @since 2024.01.17
     */
    public List<HashMap<String, Object>> selectDqiTable (HashMap<String, Object> params) throws Exception;
}