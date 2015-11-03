package me.j360.datasource.distributed.spring.config;

import me.j360.datasource.distributed.spring.config.parser.DatabaseSchemaBeanParser;
import me.j360.datasource.distributed.spring.config.parser.DatasourceBeanParser;
import me.j360.datasource.distributed.spring.config.parser.TableSchemaBeanParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created with j360-datasource -> me.j360.datasource.distributed.spring.config.
 * User: min_xu
 * Date: 2015/11/2
 * Time: 10:43
 * 说明：
 */
public class J360DatasourceNamespaceHandler extends NamespaceHandlerSupport {

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.xml.NamespaceHandler#init()
     */
    @Override
    public void init() {
        registerBeanDefinitionParser("tablename", new TableSchemaBeanParser());
        registerBeanDefinitionParser("database", new DatabaseSchemaBeanParser());
        registerBeanDefinitionParser("datasource", new DatasourceBeanParser());
    }

}