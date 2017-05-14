package com.musicbubble.service.base;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.InputStream;


/**
 * Created by happyfarmer on 5/4/2017.
 */
public class QiniuStore extends MyService {
    private static String accessKey = "pMkP9Ra2f0wcNtOY7to-IV8sq7ANoZBQ0y9tUupG";
    private static String secretKey = "Iu650TkkzTHRyFtPPeSks11IO3uICYajepY93-UO";
    private static String bucket = "musicradio";
    private static UploadManager uploadManager = new UploadManager(new Configuration(Zone.zone2()));
    private static Logger logger = Logger.getLogger(QiniuStore.class);
    private static String domain = "http://opbhb1ahv.bkt.clouddn.com";

    public static String storeFile(String path, String name) {
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            long start = System.currentTimeMillis();
            logger.info("transfer start...");
            Response response = uploadManager.put(path, name, upToken);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            logger.info("qiniu upload cost: " + (System.currentTimeMillis() - start));
            return domain + "/" + putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            logger.error(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return null;
    }

    public static String storeBytes(byte[] bytes, String name) {
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            long start = System.currentTimeMillis();
            logger.info("transfer start...");
            Response response = uploadManager.put(bytes, name, upToken);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            logger.info("qiniu upload cost: " + (System.currentTimeMillis() - start));
            return domain + "/" + putRet.key;
        }catch (QiniuException ex) {
            Response r = ex.response;
            logger.error(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return null;
    }

}
