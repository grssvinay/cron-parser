package app;

import java.util.Map;

import services.cron.Parser;

public class App {
	public static void main(String[] args) throws Exception {
		if (args.length == 0)
			throw new Exception("Please pass input");

		Map<String, String> response = Parser.parse(args[0]);// "*/15 0 1,15 * 1-5 /user/bin/find";
		Parser.print(response);
	}
}
