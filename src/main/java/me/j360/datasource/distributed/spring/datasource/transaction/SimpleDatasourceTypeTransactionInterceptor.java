package me.j360.datasource.distributed.spring.datasource.transaction;

import me.j360.datasource.distributed.route.DatasourceRoute;
import me.j360.datasource.distributed.spring.config.schema.DatasourceSchema;
import me.j360.datasource.distributed.utils.StringUtil;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Created with j360-datasource -> me.j360.datasource.distributed.spring.datasource.transaction.
 * User: min_xu
 * Date: 2015/11/3
 * Time: 14:33
 * 说明：简单类型数据源（不分读写）
 */
public class SimpleDatasourceTypeTransactionInterceptor extends AbstractDatasourceTransactionInterceptor {

    private DatasourceSchema datasourceSchema;

    public DatasourceSchema getDatasourceSchema() {
        return datasourceSchema;
    }

    public void setDatasourceSchema(DatasourceSchema datasourceSchema) {
        this.datasourceSchema = datasourceSchema;
    }

    public SimpleDatasourceTypeTransactionInterceptor(){
        super();
    }

    @Override
    public void rewriteParam(MethodInvocation invocation) {
        if(datasourceSchema == null){
            throw new RuntimeException("datasourceSchema can not be null");
        }
        if(!StringUtil.isNullOrEmpty(invocation.getArguments())&&invocation.getArguments().length>0){
            Object params=invocation.getArguments()[0];
            params= DatasourceRoute.getInstance().doDataSourceRoute(datasourceSchema, params);
            invocation.getArguments()[0]=params;
        }
    }

}
