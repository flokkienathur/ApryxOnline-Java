package net.apryx.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
	
	public static LogStream out, err;
	
	static{
		out = System.out::println;
		err = System.err::println;
	}
	
	public static void debug(String line){
		out.println(getPrepend() + line);
	}
	public static void error(String line){
		err.println(getPrepend() + line);
	}
	
	public static String getPrepend(){
		return "[" + getCurrentTimeStamp() + "] ";
	}
	
	private static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static String getCurrentTimeStamp() {
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;
	}
}