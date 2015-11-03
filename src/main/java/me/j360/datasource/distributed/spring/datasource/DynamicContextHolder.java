package me.j360.datasource.distributed.spring.datasource;


import me.j360.datasource.distributed.spring.config.schema.DatabaseSchema;


/**
 * Created with j360-datasource -> me.j360.datasource.distributed.spring.datasource.
 * User: min_xu
 * Date: 2015/10/31
 * Time: 9:55
 * 说明：用户自定义datasource数据源上下文 DatabaseSchema
 */
public class DynamicContextHolder {
	
	private static final ThreadLocal<DatabaseSchema> contextHolder = new ThreadLocal<DatabaseSchema>();

	/**
	 * 设置数据源
	 * @param dataBaseSchema
	 */
	public static void setDatabaseSchema(DatabaseSchema dataBaseSchema) {
		contextHolder.set(dataBaseSchema);
	}

	/**
	 * 获取数据源
	 * @return DatabaseSchema
	 */
	public static DatabaseSchema getDatabaseSchema() {
		return (DatabaseSchema) contextHolder.get();
	}

	/**
	 * 删除上下文中的设置
	 */
	public static void clearDatabaseSchema() {
		if(contextHolder.get()!=null){
			contextHolder.remove();
		}
		
	}
}
