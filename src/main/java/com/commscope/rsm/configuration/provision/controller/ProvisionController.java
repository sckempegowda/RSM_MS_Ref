package com.commscope.rsm.configuration.provision.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commscope.rsm.configuration.provision.model.ParameterDTO;
import com.commscope.rsm.configuration.provision.model.ProvisionedData;
import com.commscope.rsm.configuration.provision.service.ProvisionDataService;

@RestController
@RequestMapping("/provision")
public class ProvisionController {
	
	@Autowired
	private ProvisionDataService provisionDataService;

	@GetMapping(path="/get-all-provisioned-data")
	public ResponseEntity<Collection<ProvisionedData>> getAllProvisionedData() {
		Collection<ProvisionedData> provisionedDatas = provisionDataService.getAllProvisionedData();
		return ResponseEntity.ok(provisionedDatas);
	}
	
	@GetMapping (path = "/get-provisioned-data/deviceId/{deviceId}")
	public ResponseEntity<ProvisionedData> getProvisionedData(@PathVariable String deviceId) {
		ProvisionedData provisionedData =  provisionDataService.getProvisionedData(deviceId);
		return ResponseEntity.ok(provisionedData);
	}
	
	@GetMapping (path = "/get-provisioned-data/deviceId/{deviceId}/paramName/{paramName}")
	public ResponseEntity<ParameterDTO> getProvisionedData(@PathVariable String deviceId, @PathVariable String paramName) {
		ParameterDTO parameterDTO = provisionDataService.getProvisionedData(deviceId, paramName);
		return ResponseEntity.ok(parameterDTO);
	}
	
	@PostMapping (path = "/configure")
	public void provision(@Valid @RequestBody ProvisionedData provisionedData) {
		provisionDataService.provision(provisionedData);
	}
	
	@DeleteMapping (path = "/delete-provisioned-data/deviceId/{deviceId}/paramName/{paramName}")
	public void deleteProvisionedData(@PathVariable String deviceId, @PathVariable String paramName) {
		provisionDataService.deleteProvisionedData(deviceId, paramName);
	}
	
	@DeleteMapping (path = "/delete-all-provisioned-data/deviceId/{deviceId}")
	public void deleteProvisionedData(@PathVariable String deviceId) {
		provisionDataService.deleteProvisionedData(deviceId);
	}
}
