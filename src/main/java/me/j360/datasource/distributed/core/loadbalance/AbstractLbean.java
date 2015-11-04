package me.j360.datasource.distributed.core.loadbalance;

/**
 * Created with j360-datasource -> me.j360.datasource.distributed.core.loadbalance.
 * User: min_xu
 * Date: 2015/11/4
 * Time: 11:13
 * 说明：
 */
public abstract class  AbstractLbean {

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    private int weight;

}
