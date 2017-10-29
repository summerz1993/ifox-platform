package com.ifox.platform.common.page;

import com.ifox.platform.common.rest.response.PageResponseDetail;

import java.util.List;

/**
 * @author Yeager
 *
 * 简单分页类
 */
public class SimplePage<T> implements Pageable {

	/**
	 * 默认页码
	 */
	public static final int DEFAULT_PAGE_NO = 1;

	/**
	 * 默认每页数量
	 */
	public static final int DEFAULT_PAGE_SIZE = 20;

	/**
	 * 最大每页数量
	 */
	public static final int MAX_PAGE_SIZE = 1000;

	/**
	 * 默认总数
	 */
	public static final int DEFAULT_TOTAL_COUNT = 0;

	/**
	 * 总记录数
	 */
	private int totalCount = DEFAULT_TOTAL_COUNT;

	/**
	 * 每页数量
	 */
	private int pageSize = DEFAULT_PAGE_SIZE;

	/**
	 * 页码
	 */
	private int pageNo = DEFAULT_PAGE_NO;

    /**
     * 当前页的数据
     */
    private List<T> content;

    /**
     * 无参构造器
     */
	public SimplePage() {
	}

    /**
     * 构造器
     * @param pageSize 每页数量
     * @param pageNo 页码
     */
    public SimplePage(int pageSize, int pageNo) {
        setPageSize(pageSize);
        setPageNo(pageNo);
    }

    /**
	 * 构造器
	 * 
	 * @param pageNo 页码
	 * @param pageSize 每页数量
	 * @param totalCount 总计数量
	 */
	public SimplePage(int pageNo, int pageSize, int totalCount) {
		setTotalCount(totalCount);
		setPageSize(pageSize);
		setPageNo(pageNo);
		adjustPageNo();
	}

    /**
     * 构造器
     *
     * @param pageNo 页码
     * @param pageSize 每页数量
     * @param totalCount 总计数量
     * @param content 数据
     */
    public SimplePage(int pageNo, int pageSize, int totalCount, List<T> content) {
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.content = content;
    }

    @Override
	public int getTotalCount() {
		return totalCount;
	}

	@Override
	public int getPageSize() {
		return pageSize;
	}

	@Override
	public int getPageNo() {
		return pageNo;
	}

	@Override
	public int getTotalPage() {
		int totalPage = totalCount / pageSize;
		if (totalPage == 0 || totalCount % pageSize != 0) {
			totalPage++;
		}
		return totalPage;
	}

	@Override
	public boolean isFirstPage() {
		return pageNo <= 1;
	}

	@Override
	public boolean isLastPage() {
		return pageNo >= getTotalPage();
	}

	@Override
	public int getNextPage() {
		if (isLastPage()) {
			return pageNo;
		} else {
			return pageNo + 1;
		}
	}

	@Override
	public int getPrePage() {
		if (isFirstPage()) {
			return pageNo;
		} else {
			return pageNo - 1;
		}
	}

	@Override
	public int getFirstResult() {
		return (pageNo - 1) * pageSize;
	}

	public void setTotalCount(int totalCount) {
		if (totalCount < 0) {
			this.totalCount = 0;
		} else {
			this.totalCount = totalCount;
		}
	}

	public void setPageSize(int pageSize) {
		if (pageSize < 1) {
			this.pageSize = DEFAULT_PAGE_SIZE;
		} else if (pageSize > MAX_PAGE_SIZE) {
			this.pageSize = MAX_PAGE_SIZE;
		} else {
			this.pageSize = pageSize;
		}
	}

	public void setPageNo(int pageNo) {
		if (pageNo < 1) {
			this.pageNo = 1;
		} else {
			this.pageNo = pageNo;
		}
	}

	/**
	 * 调整页码，使不超过最大页数
	 */
	public void adjustPageNo() {
		if (pageNo == 1) {
			return;
		}
		int totalPage = getTotalPage();
		if (pageNo > totalPage) {
			pageNo = totalPage;
		}
	}

	public PageResponseDetail convertToPageResponseDetail() {
	    return new PageResponseDetail(getTotalCount(), getPageSize(), getPageNo());
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SimplePage{" +
            "totalCount=" + totalCount +
            ", pageSize=" + pageSize +
            ", pageNo=" + pageNo +
            ", content=" + content +
            '}';
    }
}
