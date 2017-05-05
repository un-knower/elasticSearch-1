package test.zyh.elas;
import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
public class JsonUtil {
	
	// Java实体对象转json对象
    public static String model2Json(Blog blog) {
        String jsonData = null;
        try {
            XContentBuilder jsonBuild = XContentFactory.jsonBuilder();
            jsonBuild.startObject()
            	.field("id", blog.getId())
            	.field("title", blog.getTitle())
                .field("posttime", blog.getPosttime())
                .field("content",blog.getContent())
                .field("hitsNumber",blog.getHitsNumber())
                .field("commentNumber",blog.getCommentNumber())
                .field("sorce",blog.getSorce())
                .field("tags",blog.getTags())
                .endObject();

            jsonData = jsonBuild.string();
            System.out.println(jsonData);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonData;
    }
public static void main(String[] args) {
	model2Json(new Blog(1, "git简介", "2016-06-19", "SVN与Git最主要的区别...",1,1,1.2,"简介"));
}

}
