package com.musicbubble.service.recommend;

import Jama.Matrix;
import com.musicbubble.repository.SongRepository;
import com.musicbubble.repository.UserRepository;
import com.musicbubble.service.base.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2016/12/21.
 */
@Service
public class RecommendService extends MyService {
    @Autowired
    private SongRepository songRepository;

    public List<String> getRangeOfSongById(int pre_id, int fal_id) {
        List<String> list = songRepository.findRangeOfSongById(pre_id, fal_id);
        return list;
    }

    public SongMes[] getSongVec(List<String> songStyle, int startId) {

        //the song id is between 100(startId-1)+1 to 100startId

        SongMes[] songListMes = new SongMes[songStyle.size()];

        int i = 0;
        for (String style : songStyle) {
            char[] charStyle = style.toCharArray();

            double[] songVec = new double[charStyle.length];

            for (int j = 0; j < charStyle.length; j++) {
                songVec[j] = charStyle[j] - '0';
            }
            SongMes listMes = new SongMes();
            listMes.setSong_feaVec(songVec);
            listMes.setSong_id(100 * (startId - 1) + 1 + i);

            songListMes[i] = listMes;

            i++;

        }

        return songListMes;
    }

    public UserMes[] getUserVec(List<List<Integer>> otherFea, int startId, int user_id) {

        UserMes[] userMes;
        if (user_id >= 100 * (startId - 1) + 1 && user_id <= 100 * startId) {
            userMes = new UserMes[otherFea.size() - 1];
        } else {
            userMes = new UserMes[otherFea.size()];
        }
        int i = 0;
        int j = 0;
        for (List<Integer> fea : otherFea) {

            //如果请求推荐的用户自己也在备选用户中，在备选用户中忽略他的ID
            if (100 * (startId - 1) + i + 1 == user_id) {
                i++;
                continue;
            } else {
                UserMes userMes1 = new UserMes();
                userMes1.setUser_feaVec(list2FeactureVec(fea));
                userMes1.setUser_id(100 * (startId - 1) + i + 1);

                userMes[j] = userMes1;

                i++;
                j++;
            }
        }
        return userMes;
    }

    /*推荐歌曲K临近算法*/
    public List<Integer> kNearer_song(List<Integer> user_fea, SongMes[] songlist_fea) {

        double[] user_feaVec = list2FeactureVec(user_fea);

        int i = 0;

        Matrix user_matrix = new Matrix(user_feaVec, user_feaVec.length);


        for (SongMes song_mes : songlist_fea) {


            Matrix song_matrix = new Matrix(song_mes.getSong_feaVec(), song_mes.getSong_feaVec().length);

            double[] result_value = song_matrix.arrayTimes(user_matrix).getColumnPackedCopy();

            double value = 0;

            for (double num : result_value) {
                value += num;
            }


            double song_module = getModuleofVec(song_matrix);

            double angle_value = value / song_module;

            song_mes.setAngle_value(angle_value);

            i++;

        }

        this.qSort(songlist_fea, 0, songlist_fea.length - 1);

        List<Integer> recommendList = new ArrayList<>();

        int recommendNumber = i / 10 + 1;

        for (int j = 0; j < recommendNumber; j++) {
            recommendList.add(songlist_fea[j].getSong_id());
        }

        return recommendList;

        //return result_value;


    }

    /*推荐用户K临近算法*/
    public List<Integer> kNearer_user(List<Integer> user_fea, UserMes[] userMes) {

        double[] user_feaVec = list2FeactureVec(user_fea);

        int i = 0;

        Matrix user_matrix = new Matrix(user_feaVec, user_feaVec.length);

        for (UserMes otheruser_vec : userMes) {
            Matrix outeruser_matrix = new Matrix(otheruser_vec.getUser_feaVec(), otheruser_vec.getUser_feaVec().length);

            Matrix distance_vec = outeruser_matrix.minus(user_matrix);

            otheruser_vec.setDistance_value(this.getModuleofVec(distance_vec));
            i++;
        }

        this.qSort(userMes, 0, userMes.length - 1);

        List<Integer> recommendList = new ArrayList<>();

        int recommendNumber = i / 10 + 1;

        for (int j = 0; j < recommendNumber; j++) {
            recommendList.add(userMes[j].getUser_id());
        }

        return recommendList;
    }


    private void qSort(UserMes[] userMes, int low, int high) {
        if (low < high) {
            int i = low, j = high;
            UserMes x = userMes[low];
            while (i < j) {
                while (i < j && userMes[j].getDistance_value() <= x.getDistance_value()) {
                    j--;
                }
                if (i < j) {
                    userMes[i++] = userMes[j];
                }

                while (i < j && userMes[i].getDistance_value() > x.getDistance_value()) {
                    i++;
                }
                if (i < j) {
                    userMes[j--] = userMes[i];
                }
            }
            userMes[i] = x;
            qSort(userMes, low, i - 1);
            qSort(userMes, i + 1, high);
        }
    }

    private void qSort(SongMes[] songMes, int low, int high) {
        if (low < high) {
            int i = low, j = high;
            SongMes x = songMes[low];
            while (i < j) {
                while (i < j && songMes[j].getAngle_value() <= x.getAngle_value()) {
                    j--;
                }
                if (i < j) {
                    songMes[i++] = songMes[j];
                }

                while (i < j && songMes[i].getAngle_value() > x.getAngle_value()) {
                    i++;
                }
                if (i < j) {
                    songMes[j--] = songMes[i];
                }
            }
            songMes[i] = x;
            qSort(songMes, low, i - 1);
            qSort(songMes, i + 1, high);
        }

    }


    //pass test
    private double getModuleofVec(Matrix m) {
        double[] result = m.arrayTimes(m).getColumnPackedCopy();

        double value = 0;

        for (double num : result) {
            value += num;
        }

        return Math.sqrt(value);
    }

    private double[] list2FeactureVec(List<Integer> list) {
        final int size = list.size();
        double[] feactureVec = new double[size];
        for (int i = 0; i < size; i++) {
            feactureVec[i] = list.get(i);
        }

        return feactureVec;
    }

}
