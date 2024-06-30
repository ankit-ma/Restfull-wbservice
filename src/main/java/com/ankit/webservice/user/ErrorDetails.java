package com.ankit.webservice.user;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorDetails {

	private LocalDateTime timeStamp;
	private String message;
	private String detail;
	
}
