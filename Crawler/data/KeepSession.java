2
https://raw.githubusercontent.com/meethigher/cpdaily-submit/master/KeepSession.java
package cpdailyAlpha;

public class KeepSession {
	public static void main(String[] args) throws InterruptedException {
		while(true) {
			HttpUtil.sendGet(Data.keepingUrl,Data.getHeaders());
			Thread.sleep(1000*60*10);
		}
	}
}
