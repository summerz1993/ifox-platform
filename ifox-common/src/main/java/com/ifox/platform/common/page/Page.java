package com.ifox.platform.common.page;
import java.util.List;

/**
 * @author Yeager
 *
 * 常用分页类
 */
public class Page<T> extends SimplePage implements java.io.Serializable{

	/**
	 * 当前页的数据
	 */
	private List<T> content;

	public Page() {
	}

	public Page(int pageNo, int pageSize, int totalCount) {
		super(pageNo, pageSize, totalCount);
	}

	public Page(int pageNo, int pageSize, int totalCount, List<T> list) {
		super(pageNo, pageSize, totalCount);
		this.content = list;
	}

	public Page(SimplePage simplePage){
	    setPageNo(simplePage.getPageNo());
	    setPageSize(simplePage.getPageSize());
	    setTotalCount(simplePage.getTotalCount());
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}
}
