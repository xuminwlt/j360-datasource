
package me.j360.datasource.distributed.core.shard;

import me.j360.datasource.distributed.spring.config.schema.DatabaseSchema;

import java.util.List;

/**
 * Created with j360-datasource -> me.j360.datasource.distributed.core.shard
 * User: min_xu
 * Date: 2015/10/31
 * Time: 9:55
 * 说明：数据库名称哈希算法
 */
public class DatabaseShard extends MurmHashShard<DatabaseSchema> {
	public DatabaseShard(List<DatabaseSchema> shards) {
		super(shards);
	}
}
