package me.j360.datasource.distributed.core.loadbalance;
import java.util.List;

/**
 * Created with j360-datasource -> me.j360.datasource.distributed.core.shard.support
 * User: min_xu
 * Date: 2015/10/31
 * Time: 9:55
 * 说明：负载均衡抽象类
 */
public abstract class AbstractLoadBalance implements LoadBalance {

    @Override
    public <S> S select(List<S> shards, String seed) {
        if (shards == null || shards.size() == 0) {
            return null;
        }

        if (shards.size() == 1) {
            return shards.get(0);
        }

        return doSelect(shards, seed);
    }

    protected abstract <S> S doSelect(List<S> shards, String seed);

}
