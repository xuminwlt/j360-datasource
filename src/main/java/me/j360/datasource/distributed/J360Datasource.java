package me.j360.datasource.distributed;

import me.j360.datasource.distributed.core.shard.DatabaseShard;
import me.j360.datasource.distributed.core.shard.TableBeanShard;
import me.j360.datasource.distributed.core.shard.support.TableBean;
import me.j360.datasource.distributed.route.support.TableNameBean;
import me.j360.datasource.distributed.spring.config.schema.DatabaseSchema;
import me.j360.datasource.distributed.spring.config.schema.DatasourceSchema;
import me.j360.datasource.distributed.spring.config.schema.TableSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with j360-datasource -> me.j360.datasource.distributed.utils;
 * User: min_xu
 * Date: 2015/10/31
 * Time: 9:55
 * 说明：分布式数据源核心类,单例模式，获取配置中的数据源配置，懒加载模式，配置更新后需要重启，暂不支持分布式配置方案
 */
public class J360Datasource {

	private J360Datasource() {
		
	}

	private static class SingletonHolder {
		static final J360Datasource instance = new J360Datasource();
	}

	public static J360Datasource getInstance() {
		return SingletonHolder.instance;
	}

	/**
	 * 根据表的字段名
	 * 获取分库分表选择的数据库
	 * @param datasourceSchema
	 * @param tableNameBean
	 * @return
	 */
	public DatabaseSchema getShardDataSoure(DatasourceSchema datasourceSchema,TableNameBean tableNameBean) {
		Map<String, DatabaseSchema> map = datasourceSchema.getMap();
		List<DatabaseSchema> dataBaseSchemas=new ArrayList<DatabaseSchema>();
		String tablename="";
		Boolean flag=false;
		for(String key:map.keySet()){//找出跳出循环
			List<TableSchema> tableSchemas=map.get(key).getTableSchemas();
			for(TableSchema tableSchema:tableSchemas){
				if(tableSchema.getTablefield().equals(tableNameBean.getTablefield())){
					tablename=tableSchema.getTablename();
					flag=true;
					break;
				}
			}
			if(flag){
				break;
			}
		}
		Map<String, Set<String>> tablenameMapingDatasources = datasourceSchema.collectTablenameMapingDatasource();
		Set<String> databases=tablenameMapingDatasources.get(tablename);
		for(String database:databases){
			dataBaseSchemas.add(datasourceSchema.getMap().get(database));
		}
		DatabaseShard baseSchemaHashShard=new DatabaseShard(dataBaseSchemas);
		return baseSchemaHashShard.getShardInfo(tableNameBean.getTablefieldvalue());
	}

	/**
	 * 获取分库分表选择的表名
	 * @param datasourceSchema
	 * @param tableNameBean
	 * @param databsename
	 * @return
	 */
	public TableBean getShardTableName(DatasourceSchema datasourceSchema,TableNameBean tableNameBean,String databsename) {
		Map<String, List<TableSchema>> maps = datasourceSchema.collectDataBaseMappingTablename();
		List<TableSchema> tableSchemas=maps.get(databsename);
		TableSchema result=new TableSchema();
		for(TableSchema tableSchema:tableSchemas){
			if(tableSchema.getTablefield().equals(tableNameBean.getTablefield())){
				result=tableSchema;
				break;
			}
		}
		
		List<TableBean> tableBeans=new ArrayList<TableBean>();
		for(int i=0;i<result.getCount();i++){
			TableBean tableBean=new TableBean();
			tableBean.setName(result.getTablename());
			tableBean.setPrefixname(result.getPrefixname()+i);
			tableBeans.add(tableBean);
		}
		TableBeanShard beanHashShard=new TableBeanShard(tableBeans);
		return beanHashShard.getShardInfo(tableNameBean.getTablefieldvalue());
	}
}
