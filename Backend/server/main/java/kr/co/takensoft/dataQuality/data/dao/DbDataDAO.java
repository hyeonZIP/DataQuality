package kr.co.takensoft.dataQuality.data.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * DB 데이터 가져오기 위한 DAO 입니다.
 *
 * @author 박민혁
 * @since 2024.01.17
 */
@Mapper
public interface DbDataDAO {
    /**
     * DB 데이터 select.
     *
     * @author 박민혁
     * @since 2024.01.17
     */
    public List<HashMap<String, Object>> selectDbList (HashMap<String, Object> params) throws Exception;

    /**
     * DB 데이터 insert.
     *
     * @author 박민혁
     * @since 2024.01.17
     */
    public int insertDbData (HashMap<String, Object> params) throws Exception;

    /**
     * DB 데이터 delete.
     *
     * @author 박민혁
     * @since 2024.01.18
     */
    public int deleteDbData(HashMap<String, Object> params) throws Exception;

    /**
     * DB 데이터 update.
     *
     * @author 박민혁
     * @since 2024.01.22
     */
    public int updateDbData(HashMap<String, Object> params) throws Exception;

    /**
     * DB 연결.
     *
     * @author 박민혁
     * @since 2024.01.18
     */
    public int connectDb (HashMap<String, Object> params) throws Exception;

    /**
     * DB의 스키마 정보 수집.
     *
     * @author 박민혁
     * @since 2024.01.18
     */
    public int collectSchema(HashMap<String, Object> params) throws Exception;

    /**
     * 스키마 데이터 delete.
     *
     * @author 박민혁
     * @since 2024.01.18
     */
    public int deleteSchemaData(HashMap<String, Object> params) throws Exception;


    /**
     * 테이블 정보들을 insert함.
     *
     * @author 박민혁
     * @since 2024.01.19
     */
    public int insertTableList(HashMap<String, Object> params) throws Exception;

    /**
     * 테이블 인덱스 select.
     *
     * @author 박민혁
     * @since 2024.01.19
     */
    public HashMap<String, Object> selectTableIndex(HashMap<String, Object> params) throws Exception;

    /**
     * 컬럼 정보들을 insert함.
     *
     * @author 박민혁
     * @since 2024.01.19
     */
    public int insertColumnList(HashMap<String, Object> params) throws Exception;

    /**
     * DB 목록 COUNT.
     *
     * @author 박민혁
     * @since 2024.01.19
     */
    public int selectDbCount(HashMap<String, Object> params) throws Exception;

    /**
     * 테이블 데이터 delete.
     *
     * @author 박민혁
     * @since 2024.01.22
     */
    public int deleteTableData(HashMap<String, Object> params) throws Exception;


    /**
     * 특정 DB 데이터 select.
     *
     * @author 박민혁
     * @since 2024.01.26
     */
    public HashMap<String, Object> selectDbData(HashMap<String, Object> params) throws Exception;

}
