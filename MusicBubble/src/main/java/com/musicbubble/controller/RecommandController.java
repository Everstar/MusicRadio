package com.musicbubble.controller;

import Jama.Matrix;
import com.musicbubble.service.RecommandService;
import com.musicbubble.service.SongListService;
import com.musicbubble.service.base.AccountService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.DocumentDefaultsDefinition;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.InterceptingAsyncClientHttpRequestFactory;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



import javax.persistence.criteria.CriteriaBuilder;
import java.lang.reflect.Array;
import java.util.*;

import static java.lang.System.in;

/**
 * Created by DELL on 2016/12/19.
 */
@Controller
public class RecommandController {

    @Autowired
    private AccountService accountService;


    @Autowired
    private RecommandService recommandService;

    private class SongMes{
        private int song_id;

        private double angle_value;

        private double[] song_feaVec;

        public void setAngle_value(double angle_value){this.angle_value=angle_value;}

        public void setSong_id(int song_id){this.song_id=song_id;}

        public void setSong_feaVec(double[] song_feaVec){this.song_feaVec=song_feaVec;}

        public int getSong_id(){return this.song_id;}

        public double getAngle_value(){return this.angle_value;}

        public double[] getSong_feaVec(){return this.song_feaVec;}
    }

    private class UserMes{
        private int user_id;

        private double distance_value;

        private double[] user_feaVec;

        public void setUser_id(int user_id){this.user_id=user_id;}

        public void setDistance_value(double distance_value){this.distance_value=distance_value;}

        public void setUser_feaVec(double[] user_feaVec){this.user_feaVec=user_feaVec;}

        public int getUser_id(){return this.user_id;}

        public double getDistance_value(){return this.distance_value;}

        public double[] getUser_feaVec(){return this.user_feaVec;}
    }

    @RequestMapping(value="/recommandSong",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public ResponseEntity<Object> recommandSong(@CookieValue(value = "token",required = true)String token,@RequestBody int data){
        int user_id = accountService.IdentifyUser(token);
        if (user_id == -1) return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);


        /*1. {"type_1": integer;"type_2": integer; "type_3": integer; .....}
        2.{"songlist_id": integer......}
*/
        List<Integer> user_fea=new ArrayList<>() /*recommandService.getUserFeaVec(user_id)*/;

        List<String> songStyle=recommandService.getRangeOfSongById(100*(data-1)+1,100*data);

        SongMes[] songMes=this.getSongVec(songStyle,data);

        List<Integer> recommandList=this.kNearer_song(user_fea,songMes);
       //songListService.

        return new ResponseEntity<Object>(recommandList,HttpStatus.OK);

    }

   /* @RequestMapping(value="/recommandUser",method = RequestMethod.POST,produces = "application/json;charset=UTF-8n")
    public ResponseEntity<Object> recommandUser(@CookieValue(value="token",required = true)String token,@RequestBody int data){
        int user_id = accountService.IdentifyUser(token);
        if (user_id == -1) return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);


        return new ResponseEntity<Object>(null,HttpStatus.OK);
    }
*/




    @RequestMapping(value = "/testMartix",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public ResponseEntity<Object> testMartix(){

        String song_style1="101010101010101010101";
        String song_style2="101101100000100000000";
        String song_style3="100000000000000000000";
        String song_style4="111111111111111111111";
        String song_style5="000001110000111000101";
        char[] song1=song_style1.toCharArray();
        char[] song2=song_style2.toCharArray();
        char[] song3=song_style3.toCharArray();
        char[] song4=song_style4.toCharArray();
        char[] song5=song_style5.toCharArray();


        double[] song_vec1=new double[song1.length];
        double[] song_vec2=new double[song1.length];
        double[] song_vec3=new double[song1.length];
        double[] song_vec4=new double[song1.length];
        double[] song_vec5=new double[song1.length];

        for(int j=0;j<song1.length;j++){
            song_vec1[j]=song1[j]-'0';
            song_vec2[j]=song2[j]-'0';
            song_vec3[j]=song3[j]-'0';
            song_vec4[j]=song4[j]-'0';
            song_vec5[j]=song5[j]-'0';
        }


        /*double[] songlist_vec1={1.0,1.0,0.0,1.0,0.0};
        double[] songlist_vec2={4.0,2.0,0.0,0.0,1.0};
        double[] songlist_vec3={1.0,0.0,0.0,1.0,0.0};
        double[] songlist_vec4={6.0,0.0,0.0,3.0,1.0};
        double[] songlist_vec5={8.0,2.0,3.0,6.0,0.0};*/

       /* SongListMes[] songListMes1=new SongListMes[1];
        songListMes1[0] =new SongListMes();
        songListMes1[0].setSonglist_feaVec(songlist_vec5);
        songListMes1[0].setSonglist_id(1);*/

        SongMes[] songMes=new SongMes[5];

        songMes[0] =new SongMes();
        songMes[1]=new SongMes();
        songMes[2]=new SongMes();
        songMes[3]=new SongMes();
        songMes[4]=new SongMes();

        songMes[0].setSong_id(1);
        songMes[1].setSong_id(2);
        songMes[2].setSong_id(3);
        songMes[3].setSong_id(4);
        songMes[4].setSong_id(5);

        songMes[0].setSong_feaVec(song_vec1);
        songMes[1].setSong_feaVec(song_vec2);
        songMes[2].setSong_feaVec(song_vec3);
        songMes[3].setSong_feaVec(song_vec4);
        songMes[4].setSong_feaVec(song_vec5);

        List<Integer> list=new ArrayList<>();

        list.add(1);
        list.add(0);
        list.add(2);
        list.add(1);
        list.add(6);
        list.add(0);
        list.add(0);
        list.add(0);
        list.add(3);
        list.add(5);
        list.add(0);
        list.add(0);
        list.add(6);
        list.add(10);
        list.add(0);
        list.add(12);
        list.add(9);
        list.add(0);
        list.add(0);
        list.add(1);
        list.add(11);

        //Matrix m1=new Matrix(songlist_vec1,songlist_vec1.length);

        //double module=getModuleofVec(m1);
        List<Integer> recommandList=kNearer_song(list,songMes);

        //double[] result=kNearer(list,songListMes1);

        return new ResponseEntity<Object>(testJSONArray(),HttpStatus.OK);






    }

    private List<Integer> testJSONArray(){
        String jsonArray="[11,22,33,44,55]";


        //JSONArray jsonArray1=new JSONArray(jsonArray);

        JSONArray jsonArray1=new JSONArray(jsonArray);

        List<Integer> testJson=new ArrayList<>();

        for(int i=0;i<5;i++){
            testJson.add(jsonArray1.getInt(i));
        }

        return testJson;






    }

    // 100 ( i - 1 ) + 1~~~100 i

    private SongMes[] getSongVec(List<String> songStyle,int startId){

        //the song id is between 100(startId-1)+1 to 100startId

        SongMes[] songListMes=new SongMes[songStyle.size()];

        int i=0;
        for(String style:songStyle){
            char[] charStyle=style.toCharArray();

            double[] songVec=new double[charStyle.length];

            for(int j=0;j<charStyle.length;j++){
                songVec[j]=charStyle[j]-'0';
            }
            SongMes listMes=new SongMes();
            listMes.setSong_feaVec(songVec);
            listMes.setSong_id(100*(startId-1)+1+i);

        }

        return songListMes;
    }
    private void qSort(UserMes[] userMes,int low,int high){
        if(low<high){
            int i=low,j=high;
            UserMes x=userMes[low];
            while(i<j)
            {
                while(i<j&&userMes[j].getDistance_value()<=x.getDistance_value()){
                    j--;
                }
                if(i<j){
                    userMes[i++]=userMes[j];
                }

                while(i<j&&userMes[i].getDistance_value()>x.getDistance_value()){
                    i++;
                }
                if(i<j){
                    userMes[j--]=userMes[i];
                }
            }
            userMes[i]=x;
            qSort(userMes,low,i-1);
            qSort(userMes,i+1,high);
        }
    }

    private void qSort(SongMes[] songMes,int low,int high){
        if(low<high){
            int i=low,j=high;
            SongMes x=songMes[low];
            while(i<j)
            {
                while(i<j&&songMes[j].getAngle_value()<=x.getAngle_value()){
                    j--;
                }
                if(i<j){
                    songMes[i++]=songMes[j];
                }

                while(i<j&&songMes[i].getAngle_value()>x.getAngle_value()){
                    i++;
                }
                if(i<j){
                    songMes[j--]=songMes[i];
                }
            }
            songMes[i]=x;
            qSort(songMes,low,i-1);
            qSort(songMes,i+1,high);
        }

    }


    /*选取前4个*/
    private List<Integer> kNearer_song(List<Integer> user_fea, SongMes[] songlist_fea){

        double[] user_feaVec=list2FeactureVec(user_fea);


        int i=0;

        Matrix user_matrix=new Matrix(user_feaVec,user_feaVec.length);



        for(SongMes song_mes : songlist_fea){


            Matrix songlist_matrix=new Matrix(song_mes.getSong_feaVec(),song_mes.getSong_feaVec().length);

            double[] result_value=songlist_matrix.arrayTimes(user_matrix).getColumnPackedCopy();

            double value=0;

            for(double num:result_value){
                value+=num;
            }


            double songlist_module=getModuleofVec(songlist_matrix);

            double angle_value=value/songlist_module;

            song_mes.setAngle_value(angle_value);


        }

        this.qSort(songlist_fea,0,songlist_fea.length-1);

        List<Integer> recommandList=new ArrayList<>();

        for(int j=0;j<5;j++){
            recommandList.add(songlist_fea[j].getSong_id());
        }

        return recommandList;

        //return result_value;


    }

    private List<Integer> kNearer_user(List<Integer> user_fea,UserMes[] userMes){

        double[] user_feaVec=list2FeactureVec(user_fea);

        int i=0;

        Matrix user_matrix=new Matrix(user_feaVec,user_feaVec.length);

        for(UserMes otheruser_vec:userMes){
            Matrix outeruser_matrix=new Matrix(otheruser_vec.getUser_feaVec(),otheruser_vec.getUser_feaVec().length);

            Matrix distance_vec=outeruser_matrix.minus(user_matrix);

            otheruser_vec.setDistance_value(this.getModuleofVec(distance_vec));
        }

        this.qSort(userMes,0,userMes.length);

        List<Integer> recommandList=new ArrayList<>();

        for(int j=0;j<5;j++){
            recommandList.add(userMes[j].getUser_id());
        }

        return recommandList;
    }

    //pass test
    private double getModuleofVec(Matrix m){
        double[] result=m.arrayTimes(m).getColumnPackedCopy();

        double value=0;

        for(double num:result){
            value+=num;
        }

        return  Math.sqrt(value);
    }

    private double[] list2FeactureVec(List<Integer> list) {
        final int size=list.size();
        Object[] vec=list.toArray();

        double[] feactureVec=new double[size];
        for(int i=0;i<size;i++){
            feactureVec[i]=(Integer)vec[i];
        }

        return  feactureVec;
    }


}
