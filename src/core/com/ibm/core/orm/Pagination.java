package com.ibm.core.orm;


import java.util.List;

/**
 * @author syl
 * @param <T>
 */
public class Pagination<T> {

	// 分页信息
	private int nowpage;// 当前页
	private int countrecord;// 总记录
	private int countpage;// 总页数

	public  int PAGESIZE = 5;// 每页显示的记录数

	private int startpage;// 页面中的起始页
	private int endpage;// 页面中的结束页

	private final int SHOWPAGE = 6;// 页面中显示的总页数 baidu,google显示的总页数是20

	private List<T> allentities;

	private String url;

	/** 根据当前页及总记录数来构造分页对象 */
	public Pagination(int nowpage, int countrecord) {
		this.nowpage = nowpage;
		this.countrecord = countrecord;

		/** 计算总页数 */
		this.countpage = this.countrecord % this.PAGESIZE == 0 ? this.countrecord
				/ this.PAGESIZE
				: this.countrecord / this.PAGESIZE + 1;

		/** 计算startpage与endpage的值 */

		/** 总页数数是否小于4 */
		if (this.countpage < (this.SHOWPAGE / 2 + 1)) {
			this.startpage = 1; // 页面中起始页就是1
			this.endpage = this.countpage;// 页面中的最终页就是总页数
		} else {
			/** else中是总页数大于4的情况 */

			/** 首先当前页的值是否小于等于4 */
			if (this.nowpage <= (this.SHOWPAGE / 2 + 1)) {
				this.startpage = 1;
				this.endpage = this.nowpage + 2;
				/** 判断页面的最终页是否大于总页数 */
				if (this.endpage >= this.countpage) {
					this.endpage = this.countpage;
				}
			} else {
				this.startpage = this.nowpage - 3;
				this.endpage = this.nowpage + 2;

				if (this.endpage >= this.countpage) {
					this.endpage = this.countpage;
					if (this.countpage < this.SHOWPAGE) {
						this.startpage = 1;
					} else {
						this.startpage = this.endpage - 5;
					}

				}
			}

		}

	}

	public int getNowpage() {
		return nowpage;
	}

	public void setNowpage(int nowpage) {
		this.nowpage = nowpage;
	}

	public int getCountrecord() {
		return countrecord;
	}

	public void setCountrecord(int countrecord) {
		this.countrecord = countrecord;
	}

	public int getCountpage() {
		return countpage;
	}

	public void setCountpage(int countpage) {
		this.countpage = countpage;
	}

	public int getStartpage() {
		return startpage;
	}

	public void setStartpage(int startpage) {
		this.startpage = startpage;
	}

	public int getEndpage() {
		return endpage;
	}

	public void setEndpage(int endpage) {
		this.endpage = endpage;
	}

	public List<T> getAllentities() {
		return allentities;
	}

	public void setAllentities(List<T> allentities) {
		this.allentities = allentities;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
