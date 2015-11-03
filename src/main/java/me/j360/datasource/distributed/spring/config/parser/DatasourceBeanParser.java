package me.j360.datasource.distributed.spring.config.parser;

import me.j360.datasource.distributed.spring.config.schema.DatasourceSchema;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author liubing1
 * 分布式数据源解析
 */
public class DatasourceBeanParser extends AbstractSimpleBeanDefinitionParser {

	public DatasourceBeanParser() {

	}

	@Override
	protected Class<DatasourceSchema> getBeanClass(Element element) {
		return DatasourceSchema.class;
	}

	@Override
	protected void doParse(Element element, ParserContext parserContext,
			BeanDefinitionBuilder builder) {
		// TODO Auto-generated method stub
		try {
			builder.addPropertyValue("map",
					parseMapElement(element, parserContext, builder));
			builder.addPropertyValue("type", element.getAttribute("type"));
			builder.addPropertyValue("weight",element.getAttribute("weight"));
		} catch (Exception e) {
			parserContext.getReaderContext().error(
					"class " + DatasourceSchema.class.getName()
							+ " can not be create", element, e);
		}
	}


	private Map parseMapElement(Element mapEle, ParserContext parserContext,
			BeanDefinitionBuilder builder) {

		List entryEles = DomUtils.getChildElementsByTagName(mapEle, "database");
		// 关键是以下这个ManagedMap类型，充当着一个map类型的beandefinition类型的说明对象
		ManagedMap map = new ManagedMap(entryEles.size());
		map.setMergeEnabled(true);
		map.setSource(parserContext.getReaderContext().extractSource(mapEle));

		for (Iterator it = entryEles.iterator(); it.hasNext();) {
			Element entryEle = (Element) it.next();

			String databaseName = entryEle.getAttribute("databaseName");

			map.put(databaseName,
					parserContext.getDelegate().parseCustomElement(entryEle,
							builder.getRawBeanDefinition()));

		}

		return map;
	}
}