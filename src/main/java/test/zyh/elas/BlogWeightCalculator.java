package test.zyh.elas;
/**
 * 
 * @author zhangyihang
 * @ 描述 点击数占（50%）+ 评论数占（50%）
 * a点击数得分 点击数越多得分越高<br/>
 * b.评论得分 有效评论数量越多得分越高 (有效blog不包含敏感词，无效 ，已删除的blog中回复)
 */
public class BlogWeightCalculator {

	private Double blogMaxScore = 100000.00;
    private Double maxHitsCount = 1000.00;
    private Double maxCommentCount = 1000.00;
    
    
    public Double calculate(Blog blog){
    	double socre = 0;
    	if(blog.getHitsNumber() != null && blog.getHitsNumber()>0)
    		socre += WeightAlgorithm.normalization(blog.getHitsNumber().doubleValue(), 0.0, maxHitsCount, 0.0, blogMaxScore * 0.5);
    		System.out.println(socre+"点击数");
    	
    	if(blog.getCommentNumber() != null && blog.getCommentNumber() >0)
    		socre += WeightAlgorithm.normalization(blog.getCommentNumber().doubleValue(), 0.0, maxCommentCount, 0.0, blogMaxScore * 0.5);
    		System.out.println(socre+"评论数");
    		
    	blog.setSorce(socre);
    	return socre;
    }
    
    public static void main(String[] args) {
    	BlogWeightCalculator blogWeightCalculator = new BlogWeightCalculator();
    	Blog blog2 = new Blog();
    	blog2.setCommentNumber(345);
    	blog2.setHitsNumber(456);
    	Double ss = blogWeightCalculator.calculate(blog2);
    	System.out.println(ss);
	}
    
}
