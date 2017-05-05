package test.zyh.elas;

import net.sf.json.JSONObject;
import org.elasticsearch.common.collect.HppcMaps;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import test.zyh.key.SensitiveWord;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static test.zyh.httpClinet.HttpClientUtil.sendHttpGet;

/**
 * Created by zhangyihang on 2017/5/2.
 */
public class ChannelVideo {

    public static List<String> modoToJson(){

        List<String> list = new ArrayList<String>();
        try {
            //从文本中读取日志格式
            String url = "/Users/zhangyihang/Downloads/nick.log";
            File file = new File(url);
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");
            BufferedReader br = new BufferedReader(read);
            String line;
            String[] str;
            String jsonData = null;
            int id;
            String title;
            String desc;
            String topicName;
            String nickName;
            String categoryName;
            String userTag;
            String tag;
            String createTime;

            //开始解析日志然后组装数据给ES中
            try {
                while ((line = br.readLine()) != null) {
                    String tags = null;
                    if (line.equals(""))
                        continue;
                    else
                        str = line.split("@zhangyihang@");
                    if (str.length < 0) continue;
                    id = Integer.parseInt(str[0]);
                    title = str[1];
                    desc = str[2];
                    topicName = str[3];
                    nickName = str[4];
                    categoryName = str[5];
                    userTag = str[6];
                    tag = str[7];
                    createTime = str[8];

                        try {
                            //往elasticsearch中组装数据
                            XContentBuilder jsonBuild = XContentFactory.jsonBuilder();
                            jsonBuild.startObject()
                                    .field("id", id)
                                    .field("title", title)
                                    .field("desc", desc)
                                    .field("topicName", topicName)
                                    .field("nickName", nickName)
                                    .field("categoryName", categoryName)
                                    .field("userTag", userTag)
                                    .field("tag",tag)
                                    .field("createTime",createTime)
                                    .endObject();
                            jsonData = jsonBuild.string();
                            System.out.printf(jsonData);
                            list.add(jsonData);
                        } catch (IOException e) {
                            e.printStackTrace();
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
