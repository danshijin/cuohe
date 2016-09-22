package com.smm.cuohe.domain;

/**
 * @author  zhaoyutao
 * @version 2015年11月16日 下午8:39:26
 * 类说明
 */

public class SerachParam {
	
	public SerachParam(){}
	
	/**
	 * @param attribute
	 * @param operator
	 * @param content
	 */
	public SerachParam(String attribute, String operator, String content){
		this.attribute = attribute;
		this.operator = operator;
		this.content = content;
	}
	
	/**
	 * @param attribute
	 * @param operator
	 * @param content
	 * @param _orderby
	 * @param _order
	 */
	public SerachParam(String attribute, String operator, String content, String _orderby, String _order){
		this(attribute, operator, content);
		this._orderby = _orderby;
		this._order = _order;
	}
	
	/**
	 * @param attribute
	 * @param operator
	 * @param content
	 * @param _orderby
	 * @param _order
	 * @param _start
	 * @param _size
	 */
	public SerachParam(String attribute, String operator, String content, String _orderby, String _order, Integer _start, Integer _size){
		this(attribute, operator, content, _orderby, _order);
		this._start = _start;
		this._size = _size;
	}
	
	private String attribute;
	
	private String operator;
	
	private String content;
	
	private String _orderby;
	
	private String _order;
	
	private Integer _start;
	
	private Integer _size;
	
	private Integer type;//报盘谁创建的：0 全部，1 本人创建，2 部门创建，全部
	
	private Integer userId;

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String get_orderby() {
		return _orderby;
	}

	public void set_orderby(String _orderby) {
		this._orderby = _orderby;
	}

	public String get_order() {
		return _order;
	}

	public void set_order(String _order) {
		this._order = _order;
	}

	public Integer get_start() {
		return _start;
	}

	public void set_start(Integer _start) {
		this._start = _start;
	}

	public Integer get_size() {
		return _size;
	}

	public void set_size(Integer _size) {
		this._size = _size;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
}
