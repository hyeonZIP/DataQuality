package kr.co.takensoft.dataQuality.user.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 *
 * 유저 데이터 DAO
 *
 * @author 박민혁
 * @since 2023.08.21
 */

@Mapper
public interface UserDAO {
    /**
     * users 데이터 조회
     *
     * @author 박민혁
     * @since 2024.01.16
     */
    public List<HashMap<String, Object>> selectUsers (HashMap<String, Object> params) throws Exception;

    /**
     * users 데이터 등록
     *
     * @author 박민혁
     * @since 2024.01.16
     */
    public int insertUsers(HashMap<String, Object> params) throws Exception;

    /**
     * 로그인 ID 확인
     *
     * @author 김준식
     * @since 2024.01.16
     */
    public int idCheck(HashMap<String, Object> params);

    /**
     * 로그인 사용자 비밀번호 가져오기
     *
     * @author 김준식
     * @since 2024.01.16
     */
    public String getPassword(HashMap<String, Object> params);

    /**
     * 로그인 사용자 이름 가져오기
     *
     * @author 김준식
     * @since 2024.01.17
     */
    public String getUserName(HashMap<String, Object> params);

    /**
     * 사용자 비밀번호 변경
     *
     * @author 정다정
     * @since 2024.01.22
     */
    public int updateUserPw(HashMap<String, Object> params);

    /**
     * 사용자 이메일 가져 오기
     *
     * @author 정다정
     * @since 2024.02.01
     */
    public String getUserEmail(HashMap<String,Object> params) throws Exception;
}
