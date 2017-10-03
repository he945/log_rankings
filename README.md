# log_rankings
# compile
kotlinc parse_log.kt -include-runtime -d parse_log.jar
# run on log file named log.csv
java -jar parse_log.jar log.csv
# sample input
<pre>
Path,User,Timestamp
/index.html,04a5d9a7-0a76-47a8-abd3-9e39a1abce50,2017-09-28T19:38:59+00:00
/index.html,04a5d9a7-0a76-47a8-abd3-9e39a1abce50,2017-09-28T19:38:59+00:01
/index2.html,04a5d9a7-0a76-47a8-abd3-9e39a1abce50,2017-09-28T19:38:59+00:02
</pre>
# sample output
<pre>
----------------------------------------
pages by unique hits
----------------------------------------
Page: /index.html Unique Hits: 2
Page: /index2.html Unique Hits: 1

----------------------------------------
pages by number of users
----------------------------------------
Page: /index.html Unique Users: 1
Page: /index2.html Unique Users: 1

----------------------------------------
users by unique page views
----------------------------------------
User: 04a5d9a7-0a76-47a8-abd3-9e39a1abce50 Unique Page Views: 3
</pre>
