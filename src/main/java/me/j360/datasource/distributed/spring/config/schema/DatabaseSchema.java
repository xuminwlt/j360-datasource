/**
 * 
 */
package me.j360.datasource.distributed.spring.config.schema;

import java.io.Serializable;
import java.util.List;

/**
 * Created with j360-datasource -> me.j360.datasource.distributed.spring.config.schema
 * User: min_xu
 * Date: 2015/11/2
 * Time: 10:43
 * 说明：数据源
 */
public class DatabaseSchema implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1023587279430654493L;

	private List<TableSchema> tableSchemas;// 对应的表

	private String databaseName;

	private String datasourceId;
	
	public DatabaseSchema(){
		
	}
	
	public DatabaseSchema(String databaseName, String datasourceId,
			List<TableSchema> tableSchemas) {
		super();
		
		this.tableSchemas = tableSchemas;
	}

	/**
	 * @return the tableSchemas
	 */
	public List<TableSchema> getTableSchemas() {
		return tableSchemas;
	}

	/**
	 * @param tableSchemas the tableSchemas to set
	 */
	public void setTableSchemas(List<TableSchema> tableSchemas) {
		this.tableSchemas = tableSchemas;
	}

	/**
	 * @return the databaseName
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * @param databaseName the databaseName to set
	 */
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	/**
	 * @return the datasourceId
	 */
	public String getDatasourceId() {
		return datasourceId;
	}

	/**
	 * @param datasourceId the datasourceId to set
	 */
	public void setDatasourceId(String datasourceId) {
		this.datasourceId = datasourceId;
	}
}
