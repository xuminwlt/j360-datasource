package me.j360.datasource.distributed.route.support;

import java.io.Serializable;

/**
 * Created with j360-datasource -> me.j360.datasource.distributed.route.support.
 * User: min_xu
 * Date: 2015/10/31
 * Time: 9:55
 * 说明：分布式路由Bean，TableNameBean,
 * @see me.j360.datasource.distributed.enums.RouteContextEnum
 */
public class TableNameBean implements Serializable {
	private static final long serialVersionUID = 1588694988612959719L;

	private String tablefield;
	
	private String tablefieldvalue;

	public TableNameBean() {
		super();
	}


	public String getTablefield() {
		return tablefield;
	}

	public void setTablefield(String tablefield) {
		this.tablefield = tablefield;
	}

	public String getTablefieldvalue() {
		return tablefieldvalue;
	}

	public void setTablefieldvalue(String tablefieldvalue) {
		this.tablefieldvalue = tablefieldvalue;
	}

}
