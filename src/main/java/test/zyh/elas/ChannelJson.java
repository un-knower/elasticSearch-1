package test.zyh.elas;

import net.sf.json.JSONObject;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import test.zyh.key.SensitiveWord;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static test.zyh.httpClinet.HttpClientUtil.sendHttpGet;

/**
 * Created by zhangyihang on 17/4/20.
 */
public class ChannelJson {

    public static List<String> model2Json() {

        List<String> list = new ArrayList<String>();
        try {
            //从文本中读取日志格式
            String url = "/Users/zhangyihang/Downloads/nick.log";
            File file = new File(url);
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");
            BufferedReader br = new BufferedReader(read);
            String line;
            String[] str;
            String name;
            int publicStatus;
            int delStatus;
            int status;
            String jsonData = null;
            String weiboNick;
            String selfNick;
            //开始解析日志然后组装数据给ES中
            try {
                while ((line = br.readLine()) != null) {
                    String tags = null;
                    if (line.equals(""))
                        continue;
                    else
                        str = line.split("@search_split@");
                    if (str.length < 0) continue;
                    name = str[0];

                    CreateMapping createMapping = new CreateMapping();
                    List<String> listString = createMapping.Aanlaye(name);

                    //优化分词的写法
                    Iterator<String> iterator = SensitiveWord.initMap().getSensitiveWord(listString.toString(), 1).iterator();
                        String star = null;
                        while (iterator.hasNext()) {
                            String s = iterator.next();
                            star += s + ",";
                            star.substring(4, star.length() - 1);

                        }
                        tags = star==null?tags:star.substring(4, star.length() - 1);
                    if (str[1].length() > 3) {
                        publicStatus = 0;
                    } else {
                        publicStatus = Integer.parseInt(str[1]);
                    }
                    delStatus = Integer.parseInt(str[2]);
                    status = Integer.parseInt(str[3]);
                    long userId = Long.parseLong(str[4]);
                    String response = sendHttpGet("http://api.miaopai.com/internal/user/getNickByUserId?uid=" + userId + "&type=weibo,self");
                    JSONObject jsonObject = JSONObject.fromObject(response);
                    String code = jsonObject.getString("status");
                    String listJson;
                    //如果请求成功
                    if ("200".equals(code)) {
                        JSONObject nickName = (JSONObject) jsonObject.get("result");
                        if(nickName.size()<=1){
                            selfNick = nickName.getString("self");
                            weiboNick="";
                        }else {
                            selfNick = nickName.getString("self");
                            weiboNick = nickName.getString("weibo");
                        }

                        System.out.printf("weibo :" + weiboNick + "\t" + "siteNike :" + selfNick + "\n");

                        try {
                            //往elasticsearch中组装数据
                            XContentBuilder jsonBuild = XContentFactory.jsonBuilder();
                            jsonBuild.startObject()
                                    .field("title", name)
                                    .field("publicStatus", publicStatus)
                                    .field("delStatus", delStatus)
                                    .field("status", status)
                                    .field("tags", tags==null?"":tags)
                                    .field("weiboNick", weiboNick)
                                    .field("selfNick", selfNick)
                                    .field("proRemark",System.currentTimeMillis())
                                    .endObject();
                            jsonData = jsonBuild.string();
                            list.add(jsonData);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return list;
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("读取一行数据时出错");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("文件读取路径错误FileNotFoundException");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
