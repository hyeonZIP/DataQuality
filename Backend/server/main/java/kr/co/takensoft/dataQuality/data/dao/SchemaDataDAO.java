package kr.co.takensoft.dataQuality.data.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface SchemaDataDAO {
    /**
     * 진단대상 DB 관리 스키마 조회
     *
     * @author 정다정
     * @since 2024.01.17
     */
    public List<HashMap<String, Object>> selectSchemaList(HashMap<String, Object> params) throws Exception;

    /**
     * 진단대상 DB 관리 스키마 수정
     *
     * @author 정다정
     * @since 2024.01.17
     */
    public int updateSchema(List<HashMap<String, Object>> params) throws Exception;

    /**
     * 스키마 목록 COUNT.
     *
     * @author 박민혁
     * @since 2024.01.19
     */
    public int selectSchemaListCount(HashMap<String, Object> params) throws Exception;

    /**
     * 스키마 수집을 위한 스키마 조회
     *
     * @author 박민혁
     * @since 2024.01.22
     */
    public List<HashMap<String, Object>> selectSchemaListYN(HashMap<String, Object> params) throws Exception;

    /**
     * 스키마 목록 COUNT.
     *
     * @author 박민혁
     * @since 2024.01.25
     */
    public int selectSchemaListAllCount(HashMap<String, Object> params) throws Exception;

}