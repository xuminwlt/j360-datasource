/**
 * 
 */
package me.j360.datasource.distributed.spring.config.parser;

import me.j360.datasource.distributed.spring.exception.SpringParseException;
import me.j360.datasource.distributed.spring.config.schema.DatabaseSchema;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import java.util.Iterator;
import java.util.List;

/**
 * @author liubing1
 * 数据源解析
 */
public class DatabaseSchemaBeanParser extends AbstractSimpleBeanDefinitionParser {
	/**
	 * 
	 */
	public DatabaseSchemaBeanParser() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * spring schema 标签解析
	 */
	@Override
	protected void doParse(Element element, ParserContext parserContext,
			BeanDefinitionBuilder builder) {
		// TODO Auto-generated method stub
		try{
			
			 builder.addPropertyValue("tableSchemas", parseMapElement(element,  
	                    parserContext, builder)); 
			 builder.addPropertyValue("databaseName", element.getAttribute("databaseName")); 
			 builder.addPropertyValue("datasourceId", element.getAttribute("datasourceId")); 
		}catch(Exception e){
			throw new SpringParseException("DataBaseSchemaBeanParser doParse fail",e);
		}
	}
	/**
	 * 多个tablename标签处理
	 * @param mapEle
	 * @param parserContext
	 * @param builder
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	private ManagedList parseMapElement(Element mapEle,
            ParserContext parserContext, BeanDefinitionBuilder builder){
		List<Element> entryEles = DomUtils.getChildElementsByTagName(mapEle, "tablename");
		ManagedList list=new ManagedList();
		list.setMergeEnabled(true);
		list.setSource(parserContext.getReaderContext().extractSource(mapEle));  
		
        for (Iterator it = entryEles.iterator(); it.hasNext();) {  
            Element entryEle = (Element) it.next();  
            list.add(parserContext.getDelegate().parseCustomElement(  
                    entryEle, builder.getRawBeanDefinition()));
  
        }
		return list;
		
	}
	
	@Override  
    protected Class<DatabaseSchema> getBeanClass(Element element) {
        return DatabaseSchema.class;
    } 
}
