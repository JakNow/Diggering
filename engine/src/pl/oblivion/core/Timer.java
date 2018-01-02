package pl.oblivion.core;

import org.apache.log4j.Logger;

 class Timer {

	private double lastLoopTime;
	private static final Logger logger = Logger.getLogger(Timer.class);
	 Timer() {
		this.init();
		logger.info("Initializing of Timer was successful.");
	}

	private void init() {
		lastLoopTime = getTime();
	}

	 double getTime() {
		return System.nanoTime() / 1000_000_000.0;
	}

	 float getElapsedTime() {
		double time = getTime();
		float elapsedTime = (float) (time - lastLoopTime);
		lastLoopTime = time;
		return elapsedTime;
	}

	 double getLastLoopTime() {
		return lastLoopTime;
	}
}
