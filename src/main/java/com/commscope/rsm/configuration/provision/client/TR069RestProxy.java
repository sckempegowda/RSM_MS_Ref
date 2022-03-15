package com.commscope.rsm.configuration.provision.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="tr069-service", url = "localhost:8081")
public interface TR069RestProxy {

	@PostMapping(path = "/tr069service/spv/device/{deviceId}/param/{paramName}/value/{paramValue}")
	public String setParameterValues(@PathVariable String deviceId, @PathVariable String paramName, @PathVariable String paramValue);
}
