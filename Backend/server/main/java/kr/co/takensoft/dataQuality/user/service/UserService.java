package kr.co.takensoft.dataQuality.user.service;

import kr.co.takensoft.dataQuality.user.dao.UserDAO;
import kr.co.takensoft.dataQuality.user.service.encrypt.EncryptHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserService {

    private final EncryptHelper encryptHelper;
    private final UserDAO userDAO;

    @Autowired
    public UserService(EncryptHelper encryptHelper, UserDAO userDAO) {
        this.encryptHelper = encryptHelper;
        this.userDAO = userDAO;
    }

    /**
     * users 데이터 조회
     *
     * @author 박민혁
     * @since 2024.01.16
     */
    public List<HashMap<String, Object>> selectUsers (HashMap<String, Object> params) throws Exception {
        return userDAO.selectUsers(params);
    }

    /**
     * users 데이터 등록
     *
     * @author 박민혁
     * @since 2024.01.16
     */
    public int insertUsers(HashMap<String, Object> params) throws Exception {
        String pwd = (String)params.get("user_pw");
        String hashedPwd = encryptHelper.encrypt(pwd);
        params.put("user_pw", hashedPwd);
        int result = userDAO.insertUsers(params);
        return result;
    }

}
