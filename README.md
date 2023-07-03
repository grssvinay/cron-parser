
## Processing format

	* * * * *
	minute[0-59] - hour[0-23] - day[1-31] - month[1-12,JAN-DEC] - day of week[0-6, SUN-SAT]


- For month, **JAN-DEC** text is also accepted
- For day of week, **SUN-SAT** text is also accepted



#### CLI

-- `main` method is in `App.java`

> Build:
>> mvn clean install

> Run:
>> java -cp target/cron-parser-0.0.1-SNAPSHOT.jar app.App "\*/15 0 1,15 \* 1-5 /user/bin/find"


