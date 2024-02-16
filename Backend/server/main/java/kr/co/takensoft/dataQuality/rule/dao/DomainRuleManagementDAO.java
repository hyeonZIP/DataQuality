package kr.co.takensoft.dataQuality.rule.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * 도메인 규칙 관리
 *
 * @author 박민혁
 * @since 2024.01.23
 */
@Mapper
public interface DomainRuleManagementDAO {
    /**
     * 도메인 규칙 select.
     *
     * @author 박민혁
     * @since 2024.01.23
     */
    public List<HashMap<String, Object>> selectDomainRule (HashMap<String, Object> params) throws Exception;
    /**
     * 도메인 규칙 count select.
     *
     * @author 박민혁
     * @since 2024.01.23
     */
    public int selectDomainRuleCount (HashMap<String, Object> params) throws Exception;

    /**
     * 도메인 규칙 insert.
     *
     * @author 박민혁
     * @since 2024.01.23
     */
    public int insertDomainRule (HashMap<String, Object> params) throws Exception;

    /**
     * 도메인 규칙 update.
     *
     * @author 박민혁
     * @since 2024.01.23
     */
    public int updateDomainRule(HashMap<String, Object> params) throws Exception;
}
