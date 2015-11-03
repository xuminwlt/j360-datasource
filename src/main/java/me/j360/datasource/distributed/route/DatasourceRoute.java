package me.j360.datasource.distributed.route;


import me.j360.datasource.distributed.J360Datasource;
import me.j360.datasource.distributed.core.shard.support.TableBean;
import me.j360.datasource.distributed.enums.RouteContextEnum;
import me.j360.datasource.distributed.exception.DatasourceException;
import me.j360.datasource.distributed.route.support.TableNameBean;
import me.j360.datasource.distributed.spring.config.schema.DatabaseSchema;
import me.j360.datasource.distributed.spring.config.schema.DatasourceSchema;
import me.j360.datasource.distributed.spring.datasource.DynamicContextHolder;
import me.j360.datasource.distributed.utils.BeanPropertyAccessUtil;
import me.j360.datasource.distributed.utils.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with j360-datasource -> me.j360.datasource.distributed.spring.datasource.
 * User: min_xu
 * Date: 2015/10/31
 * Time: 9:55
 * 说明：分布式路由，改变参数tableBean参数结构，重写Bean对应的表名称
 */
public class DatasourceRoute {
	private DatasourceRoute() {

	}

	private static class SingletonHolder {
		static final DatasourceRoute instance = new DatasourceRoute();
	}

	public static DatasourceRoute getInstance() {
		return SingletonHolder.instance;
	}
	/**
	 * 根据参数路由
	 * @param commonDatasourceSchema
	 * @param parameterObj
	 * @return
	 */
	public Object doDataSourceRoute(
			DatasourceSchema commonDatasourceSchema, Object parameterObj) {
		// TODO Auto-generated method stub
		 
       try {
        		 Map parametersMap =new HashMap();
        		 if (parameterObj instanceof Map) {// map 
        			  parametersMap = (Map) parameterObj;
        		 }else{//java bean
        			 parametersMap = BeanPropertyAccessUtil.transBean2Map(parameterObj);
        		 }
        		TableNameBean tableNameBean=getTableFiedlName(commonDatasourceSchema, parametersMap);
        		TableBean tableBean=getTableBean(commonDatasourceSchema, tableNameBean);
        		BeanPropertyAccessUtil.rewriteTableName(parameterObj, RouteContextEnum.tablename.toString(), tableBean.getPrefixname());
				return parameterObj;
		} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new DatasourceException("参数必须是map 或者javabean",e);
		}  
         
	}
	/**
	 * 获取分库分表的字段名
	 * @param commonDatasourceSchema
	 * @param parametersMap
	 * @return
	 */
	private TableNameBean getTableFiedlName(DatasourceSchema commonDatasourceSchema,Map parametersMap){
		TableNameBean tableNameBean=new TableNameBean();
		 for(String tablefield:commonDatasourceSchema.getTablenameFields()){
			 if(parametersMap.containsKey(tablefield)){
				  tableNameBean.setTablefieldvalue(String.valueOf(parametersMap.get(tablefield)));
				  tableNameBean.setTablefield(tablefield);
				  break;
			 }
		 }
		 if(StringUtil.isNullOrEmpty(tableNameBean.getTablefield())){
			 throw new DatasourceException("参数必须含有分库分表字段");
		 }
		 return tableNameBean;
	}
	/**
	 * 获取表名
	 * @param commonDatasourceSchema
	 * @param tableNameBean
	 * @return
	 */
	private TableBean getTableBean(DatasourceSchema commonDatasourceSchema,TableNameBean tableNameBean){
		DatabaseSchema dataBaseSchema= J360Datasource.getInstance().getShardDataSoure(commonDatasourceSchema, tableNameBean);
		DynamicContextHolder.setDatabaseSchema(dataBaseSchema);//设置数据源

		TableBean tableBean= J360Datasource.getInstance().getShardTableName(commonDatasourceSchema, tableNameBean, dataBaseSchema.getDatabaseName());
		return tableBean; 
	}
}
