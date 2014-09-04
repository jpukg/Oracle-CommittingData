package com.gvenzl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.pool.OracleDataSource;

/**
 * This class demonstrates the difference in throughput when
 * committing data differently to the Oracle Database.
 * @author gvenzl
 *
 */
public class CommittingData {

	private static String TESTTABLE = "COMMITDATA";
    private static String host = "";
	private static int port = 0;
	private static String serviceName = "";
	private static String userName = "";
	private static String password = "";
	private static boolean commitEveryRow = false;
	private static boolean commitAtEnd = false;
	private static int batchCommit =0;
	private static boolean directPath = false;

	private Connection myConnection;
	
	public CommittingData() throws SQLException {
		OracleDataSource ods = new OracleDataSource();
		ods.setDriverType("thin");
		ods.setServerName(host);
		ods.setPortNumber(port);
		ods.setServiceName(serviceName);
		ods.setUser(userName);
		ods.setPassword(password);

		myConnection = ods.getConnection();
	}
	/**
	 * Main entry point.
	 * @param args Array with various options on how to execute
	 * and which database to connect to
	 */
	public static void main(String[] args) throws Exception {

		if (args.length == 0) {
			printHelp();
		}
		else {

			for (int i=0;i<args.length;i++) {
				switch (args[i]) {
					case "-host":
						host = args[++i];
						break;
					case "-port":
						port = Integer.valueOf(args[++i]).intValue();
						break;
					case "-srvn":
						serviceName = args[++i];
						break;
					case "-user":
						userName = args[++i];
						break;
					case "-pass":
						password = args[++i];
						break;
					case "-commitEveryRow":
						commitEveryRow = true;
						break;
					case "-commitAtEnd":
						commitAtEnd = true;
						break;
					case "-batchCommit":
						batchCommit = Integer.valueOf(args[++i]).intValue();
						break;
					case "-directPath":
						directPath = true;
					default: printHelp();
				}
			}

			CommittingData myApp = new CommittingData();
			myApp.setup();
			myApp.runTests();
			myApp.tearDown();
		}
	}

	/**
	 * This method prepares the Database for testing by creating the test table
	 * @throws SQLException Any Database related error
	 */
	private void setup() throws SQLException {

		PreparedStatement stmt = myConnection.prepareStatement("CREATE TABLE " + TESTTABLE + " (ID NUMBER, TXT VARCHAR2(255))");
		stmt.execute();
	}
	
	private void tearDown() throws SQLException {
		PreparedStatement stmt = myConnection.prepareStatement("DROP TABLE " + TESTTABLE);
		stmt.execute();
		stmt = myConnection.prepareStatement("PURGE USER_RECYCLEBIN");
		stmt.execute();
	}
	
	/**
	 * Run the tests
	 * @throws SQLException Any Database error
	 */
	private void runTests() throws SQLException {
		
		if (commitEveryRow) {
			
		}
		else if (commitAtEnd) {
			
		}
		else if (batchCommit > 0) {
			
		}
	}

	/**
	 * Prints the help and exits the program.
	 */
	private static void printHelp() {
		System.out.println("Committing data to the Oracle Database - Usage:");
		System.out.println();
		System.out.println("java com.gvenzl.CommittingData -host [host] -port [port] -srvn [service name] -user [username] -pass [password] -commitEveryRow -commitAtEnd -batchCommit [commit size] -directPath");
		System.out.println();
		System.out.println("host: 		The database host name");
		System.out.println("port: 		The database listener port");
		System.out.println("service name: 	The database service name");
		System.out.println("username: 	The database username");
		System.out.println("password: 	The database user password");
		System.out.println("commitEveryRow: Commit data after every row");
		System.out.println("commitAtEnd: 	Commit data only once at the end of a load");
		System.out.println("commit size: 	N/A");
		System.out.println("directPath: 	N/A");

		System.exit(0);
	}

}