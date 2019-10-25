package com.example.demo.util;

import java.io.Serializable;

public class RestricaoHorario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Long RESTRICAO = 259200000L;

	public static Long getRESTRICAO() {
		return RESTRICAO;
	} 
	
}
