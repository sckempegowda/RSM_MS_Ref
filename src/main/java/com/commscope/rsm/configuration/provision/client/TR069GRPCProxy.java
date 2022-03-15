package com.commscope.rsm.configuration.provision.client;

import org.springframework.stereotype.Component;

import com.commscope.rsm.configuration.grpc.tr069.GetRequest;
import com.commscope.rsm.configuration.grpc.tr069.GetResponse;
import com.commscope.rsm.configuration.grpc.tr069.TR069ServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Component
public class TR069GRPCProxy {
	
	TR069ServiceGrpc.TR069ServiceBlockingStub gRPCStub;
	
	public TR069GRPCProxy() {
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9091).usePlaintext().build();
		gRPCStub = TR069ServiceGrpc.newBlockingStub(channel);
	}
		
	public GetResponse getParameter(String deviceId, String paramName) {
		GetResponse getResponse = gRPCStub.getParameter(GetRequest.newBuilder().setDeviceId(deviceId)
				.setParameterName(paramName).build());
		return getResponse;
	}
}
