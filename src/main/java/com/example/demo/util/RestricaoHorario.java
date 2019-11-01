package com.example.demo.util;

import java.io.Serializable;
import java.util.Date;

public class RestricaoHorario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Long DIA = 86400000L;

	private static Long RESTRICAO = 259200000L;

	public static Long getRESTRICAO() {
		return RESTRICAO;
	}

	public static Long getDia() {
		return DIA;
	}
	
	public static boolean permite(Date dataInicial) {

		Long hoje = new Date().getTime() + getRESTRICAO();

		Long restricao = dataInicial.getTime();

		if (hoje <= restricao) {
			return true;
		} else {
			throw new NegocioException("Não esta de acordo com o termo assinado");
		}

	}

}
