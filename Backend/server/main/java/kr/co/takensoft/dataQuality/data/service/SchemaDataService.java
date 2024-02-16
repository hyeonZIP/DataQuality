package kr.co.takensoft.dataQuality.data.service;

import kr.co.takensoft.dataQuality.data.dao.SchemaDataDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class SchemaDataService {
    @Autowired
    private SchemaDataDAO schemaDataDAO;

    /**
     * 진단대상 DB 관리 스키마 조회
     *
     * @author 정다정
     * @since 2024.01.17
     */
    public List<HashMap<String,Object>> selectSchemaList(HashMap<String,Object> params) throws Exception{
        int currentPage = (int) params.get("currentPage");
        int perPage = (int) params.get("perPage");
        int startIndex = (currentPage - 1) * perPage;
        params.put("startIndex", startIndex);
        return schemaDataDAO.selectSchemaList(params);
    }
    /**
     * 진단대상 DB 관리 스키마 수정
     *
     * @author 정다정
     * @since 2024.01.17
     */
    public int updateSchema(List<HashMap<String,Object>> params) throws Exception {
        int result = schemaDataDAO.updateSchema(params);
        return result;
    }

    /**
     * Schema 목록 COUNT.
     *
     * @author 박민혁
     * @since 2024.01.19
     */
    public int selectSchemaListCount(HashMap<String, Object> params) throws Exception{
        return schemaDataDAO.selectSchemaListCount(params);
    }
    /**
     * 메인화면을 위한 Schema 목록 COUNT.
     *
     * @author 박민혁
     * @since 2024.01.25
     */
    public int selectSchemaListAllCount(HashMap<String, Object> params) throws Exception{
        return schemaDataDAO.selectSchemaListAllCount(params);
    }
}