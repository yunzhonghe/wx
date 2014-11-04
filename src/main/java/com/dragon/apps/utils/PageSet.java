package com.dragon.apps.utils;

import java.util.List;

public class PageSet {
	private int totalSize;//总数
	private int totalPage;//总页数	private int currPage = 1;//当前页	private int pageSize = 10;//单页数	@SuppressWarnings("rawtypes")
	private List resultList;//返回结果
	
	public PageSet(){}
	public PageSet(int currPage,int pageSize){
		this.currPage = currPage;
		this.pageSize = pageSize;
	}

	public int getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
		
		this.totalPage = this.totalSize / this.pageSize;
		if((this.totalSize % this.pageSize) > 0)
			this.totalPage = this.totalPage + 1;
		
		if(this.totalSize == 0){
			this.currPage = 1;
		}else{
			if(this.currPage == 0)
				this.currPage = 1;
		}
	}
	public int getTotalPage() {
		if(totalSize == 0){	
			return 1;
		}else{
			return totalPage;
		}
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getCurrPage() {
		return currPage;
	}
	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	@SuppressWarnings("rawtypes")
	public List getResultList() {
		return resultList;
	}
	@SuppressWarnings("rawtypes")
	public void setResultList(List resultList) {
		this.resultList = resultList;
	}
}
