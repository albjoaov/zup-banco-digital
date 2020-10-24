package br.com.zup.bootcamp.bancodigital.shared;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.random;

public class RandomUtils {

	public static String randomNumberGeneratorWithLeadingZeros(int digitAmount) {
		int min = 0;
		int max = 10; // exclusive

		return IntStream
				.generate(() -> (int) (random() * (max - min) + min))
				.limit(digitAmount)
				.mapToObj(String::valueOf)
				.collect(Collectors.joining());
	}
}
