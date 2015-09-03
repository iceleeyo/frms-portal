package cn.com.bsfit.frms.portal.config;

import javax.sql.DataSource;

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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * DS数据库配置
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
@Configuration
@MapperScan(basePackages = { "cn.com.bsfit.frms.ds.mapper" }, sqlSessionFactoryRef = "dsSqlSessionFactory")
public class DSDatasourceConfig {
	
	private Logger logger = LoggerFactory.getLogger(DSDatasourceConfig.class);
	
	@Value("${ds.jdbc.type}")
	private String dsJdbcType;
	
	@Bean
	@ConfigurationProperties(prefix = "ds.jdbc")
	public DataSource dsDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "dsSqlSessionFactory")
	public SqlSessionFactory dsSqlSessionFactory(@Qualifier("dsDataSource") DataSource dsDataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dsDataSource);
		logger.info("dsJdbcType:" + dsJdbcType);
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		
		bean.setMapperLocations(resolver.getResources("classpath*:" + dsJdbcType + "/cn/com/bsfit/frms/ds/sqlmap/*Mapper.xml"));
		return bean.getObject();
	}
	
	@Bean
    public JdbcTemplate dsJdbcTemplate(@Qualifier("dsDataSource") DataSource dsDataSource) {
        return new JdbcTemplate(dsDataSource);
    }
	
	@Bean
    public PlatformTransactionManager dsTransactionManager(@Qualifier("dsDataSource") DataSource dsDataSource) {
		return new DataSourceTransactionManager(dsDataSource);
    }
	
}
