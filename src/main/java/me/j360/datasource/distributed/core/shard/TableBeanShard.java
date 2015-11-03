package me.j360.datasource.distributed.core.shard;

import me.j360.datasource.distributed.core.loadbalance.ConsistentHashLoadBalance;
import me.j360.datasource.distributed.core.loadbalance.MurmHashShard;
import me.j360.datasource.distributed.core.shard.support.TableBean;

import java.util.List;
/**
 * Created with j360-datasource -> me.j360.datasource.distributed.core.shard
 * User: min_xu
 * Date: 2015/10/31
 * Time: 9:55
 * 说明：Table表名称哈希算法
 */
@Deprecated
public  class TableBeanShard extends ConsistentHashLoadBalance {
	public TableBeanShard() {

	}
}
