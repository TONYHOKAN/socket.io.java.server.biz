package com.dfocus.pmsg.facade.impl;

import com.dfocus.pmsg.facade.api.SecretFacade;
import com.dfocus.pmsg.facade.web.rsp.Response;
import com.dfocus.pmsg.service.atom.ISecretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author baozi
 * @Date: 2019/6/11 10:05
 * @Description:
 */
@RestController
public class SecretFacadeImpl implements SecretFacade {

	@Autowired
	ISecretService projectKeyService;

	@Override
	public Response<String> getPublicKey(@PathVariable String projectId) {
		return Response.success(projectKeyService.selectPublicKeyByProjectId(projectId));
	}

	@Override
	public Response<Boolean> saveOrUpdateKey(@PathVariable String projectId, @RequestParam String publicKey) {
		return Response.success(projectKeyService.saveOrUpdatePublicKey(projectId, publicKey));
	}

}
