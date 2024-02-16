package kr.co.takensoft.dataQuality.data.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface TableDataDAO {
    /**
     * 검색 용 DBMS 명 조회
     *
     * @author 정다정
     * @since 2024.01.23
     */
    public List<HashMap<String,Object>> selectDbmsName(HashMap<String,Object> params) throws Exception;

    /**
     * 검색 용 스키마 명 조회
     *
     * @author 정다정
     * @since 2024.01.23
     */
    public List<HashMap<String,Object>> selectSchemaName(HashMap<String,Object> params) throws Exception;

    /**
     * 테이블 조회
     *
     * @author 정다정
     * @since 2024.01.23
     */
    public List<HashMap<String, Object>> selectTableList(HashMap<String,Object> params) throws Exception;

    /**
     * 테이블 제외 여부, 사유 저장
     *
     * @author 정다정
     * @since 2024.01.24
     */
    public int updateTableData(HashMap<String,Object> params) throws Exception;

    /**
     * 테이블 목록 COUNT
     *
     * @author 정다정
     * @since 2024.01.24
     */
    public int selectTableListCount(HashMap<String, Object> params) throws Exception;

    /**
     * 메인 화면 용 테이블 목록 모두 COUNT
     *
     * @author 박민혁
     * @since 2024.01.25
     */
    public int selectTableListAllCount(HashMap<String, Object> params) throws Exception;
}
