package com.commscope.rsm.configuration.provision.controller;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class ResponseMetadata {

	private Date timeStamp;

	private String message;

}
