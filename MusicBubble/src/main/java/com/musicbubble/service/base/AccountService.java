package com.musicbubble.service.base;

import com.musicbubble.model.UserEntity;
import com.musicbubble.repository.UserRepository;
import com.musicbubble.tools.CommonUtil;
import com.musicbubble.tools.DESUtil;
import com.musicbubble.tools.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.musicbubble.tools.Encrypt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Calendar;


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
    public int SignUp(String user_name, String passwd, String sex, String birthday, String email) {
        if (user_name.length() > 100
                || passwd.length() > 20
                || (!sex.equals("M") && !sex.equals("F")))
            return 0;

        UserEntity entity = new UserEntity();
        entity.setUserId(0);
        entity.setUserName(user_name);
        entity.setImageId(1);
        entity.setPasswd(Encrypt.SHA512(passwd));
        entity.setRank(1);
        entity.setSex(sex.equals("M") ? "M" : "F");
        entity.setExperience(0);
        entity.setBirthday(birthday);
        entity.setMail(email);

        entity.setLastSignin(new Timestamp(2000));

        entity = userRepository.saveAndFlush(entity);

        return entity.getUserId();

    }

    @Transactional
    public boolean SignIn(String user_name, String passwd) {
        UserEntity entity = userRepository.findByUserName(user_name);
        if (entity == null)
            return false;
        String password = entity.getPasswd();
        if (!password.equals(Encrypt.SHA512(passwd))) {
            System.out.println("password invalid");
            return false;
        }
        Timestamp cur = getDayBegin();
        System.out.println(cur.toString());

        if (entity.getLastSignin().before(cur))
            updateExperience(entity.getUserId(), 10);

        userRepository.updateTime(entity.getUserId(), new Timestamp(System.currentTimeMillis()));

        return true;
    }

    public boolean SignOut() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                cookie.setMaxAge(0);//删除Cookie
                cookie.setPath("/");
                cookie.setValue(null);
                HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
                response.addCookie(cookie);
                return true;
            }
        }
        return false;
    }

    @Transactional
    public void SetDefaultSongList(int user_id, int list_id) {
        userRepository.setListId(user_id, list_id);
    }

    public int FindDefaultSongList(int user_id) {
        UserEntity entity = userRepository.findOne(user_id);
        return entity.getListId();
    }

    public String GetUserNameById(int user_id) {
        UserEntity userEntity = userRepository.findOne(user_id);
        return userEntity == null ? null : userEntity.getUserName();
    }

    @Transactional
    public boolean SetImage(int user_id, int image_id){
        if (image_id == 0)return false;

        userRepository.setImage(user_id, image_id);
        return true;
    }

    public int GetImage(int user_id) {
        UserEntity userEntity = userRepository.findOne(user_id);
        return userEntity == null ? null : userEntity.getImageId();
    }

    public int IdentifyUser(String token) {
        int user_id = 0;
        try {
            String decrypted = DESUtil.decrypt(token, DESUtil.getKey());

            System.out.println("decrypted :" + decrypted);

            String[] tokens = decrypted.split("&");
            boolean expires = TimeUtil.expires(tokens[1]);
            if (!expires)
                user_id = Integer.parseInt(tokens[0]);
            else {
                System.out.println("expires");
                SignOut();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user_id;
    }

    public void updateExperience(int user_id, int exp) {
        userRepository.incExperience(user_id, exp);
        UserEntity entity = userRepository.findOne(user_id);
        if (entity.getExperience() >= CommonUtil.MaxExp(entity.getRank()))
            userRepository.incRank(entity.getUserId());
    }

    public UserEntity findOne(int user_id) {
        return userRepository.findOne(user_id);
    }

    private Timestamp getDayBegin() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 001);
        return new Timestamp(calendar.getTimeInMillis());
    }

}
