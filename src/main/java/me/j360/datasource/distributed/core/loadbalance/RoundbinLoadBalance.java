package me.j360.datasource.distributed.core.loadbalance;

import java.util.List;


/**
 * Created with j360-datasource -> me.j360.datasource.distributed.core.shard.support
 * User: min_xu
 * Date: 2015/10/31
 * Time: 9:55
 * 说明：权重负载均衡轮训算法
 */
public class RoundbinLoadBalance extends AbstractLoadBalance{
    @Override
    protected <S> S doSelect(List<S> shards, String seed) {
        // TODO
        return null;
    }
}
