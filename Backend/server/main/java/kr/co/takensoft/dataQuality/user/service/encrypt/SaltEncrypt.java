package kr.co.takensoft.dataQuality.user.service.encrypt;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

/**
 *
 * 비밀 번호 암호화 컴포넌트
 *
 * @author 정다정
 * @since 2024.01.16
 *
 */
@Component
public class SaltEncrypt implements EncryptHelper {
    private static final int MAX_HASH_LENGTH = 50; //비밀 번호 최대 길이 50자

    @Override
    public String encrypt(String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // Truncate the hash if it exceeds the maximum length
//        if (hashedPassword.length() > MAX_HASH_LENGTH) {
//            hashedPassword = hashedPassword.substring(0, MAX_HASH_LENGTH);
//        }

        return hashedPassword;
    }

    /**
     *  입력한 기존의 패스워드와 해시된 패스워드를 비교
     *
     * @author 김준식
     * @since 2024.01.16
     */
    @Override
    public boolean isMatch(String password, String hashed) {
        boolean result = BCrypt.checkpw(password, hashed);
        return  result;
    }
}
