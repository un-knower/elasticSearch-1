package test.zyh.httpClinet;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyihang on 17/4/24.
 */
public class httpClient {

    public String getUrl(Long userId){
        StringBuffer stringBuffer = new StringBuffer();
        String url = "http://api.miaopai.com/internal/user/getNickByUserId?uid=237784601&type=weibo,self";
//        stringBuffer.append(url).append("?uid=").append(userId).append("&type").append("weibo");
        return url;
    }

    public List<String> getUserId(Long userId){
        List<String> responseList = new ArrayList<String>();
        String url = getUrl(userId);
        String guessResponse;
        guessResponse =GuessResponse(url);
        responseList = resolveResponse(guessResponse);
//        System.out.printf(responseList.toString());
        return responseList;
    }
    public String GuessResponse(String url){
        String result = null;
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet();
        try{
            request.setURI(new URI(url));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try{
            HttpResponse httpResponse = client.execute(request);
            if (httpResponse != null) {
                HttpEntity resEntity = httpResponse.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, "utf-8");
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    public List<String> resolveResponse(String response) {
        List<String> shakeList = new ArrayList<String>();
        JSONObject json = JSONObject.fromObject(response);
        String code = json.getString("status");
        String listJson;
        //如果请求成功
        if("200".equals(code)) {
            JSONObject nickName = (JSONObject) json.get("result");
            String weibo = nickName.getString("weibo") ;
           String mp = nickName.getString("self");
            System.out.printf("weibo"+weibo+"\n"+" mp"+mp);
        }
        return shakeList;
    }

    public static void main(String[] args) {
        httpClient httpClient = new httpClient();
        httpClient.getUserId(7553L);
    }
}
