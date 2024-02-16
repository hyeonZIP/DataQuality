package kr.co.takensoft.dataQuality.user.service.encrypt;

/**
 *
 * 비밀번호 암호화 생성을 위한 interface
 *
 * @author 정다정
 * @since 2024.01.16
 */
public interface EncryptHelper {
    String encrypt(String password);

    /**
     *  로그인시 입력한 기존의 패스워드와 해시된 패스워드를 비교해주는 isMatch
     *
     * @author 김준식
     * @since 2024.01.16
     */
    boolean isMatch(String password, String hashed);
}