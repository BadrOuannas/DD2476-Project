29
https://raw.githubusercontent.com/swwheihei/wvp/master/src/main/java/com/genersoft/iot/vmp/gb28181/transmit/request/impl/OtherRequestProcessor.java
package com.genersoft.iot.vmp.gb28181.transmit.request.impl;

import javax.sip.RequestEvent;
import javax.sip.ServerTransaction;

import org.springframework.stereotype.Component;

import com.genersoft.iot.vmp.gb28181.SipLayer;
import com.genersoft.iot.vmp.gb28181.transmit.request.ISIPRequestProcessor;

/**    
 * @Description:暂不支持的消息请求处理器
 * @author: songww
 * @date:   2020年5月3日 下午5:32:59     
 */
@Component
public class OtherRequestProcessor implements ISIPRequestProcessor {

	/**   
	 * <p>Title: process</p>   
	 * <p>Description: </p>   
	 * @param evt
	 * @param layer
	 * @param transaction
	 * @param config    
	 */  
	@Override
	public void process(RequestEvent evt, SipLayer layer, ServerTransaction transaction) {
		System.out.println("no support the method! Method:" + evt.getRequest().getMethod());
	}

}
