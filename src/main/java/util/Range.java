package util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

public class Range {
	int start;
	int end;
	private String field;

	Range(int start, int end, String field) {
		this.start = start;
		this.end = end;
		this.setField(field);
	}

	public void rangeValues(String value, List<Integer> rangeValues) throws IllegalArgumentException {
		if (value.isEmpty() || value.startsWith("-") || value.startsWith("/") || value.startsWith(",")
				|| value.endsWith("-") || value.endsWith("/") || value.endsWith(","))
			throw new IllegalArgumentException(value);

		if (field.equals("month") || field.equals("day of week"))
			value = updateTextToNumber(value, field);

		if (value.equals("*")) {
			IntStream.rangeClosed(start, end).forEach(rangeValues::add);
		} else if (value.contains(",")) {
			String[] subParts = value.split(",");
			Set<String> uniqueSubParts = new HashSet<String>(Arrays.asList(subParts));
			uniqueSubParts.forEach(e -> {
				try {
					rangeValues(e, rangeValues);
				} catch (IllegalArgumentException e1) {
					throw new IllegalArgumentException(e1.getMessage());
				}
			});
		} else if (value.contains("/")) {
			String[] subParts = value.split("/");
			if (subParts.length > 2 || subParts[1].contains("-") || subParts[1].contains("*"))
				throw new IllegalArgumentException(value);

			if (subParts[0].contains("-")) {
				int interval = Integer.parseInt(subParts[1]);
				rangeHandler(subParts[0], interval, rangeValues);
			} else {
				int interval = Integer.parseInt(subParts[1]);
				int begin = subParts[0].equals("*") ? start : Integer.parseInt(subParts[0]);
				for (int i = begin; i <= end; i += interval) {
					rangeValues.add(i);
				}
			}
		} else if (value.contains("-")) {
			rangeHandler(value, 1, rangeValues);
		} else {
			rangeValues.add(Integer.parseInt(value));
		}
	}

	private String updateTextToNumber(String value, String field) {
		Map<String, Integer> mp = null;
		switch (field) {
		case "month":
			mp = Constant.MONTHS;
		case "day of week":
			mp = Constant.DAYS_OF_WEEK;
		}
		for (Map.Entry<String, Integer> entry : mp.entrySet())
			value = value.replace(entry.getKey(), String.valueOf(entry.getValue()));
		return value;
	}

	private void rangeHandler(String rangeStr, int interval, List<Integer> rangeValues) {
		if (rangeStr.startsWith("-") || rangeStr.endsWith("-") || rangeStr.contains("*"))
			throw new IllegalArgumentException(rangeStr);

		String[] rangeParts = rangeStr.split("-");

		if (rangeParts.length != 2 || rangeParts[0].isEmpty() || rangeParts[1].isEmpty())
			throw new IllegalArgumentException(rangeStr);

		int rangeStart = Integer.parseInt(rangeParts[0]);
		int rangeEnd = Integer.parseInt(rangeParts[1]);

		if (rangeStart > rangeEnd || rangeEnd > end || rangeStart < start)
			throw new IllegalArgumentException(rangeStr);

		for (int i = rangeStart; i <= rangeEnd; i += interval)
			rangeValues.add(i);
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

}
