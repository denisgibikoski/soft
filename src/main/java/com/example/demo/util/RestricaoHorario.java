package com.example.demo.util;

import java.io.Serializable;
import java.util.Date;

public class RestricaoHorario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Long RESTRICAO = 259200000L;

	public static Long getRESTRICAO() {
		return RESTRICAO;
	}

	public static boolean permite(Date dataInicial) {
		// TODO Auto-generated method stub
		return false;
	} 
	
	
}
