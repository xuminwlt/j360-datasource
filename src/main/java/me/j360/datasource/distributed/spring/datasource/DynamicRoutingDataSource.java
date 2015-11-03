package me.j360.datasource.distributed.spring.datasource;

/**
 * Created with j360-datasource -> me.j360.datasource.distributed.spring.datasource.
 * User: min_xu
 * Date: 2015/10/31
 * Time: 9:55
 * 说明：spring直接继承AbstractRoutingDataSource
 */
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicContextHolder.getDatabaseSchema().getDatasourceId();
        //System.out.println(o);
        //return o;
    }
}