/**
 * 
 */
package me.j360.datasource.distributed.spring.config.parser;

import me.j360.datasource.distributed.spring.config.schema.TableSchema;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * @author liubing1
 * tablename 解析
 */
public class TableSchemaBeanParser implements BeanDefinitionParser {

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.xml.BeanDefinitionParser#parse(org.w3c.dom.Element, org.springframework.beans.factory.xml.ParserContext)
	 */
	@Override
	public BeanDefinition parse(Element element, ParserContext context) {
		// TODO Auto-generated method stub
		RootBeanDefinition def = new RootBeanDefinition();
		// 设置Bean Class
        def.setBeanClass(TableSchema.class);
        // 注册ID属性
        String id = element.getAttribute("id");
        BeanDefinitionHolder idHolder = new BeanDefinitionHolder(def, id);
        BeanDefinitionReaderUtils.registerBeanDefinition(idHolder, context.getRegistry());
        
        //注册属性
        String tablename= element.getAttribute("tablename");
        String prefixname=element.getAttribute("prefixname");
        String count=element.getAttribute("count");
        String tablefield=element.getAttribute("tablefield");
        
        BeanDefinitionHolder tablenameHolder = new BeanDefinitionHolder(def, tablename);
        BeanDefinitionHolder prefixnameNameHolder = new BeanDefinitionHolder(def, prefixname);
        BeanDefinitionHolder countHolder = new BeanDefinitionHolder(def, count);
        BeanDefinitionHolder tablefieldHolder = new BeanDefinitionHolder(def, tablefield);
        
        BeanDefinitionReaderUtils.registerBeanDefinition(tablenameHolder, context.getRegistry());
        BeanDefinitionReaderUtils.registerBeanDefinition(prefixnameNameHolder, context.getRegistry());
        BeanDefinitionReaderUtils.registerBeanDefinition(countHolder, context.getRegistry());
        BeanDefinitionReaderUtils.registerBeanDefinition(tablefieldHolder, context.getRegistry());
        
        if(StringUtils.hasText(tablename)){
        	def.getPropertyValues().addPropertyValue("tablename", tablename);
        }
        if(StringUtils.hasText(prefixname)){
        	def.getPropertyValues().addPropertyValue("prefixname", prefixname);
        }
        if(StringUtils.hasText(prefixname)){
        	def.getPropertyValues().addPropertyValue("count", Integer.parseInt(count));
        }
        
        if(StringUtils.hasText(tablefield)){
        	def.getPropertyValues().addPropertyValue("tablefield", tablefield);
        }
        return def;
	}

}
