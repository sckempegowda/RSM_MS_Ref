package com.commscope.rsm.configuration.provision.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.commscope.rsm.configuration.provision.client.TR069GRPCProxy;
import com.commscope.rsm.configuration.provision.client.TR069RestProxy;
import com.commscope.rsm.configuration.provision.entity.ProvisionDataEntity;
import com.commscope.rsm.configuration.provision.exception.DeviceNotFoundException;
import com.commscope.rsm.configuration.provision.exception.ParameterNotFoundException;
import com.commscope.rsm.configuration.provision.model.ParameterDTO;
import com.commscope.rsm.configuration.provision.model.ProvisionedData;
import com.commscope.rsm.configuration.provision.repository.ProvisoinDataRepository;
import com.commscope.rsm.configuration.provision.utils.ErrorCode;
import com.commscope.rsm.configuration.grpc.tr069.GetResponse;

@Component
public class ProvisionDataService {

	Logger logger = LoggerFactory.getLogger(ProvisionDataService.class);

	@Autowired
	private ProvisoinDataRepository provisoinDataRepository;

	@Autowired
	private TR069RestProxy restProxy;
	
	@Autowired
	private TR069GRPCProxy gRPCProxy; 

	/**
	 * @return
	 */
	public Collection<ProvisionedData> getAllProvisionedData() {
		Map<String, ProvisionedData> provisionedMap = new HashMap<>();
		List<ProvisionDataEntity> provisionDataEntities = provisoinDataRepository.findAll();
		for (ProvisionDataEntity provisionDataEntity : provisionDataEntities) {
			String deviceId = provisionDataEntity.getDeviceId();
			ProvisionedData provisionedData = provisionedMap.get(deviceId);
			if (provisionedData == null) {
				provisionedData = new ProvisionedData(deviceId, new ArrayList<ParameterDTO>());
				provisionedMap.put(deviceId, provisionedData);
			}

			ParameterDTO parameterDTO = new ParameterDTO(provisionDataEntity.getId(),
					provisionDataEntity.getParamName(), provisionDataEntity.getParamValue());
			provisionedData.getParameterDTOs().add(parameterDTO);
		}

		Collection<ProvisionedData> provisionedDatas = provisionedMap.values();
		return provisionedDatas;
	}

	/**
	 * @param deviceId
	 * @return
	 */
	public ProvisionedData getProvisionedData(String deviceId) {
		ProvisionedData provisionedData = getDeviceProvisionedData(deviceId);

		if (provisionedData == null) {
			DeviceNotFoundException dnfe = new DeviceNotFoundException(ErrorCode.DEVICE_NOT_FOUND);
			throw dnfe;
		}
		return provisionedData;
	}

	/**
	 * @param deviceId
	 * @param paramName
	 * @return
	 */
	public ParameterDTO getProvisionedData(String deviceId, String paramName) {
		ParameterDTO parameterDTO = null;
		List<ProvisionDataEntity> provisionDataEntities = provisoinDataRepository.findByDeviceIdAndParamName(deviceId,
				paramName);
		for (ProvisionDataEntity provisionDataEntity : provisionDataEntities) {
			parameterDTO = new ParameterDTO(provisionDataEntity.getId(), provisionDataEntity.getParamName(),
					provisionDataEntity.getParamValue());

			GetResponse response = gRPCProxy.getParameter(deviceId, paramName);
			logger.info("GRPC Response Value: " + response.getParameterValue());
		}

		if (parameterDTO == null) {
			throw new ParameterNotFoundException();
		}

		return parameterDTO;
	}

	/**
	 * @param provisionedData
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void provision(ProvisionedData provisionedData) {
		String deviceId = provisionedData.getDeviceId();
		List<ParameterDTO> reqParamDTOs = provisionedData.getParameterDTOs();
		ProvisionedData existingProvData = getDeviceProvisionedData(deviceId);
		if (existingProvData != null) {
			List<ParameterDTO> existingParamsDTOs = existingProvData.getParameterDTOs();
			List<ParameterDTO> tempParamDTOs = new ArrayList<>();
			for (ParameterDTO reqParamDTO : reqParamDTOs) {
				boolean found = false;
				for (ParameterDTO existingParamDTO : existingParamsDTOs) {
					if (reqParamDTO.equals(existingParamDTO)) {
						existingParamDTO.setParamVal(reqParamDTO.getParamVal());
						found = true;
						break;
					}
				}
				if (!found) {
					tempParamDTOs.add(reqParamDTO);
				}
			}
			existingParamsDTOs.addAll(tempParamDTOs);
			provisionedData.setParameterDTOs(existingParamsDTOs);
		} else {
			provisionedData.setParameterDTOs(reqParamDTOs);
		}

		List<ParameterDTO> newParameterDTOs = provisionedData.getParameterDTOs();
		for (ParameterDTO parameterDTO : newParameterDTOs) {
			ProvisionDataEntity provisionDataEntity = null;
			if (parameterDTO.getId() == 0) {
				provisionDataEntity = new ProvisionDataEntity(deviceId, parameterDTO.getParamName(),
						parameterDTO.getParamVal());
			} else {
				provisionDataEntity = new ProvisionDataEntity(parameterDTO.getId(), deviceId,
						parameterDTO.getParamName(), parameterDTO.getParamVal());
			}

			String status = restProxy.setParameterValues(deviceId, parameterDTO.getParamName(), parameterDTO.getParamVal());
			logger.info("Status: " + status);
			provisoinDataRepository.save(provisionDataEntity);
		}

	}

	/**
	 * @param deviceId
	 * @param paramName
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void deleteProvisionedData(String deviceId, String paramName) {
		provisoinDataRepository.deleteByDeviceIdAndParamName(deviceId, paramName);
	}

	/**
	 * @param deviceId
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void deleteProvisionedData(String deviceId) {
		provisoinDataRepository.deleteByDeviceId(deviceId);
	}

	/**
	 * @param deviceId
	 * @return
	 */
	private ProvisionedData getDeviceProvisionedData(String deviceId) {
		ProvisionedData provisionedData = null;
		List<ProvisionDataEntity> provisionDataEntities = provisoinDataRepository.findByDeviceId(deviceId);

		for (ProvisionDataEntity provisionDataEntity : provisionDataEntities) {
			if (provisionedData == null) {
				provisionedData = new ProvisionedData(deviceId, new ArrayList<ParameterDTO>());
			}
			ParameterDTO parameterDTO = new ParameterDTO(provisionDataEntity.getId(),
					provisionDataEntity.getParamName(), provisionDataEntity.getParamValue());
			provisionedData.getParameterDTOs().add(parameterDTO);
		}
		return provisionedData;
	}
}
