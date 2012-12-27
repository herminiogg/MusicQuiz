package com.ia.musicquiz;

public abstract class ActivityFinishedOnPause extends ActivityFullScreen {

	@Override
	protected void onPause() {
		finish();
		super.onPause();
	}
}
