//GameLoop from the Java game

package com.learning.gametut;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

	private static final String TAG = MainThread.class.getSimpleName();
	
	private boolean running;
	private SurfaceHolder surfaceHolder;
	private MainGamePanel gamePanel;
	private Paint blackPaint;
	private double fps;

	public MainThread(SurfaceHolder surfaceHolder, MainGamePanel mainGamePanel) {
		super();
		this.gamePanel = mainGamePanel;
		this.surfaceHolder = surfaceHolder;
		running = false;

		blackPaint = new Paint();
		blackPaint.setARGB(255, 0, 0, 0);
	}

	public void setRunning(boolean runing) {
		this.running = runing;

	}

	@Override
	public void run() {
		long lastLoopTime = System.nanoTime();
		final int TARGET_FPS = 30;
		final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
		float lastFpsTime = 0;

		Canvas canvas;
		Log.d(TAG, "Starting game loop");
		
		while (running) {
			
			canvas = null;
			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			double delta = updateLength / ((double) OPTIMAL_TIME);
			lastFpsTime += updateLength;
			fps++;

			if (lastFpsTime >= 1000000000) {
			//	Log.d(TAG, "FPS :" + fps);
				/* System.out.println(fps); Enable this to display FPS */
				lastFpsTime = 0;
				fps = 0;
			}

			try {

				canvas = this.surfaceHolder.lockCanvas();

				if (canvas != null) {
					synchronized (surfaceHolder) {

						this.gamePanel.update(delta);
						canvas.drawRect(0, 0, canvas.getWidth(),
								canvas.getHeight(), blackPaint);
						this.gamePanel.render(canvas);

					}
				}

			} finally {
				// in case of an exception the surface is not left in
				// an inconsistent state
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			} // end finally
			
		}
		Log.d(TAG, "Game Loop Exit");
	}
}
