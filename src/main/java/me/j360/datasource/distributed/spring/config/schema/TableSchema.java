package me.j360.datasource.distributed.spring.config.schema;

import java.io.Serializable;

/**
 * Created with j360-datasource -> me.j360.datasource.distributed.spring.config.schema
 * User: min_xu
 * Date: 2015/11/2
 * Time: 10:43
 * 说明：分库分表表格
 */
public class TableSchema implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3216616684917523710L;

	private String tablename;// 表名

	private String prefixname;// 别名

	private int count;// 数量

	private String tablefield;// 字段名
	
	public TableSchema(){
		
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getPrefixname() {
		return prefixname;
	}

	public void setPrefixname(String prefixname) {
		this.prefixname = prefixname;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getTablefield() {
		return tablefield;
	}

	public void setTablefield(String tablefield) {
		this.tablefield = tablefield;
	}
	
	
}
