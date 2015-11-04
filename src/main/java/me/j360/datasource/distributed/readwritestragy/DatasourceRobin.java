/**
 * 
 */
package me.j360.datasource.distributed.readwritestragy;

import me.j360.datasource.distributed.core.loadbalance.RoundRobinLoadBalance;
import me.j360.datasource.distributed.spring.config.schema.DatasourceSchema;

import java.util.List;

/**
 * Created with j360-datasource -> me.j360.datasource.distributed.readwritestragy.
 * User: min_xu
 * Date: 2015/10/31
 * Time: 9:55
 * 说明：数据源权重轮训算法RR，获取本次最佳数据源
 */
public class DatasourceRobin {
    public static DatasourceSchema GetBestDatasourceSchema(List<DatasourceServer> commonDatasourceSchemas) {
        RoundRobinLoadBalance rr = new RoundRobinLoadBalance();
        return  rr.select2(commonDatasourceSchemas).getCommonDatasourceSchema();
    }
	public static DatasourceSchema GetBestCommonDatasourceSchema(List<DatasourceServer> commonDatasourceSchemas) {
		DatasourceServer server = null;
		DatasourceServer best = null;
        int total = 0;
        for(int i=0,len=commonDatasourceSchemas.size();i<len;i++){
            //当前服务器对象
        	server = commonDatasourceSchemas.get(i);
        	 //当前服务器已宕机，排除
            if(server.isDown()){
                continue;
            }
            server.currentWeight += server.effectiveWeight;
            total += server.effectiveWeight;
            
            if(server.effectiveWeight < server.weight){
                server.effectiveWeight++;
            }
            
            if(best == null || server.currentWeight>best.currentWeight){
                best = server;
            }
            
        }

        if (best == null) {
            return null;
        }
        best.currentWeight -= total;
        return best.getCommonDatasourceSchema();
    }

}	
