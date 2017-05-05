package test.zyh.elas;

import java.io.Serializable;

public class Blog implements Serializable {

    private static final long serialVersionUID = -748988973289886465L;

    private Integer id;
	private String title;
	private String posttime;
	private String content;
	//浏览数
	private Integer hitsNumber = 0;
	//评论数
	private Integer commentNumber = 0;
	//评分weight
	private Double sorce  = 0.0;
	//标签字段
	private String tags;
	

	public Blog() {}

	public Blog(Integer id, String title, String posttime, String content,Integer hitsNumber,Integer commentNumber,Double sorce,String tags) {
		this.id = id;
		this.title = title;
		this.posttime = posttime;
		this.content = content;
		this.hitsNumber =hitsNumber;
		this.commentNumber = commentNumber;
		this.sorce = sorce;
		this.tags = tags;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPosttime() {
		return posttime;
	}

	public void setPosttime(String posttime) {
		this.posttime = posttime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getHitsNumber() {
		return hitsNumber;
	}

	public void setHitsNumber(Integer hitsNumber) {
		this.hitsNumber = hitsNumber;
	}

	public Integer getCommentNumber() {
		return commentNumber;
	}

	public void setCommentNumber(Integer commentNumber) {
		this.commentNumber = commentNumber;
	}

	public Double getSorce() {
		return sorce;
	}

	public void setSorce(Double sorce) {
		this.sorce = sorce;
	}
	
	
}
