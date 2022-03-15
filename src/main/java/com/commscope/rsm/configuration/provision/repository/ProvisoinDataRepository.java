package com.commscope.rsm.configuration.provision.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.commscope.rsm.configuration.provision.entity.ProvisionDataEntity;

@Repository
public interface ProvisoinDataRepository extends JpaRepository<ProvisionDataEntity, Long>{

	public List<ProvisionDataEntity> findByDeviceId(String deviceId);
	
	public List<ProvisionDataEntity> findByDeviceIdAndParamName(String deviceId, String paramName);
	
	public void deleteByDeviceId(String deviceId);
	
	public void deleteByDeviceIdAndParamName(String deviceId, String paramName);
}


// Comment - atabase tables creation - Install and upgrade
// Migration - 
// Rapid development is the strategy moving forward