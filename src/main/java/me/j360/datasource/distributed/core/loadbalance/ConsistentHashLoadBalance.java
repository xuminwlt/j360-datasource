package me.j360.datasource.distributed.core.loadbalance;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
/**
 * Created with j360-datasource -> me.j360.datasource.distributed.core.shard.support
 * User: min_xu
 * Date: 2015/10/31
 * Time: 9:55
 * 说明：一致性hash算法
 */

public class ConsistentHashLoadBalance extends AbstractLoadBalance {

    @Override
    protected <S> S doSelect(List<S> shards, String seed) {
        if(seed == null || seed.length() == 0){
            seed = "HASH-".concat(String.valueOf(ThreadLocalRandom.current().nextInt()));
        }
        MurmHash<S> selector = new MurmHash<S>(shards);
        return selector.getSelector(seed);
    }
}
