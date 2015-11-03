package me.j360.datasource.distributed.readwritestragy;

import me.j360.datasource.distributed.spring.config.schema.DatasourceSchema;

import java.io.Serializable;

/**
 * Created with j360-datasource -> me.j360.datasource.distributed.readwritestragy.
 * User: min_xu
 * Date: 2015/10/31
 * Time: 9:55
 * 说明：数据源，记录每个数据源的相关参数
 */
public class DatasourceServer implements Serializable {
	private static final long serialVersionUID = -7040426931464129112L;

	private DatasourceSchema commonDatasourceSchema;
	
	public int weight;
    
	public int effectiveWeight;
    
	public int currentWeight;
	
	private boolean down=false;
	/**
	 * @return the commonDatasourceSchema
	 */
	public DatasourceSchema getCommonDatasourceSchema() {
		return commonDatasourceSchema;
	}

	/**
	 * @param commonDatasourceSchema the commonDatasourceSchema to set
	 */
	public void setCommonDatasourceSchema(
			DatasourceSchema commonDatasourceSchema) {
		this.commonDatasourceSchema = commonDatasourceSchema;
	}

	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
		if(weight==0){
			setDown(true);
		}else{
			setDown(false);
		}
		
	}

	/**
	 * @return the effectiveWeight
	 */
	public int getEffectiveWeight() {
		return effectiveWeight;
	}

	/**
	 * @param effectiveWeight the effectiveWeight to set
	 */
	public void setEffectiveWeight(int effectiveWeight) {
		this.effectiveWeight = effectiveWeight;
	}

	/**
	 * @return the currentWeight
	 */
	public int getCurrentWeight() {
		return currentWeight;
	}

	/**
	 * @param currentWeight the currentWeight to set
	 */
	public void setCurrentWeight(int currentWeight) {
		this.currentWeight = currentWeight;
	}

	public DatasourceServer() {

	}

	/**
	 * @return the down
	 */
	public boolean isDown() {
		return down;
	}

	/**
	 * @param down the down to set
	 */
	public void setDown(boolean down) {
		this.down = down;
	}
    
	
}
