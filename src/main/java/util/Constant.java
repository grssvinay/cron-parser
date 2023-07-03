package util;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Constant {

	public static Map<String, Integer> MONTHS = Stream
			.of(new AbstractMap.SimpleImmutableEntry<>("JAN", 1), new AbstractMap.SimpleImmutableEntry<>("FEB", 2),
					new AbstractMap.SimpleImmutableEntry<>("MAR", 3), new AbstractMap.SimpleImmutableEntry<>("APR", 4),
					new AbstractMap.SimpleImmutableEntry<>("MAY", 5), new AbstractMap.SimpleImmutableEntry<>("JUN", 6),
					new AbstractMap.SimpleImmutableEntry<>("JUL", 7), new AbstractMap.SimpleImmutableEntry<>("AUG", 8),
					new AbstractMap.SimpleImmutableEntry<>("SEP", 9), new AbstractMap.SimpleImmutableEntry<>("OCT", 10),
					new AbstractMap.SimpleImmutableEntry<>("NOV", 11),
					new AbstractMap.SimpleImmutableEntry<>("DEC", 12))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));;
	public static Map<String, Integer> DAYS_OF_WEEK = Stream
			.of(new AbstractMap.SimpleImmutableEntry<>("SUN", 0), new AbstractMap.SimpleImmutableEntry<>("MON", 1),
					new AbstractMap.SimpleImmutableEntry<>("TUE", 2), new AbstractMap.SimpleImmutableEntry<>("WED", 3),
					new AbstractMap.SimpleImmutableEntry<>("THU", 4), new AbstractMap.SimpleImmutableEntry<>("FRI", 5),
					new AbstractMap.SimpleImmutableEntry<>("SAT", 6))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

	public static Map<Integer, Range> positionRanges() {
		Map<Integer, Range> ranges = new HashMap<>();
		ranges.put(0, new Range(0, 59, "minute"));
		ranges.put(1, new Range(0, 23, "hour"));
		ranges.put(2, new Range(1, 31, "day of month"));
		ranges.put(3, new Range(1, 12, "month"));
		ranges.put(4, new Range(0, 6, "day of week"));
		return ranges;
	}

}
