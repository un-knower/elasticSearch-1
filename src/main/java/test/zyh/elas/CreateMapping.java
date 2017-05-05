package test.zyh.elas;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.index.query.functionscore.script.ScriptScoreFunctionBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptService.ScriptType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHits;
import org.elasticsearch.search.sort.SortOrder;

import org.elasticsearch.action.admin.indices.analyze.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.functionScoreQuery;

public class CreateMapping {

    private static Client client;

    /**
     * 链接elasticSearch的服务。
     *
     * @return
     * @throws UnknownHostException
     */
    public static Client getClient() throws UnknownHostException {
        client = TransportClient.builder().build().addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        //如果修改过集群的名称：
//		Settings settings = ImmutableSettings.settingsBuilder()
//		.put("cluster.name", "elasticsearch_01").build();
//		Client client = new TransportClient(settings)       
//		.addTransportAddress(new InetSocketTransportAddress("172.20.0.196", 9300));
        return client;
    }

    /**
     * 先创建index
     *
     * @param index
     */
    public void createIndex(String index) {
        client.admin().indices().create(new CreateIndexRequest(index)).actionGet();
        // waitForYellow
        client.admin().cluster().health(new ClusterHealthRequest(index)
                .waitForYellowStatus())
                .actionGet();
    }

    /**
     * 创建yihang的indexmapping
     *
     * @param index
     * @param type
     * @throws IOException
     */
    public void createMapping(String index, String type) throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .startObject(type)
                .startObject("_all")
                .field("analyzer", "ik_max_word")
                .field("search_analyzer", "ik_max_word")
                .field("term_vector", "no")
                .field("store", "false")
                .endObject()
                .startObject("properties")
                .startObject("id").field("type", "integer").field("store", "yes").endObject()
                .startObject("title").field("type", "string").field("store", "yes").field("analyzer", "ik_max_word")
                .field("search_analyzer", "ik_max_word").field("term_vector", "with_positions_offsets").endObject()
                .startObject("posttime").field("type", "string").field("store", "yes").endObject()
                .startObject("content").field("type", "string").field("store", "yes").field("analyzer", "ik_max_word")
                .field("search_analyzer", "ik_max_word").field("term_vector", "with_positions_offsets").endObject()
                .startObject("hitsNumber").field("type", "integer").field("store", "yes").endObject()
                .startObject("commentNumber").field("type", "integer").field("store", "yes").endObject()
                .startObject("sorce").field("type", "double").field("store", "yes").endObject()
                .startObject("tags").field("type", "string").field("store", "yes").field("analyzer", "ik_max_word")
                .field("search_analyzer", "ik_max_word").field("term_vector", "with_positions_offsets").endObject()
                .endObject()
                .endObject()
                .endObject();
        PutMappingRequest mapping = Requests.putMappingRequest(index).type(type).source(builder);
        client.admin().indices().putMapping(mapping).actionGet();
    }


    public void createMappingChannelUser(String index ,String type) throws IOException{
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .startObject(type)
                .startObject("_all")
                .field("analyzer", "ik_max_word")
                .field("search_analyzer", "ik_max_word")
                .field("term_vector", "no")
                .field("store", "false")
                .endObject()
                .startObject("properties")
                .startObject("id").field("type", "integer").field("store", "yes").endObject()

                .startObject("title").field("type", "string").field("store", "yes").field("analyzer", "ik_max_word")
                .field("search_analyzer", "ik_max_word").field("term_vector", "with_positions_offsets").endObject()

                .startObject("desc").field("type", "string").field("store", "yes").field("analyzer", "ik_max_word")
                .field("search_analyzer", "ik_max_word").field("term_vector", "with_positions_offsets").endObject()

                .startObject("topicName").field("type", "string").field("store", "yes").field("analyzer", "ik_max_word")
                .field("search_analyzer", "ik_max_word").field("term_vector", "with_positions_offsets").endObject()

                .startObject("nickName").field("type", "string").field("store", "yes").field("analyzer", "ik_max_word")
                .field("search_analyzer", "ik_max_word").field("term_vector", "with_positions_offsets").endObject()

                .startObject("categoryName").field("type", "string").field("store", "yes").field("analyzer", "ik_max_word")
                .field("search_analyzer", "ik_max_word").field("term_vector", "with_positions_offsets").endObject()

                .startObject("userTag").field("type", "string").field("store", "yes").field("analyzer", "ik_max_word")
                .field("search_analyzer", "ik_max_word").field("term_vector", "with_positions_offsets").endObject()

                .startObject("tag").field("type", "string").field("store", "yes").field("analyzer", "ik_max_word")
                .field("search_analyzer", "ik_max_word").field("term_vector", "with_positions_offsets").endObject()

                .startObject("createTime").field("type", "string").field("store", "yes").endObject()
                .endObject()
                .endObject()
                .endObject();
        PutMappingRequest mapping = Requests.putMappingRequest(index).type(type).source(builder);
        client.admin().indices().putMapping(mapping).actionGet();
        System.out.printf("ok");
    }


    /**
     * 创建channel的index
     *
     * @param index
     * @param type
     * @throws IOException
     */
    public void createMappingChannel(String index, String type) throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .startObject(type)
                .startObject("_all")
                .field("analyzer", "ik_smart")
                .field("search_analyzer", "ik_smart")
                .field("term_vector", "no")
                .field("store", "false")
                .endObject()
                .startObject("properties")
                .startObject("title").field("type", "string").field("store", "yes").field("analyzer", "ik_max_word")
                .field("search_analyzer", "ik_max_word").field("term_vector", "with_positions_offsets").endObject()
                .startObject("publicStatus").field("type", "integer").field("store", "yes").endObject()
                .startObject("delStatus").field("type", "integer").field("store", "yes").endObject()
                .startObject("status").field("type", "integer").field("store", "yes").endObject()
                .startObject("tags").field("type", "string").field("store", "yes").field("analyzer", "ik_max_word")
                .field("search_analyzer", "ik_max_word").field("term_vector", "with_positions_offsets").endObject()
                .startObject("weiboNick").field("type", "string").field("store", "yes").field("analyzer", "ik_max_word")
                .field("search_analyzer", "ik_max_word").field("term_vector", "with_positions_offsets").endObject()
                .startObject("selfNick").field("type", "string").field("store", "yes").field("analyzer", "ik_max_word")
                .field("search_analyzer", "ik_max_word").field("term_vector", "with_positions_offsets").endObject()
                .endObject()
                .endObject()
                .endObject();
        PutMappingRequest mapping = Requests.putMappingRequest(index).type(type).source(builder);
        client.admin().indices().putMapping(mapping).actionGet();
        System.out.printf("ok");
    }

    /**
     * 单个词合并,分词逻辑
     *
     * @param name
     * @return
     */
    public List<String> Aanlaye(String name) {
        //单个分词合并
        AnalyzeResponse response2 = client.admin().indices().prepareAnalyze(name).setAnalyzer("ik_smart").execute().actionGet();//执行
        List<AnalyzeResponse.AnalyzeToken> tokens = response2.getTokens();
        JSONArray json = JSONArray.fromObject(tokens);
        List<String> list = new ArrayList<String>();
        List<String> list1 = new ArrayList<String>();
        String str = "";
        for (int i = 0; i < json.size(); i++) {
            JSONObject job = json.getJSONObject(i);
            if (job.get("term").toString().length() <= 1) {
                str += job.get("term").toString();
            } else {
                list.add(job.get("term").toString());
            }
        }
        list1.add(str);
        list.addAll(list1);
        return list;
    }

    /**
     * 创建数据
     *
     * @param index
     * @param type
     */
    public void createData(String index, String type) {
        long startTime = System.currentTimeMillis();
        List<String> jsondata = DataFactory.getInitJsonData();
        for (int i = 0; i < jsondata.size(); i++) {
            IndexResponse indexResp = client.prepareIndex().setIndex(index).setType(type).setSource(jsondata.get(i)).execute().actionGet();
            boolean isCreated = indexResp.isCreated();
            System.out.println("是否成功创建数据isCreated:" + isCreated);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("程序执行的时间" + (endTime - startTime) + "ms");
    }

    //	indexResp
    public void queryData(String index, String type) {
        QueryBuilder queryBuilder = QueryBuilders.termQuery("content", "中国");
        SearchResponse searchResponse = client.prepareSearch(index).setTypes(type)
                .setQuery(queryBuilder)
                .execute()
                .actionGet();
        SearchHits hits = searchResponse.getHits();
        System.out.println("查询到记录数:" + hits.getTotalHits());
        SearchHit[] searchHists = hits.getHits();
        for (SearchHit sh : searchHists) {
            System.out.println("content:" + sh.getSource().get("content"));
        }
    }

    //sorce_script打分算法
    private static void fromSize() throws UnknownHostException {
        Map<String, Object> params = new HashMap<String, Object>();
        Double blogMaxScore = 100000.00;
        Double maxHitsCount = 1000.00;
        Double maxCommentCount = 1000.00;
        params.put("num1", 1);
        params.put("num2", 2);
        String inlineScript =
                "int blogMaxScore = 1000;" +
                        "int sorce = 0;"
                        + "hitsNumber= doc['hitsNumber'].value;"
                        + "commentNumber= doc['commentNumber'].value;"
                        + "if(hitsNumber>0 && hitsNumber != null)" +
                        "{sorce =sorce+ (hitsNumber-0) / (100-0) + blogMaxScore * 0.5 };"
                        + "if(commentNumber>0 && commentNumber != null)" +
                        "{sorce =sorce+ (hitsNumber-0) / (100-0) + blogMaxScore * 0.5 };"
                        + "return sorce";
        System.out.println(inlineScript + "评分");
        Script script = new Script(inlineScript, ScriptType.INLINE, "groovy", params);
        ScriptScoreFunctionBuilder scriptBuilder = ScoreFunctionBuilders.scriptFunction(script);
        TransportClient client =
                TransportClient.builder().build()
                        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("LOCALHOST"), 9300));
        long count = client.prepareCount("yihang").setTypes("zhang").execute().actionGet().getCount();
        SearchRequestBuilder requestBuilder =
                client.prepareSearch("yihang").setTypes("zhang")
                        .setQuery(functionScoreQuery(QueryBuilders.matchQuery("title", "兰州"), scriptBuilder));
        SearchResponse response = requestBuilder.setFrom(0).setSize(10).execute().actionGet();
        requestBuilder.addSort("_score", SortOrder.ASC);
        long sum = response.getHits().hits().length;
        System.out.println("总量" + count + " 已经查到" + sum);
        SearchHit[] hits = response.getHits().getHits();
        for (int j = 0; j < hits.length; j++) {
            System.out.println(hits[j].getSourceAsString());
            System.out.println(hits[j].getScore());
        }

    }

    /**
     * @param index 索引名称
     * @param type  索引类型
     * @param from  0
     * @param size  大小
     * @param name  聚合查询参数
     * @param sort  排序字段
     * @param order 升序or降序
     */
    //聚合查询
    public void aggrdation(String index, String type, String from, String size, String name, String sort, String order) {
        //聚合查询根据关键字
        SearchResponse response1 = null;

        SearchRequestBuilder responsebuilder = client.prepareSearch("yihang").setTypes("zhang").setFrom(0).setSize(250);
        AggregationBuilder aggregation = AggregationBuilders
                .terms("agg")
                .field("id")
                .subAggregation(AggregationBuilders.topHits("top").setFrom(0).setSize(10)).size(100);
        response1 = responsebuilder.setQuery(QueryBuilders.boolQuery()
                .must(QueryBuilders.matchPhraseQuery("tags", "范冰冰")))
                .addSort("commentNumber", SortOrder.DESC)
                .addAggregation(aggregation)// .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setExplain(true).execute().actionGet();
        SearchHits searchHits = response1.getHits();

        Terms agg = response1.getAggregations().get("agg");
        System.out.printf(String.valueOf(agg.getBuckets().size()));

        for (Terms.Bucket entry : agg.getBuckets()) {
            Long key = (Long) entry.getKey(); // bucket key
            long docCount = entry.getDocCount(); // Doc count
            System.out.println("key " + key + " doc_count " + docCount);

            // 遍历出来的值看看是不是我想要的。
            TopHits topHits = entry.getAggregations().get("top");
            for (SearchHit hit : topHits.getHits().getHits()) {
                System.out.println(" -> id " + hit.getId() + " _source [{}]"
                        + hit.getSource().get("id"));
            }
        }
        System.out.println(searchHits.getTotalHits());
        for (int i = 0; i < searchHits.getTotalHits(); i++) {
            String title = (String) searchHits.getHits()[i].getSource().get("title");
            String content = (String) searchHits.getHits()[i].getSource().get("content");
            System.out.printf("title :=" + title + "content:=" + content);
        }
    }
    public List<String> boolQuery(ElasticSearchHandler searchHandler){
        //标签查询《boolquery查询》
        List<String> boolQueryList = new ArrayList<String>();
        List<String> SEARCH_FIELDS = Arrays.asList(new String[]{"title","desc","topicName","userTag","categoryName","nickName"});
        BoolQueryBuilder bool = QueryBuilders.boolQuery();
        String keyword = "因为一个人拒绝所有人";
        //按照空格分词 , 每个分词的查询结果取交集
        String[] keywordArr = keyword.split("\\s+");
        for (String key : keywordArr) {
            System.out.printf("key :" +key+"\t");
            if (key != null) {
                BoolQueryBuilder boolChild = QueryBuilders.boolQuery();
				/*
				 * 布尔查询采用越多匹配越好的方法，所以每个match子句的得分会被加起来变成最后的每个文档的得分。
				 * 匹配两个子句的文档的得分会比只匹配了一个文档的得分高
				 * */
                for (String fieldName : SEARCH_FIELDS) {
                    if (fieldName.equals("title")) {
                        boolChild.should(QueryBuilders.matchQuery(fieldName, key).boost(3.5f));
                    } else if (fieldName.equals("desc")) {
                        boolChild.should(QueryBuilders.matchQuery(fieldName, key).boost(3.5f));
                    } else if (fieldName.equals("topicName")){
                        boolChild.should(QueryBuilders.matchQuery(fieldName,key).boost(1));
                    } else if(fieldName.equals("userTag")){
                        boolChild.should(QueryBuilders.matchQuery(fieldName,key).boost(1));
                    } else if (fieldName.equals("categoryName")){
                        boolChild.should(QueryBuilders.matchQuery(fieldName,key).boost(0.5f));
                    } else if (fieldName.equals("nickName")){
                        boolChild.should(QueryBuilders.matchQuery(fieldName,key).boost(0.5f));
                    }
                }
                bool.must(boolChild);
            }
        }

        //根据categoryName查询
       // QueryBuilder queryBuilder = QueryBuilders.matchQuery("categoryName", "明星");

        SearchResponse response = searchHandler.searcher1(bool, "channelvideo", "channelvideo", "createTime", "desc", 1, 10);
        SearchHits hits = response.getHits();
        System.out.println("查询到记录数:" + hits.getTotalHits());
        SearchHit[] searchHit = hits.getHits();
        for (SearchHit searchHit1 : searchHit) {
            String title = (String) searchHit1.getSource().get("title");
            boolQueryList.add(title);
            System.out.printf("categoryName:" + searchHit1.getSource().get("categoryName") + "\t" + "title:" + searchHit1.getSource().get("title") + "\t" + "desc:" + searchHit1.getSource().get("desc")+"\t"+"userTag"+searchHit1.getSource().get("userTag"));
            System.out.printf("\n");
        }
        return boolQueryList;
    }

    public static void main(String[] args) throws IOException {
        CreateMapping createMapping = new CreateMapping();
        ElasticSearchHandler eHandler = new ElasticSearchHandler();
        CreateMapping.getClient();
//		createMapping.createIndex("channelvideo");
//        createMapping.createMappingChannelUser("channelvideo", "channelvideo");
//		createMapping.createMappingChannel("channel", "start");
//		createMapping.createData("channelvideo", "channelvideo");
//		createMapping.queryData("yihang", "zhang");

		/*QueryBuilders.termQuery("tags","范冰冰");
        QueryBuilders.existsQuery("content");*/


//		QueryBuilder QueryBuilder2 = QueryBuilders.boolQuery().must(QueryBuilders.multiMatchQuery("范冰冰", "tags")).must(QueryBuilders.multiMatchQuery("范冰冰", "title"));

//		QueryBuilder queryBuilder =  QueryBuilders.matchQuery("title", "北京兰州");


//		String str = new String("贾乃亮,李冰冰,范冰冰");
//		List<String> list = new ArrayList<String>();
//		String[] newstr = str.split(",");
//		for (int i =0;i<newstr.length;i++){
//			list.add(newstr[i]);
//		}
//		System.out.printf(list.toString()+"\n");
//		QueryBuilder queryBuilder1 = QueryBuilders.multiMatchQuery(list.toString(),"tags","content");

        List<String> queryList = createMapping.boolQuery(eHandler);
//        System.out.printf(queryList.toString());
        //elastic_scrip_sorce写法
        //fromSize();


        client.close();
    }

}
