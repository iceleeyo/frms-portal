package cn.com.bsfit.frms.portal.bo.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.com.bsfit.frms.portal.base.pojo.Users;
import cn.com.bsfit.frms.portal.base.util.SysContent;
import cn.com.bsfit.frms.portal.bo.AuthBO;

@Service
public class AuthBOImpl implements AuthBO {

	private AuthBOImpl() {
		
	}
	
	@Override
	public Boolean permitAll() {
		return true;
	}

	@Override
	public Boolean denyAll() {
		return false;
	}

	@Override
	public Boolean hasAuthority(String resourcesCode) {
		List<String> resourcesCodeList = getAuthorityList();
		return resourcesCodeList.contains(resourcesCode);
	}

	@Override
	public Boolean hasAllAuthority(String... resourcesCodes) {
		List<String> resourcesCodeList = getAuthorityList();
		int arrLen = resourcesCodes.length;
		int flag = 0;
		for (String resourcesCode : resourcesCodes) {
			if (resourcesCodeList.contains(resourcesCode)) {
				flag++;
			}
		}
		if (flag == arrLen) {
			return true;
		}
		return false;
	}

	@Override
	public Boolean hasAnyAuthority(String... resourcesCodes) {
		List<String> resourcesCodeList = getAuthorityList();
		for (String resourcesCode : resourcesCodes) {
			if (resourcesCodeList.contains(resourcesCode)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Boolean hasAllOrAnyAuthority(String resourcesCode) {
		List<String> resourcesCodeList = getAuthorityList();
		boolean flag = false;
		String[] strs = resourcesCode.split("\\|\\|");
		for (String str : strs) {
			String[] arr = str.split("&&");
			int length = arr.length;
			int num = 0;
			for (String s : arr) {
				if (resourcesCodeList.contains(s)) {
					num++;
				}
				if (num == length) {
					flag = true;
				}
			}
		}
		return flag;
	}

	private List<String> getAuthorityList() {
		Users users = SysContent.getUser();
		return users == null ? new ArrayList<String>() : users.getResourceCodeList();
	}
}
