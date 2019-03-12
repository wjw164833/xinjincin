package com.fsy.task.util;

import org.apache.http.ConnectionClosedException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {
    public static int count = 0;
//    public static CookieStore cookieStore = new BasicCookieStore();
//    public static CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

    public static Map<String , Integer > map = new HashMap<>();

    public static String getResByUrlAndCookie(String url , Map<String,String> headerParams , String cookie , boolean getCookie)  {

        CloseableHttpClient httpclient = HttpClients.createDefault();
//        httpclient.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, false);
        HttpGet httpGet = new HttpGet(url);
        Header header = new BasicHeader("Cookie",cookie);

        httpGet.addHeader("Accept-Charset", "UTF-8");
//        httpGet.addHeader("Host", HOST);
//        httpGet.addHeader("Accept", ACCEPT);
        httpGet.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 1.7; .NET CLR 1.1.4322; CIBA; .NET CLR 2.0.50727)");


        httpGet.addHeader(header);
        if(headerParams != null && headerParams.size() >0){
            for(Map.Entry<String, String> entry : headerParams.entrySet()){
                Header basicHeader = new BasicHeader(entry.getKey() , entry.getValue());
                httpGet.addHeader(basicHeader);
            }
        }
        CloseableHttpResponse response2 = null;
        try {
            response2 = httpclient.execute(httpGet);

            if(response2.getStatusLine().getStatusCode() == 400){
                String key = url + cookie + getCookie;

                if(map.containsKey(key)){
                    Integer count = map.get(key);
                    if(count >= 10){
                        return null;
                    }
                    count = count + 1;
                    map.put(key , count);
                }else{
                    map.put(key , 0);
                }
                return getResByUrlAndCookie(url , headerParams , cookie , getCookie);
            }
            HttpEntity entity2 = response2.getEntity();
            String respStr = null;
            try {
                respStr = EntityUtils.toString(entity2 , Charset.defaultCharset());
            }catch (ConnectionClosedException closedException){
//                closedException
                if(url.equals("http://course.njcedu.com/newcourse/coursecc.htm?courseId=70001756")){
                    return null;
                }
                httpclient.close();
                response2.close();

                //歇息60秒再重新创建连接请求
                try {
                    Thread.sleep(60*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("closedException ... try again" );
                return getResByUrlAndCookie(url , headerParams , cookie , getCookie);
//                return null;
                //try again
//                getResByUrlAndCookie(url, headerParams, cookie, getCookie);
            }

            Header [] cookies = response2.getHeaders("Set-Cookie");
            StringBuffer cookieStr = new StringBuffer();
            if(cookies != null && cookies.length != 0){
                for(Header cookHeader : cookies){
                    cookieStr.append(cookHeader.getValue() + ";");
                }
            }
//            response2.close();

            if(getCookie){
                return respStr + "#" + cookieStr.toString();
            }else{
                return respStr;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static String postResByUrlAndCookie(String url , String cookie,Map<String,String> params , Boolean getCookie) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httPost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if(params != null && params.size() != 0){
            for(Map.Entry entry : params.entrySet()){
                nvps.add(new BasicNameValuePair(entry.getKey().toString() , entry.getValue().toString()));
            }
        }
        Header header = new BasicHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");

        //cookie
        if(cookie != null && !cookie.equals("")){
            Header cookieHeader = new BasicHeader("Cookie",cookie);
            httPost.addHeader(cookieHeader);
        }

        Header referHeader = new BasicHeader("Referer","http://sso.njcedu.com/login.htm");
        httPost.addHeader(header);

        httPost.addHeader(referHeader);
        httPost.addHeader(header);
        try {
            httPost.setEntity(new UrlEncodedFormEntity(nvps , Charset.forName("UTF-8")));
            CloseableHttpResponse response2 = httpclient.execute(httPost);
            Header [] cookies = response2.getHeaders("Set-Cookie");
            StringBuffer cookieStr = new StringBuffer();
            if(cookies != null && cookies.length != 0){
                for(Header cookHeader : cookies){
                    cookieStr.append(cookHeader.getValue() + ";");
                }
            }

            HttpEntity entity2 = response2.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed

            String respStr = EntityUtils.toString(entity2 , Charset.defaultCharset());
            if(getCookie){
                return respStr + "#" + cookieStr.toString();
            }

            response2.close();
            return respStr;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
