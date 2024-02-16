package kr.co.takensoft.dataQuality.data.service;

import kr.co.takensoft.dataQuality.data.dao.TableDataDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class TableDataService {

    @Autowired
    private TableDataDAO tableDataDAO;

    /**
     * 검색용 DBMS 명 조회
     *
     * @author 정다정
     * @since 2024.01.23
     */
    public List<HashMap<String, Object>> selectDbmsName(HashMap<String,Object> params) throws Exception{
        return tableDataDAO.selectDbmsName(params);
    }

    /**
     * 검색용 스키마 명 조회
     *
     * @author 정다정
     * @since 2024.01.23
     */
    public List<HashMap<String, Object>> selectSchemaName(HashMap<String,Object> params) throws Exception{
        return tableDataDAO.selectSchemaName(params);
    }

    /**
     * 테이블 조회
     *
     * @author 정다정
     * @since 2024.01.23
     */
    public List<HashMap<String, Object>> selectTableList(HashMap<String,Object> params) throws Exception{
        int currentPage = (int) params.get("currentPage");
        int perPage = (int) params.get("perPage");
        int startIndex = (currentPage - 1) * perPage;
        params.put("startIndex", startIndex);
        return tableDataDAO.selectTableList(params);
    }

    /**
     * 테이블 목록 COUNT.
     *
     * @author 정다정
     * @since 2024.01.22
     */
    public int tableListCount(HashMap<String, Object> params) throws Exception{
        return tableDataDAO.selectTableListCount(params);
    }

    /**
     * 테이블 제외 여부, 사유 저장
     *
     * @author 정다정
     * @since 2024.01.24
     */
    public int updateTableData(HashMap<String,Object> params) throws Exception{
        return tableDataDAO.updateTableData(params);
    }

    /**
     * 메인 화면 용 테이블 목록 모두 COUNT
     *
     * @author 박민혁
     * @since 2024.01.25
     */
    public int selectTableListAllCount(HashMap<String, Object> params) throws Exception{
        return tableDataDAO.selectTableListAllCount(params);
    }

}