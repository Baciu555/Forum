package com.baciu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AjaxResponse {
	
	private String status;
	private Object data;
	
	public AjaxResponse(){
		
	}

}
