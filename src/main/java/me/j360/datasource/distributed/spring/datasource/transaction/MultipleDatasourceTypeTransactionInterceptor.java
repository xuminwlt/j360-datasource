package me.j360.datasource.distributed.spring.datasource.transaction;

import me.j360.datasource.distributed.readwritestragy.DatasourceRobin;
import me.j360.datasource.distributed.readwritestragy.DatasourceServer;
import me.j360.datasource.distributed.route.DatasourceRoute;
import me.j360.datasource.distributed.spring.config.schema.DatasourceSchema;
import me.j360.datasource.distributed.spring.config.schema.WriteReadDatasourceSchema;
import me.j360.datasource.distributed.utils.StringUtil;
import org.aopalliance.intercept.MethodInvocation;

import java.util.List;
import java.util.Map;

/**
 * Created with j360-datasource -> me.j360.datasource.distributed.spring.datasource.transaction.
 * User: min_xu
 * Date: 2015/11/3
 * Time: 14:50
 * 说明：多类型数据源（读写分离用）
 */
public class MultipleDatasourceTypeTransactionInterceptor extends AbstractDatasourceTransactionInterceptor {
    private WriteReadDatasourceSchema writeReadDataSourceSchema;

    private String queryMethoedStart;

    private String operateMethodStart;

    public MultipleDatasourceTypeTransactionInterceptor(){
        super();
    }

    @Override
    public void rewriteParam(MethodInvocation invocation) {
        if(writeReadDataSourceSchema==null){
            throw new RuntimeException("writeReadDataSourceSchema can not be null");
        }
        if(queryMethoedStart==null){
            throw new RuntimeException("queryMethoedStart can not be null");
        }
        if(operateMethodStart==null){
            throw new RuntimeException("operateMethodStart can not be null");
        }
        if(!StringUtil.isNullOrEmpty(invocation.getArguments())&&invocation.getArguments().length>0){
            Object params=invocation.getArguments()[0];
            DatasourceSchema commonDatasourceSchema = null;
            if(invocation.getMethod().getName().startsWith(queryMethoedStart)){
                Map<String,List<DatasourceServer>> commonDatasourceServerMap = writeReadDataSourceSchema.groupDataSourceByType();
                List<DatasourceServer> commonDatasourceServers=commonDatasourceServerMap.get("read");
                commonDatasourceSchema= DatasourceRobin.GetBestCommonDatasourceSchema(commonDatasourceServers);
            }else if(invocation.getMethod().getName().startsWith(operateMethodStart)){
                Map<String,List<DatasourceServer>> commonDatasourceServerMap = writeReadDataSourceSchema.groupDataSourceByType();
                List<DatasourceServer> commonDatasourceServers=commonDatasourceServerMap.get("write");
                commonDatasourceSchema=DatasourceRobin.GetBestCommonDatasourceSchema(commonDatasourceServers);
            }
            params= DatasourceRoute.getInstance().doDataSourceRoute(commonDatasourceSchema, params);
            invocation.getArguments()[0] = params;
        }
    }


    public WriteReadDatasourceSchema getWriteReadDataSourceSchema() {
        return writeReadDataSourceSchema;
    }

    public void setWriteReadDataSourceSchema(WriteReadDatasourceSchema writeReadDataSourceSchema) {
        this.writeReadDataSourceSchema = writeReadDataSourceSchema;
    }

    public String getQueryMethoedStart() {
        return queryMethoedStart;
    }

    public void setQueryMethoedStart(String queryMethoedStart) {
        this.queryMethoedStart = queryMethoedStart;
    }

    public String getOperateMethodStart() {
        return operateMethodStart;
    }

    public void setOperateMethodStart(String operateMethodStart) {
        this.operateMethodStart = operateMethodStart;
    }

}