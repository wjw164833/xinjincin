package com.fsy.task.util;

import com.fsy.task.GlobalConfig;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {
    public static String getResByUrlAndCookie(String url , String cookie) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//        nvps.add(new BasicNameValuePair("username", "vip"));
//        nvps.add(new BasicNameValuePair("password", "secret"));
        Header header = new BasicHeader("Cookie",cookie);
        httpGet.addHeader(header);
        CloseableHttpResponse response2 = httpclient.execute(httpGet);
        HttpEntity entity2 = response2.getEntity();
        // do something useful with the response body
        // and ensure it is fully consumed
        String respStr = EntityUtils.toString(entity2 , Charset.defaultCharset());
        return respStr;
    }

    public static String postResByUrlAndCookie(String url , String cookie,Map<String,String> params) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httPost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if(params != null && params.size() != 0){
            for(Map.Entry entry : params.entrySet()){
                nvps.add(new BasicNameValuePair(entry.getKey().toString() , entry.getValue().toString()));
            }
        }
        Header header = new BasicHeader("Content-Type","application/x-www-form-urlencoded");
        Header cookieHeader = new BasicHeader("Cookie",cookie);
        Header referHeader = new BasicHeader("Referer","http://sso.njcedu.com/login.htm?domain=zync.njcedu.com");
        httPost.addHeader(header);
        httPost.addHeader(cookieHeader);
        httPost.addHeader(referHeader);
        httPost.addHeader(header);
        httPost.setEntity(new UrlEncodedFormEntity(nvps));

        CloseableHttpResponse response2 = httpclient.execute(httPost);
        HttpEntity entity2 = response2.getEntity();
        // do something useful with the response body
        // and ensure it is fully consumed
        String respStr = EntityUtils.toString(entity2 , Charset.defaultCharset());
        return respStr;
    }
}
