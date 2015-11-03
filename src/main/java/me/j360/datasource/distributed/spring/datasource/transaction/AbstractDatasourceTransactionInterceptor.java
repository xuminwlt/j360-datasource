package me.j360.datasource.distributed.spring.datasource.transaction;

import me.j360.datasource.distributed.spring.config.schema.DatasourceSchema;
import me.j360.datasource.distributed.spring.datasource.DynamicContextHolder;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * Created with j360-datasource -> me.j360.datasource.distributed.spring.datasource.transaction.
 * User: min_xu
 * Date: 2015/11/3
 * Time: 14:19
 * 说明：抽象事务拦截器，清理事务信息时将数据源信息一并清除
 */
public abstract class AbstractDatasourceTransactionInterceptor extends TransactionInterceptor {

    public AbstractDatasourceTransactionInterceptor(){
        super();
    }

    @Override
    protected void cleanupTransactionInfo(TransactionInfo txInfo) {
        if (txInfo != null) {
            DynamicContextHolder.clearDatabaseSchema();
            super.cleanupTransactionInfo(txInfo);
        }
    }

    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        // Work out the target class: may be {@code null}.
        // The TransactionAttributeSource should be passed the target class
        // as well as the method, which may be from an interface.
        Class<?> targetClass = (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);
        rewriteParam(invocation);
        // Adapt to TransactionAspectSupport's invokeWithinTransaction...
        return invokeWithinTransaction(invocation.getMethod(), targetClass, new InvocationCallback() {
            @Override
            public Object proceedWithInvocation() throws Throwable {
                return invocation.proceed();
            }
        });
    }

    public abstract void rewriteParam(final MethodInvocation invocation);
}
