137
https://raw.githubusercontent.com/201206030/novel-plus/master/novel-admin/src/main/java/com/java2nb/common/utils/HttpContextUtils.java
package com.java2nb.common.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class HttpContextUtils {
	public static HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
}