package cn.com.bsfit.frms.portal.service;

import cn.com.bsfit.frms.portal.base.mapper.CountriesCodeMapper;
import cn.com.bsfit.frms.portal.base.mapper.CurrencyCodeMapper;
import cn.com.bsfit.frms.portal.base.mapper.RegierungsbezirkCodeMapper;
import cn.com.bsfit.frms.portal.base.pojo.*;
import cn.com.bsfit.frms.portal.base.util.ExtJSResponse;
import cn.com.bsfit.frms.portal.base.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 反洗钱基础代码 货币代码表,行政区划分代码表,国际地区代码表
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
@RestController
@RequestMapping("/amlBaseCode")
public class AmlBaseServerResource {

	@Autowired
	private CountriesCodeMapper countriesCodeMapper;
	@Autowired
	private CurrencyCodeMapper currencyCodeMapper;
	@Autowired
	private RegierungsbezirkCodeMapper regierungsbezirkCodeMapper;
	
	/**
	 * 获取国际地区代码
	 * 
	 * @param page
	 * @param start
	 * @param limit
	 * @param digitalCode 数字代码
	 * @param letterCode 字母代码
	 * @param forShort 简称
	 * @param fullName 全称
	 * @return
	 */
	@RequestMapping(value = "/countriesList", method = { RequestMethod.GET })
	public ExtJSResponse findCountriesList(final @RequestParam(value = "page", required = true, defaultValue = "1") String page,
			final @RequestParam(value = "start", required = true, defaultValue = "0") String start,
			final @RequestParam(value = "limit", required = true, defaultValue = "25") String limit,
			final @RequestParam(value = "digitalCode", required = false, defaultValue = "") String digitalCode,
			final @RequestParam(value = "letterCode", required = false, defaultValue = "") String letterCode,
			final @RequestParam(value = "forShort", required = false, defaultValue = "") String forShort,
			final @RequestParam(value = "fullName", required = false, defaultValue = "") String fullName) {
		// 设置查询条件
		CountriesCodeExample example = new CountriesCodeExample();
		CountriesCodeExample.Criteria criteria = example.createCriteria();
		if (digitalCode != null && !"".equals(digitalCode)) {
			criteria.andDigitalCodeLike("%" + digitalCode + "%");
		}
		if (letterCode != null && !"".equals(letterCode)) {
			criteria.andLetterCodeLike("%" + letterCode + "%");
		}
		if (forShort != null && !"".equals(forShort)) {
			criteria.andForShortLike("%" + forShort + "%");
		}
		if (fullName != null && !"".equals(fullName)) {
			criteria.andFullNameLike("%" + fullName + "%");
		}
		// 查询总数
		int total = countriesCodeMapper.countByExample(example);
		example.setPage(new Page(start, limit));
		List<CountriesCode> countriesCodeList = countriesCodeMapper.selectByExample(example);
		return ExtJSResponse.successRes4Find(countriesCodeList, total);
	}

	/**
	 * 获取货币代码
	 * 
	 * @param page
	 * @param start
	 * @param limit
	 * @param digitalCode
	 * @param letterCode
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/currencyList", method = { RequestMethod.GET })
	public ExtJSResponse findCurrencyList(final @RequestParam(value = "page", required = true, defaultValue = "1") String page,
			final @RequestParam(value = "start", required = true, defaultValue = "0") String start,
			final @RequestParam(value = "limit", required = true, defaultValue = "25") String limit,
			final @RequestParam(value = "digitalCode", required = false) String digitalCode,
			final @RequestParam(value = "letterCode", required = false) String letterCode,
			final @RequestParam(value = "name", required = false) String name) {
		// 设置查询条件
		CurrencyCodeExample example = new CurrencyCodeExample();
		CurrencyCodeExample.Criteria criteria = example.createCriteria();
		if (digitalCode != null && !"".equals(digitalCode)) {
			criteria.andDigitalCodeLike("%" + digitalCode + "%");
		}
		if (letterCode != null && !"".equals(letterCode)) {
			criteria.andLetterCodeLike("%" + letterCode + "%");
		}
		if (name != null && !"".equals(name)) {
			criteria.andNameLike("%" + name + "%");
		}
		// 查询总数
		int total = currencyCodeMapper.countByExample(example);
		example.setPage(new Page(start, limit));
		List<CurrencyCode> currencyCodeList = currencyCodeMapper.selectByExample(example);
		return ExtJSResponse.successRes4Find(currencyCodeList, total);
	}

	/**
	 * 获取行政区划分代码
	 * 
	 * @param page
	 * @param start
	 * @param limit
	 * @param code
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/regierungsbezirkList", method = { RequestMethod.GET })
	public ExtJSResponse findRegierungsbezirkList(final @RequestParam(value = "page", required = true, defaultValue = "1") String page,
			final @RequestParam(value = "start", required = true, defaultValue = "0") String start,
			final @RequestParam(value = "limit", required = true, defaultValue = "25") String limit,
			final @RequestParam(value = "code", required = false) String code,
			final @RequestParam(value = "name", required = false) String name) {
		// 设置查询条件
		RegierungsbezirkCodeExample example = new RegierungsbezirkCodeExample();
		RegierungsbezirkCodeExample.Criteria criteria = example.createCriteria();
		if (code != null && !"".equals(code)) {
			criteria.andCodeLike("%" + code + "%");
		}
		if (name != null && !"".equals(name)) {
			criteria.andNameLike("%" + name + "%");
		}
		// 查询总数
		int total = regierungsbezirkCodeMapper.countByExample(example);
		example.setPage(new Page(start, limit));
		List<RegierungsbezirkCode> regierungsbezirkCodeList = regierungsbezirkCodeMapper.selectByExample(example);
		return ExtJSResponse.successRes4Find(regierungsbezirkCodeList, total);
	}

	/**
	 * 根据地区名称获取行政区划分代码
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findRegierungsbezirkListByName", method = { RequestMethod.GET },produces = { MediaType.TEXT_PLAIN_VALUE })
	public @ResponseBody String findRegierungsbezirkListByName(final @RequestParam(value = "name", required = true) String name) throws Exception {
		RegierungsbezirkCodeExample example = new RegierungsbezirkCodeExample();
		RegierungsbezirkCodeExample.Criteria criteria = example.createCriteria();
		if (name != null && !"".equals(name)) {
			criteria.andNameEqualTo(name);
		} else {
			throw new Exception("地区名称为空!");
		}
		RegierungsbezirkCode regierungsbezirkCode = null;
		List<RegierungsbezirkCode> regierungsbezirkCodeList = regierungsbezirkCodeMapper.selectByExample(example);
		if (regierungsbezirkCodeList != null && regierungsbezirkCodeList.size() >= 1) {
			regierungsbezirkCode = regierungsbezirkCodeList.get(0);
		}
		if (regierungsbezirkCode != null) {
			return regierungsbezirkCode.getCode() == null ? null : regierungsbezirkCode.getCode();
		} else {
			throw new Exception("无法根据地区名称:" + name + "获取行政区划分代码");
		}
	}
}
