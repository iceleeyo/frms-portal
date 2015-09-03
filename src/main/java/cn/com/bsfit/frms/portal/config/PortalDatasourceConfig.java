package cn.com.bsfit.frms.portal.config;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import cn.com.bsfit.frms.portal.util.MapperLocations;

/**
 * Portal数据库配置
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
@Configuration
@MapperScan(basePackages = { "cn.com.bsfit.frms.portal.base.mapper" }, sqlSessionFactoryRef = "portalSqlSessionFactory")
public class PortalDatasourceConfig {

	private Logger logger = LoggerFactory.getLogger(PortalDatasourceConfig.class);

	@Value("${portal.jdbc.type}")
	private String portalJdbcType;

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "portal.jdbc")
	public DataSource portalDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "portalSqlSessionFactory")
	public SqlSessionFactory portalSqlSessionFactory(@Qualifier("portalDataSource") DataSource portalDataSource, List<MapperLocations> mapperLocationsList) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(portalDataSource);
		logger.info("portalJdbcType:" + portalJdbcType);
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		
		Resource[] resources = {};
        for (MapperLocations mapperLocations : mapperLocationsList) {
            String[] locations = mapperLocations.getLocations();
            if (ArrayUtils.isNotEmpty(locations)) {
                for (String location : locations) {
                    resources = ArrayUtils.addAll(resources, resolver.getResources(location));
                }
            }
        }
        bean.setMapperLocations(resources);
		return bean.getObject();
	}

	@Bean
	public MapperLocations portalLocations(@Value("${portal.jdbc.type}") String jdbcType) {
		return new MapperLocations("classpath*:" + jdbcType + "/cn/com/bsfit/frms/portal/base/sqlmap/*Mapper.xml");
	}
	
	@Bean
	public JdbcTemplate portalJdbcTemplate(@Qualifier("portalDataSource") DataSource portalDataSource) {
		return new JdbcTemplate(portalDataSource);
	}

	@Bean
	public PlatformTransactionManager portalTransactionManager(@Qualifier("portalDataSource") DataSource portalDataSource) {
		return new DataSourceTransactionManager(portalDataSource);
	}
	
}
