package kr.co.takensoft.dataQuality.data.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ColumnDataDAO {

    /**
     * 검색할 DBMS 선택
     *
     * @author 임재현
     * @since 2024.01.24
     */
    public List<HashMap<String, Object>> selectDbms (HashMap<String, Object> params) throws Exception;

    /**
     * 검색할 스키마 선택
     *
     * @author 임재현
     * @since 2024.01.24
     */
    public List<HashMap<String, Object>> selectSchema (HashMap<String, Object> params) throws Exception;
    /**
     * 검색결과 테이블 조회
     *
     * @author 임재현
     * @since 2024.01.24
     */
    public List<HashMap<String, Object>> selectSearchResult (HashMap<String, Object> params) throws Exception;
    /**
     * 검색결과테이블 목록 COUNT
     *
     * @author 임재현
     * @since 2024.01.24
     */
    public int searchResultCount(HashMap<String,Object> params) throws Exception;

    /**
     * 테이블 컬럼 조회
     *
     * @author 임재현
     * @since 2024.01.25
     */
    public List<HashMap<String, Object>> selectTableColumn (HashMap<String, Object> params) throws Exception;

    /**
     * 테이블 컬럼 COUNT
     *
     * @author 임재현
     * @since 2024.01.25
     */
    public int tableColumnCount(HashMap<String,Object> params) throws Exception;

    /**
     * 컬럼 분석을 위해서 컬럼 데이터 가져오기
     *
     * @author 박민혁
     * @since 2024.02.06
     */
    public List<HashMap<String, Object>> selectTableColumnNM (HashMap<String, Object> params) throws Exception;

    /**
     * 컬럼 분석 결과 select
     *
     * @author 박민혁
     * @since 2024.02.06
     */
    public List<HashMap<String, Object>> selectColumnAnl(HashMap<String,Object> params) throws Exception;

    /**
     * 컬럼 분석 결과 insert
     *
     * @author 박민혁
     * @since 2024.02.06
     */
    public int insertColumnAnl(HashMap<String,Object> params) throws Exception;

    /**
     * 컬럼 분석 결과 delete
     *
     * @author 박민혁
     * @since 2024.02.06
     */
    public int deleteColumnAnl(HashMap<String,Object> params) throws Exception;
}

