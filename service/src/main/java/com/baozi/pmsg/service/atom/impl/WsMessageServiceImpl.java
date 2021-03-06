package com.baozi.pmsg.service.atom.impl;

import com.corundumstudio.socketio.BroadcastOperations;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.baozi.pmsg.facade.model.WsMessage;
import com.baozi.pmsg.service.atom.IWsMessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author: baozi
 * @date: 2019/8/8 17:05
 * @description:
 */
@Slf4j
@Service
public class WsMessageServiceImpl implements IWsMessageService {

	@Autowired
	SocketIOServer socketIOServer;

	@Autowired
	ObjectMapper objectMapper;

	@Override
	public boolean send(WsMessage message) {

		log.info("project:{}|group:{}|message:{}", message.getProjectId(), message.getTopic(), message.getPayload());

		// 获取namespace
		SocketIONamespace namespace = socketIOServer.getNamespace("/" + message.getProjectId());
		if (namespace == null) {
			log.error("该项目下没发现客户端, projectId:{}", message.getProjectId());
			return false;
		}

		// 获取room
		BroadcastOperations roomOperations = namespace.getRoomOperations(message.getTopic());

		try {
			roomOperations.sendEvent(StringUtils.isEmpty(message.getEvent()) ? "message" : message.getEvent(),
					objectMapper.writeValueAsString(message));
		}
		catch (JsonProcessingException e) {
			log.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

}
