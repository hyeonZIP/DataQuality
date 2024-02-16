package kr.co.takensoft.dataQuality.schedule.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * 총합 규칙 관리
 *
 * @author 박민혁
 * @since 2024.01.23
 */
@Mapper
public interface ManageDAO {
    /**
     * 총합 규칙 관리 select.
     *
     * @author 박민혁
     * @since 2024.01.23
     */
    public List<HashMap<String, Object>> selectManageRule (HashMap<String, Object> params) throws Exception;

    /**
     * 총합 규칙 관리 count select.
     *
     * @author 박민혁
     * @since 2024.01.23
     */
    public int selectManageRuleCount (HashMap<String, Object> params) throws Exception;

    /**
     * 총합 규칙 관리 insert.
     *
     * @author 박민혁
     * @since 2024.01.23
     */
    public int insertManageRule (HashMap<String, Object> params) throws Exception;

    /**
     * 총합 규칙 관리 delete
     *
     * @author 박민혁
     * @since 2024.02.01
     */
    public HashMap<String, Object> selectManageId (HashMap<String, Object> params) throws Exception;

    /**
     * 총합 규칙 관리 update
     *
     * @author 박민혁
     * @since 2024.02.01
     */
    public int updateManageRule (HashMap<String, Object> params) throws Exception;

    /**
     * 총합 규칙 관리 delete
     *
     * @author 박민혁
     * @since 2024.02.01
     */
    public int deleteManageRule (HashMap<String, Object> params) throws Exception;

    /**
     * 총합 규칙 id 연동 delete
     *
     * @author 박민혁
     * @since 2024.02.01
     */
    public int deleteManageRuleId (int param) throws Exception;

    /**
     * 연동 안된 총합 규칙 관리 전부 delete
     *
     * @author 박민혁
     * @since 2024.02.05
     */
    public int deleteManageRuleAll () throws Exception;
}
