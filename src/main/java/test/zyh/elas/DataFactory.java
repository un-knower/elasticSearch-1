package test.zyh.elas;
import java.util.ArrayList;
import java.util.List;
public class DataFactory {

	 public static DataFactory dataFactory = new DataFactory();
	 private DataFactory() {
	    }

	    public DataFactory getInstance() {
	        return dataFactory;
	    }
	    public static List<String> getInitJsonData() {
	        List<String> list = new ArrayList<String>();

			ChannelJson channelJson = new ChannelJson();
			ChannelVideo channelVideo = new ChannelVideo();
//			String data = channelJson.model2Json();
	        /*Blog blog = new Blog();
	        BlogWeightCalculator blogWeightCalculator = new BlogWeightCalculator();
	        blog.setHitsNumber(234);
	        blog.setCommentNumber(323);
	        Double socre = blogWeightCalculator.calculate(blog);
	        String data1 = JsonUtil.model2Json(new Blog(1, "git简介", "20160119", "SVN与Git最主要的区别...",234,323,socre,"git简介"));
	        blog.setHitsNumber(111);
	        blog.setCommentNumber(111);
	        Double socre1 = blogWeightCalculator.calculate(blog);
	        String data2 = JsonUtil.model2Json(new Blog(2, "Java中泛型的介绍与简单使用", "20161019", "学习目标 掌握泛型的产生意义",111,111,socre1,"简介"));
	        blog.setHitsNumber(222);
	        blog.setCommentNumber(222);
	        Double socre2 = blogWeightCalculator.calculate(blog);
	        String data3 = JsonUtil.model2Json(new Blog(3, "SQL基本操作", "20160219", "基本操作：CRUD ...",222,222,socre2,"SQL操作"));
	        blog.setHitsNumber(333);
	        blog.setCommentNumber(333);
	        Double socre3 = blogWeightCalculator.calculate(blog);
	        String data4 = JsonUtil.model2Json(new Blog(4, "Hibernate框架基础", "20160319", "Hibernate框架基础...",333,333,socre3,"框架基础"));
	        blog.setHitsNumber(444);
	        blog.setCommentNumber(444);
	        Double socre4 = blogWeightCalculator.calculate(blog);
	        String data5 = JsonUtil.model2Json(new Blog(5, "边兰州够欠嘛", "20160419", "美国给伊拉克是个烂摊子吗",444,444,socre4,"兰州"));
	        blog.setHitsNumber(555);
	        blog.setCommentNumber(555);
	        Double socre5 = blogWeightCalculator.calculate(blog);
	        String data6 = JsonUtil.model2Json(new Blog(6, "范冰冰美不美", "20160519", "中韩预警冲突调查，韩国警察平均一天抠搜一条中国渔船",555,555,socre5,"范冰冰"));
	        blog.setHitsNumber(666);
	        blog.setCommentNumber(666);
	        Double socre6 = blogWeightCalculator.calculate(blog);
	        String data7 = JsonUtil.model2Json(new Blog(7, "北京国美在线", "20160619", "中国驻洛杉矶领事馆遭亚裔男子枪击，嫌疑犯已经自首。",666,666,socre6,"国美"));
	        blog.setHitsNumber(777);
	        blog.setCommentNumber(777);
	        Double socre7 = blogWeightCalculator.calculate(blog);
	        String data8 = JsonUtil.model2Json(new Blog(8, "北京艺龙旅行网", "20160719", "中华人民共和国",777,777,socre7,"艺龙"));
	        blog.setHitsNumber(888);
	        blog.setCommentNumber(888);
	        Double socre8 = blogWeightCalculator.calculate(blog);
	        String data9 = JsonUtil.model2Json(new Blog(9, "北京秒拍单位", "20160819", "Shell是什么...",888,888,socre8,"秒拍"));
	        blog.setHitsNumber(999);
	        blog.setCommentNumber(999);
	        Double socre9 = blogWeightCalculator.calculate(blog);
	        String data10 = JsonUtil.model2Json(new Blog(10, "北京神华集团", "20160919", "Shell是什么...",999,999,socre9,"神华"));
			String data11 = JsonUtil.model2Json(new Blog(11,"哈尔滨的贾乃亮","20170412","贾乃亮，1984年4月12日出生于黑龙江省哈尔滨松北区",1,1,1.0,"贾乃亮"));
			String data12 = JsonUtil.model2Json(new Blog(12,"山东的范冰冰","20170412","1981年9月16日出生于山东青岛，影视女演员、流行乐歌手",1,1,1.0,"范冰冰"));
			String data13 = JsonUtil.model2Json(new Blog(14,"上海戏剧学院表演系本科班的李冰冰","20170412","中国大陆女演员。1973年2月27日出生于黑龙江省哈尔滨市五常市，1997年毕业于上海戏剧学院表演系本科班。2000年因主演电视剧《少年包青天》而被观众所知",1,1,1.0,"李冰冰"));
			list.add(data1);
	        list.add(data2);
	        list.add(data3);
	        list.add(data4);
	        list.add(data5);
	        list.add(data6);
	        list.add(data7);
	        list.add(data8);
	        list.add(data9);
	        list.add(data10);
			list.add(data11);
			list.add(data12);
			list.add(data13);*/
//			list.add(data);
//	        return channelJson.model2Json();
			return  channelVideo.modoToJson();
	    }
	    
}
