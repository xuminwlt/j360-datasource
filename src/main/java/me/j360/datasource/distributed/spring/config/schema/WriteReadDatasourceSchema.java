/**
 * 
 */
package me.j360.datasource.distributed.spring.config.schema;

import me.j360.datasource.distributed.readwritestragy.DatasourceServer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with j360-datasource -> me.j360.datasource.distributed.spring.config.
 * User: min_xu
 * Date: 2015/11/2
 * Time: 10:43
 * 说明：读写混合数据源
 */
public class WriteReadDatasourceSchema implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4892479165465186610L;
	
	private List<DatasourceSchema> commonDatasourceSchemas =new ArrayList<DatasourceSchema>();
	
	public WriteReadDatasourceSchema(){
		
	}

	/**
	 * @return the commonDatasourceSchemas
	 */
	public List<DatasourceSchema> getCommonDatasourceSchemas() {
		return commonDatasourceSchemas;
	}

	/**
	 * @param commonDatasourceSchemas the commonDatasourceSchemas to set
	 */
	public void setCommonDatasourceSchemas(
			List<DatasourceSchema> commonDatasourceSchemas) {
		this.commonDatasourceSchemas = commonDatasourceSchemas;
	}
	
	public Map<String,List<DatasourceServer>> groupDataSourceByType(){
		Map<String,List<DatasourceServer>> maps=new HashMap<String, List<DatasourceServer>>();
		
		for(DatasourceSchema commonDatasourceSchema:commonDatasourceSchemas){
			if(maps.containsKey(commonDatasourceSchema.getType())){
				List<DatasourceServer> datasourceSchemas=maps.get(commonDatasourceSchema.getType());
				DatasourceServer commonDatasourceServer=new DatasourceServer();
				commonDatasourceServer.setCommonDatasourceSchema(commonDatasourceSchema);
				commonDatasourceServer.setWeight(Integer.parseInt(commonDatasourceSchema.getWeight()));
				commonDatasourceServer.setEffectiveWeight(Integer.parseInt(commonDatasourceSchema.getWeight()));
				commonDatasourceServer.setCurrentWeight(0);
				datasourceSchemas.add(commonDatasourceServer);
			}else{
				List<DatasourceServer> datasourceSchemas =new ArrayList<DatasourceServer>();
				DatasourceServer commonDatasourceServer=new DatasourceServer();
				commonDatasourceServer.setCommonDatasourceSchema(commonDatasourceSchema);
				commonDatasourceServer.setWeight(Integer.parseInt(commonDatasourceSchema.getWeight()));
				commonDatasourceServer.setEffectiveWeight(Integer.parseInt(commonDatasourceSchema.getWeight()));
				commonDatasourceServer.setCurrentWeight(0);
				datasourceSchemas.add(commonDatasourceServer);
				maps.put(commonDatasourceSchema.getType(), datasourceSchemas);
			}
		}
		return maps;
		
	}
}
