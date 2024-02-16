package kr.co.takensoft.dataQuality.result.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

/**
 * 총합 규칙 결과 관리
 *
 * @author 박민혁
 * @since 2024.02.06
 */
@Mapper
public interface ManageResultDAO {
    /**
     * 총합 규칙 결과 select.
     *
     * @author 박민혁
     * @since 2024.02.07
     */
    public int selectManageResult (HashMap<String, Object> params) throws Exception;

    /**
     * 총합 규칙 결과  insert.
     *
     * @author 박민혁
     * @since 2024.02.06
     */
    public int insertManageResult (HashMap<String, Object> params) throws Exception;
    /**
     * 총합 규칙 결과 delete.
     *
     * @author 박민혁
     * @since 2024.02.07
     */
    public int deleteManageResult (HashMap<String, Object> params) throws Exception;
}
