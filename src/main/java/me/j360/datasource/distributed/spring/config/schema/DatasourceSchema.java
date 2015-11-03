/**
 * 
 */
package me.j360.datasource.distributed.spring.config.schema;

import java.io.Serializable;
import java.util.*;

/**
 * Created with j360-datasource -> me.j360.datasource.distributed.spring.config.schema
 * User: min_xu
 * Date: 2015/11/2
 * Time: 10:43
 * 说明：数据源datasource集合
 */
public class DatasourceSchema implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6229684042964298320L;
   
	private Map<String, DatabaseSchema> map;

	private String type;// 类型
	
	private String weight;//权重
	
	public DatasourceSchema() {

	}

	/**
	 * @return the map
	 */
	public Map<String, DatabaseSchema> getMap() {
		return map;
	}

	/**
	 * @param map
	 *            the map to set
	 */
	public void setMap(Map<String, DatabaseSchema> map) {
		this.map = map;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * 通过库名查询数据库对应的表
	 */
	public Map<String, List<TableSchema>> collectDataBaseMappingTablename() {
		Map<String, List<TableSchema>> databaseTablesmaps = new HashMap<String, List<TableSchema>>();
		for (String key : map.keySet()) {
			DatabaseSchema baseSchema = map.get(key);
			databaseTablesmaps.put(baseSchema.getDatabaseName(),
					baseSchema.getTableSchemas());
		}
		return databaseTablesmaps;
	}
	
	/**
	 * 查询表对应的数据库
	 * 
	 * @return
	 */
	public Map<String, Set<String>> collectTablenameMapingDatasource() {
		Map<String, Set<String>> tablenameMapingDatasources = new HashMap<String, Set<String>>();
		for (String key : map.keySet()) {
			for (TableSchema tableSchema : map.get(key).getTableSchemas()) {
				if (tablenameMapingDatasources.containsKey(tableSchema
						.getTablename())) {
					Set<String> databases = tablenameMapingDatasources
							.get(tableSchema.getTablename());
					databases.add(map.get(key).getDatabaseName());
				} else {
					Set<String> databases = new HashSet<String>();
					databases.add(map.get(key).getDatabaseName());
					tablenameMapingDatasources.put(tableSchema.getTablename(),
							databases);
				}
			}
		}
		return tablenameMapingDatasources;
	}
	/**
	 * 根据字段名找到对应表名
	 * @param tablenamefield
	 * @return
	 */
	public String collectTablename(String tablenamefield) {
		String tablename = null;
		for (String key : map.keySet()) {
			for (TableSchema tableSchema : map.get(key).getTableSchemas()) {
				if (tableSchema.getTablefield().equals(tablenamefield)) {
					tablename = tableSchema.getTablefield();
				}
				break;
			}
			break;

		}
		return tablename;
	}
	/**
	 * 获取字段名
	 * @return
	 */
	public HashSet<String> getTablenameFields(){
		HashSet<String> tablenameFields=new HashSet<String>();
		for (String key : map.keySet()) {
			for (TableSchema tableSchema : map.get(key).getTableSchemas()) {
				tablenameFields.add(tableSchema.getTablefield());
			}
		}
		return tablenameFields;
	}

	/**
	 * @return the weight
	 */
	public String getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(String weight) {
		this.weight = weight;
	}
	
	
}
