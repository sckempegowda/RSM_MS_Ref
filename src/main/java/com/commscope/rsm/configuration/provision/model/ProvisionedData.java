package com.commscope.rsm.configuration.provision.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProvisionedData {
	@Size(min = 6, max = 12, message = "DeviceID must be having min 6 and max 12 letter")
	private String deviceId;

	@NotEmpty
	private List<ParameterDTO> parameterDTOs;

}
