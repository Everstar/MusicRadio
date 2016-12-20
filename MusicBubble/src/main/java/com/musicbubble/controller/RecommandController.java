package com.musicbubble.controller;

import Jama.Matrix;
import com.musicbubble.service.RecommandService;
import com.musicbubble.service.SongListService;
import com.musicbubble.service.base.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.DocumentDefaultsDefinition;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private SongListService songListService;

    @Autowired
    private RecommandService recommandService;

    @RequestMapping(value="/recommandSonglist",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public ResponseEntity<Object> recommandSongList(@CookieValue(value = "token",required = true)String token,@RequestBody List<String> data){
        int user_id = accountService.IdentifyUser(token);
        if (user_id == -1) return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);


        /*1. {"type_1": integer;"type_2": integer; "type_3": integer; .....}
        2.{"songlist_id": integer......}
*/

       //songListService.

        return new ResponseEntity<Object>(null,HttpStatus.OK);

    }

    private class RecommandValue{
        private final int songlistId;
        private final double angleValue;

        public RecommandValue(int songlistId,double angleValue)
        {
            this.songlistId=songlistId;
            this.angleValue=angleValue;
        }

        public int getSongListId(){return this.songlistId;}

       // public void setSongListId(int songListId){this.songlistId=songListId;}

        public double getAngleValue(){return this.angleValue;}

       // public void setAngleValue(double angleValue){this.angleValue=angleValue;}
    }

    private class SongListMes{
        int songlist_id;
        double[] songlist_feaVec;

        public void setSonglist_id(int songlist_id){this.songlist_id=songlist_id;}

        public void setSonglist_feaVec(double[] songlist_feaVec){this.songlist_feaVec=songlist_feaVec;}

        public int getSonglist_id(){return this.songlist_id;}

        public double[] getSonglist_feaVec(){return this.songlist_feaVec;}
    }

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

        SongListMes[] songListMes=new SongListMes[5];

        songListMes[0] =new SongListMes();
        songListMes[1]=new SongListMes();
        songListMes[2]=new SongListMes();
        songListMes[3]=new SongListMes();
        songListMes[4]=new SongListMes();

        songListMes[0].setSonglist_id(1);
        songListMes[1].setSonglist_id(2);
        songListMes[2].setSonglist_id(3);
        songListMes[3].setSonglist_id(4);
        songListMes[4].setSonglist_id(5);

        songListMes[0].setSonglist_feaVec(song_vec1);
        songListMes[1].setSonglist_feaVec(song_vec2);
        songListMes[2].setSonglist_feaVec(song_vec3);
        songListMes[3].setSonglist_feaVec(song_vec4);
        songListMes[4].setSonglist_feaVec(song_vec5);

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
        List<Double> recommandList=kNearer(list,songListMes);

        //double[] result=kNearer(list,songListMes1);

        return new ResponseEntity<Object>(recommandList,HttpStatus.OK);





    }

    private void qSort(RecommandValue[] recomandValue,int low,int high){
        if(low<high){
            int i=low,j=high;
            RecommandValue x=recomandValue[low];
            while(i<j)
            {
                while(i<j&&recomandValue[j].getAngleValue()<=x.getAngleValue()){
                    j--;
                }
                if(i<j){
                    recomandValue[i++]=recomandValue[j];
                }

                while(i<j&&recomandValue[i].getAngleValue()>x.getAngleValue()){
                    i++;
                }
                if(i<j){
                    recomandValue[j--]=recomandValue[i];
                }
            }
            recomandValue[i]=x;
            qSort(recomandValue,low,i-1);
            qSort(recomandValue,i+1,high);
        }

    }


    /*选取前4个*/
    private List<Double> kNearer(List<Integer> user_fea, SongListMes[] songlist_fea){

        double[] user_feaVec=list2FeactureVec(user_fea);

        RecommandValue[] recommandValues=new RecommandValue[songlist_fea.length];

        int i=0;

        Matrix user_matrix=new Matrix(user_feaVec,user_feaVec.length);
        //double user_module=getModuleofVec(user_matrix);

        //Matrix songlist_matrix=new Matrix(songlist_fea[0].getSonglist_feaVec(),songlist_fea[0].getSonglist_feaVec().length);

        //double[]result_value=songlist_matrix.arrayTimes(user_matrix).getColumnPackedCopy();


        for(SongListMes songlist_mes : songlist_fea){


            Matrix songlist_matrix=new Matrix(songlist_mes.getSonglist_feaVec(),songlist_mes.getSonglist_feaVec().length);

            double[] result_value=songlist_matrix.arrayTimes(user_matrix).getColumnPackedCopy();

            double value=0;

            for(double num:result_value){
                value+=num;
            }


            double songlist_module=getModuleofVec(songlist_matrix);

            double angle_value=value/songlist_module;

            recommandValues[i]=new RecommandValue(songlist_mes.songlist_id,angle_value);

            i++;
        }

        this.qSort(recommandValues,0,recommandValues.length-1);

        List<Double> recommandList=new ArrayList<>();

        for(int j=0;j<5;j++){
            recommandList.add(recommandValues[j].getAngleValue());
        }

        return recommandList;

        //return result_value;


    }

    private double testArrayTimes()
    {
        double[] value1={8.0,2.0,3.0,6.0,0.0};
        double[] value2={1.0,0.0,2.0,1.0,6.0};

        Matrix m1=new Matrix(value1,value1.length);
        Matrix m2=new Matrix(value2,value2.length);

        double[] result=m2.arrayTimes(m1).getColumnPackedCopy();

        double value=0;

        for(double num:result){
            value+=num;
        }

        return value;
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

    private double[] list2FeactureVec(List<Integer> list)
    {
        final int size=list.size();
        Object[] vec=list.toArray();

        double[] feactureVec=new double[size];
        for(int i=0;i<size;i++){
            feactureVec[i]=(Integer)vec[i];
        }

        return  feactureVec;
    }


}
