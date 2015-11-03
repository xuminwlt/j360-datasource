package me.j360.datasource.distributed.core.shard;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created with j360-datasource -> me.j360.datasource.distributed.core.shard
 * User: min_xu
 * Date: 2015/10/31
 * Time: 9:55
 * 说明：
 * murmhash算法，来确定使用哪个节点的database或者tablename，读库+表名，此处由id计算得出真实节点
 * Redis，Memcached，Cassandra，HBase，Lucene都用它
 * import com.google.guava
 * @see * https://github.com/google/guava/blob/master/guava/src/com/google/common/hash/Murmur3_32HashFunction.java
 */
public class MurmHashShard<S> {
	
	private TreeMap<Long, S> nodes; // 虚拟节点

	private List<S> shards; // 真实机器节点

	private final int NODE_NUM = 64; // 每个机器节点关联的虚拟节点个数

	public MurmHashShard(List<S> shards) {
		super();
		this.shards = shards;
		init();
	}

	private void init() { // 初始化一致性hash环

		nodes = new TreeMap<Long, S>();
		for (int i = 0; i != shards.size(); ++i) { // 每个真实机器节点都需要关联虚拟节点
			final S shardInfo = shards.get(i);
			for (int n = 0; n < NODE_NUM; n++)
				// 一个真实机器节点关联NODE_NUM个虚拟节点
				nodes.put(hash("SHARD-" + i + "-NODE-" + n), shardInfo);
		}

	}

	public S getShardInfo(String key) {

		SortedMap<Long, S> tail = nodes.tailMap(hash(key)); // 沿环的顺时针找到一个虚拟节点
		if (tail.size() == 0) {
			return nodes.get(nodes.firstKey());
		}
		return tail.get(tail.firstKey()); // 返回该虚拟节点对应的真实机器节点的信息
	}

	/**
	 * 
	 * MurMurHash算法，是非加密HASH算法，性能很高，
	 * 
	 * 比传统的CRC32,MD5，SHA-1（这两个算法都是加密HASH算法，复杂度本身就很高，带来的性能上的损害也不可避免）
	 * 
	 * 等HASH算法要快很多，而且据说这个算法的碰撞率很低.
	 */

	private Long hash(String key) {

		ByteBuffer buf = ByteBuffer.wrap(key.getBytes());
		int seed = 0x1234ABCD;
		ByteOrder byteOrder = buf.order();
		buf.order(ByteOrder.LITTLE_ENDIAN);
		long m = 0xc6a4a7935bd1e995L;
		int r = 47;
		long h = seed ^ (buf.remaining() * m);
		long k;

		while (buf.remaining() >= 8) {
			k = buf.getLong();
			k *= m;
			k ^= k >>> r;
			k *= m;
			h ^= k;
			h *= m;
		}
		if (buf.remaining() > 0) {
			ByteBuffer finish = ByteBuffer.allocate(8).order(
					ByteOrder.LITTLE_ENDIAN);
			finish.put(buf).rewind();
			h ^= finish.getLong();
			h *= m;
		}
		h ^= h >>> r;
		h *= m;
		h ^= h >>> r;
		buf.order(byteOrder);
		return h;
	}
}

