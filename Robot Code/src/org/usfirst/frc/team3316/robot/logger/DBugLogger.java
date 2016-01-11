package org.usfirst.frc.team3316.robot.logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.Formatter;

public class DBugLogger extends Logger {
	
	private static String name = "";
	
	private class DBugFormatter extends Formatter {
	    public String format(LogRecord record) 
	    {
	    	return record.getMillis() + ":" + record.getLevel() + ":" + record.getSourceClassName() +
	    			":" + record.getSourceMethodName() + ":" + record.getMessage() + "\n";
	    }
	}
	
    public DBugLogger() 
    {
    	super(name, null);

    	Handler[] handlers = this.getHandlers();
		for (int i=0; i<handlers.length; i++ ) {
			handlers[i].setLevel( Level.FINEST );
		}
		this.setLevel(Level.FINEST);
		this.setUseParentHandlers(true); //disables console output if 'false' is given as a parameter
		
		try {
			String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
		    FileHandler fh = new FileHandler("/home/lvuser/logs/logFile" + timeStamp + ".log"); 
		    this.addHandler(fh);
		    DBugFormatter formatter = new DBugFormatter();
	        fh.setFormatter(formatter);
	    } 
	    catch (SecurityException e) {
	        e.printStackTrace();
	    } 
	    catch (IOException e) {  
	        e.printStackTrace();  
	    }
    }

    public void severe(Exception e) {
    	StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionStackTrace = sw.toString();
		this.severe(e.getMessage() + ":" + exceptionStackTrace);
    }
}
