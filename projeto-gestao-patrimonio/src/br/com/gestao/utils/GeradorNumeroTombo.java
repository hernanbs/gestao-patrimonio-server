package br.com.gestao.utils;

import java.util.Random;

public class GeradorNumeroTombo {
	public static int gerarNumeroTombo() {
		Random random = new Random();
		return random.nextInt(999999) + 100000;
	}
}
