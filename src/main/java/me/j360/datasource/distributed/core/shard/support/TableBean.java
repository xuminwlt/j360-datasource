package me.j360.datasource.distributed.core.shard.support;

import java.io.Serializable;

/**
 * Created with j360-datasource -> me.j360.datasource.distributed.core.shard.support
 * User: min_xu
 * Date: 2015/10/31
 * Time: 9:55
 * 说明：表名称类
 */
public class TableBean implements Serializable {
	private static final long serialVersionUID = -2662430180662615369L;

	private String name;

	private String prefixname;

	public TableBean() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrefixname() {
		return prefixname;
	}

	public void setPrefixname(String prefixname) {
		this.prefixname = prefixname;
	}

}
