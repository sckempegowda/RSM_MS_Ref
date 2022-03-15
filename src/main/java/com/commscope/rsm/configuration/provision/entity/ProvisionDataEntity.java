package com.commscope.rsm.configuration.provision.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="PROVISION_DATA")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProvisionDataEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROVISION_DATA_SEQ")
	@SequenceGenerator(name = "PROVISION_DATA_SEQ", sequenceName = "PROVISION_DATA_SEQ", allocationSize = 1)
	private long id;

	@Column(name = "DEVICE_ID")
	private String deviceId;
	
	@Column(name = "PARAM_NAME")
	private String paramName;
	
	@Column(name = "PARAM_VALUE")
	private String paramValue;

	public ProvisionDataEntity(String deviceId, String paramName, String paramValue) {
		super();
		this.deviceId = deviceId;
		this.paramName = paramName;
		this.paramValue = paramValue;
	}
}
