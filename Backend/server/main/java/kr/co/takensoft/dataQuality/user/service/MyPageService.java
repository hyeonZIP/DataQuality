package kr.co.takensoft.dataQuality.user.service;

import kr.co.takensoft.dataQuality.user.dao.UserDAO;
import kr.co.takensoft.dataQuality.user.service.encrypt.EncryptHelper;
import kr.co.takensoft.dataQuality.user.service.encrypt.SaltEncrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class MyPageService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private SaltEncrypt saltEncrypt;
    @Autowired
    private EncryptHelper encryptHelper;

    /**
     *
     * 사용자 비밀 번호 변경
     *
     * @author 정다정
     * @since 2024.01.22
     */
    public int updateUserPw(HashMap<String,Object> params) throws Exception{
        int result = 0;
        String userPw = userDAO.getPassword(params);
        // 입력한 비밀 번호와 저장된 비밀 번호가 일치 하는지 검사
        if(saltEncrypt.isMatch(params.get("user_pw").toString(), userPw) == false) {
            result = 0; //일치 하지 않으면 0 리턴
        }
        else{
            String updatePw = (String) params.get("update_pw");
            String hashedPwd = encryptHelper.encrypt(updatePw);
            params.put("update_pw", hashedPwd);
            result = userDAO.updateUserPw(params);
        }
        System.out.println("result : "+ result);
        return result;
    }

    /**
     * 사용자 이메일 가져 오기
     *
     * @author 정다정
     * @since 2024.02.01
     */
    public String getUserEmail(HashMap<String,Object> params) throws Exception{
        return userDAO.getUserEmail(params);
    }

}
