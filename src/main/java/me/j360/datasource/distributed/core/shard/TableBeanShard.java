package me.j360.datasource.distributed.core.shard;

import me.j360.datasource.distributed.core.shard.support.TableBean;

import java.util.List;
/**
 * Created with j360-datasource -> me.j360.datasource.distributed.core.shard
 * User: min_xu
 * Date: 2015/10/31
 * Time: 9:55
 * 说明：Table表名称哈希算法
 */
public  class TableBeanShard extends MurmHashShard<TableBean> {
	public TableBeanShard(List<TableBean> shards) {
		super(shards);
	}

}
