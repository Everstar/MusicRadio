package com.musicbubble.service.base;

import com.musicbubble.model.UserEntity;
import com.musicbubble.repository.UserRepository;
import com.musicbubble.tools.DESUtil;
import com.musicbubble.tools.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.musicbubble.service.base.MyService;
import com.musicbubble.tools.Encrypt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;



/**
 * Created by happyfarmer on 2016/12/6.
 */

@Service
public class AccountService extends MyService {
    @Autowired
    private UserRepository userRepository;

    public Integer Exists(String user_name) {
        UserEntity entity = null;
        entity = userRepository.findByUserName(user_name);
        return entity != null ? entity.getUserId() : -1;
    }

    @Transactional
    public int SignUp(String user_name, String passwd, String sex) {
        if (user_name.length() > 100 || passwd.length() > 20 || (!sex.equals("M") && !sex.equals("F")))
            return -1;

        UserEntity entity = new UserEntity();
        entity.setUserId(0);
        entity.setUserName(user_name);
        entity.setImageId(1);
        entity.setPasswd(Encrypt.SHA512(passwd));
        entity.setRank(1);
        entity.setSex(sex.equals("M") ? "M" : "F");
        entity.setExperience(0);

        entity = userRepository.saveAndFlush(entity);
        return entity.getUserId();

    }

    @Transactional
    public boolean SignIn(String user_name, String passwd) {
        String password = userRepository.findPasswdByUserName(user_name);
        if (!password.equals(Encrypt.SHA512(passwd))) {
            System.out.println("password invalid");
            return false;
        }
        userRepository.incExperience(user_name, 10);

        return true;
    }

    @Transactional
    public void SetDefaultSongList(int user_id, int list_id){
        userRepository.setListId(user_id, list_id);
    }

    public String GetUserNameById(int user_id) {
        UserEntity userEntity = userRepository.findOne(user_id);
        return userEntity == null ? null : userEntity.getUserName();
    }

    public int GetImage(int user_id){
        UserEntity userEntity = userRepository.findOne(user_id);
        return userEntity == null ? null : userEntity.getImageId();
    }

    public int IdentifyUser(String token) {
        int user_id = -1;
        try {
            String decrypted = DESUtil.decrypt(token, DESUtil.getKey());

            System.out.println("decrypted :" + decrypted);

            String[] tokens = decrypted.split("&");
            boolean expires = DateTimeUtil.expires(tokens[1]);
            if (!expires)
                user_id = Integer.parseInt(tokens[0]);
            else {
                System.out.println("expires");
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                Cookie[] cookies = request.getCookies();
                for (Cookie cookie : cookies){
                    if(cookie.getName().equals("token"))
                        cookie.setMaxAge(0);//删除Cookie
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user_id;
    }


    public UserEntity findOne(int user_id){
        return userRepository.findOne(user_id);
    }

}
