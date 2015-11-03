package me.j360.datasource.distributed.utils;
import me.j360.datasource.distributed.exception.DatasourceException;
import org.springframework.beans.BeanUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with j360-datasource -> me.j360.datasource.distributed.utils;
 * User: min_xu
 * Date: 2015/10/31
 * Time: 9:55
 * 说明：对Object的属性的读取写入工具，参考Spring BeanUtil
 */
public class BeanPropertyAccessUtil {

    /**
     * 通过反射设置一个对象的某个属性值
     *
     * @param propertyName,name of the property
     * @param propertyValue,new value of the property
     * @param object,the        object which contains the property
     */
    public static void setPropertyValue(final String propertyName, final Object propertyValue, final Object object) throws InvocationTargetException, IllegalAccessException {
        if (StringUtil.isNullOrEmpty(propertyName) || object == null) {
            throw new IllegalArgumentException("'propertyName' cann't be null or empty,'object' cann't be null!");
        }
        PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(object.getClass(), propertyName);
        if (descriptor != null && descriptor.getWriteMethod() != null) {
            descriptor.getWriteMethod().invoke(object, propertyValue);
        }
    }

    /**
     * 通过反射获取一个对象的某个属性值
     *
     * @param propertyName,name of the property
     * @param object,the        object which contains the property
     * @return the value object of the property
     */
    public static Object getPropertyValue(final String propertyName, final Object object) throws InvocationTargetException, IllegalAccessException {
        if (StringUtil.isNullOrEmpty(propertyName) || object == null) {
            throw new IllegalArgumentException("'propertyName' cann't be null or empty,'object' cann't be null!");
        }
        PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(object.getClass(), propertyName);
        if (descriptor != null && descriptor.getReadMethod() != null) {
            return descriptor.getReadMethod().invoke(object);
        }
        return null;
    }
    
    /**
	 * 重写实体参数类表名
	 * @param parameterObj
	 * @param tableNameField
	 * @param physicalTableName
	 * @throws DatasourceException
	 */
	public static void rewriteTableName(final Object parameterObj,final String tableNameField, final String physicalTableName) throws DatasourceException {
        try {
            if (parameterObj instanceof Map) {
                ((Map) parameterObj).put(tableNameField, physicalTableName);
            } else if (parameterObj != null) {
                setPropertyValue(tableNameField, physicalTableName, parameterObj);
            }
        } catch (Exception e) {
            throw new DatasourceException("rewrite table name error!", e);
        }
    }
	/**
	 * java bean 转为map
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> transBean2Map(Object obj) {

        if(obj == null){
            return null;
        }        
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value);
                }

            }
        } catch (Exception e) {
            throw new DatasourceException("transBean2Map Error!", e);
        }

        return map;

    }
}
