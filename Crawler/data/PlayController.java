29
https://raw.githubusercontent.com/swwheihei/wvp/master/src/main/java/com/genersoft/iot/vmp/vmanager/play/PlayController.java
package com.genersoft.iot.vmp.vmanager.play;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.genersoft.iot.vmp.gb28181.bean.Device;
import com.genersoft.iot.vmp.gb28181.transmit.cmd.impl.SIPCommander;
import com.genersoft.iot.vmp.storager.IVideoManagerStorager;

@RestController
@RequestMapping("/api")
public class PlayController {
	
	private final static Logger logger = LoggerFactory.getLogger(PlayController.class);
	
	@Autowired
	private SIPCommander cmder;
	
	@Autowired
	private IVideoManagerStorager storager;
	
	@GetMapping("/play/{deviceId}/{channelId}")
	public ResponseEntity<String> play(@PathVariable String deviceId,@PathVariable String channelId){
		
		Device device = storager.queryVideoDevice(deviceId);
		String ssrc = cmder.playStreamCmd(device, channelId);
		
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("设备预览 API调用，deviceId：%s ，channelId：%s",deviceId, channelId));
			logger.debug("设备预览 API调用，ssrc："+ssrc+",ZLMedia streamId:"+Integer.toHexString(Integer.parseInt(ssrc)));
		}
		
		if(ssrc!=null) {
			JSONObject json = new JSONObject();
			json.put("ssrc", ssrc);
			return new ResponseEntity<String>(json.toString(),HttpStatus.OK);
		} else {
			logger.warn("设备预览API调用失败！");
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
