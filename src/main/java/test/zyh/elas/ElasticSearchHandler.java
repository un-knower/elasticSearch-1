package test.zyh.elas;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
public class ElasticSearchHandler {
	
	private static  Client client;
	private static String ServerIP = "127.0.0.1";// ElasticSearch server ip
    private static int ServerPort = 9300;// port
	public ElasticSearchHandler(){
		this("127.0.0.1");
	}
	public ElasticSearchHandler(String idAddress) {
		try {
			client = TransportClient.builder().build().addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(idAddress),9300));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	    
	}
	
	//查询
	public List<Blog> searcher(QueryBuilder queryBuilder,String indexName,String type){
    	List<Blog> list = new ArrayList<Blog>();
    	SearchResponse response = client.prepareSearch(indexName).setTypes(type).setQuery(queryBuilder).execute().actionGet();
    	SearchHits hits = response.getHits();
    	System.out.println("查询到记录数=" + hits.getTotalHits());
    	SearchHit[] searchHists = hits.getHits();
    	if (searchHists.length > 0) {
			for (SearchHit searchHit : searchHists) {
				Integer id  = (Integer) searchHit.getSource().get("id");
				String title = String.valueOf(searchHit.getSource().get("title"));
				String posttime = String.valueOf(searchHit.getSource().get("posttime"));
				String content = String.valueOf(searchHit.getSource().get("content"));
				list  .add(new Blog(id,title,posttime,content,1,1,1.0,""));
			}
		}
		return list;
    	
    }
	
	//创建index
	public void createIndexResonpse(String indexName,String type,List<String> jsonData){
		IndexRequestBuilder requestBuilder = client.prepareIndex(indexName, type).setRefresh(true);
		for(int i=0; i<jsonData.size(); i++){
            requestBuilder.setSource(jsonData.get(i)).execute().actionGet();
        } 
//      try {
//      /* 创建客户端 */
//      // client startup
//      Client client = TransportClient.builder().build()
//              .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
//
//      List<String> jsonData = DataFactory.getInitJsonData();
//
//      for (int i = 0; i < jsonData.size(); i++) {
//          IndexResponse response = client.prepareIndex("blog", "article").setSource(jsonData.get(i)).get();
//          if (response.isCreated()) {
//             System.out.println("创建成功!");
//          }
//      }
//      client.close();
//  } catch (UnknownHostException e) {
//      e.printStackTrace();
//  } catch (IOException e) {
//      e.printStackTrace();
//  }
        
	}
	
	//删除索引
	public void deleteIndexName(String indexName,String type){
		try {
			DeleteResponse dResponse = client.prepareDelete(indexName, type, "").execute().actionGet();
			 if (dResponse.isFound()) {
	             System.out.println("删除成功");
	         } else {
	             System.out.println("删除失败");
	         }
		} catch (Exception e) {
			  e.printStackTrace();
		}
		  deleteIndex(indexName);//删除名为test的索引库
	}
	
	public static void deleteIndex(String indexName) {

        try {
            if (!isIndexExists(indexName)) {
                System.out.println(indexName + " not exists");
            } else {
                Client client = TransportClient.builder().build().addTransportAddress(
                        new InetSocketTransportAddress(InetAddress.getByName(ServerIP),
                                ServerPort));

                DeleteIndexResponse dResponse = client.admin().indices().prepareDelete(indexName)
                        .execute().actionGet();
                if (dResponse.isAcknowledged()) {
                    System.out.println("delete index "+indexName+"  successfully!");
                }else{
                    System.out.println("Fail to delete index "+indexName);
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
	
	// 判断索引是否存在 传入参数为索引库名称
    public static boolean isIndexExists(String indexName) {
        boolean flag = false;
        try {
            Client client = TransportClient.builder().build().addTransportAddress(
                    new InetSocketTransportAddress(InetAddress.getByName(ServerIP), ServerPort));

            IndicesExistsRequest inExistsRequest = new IndicesExistsRequest(indexName);

            IndicesExistsResponse inExistsResponse = client.admin().indices()
                    .exists(inExistsRequest).actionGet();

            if (inExistsResponse.isExists()) {
                flag = true;
            } else {
                flag = false;
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return flag;
    }
	
	/** 
     * TODO NotSolved 
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
     * disjunction max query 
     * 一个生成的子查询文件产生的联合查询， 
     * 而且每个分数的文件具有最高得分文件的任何子查询产生的， 
     * 再加上打破平手的增加任何额外的匹配的子查询。 
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
     */  
    protected static QueryBuilder disMaxQuery() {  
        return QueryBuilders.disMaxQuery()  
                .add(QueryBuilders.termQuery("title", "git"))          // Your queries  
                .add(QueryBuilders.termQuery("name", "简介"))   // Your queries  
                .boost(1.2f)  
                .tieBreaker(0.7f);  
    }
    
    
    
    /**
    * 分頁查詢，返回搜索結果SearchResponse
    * 
    * @param queryBuilder
    *            查詢條件
    * @param indexName
    *            索引库名 相當於 数据库名
    * @param type
    *            索引类型 相當於 表名
    * @param sort
    *            排序字段
    * @param order
    *            排序方式
    * @param page
    *            当前页码
    * @param pageSize
    *            分页大小
    * @return 返回搜索結果SearchResponse
    */
	public SearchResponse searcher1(QueryBuilder queryBuilder, String indexName, String type, String sort, String order, Integer page, Integer pageSize) {
		try {
			// 创建查询索引
			SearchRequestBuilder searchRequestBuilder = client.prepareSearch(indexName);
			// 设置查询索引类型
			searchRequestBuilder.setTypes(type);
			// 设置查询条件
			searchRequestBuilder.setQuery(queryBuilder);

			// 分頁
			if ((page > 0) && (pageSize > 0)) {
				// 设置查询数据的位置,分页用吧
				searchRequestBuilder.setFrom(pageSize * (page - 1));
				// 设置查询结果集的最大条数
				searchRequestBuilder.setSize(pageSize);
			}
			// 字段排序
			SortOrder sortOrder = SortOrder.ASC;// 排序方式 默认升序
			// 排序方式
			if (order.equals("desc")) {
				sortOrder = SortOrder.DESC;// 排序方式 註明降序
			}
			// 排序字段
			if ((null != sort) && (!sort.equals(""))) {
				searchRequestBuilder.addSort(sort, sortOrder);// 升序
			}
			// 设置是否按查询匹配度排序
			searchRequestBuilder.setExplain(true);
			// 最后就是返回搜索响应信息
			SearchResponse response = searchRequestBuilder.execute().actionGet();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
    
    
	public static void main(String[] args) {
		
		ElasticSearchHandler eHandler = new ElasticSearchHandler();
		//删除
		//eHandler.deleteIndexName("blog", "article");
		//创建
		//List<String> jsonData = DataFactory.getInitJsonData();
		//eHandler.createIndexResonpse("blog", "article", jsonData);
		/**
		 * AnalyzeResponse response2 = client.admin().indices().prepareAnalyze("中华人民共和国")//内容
					.setAnalyzer("ik")//指定分词器
						.execute().actionGet();//执行
		List<AnalyzeToken> tokens = response2.getTokens();
		String results = JSONArray.fromObject(tokens).toString();
		System.out.println(results);
		 */
		
		//根据title查询
		QueryBuilder queryBuilder = QueryBuilders.matchQuery("title", "兰州北京");
		//根据id查询
		QueryBuilder queryBuilder1 = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("id", 1));
		//多字段查询
		QueryBuilder queryBuilder2 = QueryBuilders.multiMatchQuery("兰州北京","title","content");
		//模糊查询
		//模糊查询，?匹配单个字符，*匹配多个字符
		WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("title", "*简*");
		//使用BoolQueryBuilder进行复合查询
		WildcardQueryBuilder wildcardQueryBuilder2 = QueryBuilders.wildcardQuery("content", "*你好*");
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(wildcardQueryBuilder);
		boolQueryBuilder.must(wildcardQueryBuilder2);
		
		WildcardQueryBuilder queryBuilder3 = QueryBuilders.wildcardQuery("title", "*简介*");//搜索名字中含有git的文档  
		WildcardQueryBuilder queryBuilder4 = QueryBuilders.wildcardQuery("content", "*基本*");//搜索content中含有基本的文档  
		BoolQueryBuilder boolQueryBuilder1 = QueryBuilders.boolQuery();  
		//name中含有jack或者interest含有read，相当于or  
		boolQueryBuilder1.should(queryBuilder3);  
		boolQueryBuilder1.should(queryBuilder4);  
		QueryBuilder qb = QueryBuilders.disMaxQuery()  
                .add(QueryBuilders.termQuery("title", "git"))          // Your queries  
                .add(QueryBuilders.termQuery("title", "Java"))   // Your queries  
                .boost(1.2f)  
                .tieBreaker(0.7f); 
//		QueryBuilder qb1 = disMaxQuery();
		List<Blog> result = eHandler.searcher(boolQueryBuilder1, "blog", "article");
		//根据我想要的查询排序返回result
		SearchResponse response = eHandler.searcher1(qb, "blog", "article", "posttime", "desc", 0, 10);
		SearchHits hits = response.getHits();
		System.out.println("查询到记录数=" + hits.getTotalHits());
    	SearchHit[] searchHists = hits.getHits();
    	List<Blog> bList = new ArrayList<Blog>();
		if (searchHists.length > 0) {
			for (SearchHit searchHit : searchHists) {
				Integer id = (Integer) searchHit.getSource().get("id");
				String title = String.valueOf(searchHit.getSource().get("title"));
				bList.add(new Blog(id,title,"","",1,1,10.0,""));
				System.out.println("我打印的id排序顺序是我想要的--"+id+"\t\t"+title);
			}
		}
		List<Blog> resultList = bList;
		
		for (Blog blog : resultList) {
			Integer id  = blog.getId();
			String title =blog.getTitle();
			System.out.println(id+"文章名字:\t\t"+title);
		}
//		 for(int i=0; i<resultList.size(); i++){
//	            Blog blog = result.get(i);
//	            System.out.println("(" + blog.getId() + ")文章名称:" +blog.getTitle() + "\t\t" + blog.getPosttime() + "\t\t" + blog.getContent());
//	        }
		  		
    }
}
