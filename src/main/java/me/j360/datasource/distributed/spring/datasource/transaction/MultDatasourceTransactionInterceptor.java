
package me.j360.datasource.distributed.spring.datasource.transaction;

import me.j360.datasource.distributed.readwritestragy.DatasourceRobin;
import me.j360.datasource.distributed.readwritestragy.DatasourceServer;
import me.j360.datasource.distributed.route.DatasourceRoute;
import me.j360.datasource.distributed.spring.config.schema.DatasourceSchema;
import me.j360.datasource.distributed.spring.config.schema.WriteReadDatasourceSchema;
import me.j360.datasource.distributed.utils.StringUtil;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.support.CallbackPreferringPlatformTransactionManager;
import org.springframework.transaction.support.TransactionCallback;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
@Deprecated
public class MultDatasourceTransactionInterceptor extends DatasourceTransactionAspectSupport implements MethodInterceptor, Serializable {
	
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	private WriteReadDatasourceSchema writeReadDataSourceSchema;
	
	private String queryMethoedStart;
	
	private String operateMethodStart;
	/**
	 * Create a new TransactionInterceptor.
	 * <p>Transaction manager and transaction attributes still need to be set.
	 * @see #setTransactionManager
	 * @see #setTransactionAttributes(Properties)
	 * @see #setTransactionAttributeSource(TransactionAttributeSource)
	 */
	public MultDatasourceTransactionInterceptor() {

	}

	/**
	 * Create a new TransactionInterceptor.
	 * @param ptm the transaction manager to perform the actual transaction management
	 * @param attributes the transaction attributes in properties format
	 * @see #setTransactionManager
	 * @see #setTransactionAttributes(Properties)
	 */
	public MultDatasourceTransactionInterceptor(PlatformTransactionManager ptm, Properties attributes) {
		setTransactionManager(ptm);
		setTransactionAttributes(attributes);
	}

	/**
	 * Create a new TransactionInterceptor.
	 * @param ptm the transaction manager to perform the actual transaction management
	 * @param tas the attribute source to be used to find transaction attributes
	 * @see #setTransactionManager
	 * @see #setTransactionAttributeSource(TransactionAttributeSource)
	 */
	public MultDatasourceTransactionInterceptor(PlatformTransactionManager ptm, TransactionAttributeSource tas) {
		setTransactionManager(ptm);
		setTransactionAttributeSource(tas);
	}

	//todo 拦截参数，设置切面线程的数据源
	public Object invoke(final MethodInvocation invocation) throws Throwable {
		
		Class<?> targetClass = (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);
		if(writeReadDataSourceSchema==null){
			throw new RuntimeException("commonDatasourceSchema can not be null");
		}
		if(queryMethoedStart==null){
			throw new RuntimeException("queryMethoedStart can not be null");
		}
		if(operateMethodStart==null){
			throw new RuntimeException("operateMethodStart can not be null");
		}
		if(!StringUtil.isNullOrEmpty(invocation.getArguments())&&invocation.getArguments().length>0){
			Object params=invocation.getArguments()[0];
			DatasourceSchema commonDatasourceSchema=null;
			
			if(invocation.getMethod().getName().startsWith(queryMethoedStart)){
				Map<String,List<DatasourceServer>> commonDatasourceServerMap=writeReadDataSourceSchema.groupDataSourceByType();
				List<DatasourceServer> commonDatasourceServers=commonDatasourceServerMap.get("read");
				commonDatasourceSchema= DatasourceRobin.GetBestCommonDatasourceSchema(commonDatasourceServers);
			}else if(invocation.getMethod().getName().startsWith(operateMethodStart)){
				Map<String,List<DatasourceServer>> commonDatasourceServerMap=writeReadDataSourceSchema.groupDataSourceByType();
				List<DatasourceServer> commonDatasourceServers=commonDatasourceServerMap.get("write");
				commonDatasourceSchema=DatasourceRobin.GetBestCommonDatasourceSchema(commonDatasourceServers);
			}
			
			params= DatasourceRoute.getInstance().doDataSourceRoute(commonDatasourceSchema, params);

			invocation.getArguments()[0]=params;
		}
		// If the transaction attribute is null, the method is non-transactional.
		final TransactionAttribute txAttr =
				getTransactionAttributeSource().getTransactionAttribute(invocation.getMethod(), targetClass);
		final PlatformTransactionManager tm = determineTransactionManager(txAttr);
		final String joinpointIdentification = methodIdentification(invocation.getMethod(), targetClass);

		if (txAttr == null || !(tm instanceof CallbackPreferringPlatformTransactionManager)) {
			// Standard transaction demarcation with getTransaction and commit/rollback calls.
			TransactionInfo txInfo = createTransactionIfNecessary(tm, txAttr, joinpointIdentification);
			Object retVal = null;
			try {
				// This is an around advice: Invoke the next interceptor in the chain.
				// This will normally result in a target object being invoked.
				retVal = invocation.proceed();
			}
			catch (Throwable ex) {
				// target invocation exception
				completeTransactionAfterThrowing(txInfo, ex);
				throw ex;
			}
			finally {
				cleanupTransactionInfo(txInfo);
			}
			commitTransactionAfterReturning(txInfo);
			return retVal;
		}

		else {
			// It's a CallbackPreferringPlatformTransactionManager: pass a TransactionCallback in.
			try {
				Object result = ((CallbackPreferringPlatformTransactionManager) tm).execute(txAttr,
						new TransactionCallback<Object>() {
							public Object doInTransaction(TransactionStatus status) {
								TransactionInfo txInfo = prepareTransactionInfo(tm, txAttr, joinpointIdentification, status);
								try {
									return invocation.proceed();
								}
								catch (Throwable ex) {
									if (txAttr.rollbackOn(ex)) {
										// A RuntimeException: will lead to a rollback.
										if (ex instanceof RuntimeException) {
											throw (RuntimeException) ex;
										}
										else {
											throw new ThrowableHolderException(ex);
										}
									}
									else {
										// A normal return value: will lead to a commit.
										return new ThrowableHolder(ex);
									}
								}
								finally {
									cleanupTransactionInfo(txInfo);
								}
							}
						});

				// Check result: It might indicate a Throwable to rethrow.
				if (result instanceof ThrowableHolder) {
					throw ((ThrowableHolder) result).getThrowable();
				}
				else {
					return result;
				}
			}
			catch (ThrowableHolderException ex) {
				throw ex.getCause();
			}
		}
	}


	//---------------------------------------------------------------------
	// Serialization support
	//---------------------------------------------------------------------

	private void writeObject(ObjectOutputStream oos) throws IOException {
		// Rely on default serialization, although this class itself doesn't carry state anyway...
		oos.defaultWriteObject();

		// Deserialize superclass fields.
		oos.writeObject(getTransactionManagerBeanName());
		oos.writeObject(getTransactionManager());
		oos.writeObject(getTransactionAttributeSource());
		oos.writeObject(getBeanFactory());
	}

	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		// Rely on default serialization, although this class itself doesn't carry state anyway...
		ois.defaultReadObject();

		// Serialize all relevant superclass fields.
		// Superclass can't implement Serializable because it also serves as base class
		// for AspectJ aspects (which are not allowed to implement Serializable)!
		setTransactionManagerBeanName((String) ois.readObject());
		setTransactionManager((PlatformTransactionManager) ois.readObject());
		setTransactionAttributeSource((TransactionAttributeSource) ois.readObject());
		setBeanFactory((BeanFactory) ois.readObject());
	}


	/**
	 * Internal holder class for a Throwable, used as a return value
	 * from a TransactionCallback (to be subsequently unwrapped again).
	 */
	private static class ThrowableHolder {

		private final Throwable throwable;

		public ThrowableHolder(Throwable throwable) {
			this.throwable = throwable;
		}

		public final Throwable getThrowable() {
			return this.throwable;
		}
	}


	/**
	 * Internal holder class for a Throwable, used as a RuntimeException to be
	 * thrown from a TransactionCallback (and subsequently unwrapped again).
	 */
	private static class ThrowableHolderException extends RuntimeException {

		public ThrowableHolderException(Throwable throwable) {
			super(throwable);
		}

		@Override
		public String toString() {
			return getCause().toString();
		}
	}

}
