package me.j360.datasource.distributed.core.loadbalance;

import me.j360.datasource.distributed.readwritestragy.DatasourceServer;
import me.j360.datasource.distributed.utils.AtomicPositiveInteger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * Created with j360-datasource -> me.j360.datasource.distributed.core.shard.support
 * User: min_xu
 * Date: 2015/10/31
 * Time: 9:55
 * 说明：rr算法 Round robin load balance.
 */

public class RoundRobinLoadBalance extends AbstractLoadBalance {

    public static final String NAME = "roundrobin"; 
    
    private final ConcurrentMap<String, AtomicPositiveInteger> sequences = new ConcurrentHashMap<String, AtomicPositiveInteger>();

    private final ConcurrentMap<String, AtomicPositiveInteger> weightSequences = new ConcurrentHashMap<String, AtomicPositiveInteger>();


    @Override
    protected <S> S doSelect(List<S> shards, String seed) {
        return null;
    }

    public DatasourceServer select2(List<DatasourceServer> shards) {
        if (shards == null || shards.size() == 0) {
            return null;
        }
        if (shards.size() == 1) {
            return shards.get(0);
        }
        return doSelect2(shards);
    }

    protected int getWeight(DatasourceServer shard) {
        int weight = shard.getWeight();
        if (weight > 0) {
            //long timestamp = invoker.getUrl().getParameter(Constants.TIMESTAMP_KEY, 0L);
            //if (timestamp > 0L) {
                int uptime = (int) (System.currentTimeMillis()); //- timestamp);
                int warmup = uptime;//invoker.getUrl().getParameter(Constants.WARMUP_KEY, Constants.DEFAULT_WARMUP);
                if (uptime > 0) { // && uptime < warmup
                    weight = calculateWarmupWeight(uptime, warmup, weight);
                }
            //}
        }
        return weight;
    }


    protected DatasourceServer doSelect2(List<DatasourceServer> shards) {
        int length = shards.size();
        int maxWeight = 0; // 最大权重
        int minWeight = Integer.MAX_VALUE; // 最小权重

        for (int i = 0; i < length; i++) {
            int weight = getWeight(shards.get(i));
            maxWeight = Math.max(maxWeight, weight); // 累计最大权重
            minWeight = Math.min(minWeight, weight); // 累计最小权重
        }

        String seed = "DatasourceServer-key";
        if (maxWeight > 0 && minWeight < maxWeight) { // 权重不一样
            AtomicPositiveInteger weightSequence = weightSequences.get(seed);
            if (weightSequence == null) {
                weightSequences.putIfAbsent(seed, new AtomicPositiveInteger());
                weightSequence = weightSequences.get(seed);
            }
            int currentWeight = weightSequence.getAndIncrement() % maxWeight;
            List<DatasourceServer> weightInvokers = new ArrayList<DatasourceServer>();
            for (DatasourceServer server : shards) { // 筛选权重大于当前权重基数的Invoker
                if (getWeight(server) > currentWeight) {
                    weightInvokers.add(server);
                }
            }
            int weightLength = weightInvokers.size();
            if (weightLength == 1) {
                return weightInvokers.get(0);
            } else if (weightLength > 1) {
                shards = weightInvokers;
                length = shards.size();
            }
        }
        AtomicPositiveInteger sequence = sequences.get(seed);
        if (sequence == null) {
            sequences.putIfAbsent(seed, new AtomicPositiveInteger());
            sequence = sequences.get(seed);
        }
        // 取模轮循
        return shards.get(sequence.getAndIncrement() % length);
    }

    public static final String  WEIGHT_KEY                         = "weight";
    public static final int     DEFAULT_WEIGHT                     = 1;


    static int calculateWarmupWeight(int uptime, int warmup, int weight) {
        int ww = (int) ( (float) uptime / ( (float) warmup / (float) weight ) );
        return ww < 1 ? 1 : (ww > weight ? weight : ww);
    }

}