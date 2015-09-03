package cn.com.bsfit.frms.portal.util;

/**
 * 创建本类的对象并注册到spring容器中，即可在子模块中使用portal这一数据源可以
 * 
 * @author ccj
 * 
 * @since 1.2.0
 *
 */
public class MapperLocations {
	
	private String[] locations;

	public MapperLocations(String... locations) {
		this.locations = locations;
	}

	public String[] getLocations() {
		return locations;
	}
}
