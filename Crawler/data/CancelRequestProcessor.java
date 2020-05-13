29
https://raw.githubusercontent.com/swwheihei/wvp/master/src/main/java/com/genersoft/iot/vmp/gb28181/transmit/request/impl/CancelRequestProcessor.java
package com.genersoft.iot.vmp.gb28181.transmit.request.impl;

import javax.sip.RequestEvent;
import javax.sip.ServerTransaction;

import org.springframework.stereotype.Component;

import com.genersoft.iot.vmp.gb28181.SipLayer;
import com.genersoft.iot.vmp.gb28181.transmit.request.ISIPRequestProcessor;

/**    
 * @Description:CANCEL请求处理器
 * @author: songww
 * @date:   2020年5月3日 下午5:32:23     
 */
@Component
public class CancelRequestProcessor implements ISIPRequestProcessor {

	/**   
	 * 处理CANCEL请求
	 *  
	 * @param evt
	 * @param layer
	 * @param transaction
	 * @param config    
	 */  
	@Override
	public void process(RequestEvent evt, SipLayer layer, ServerTransaction transaction) {
		// TODO Auto-generated method stub
		
	}

}
