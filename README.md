WalletHub Access Log File.
========================

<h2>Notes to run the program: </h2>

* Install mysql (via docker or clean installation in your local machine).
* Edit my.ini file and set secure-file-priv attribute to the directory where access log file will be located.
* Start mysql server.
* Connect to mysql (user root and password root) and launch the script to create the database and tables. 
* Download the git code.
* Open a console and position yourself where pom.xml file is.
* Run 'mvn package' command (maven previously installed).
* Run 'java -cp target/parser-1.0-SNAPSHOT.jar com.ef.wallethub.Parser --accesslog=/path/to/file --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100 ' command.