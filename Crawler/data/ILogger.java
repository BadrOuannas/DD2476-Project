2
https://raw.githubusercontent.com/miricel/CO_Project/master/UPTBench/src/logging/ILogger.java
package logging;

public interface ILogger {
	
	public void write(long x);
	public void write(String s);
	public void write(Object ... params);
	public void close();
	
	public void writeTime(long value,TimeUnit unit);
	public void writeTime(String string, long value, TimeUnit unit);


}
