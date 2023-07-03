package services.cron;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import util.Constant;
import util.Range;

public class Parser {
	public static Map<String, String> parse(String expression) throws Exception {
		if (expression == null || expression.trim().isEmpty())
			throw new IllegalArgumentException("Invalid String");

		String[] parts = expression.split(" ");
		if (parts.length < 5)
			throw new IllegalArgumentException("Invalid String");

		return validateAndGenerateRanges(parts);
	}

	private static Map<String, String> validateAndGenerateRanges(String[] parts) throws IllegalAccessException {
		Map<Integer, Range> ranges = Constant.positionRanges();
		Map<String, String> response = new LinkedHashMap<>();

		for (int i = 0; i < parts.length - 1; i++) {
			List<Integer> rangeValues = new ArrayList<>();
			ranges.get(i).rangeValues(parts[i], rangeValues);
			rangeValues = rangeValues.stream().distinct().sorted().collect(Collectors.toList());
			response.put(ranges.get(i).getField(),
					rangeValues.stream().map(String::valueOf).collect(Collectors.joining(" ")));
		}
		if (parts.length > 5)
			response.put("command", parts[parts.length - 1]);
		return response;
	}

	public static void print(Map<String, String> ranges) {
		Formatter fmt = new Formatter();
		ranges.forEach((k,v) -> fmt.format("%-14s%s\n", k, v));
		System.out.println(fmt);
	}
}
