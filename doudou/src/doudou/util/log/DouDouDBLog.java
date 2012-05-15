package doudou.util.log;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DouDouDBLog {

	private final Log log;
	private final AtomicLong seq = new AtomicLong();

	public DouDouDBLog(String name) {
		log = LogFactory.getLog(name);
	}

	public void error(long time, String msg) {
		if (log.isErrorEnabled()) {
			log.error("thread=" + Thread.currentThread().getId() + ", seq=" + seq.getAndIncrement() + ", time=" + (System.currentTimeMillis() - time) + ", msg=" + msg);
		}
	}

	public void error(long time, String msg, Throwable e) {
		if (log.isErrorEnabled()) {
			log.error("thread=" + Thread.currentThread().getId() + ", seq=" + seq.getAndIncrement() + ", time=" + (System.currentTimeMillis() - time) + ", msg=" + msg, e);
		}
	}

	public void info(long time, String msg) {
		if (log.isInfoEnabled()) {
			log.info("thread=" + Thread.currentThread().getId() + ", seq=" + seq.getAndIncrement() + ", time=" + (System.currentTimeMillis() - time) + ", msg=" + msg);
		}
	}

	public boolean isInfoEnabled() {
		return log.isInfoEnabled();
	}

	public boolean isErrorEnabled() {
		return log.isErrorEnabled();
	}

}
