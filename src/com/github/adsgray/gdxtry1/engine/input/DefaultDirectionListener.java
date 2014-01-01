package com.github.adsgray.gdxtry1.engine.input;

import android.util.Log;

import com.github.adsgray.gdxtry1.engine.WorldIF;
import com.github.adsgray.gdxtry1.engine.input.SimpleDirectionGestureDetector.DirectionListener;

public class DefaultDirectionListener implements DirectionListener {
    	
		    @Override
		    public void onUp(DirectionListener.FlingInfo f) {
		        // TODO Auto-generated method stub
		        Log.d("input", String.format("screen swiped UP start(%f,%f) vel(%f,%f)", f.startX, f.startY, f.velocityX, f.velocityY));
		    }

		    @Override
		    public void onRight(DirectionListener.FlingInfo f) {
		        // TODO Auto-generated method stub
		        Log.d("input", String.format("screen swiped RIGHT start(%f,%f) vel(%f,%f)", f.startX, f.startY, f.velocityX, f.velocityY));

		    }

		    @Override
		    public void onLeft(DirectionListener.FlingInfo f) {
		        // TODO Auto-generated method stub
		        Log.d("input", String.format("screen swiped LEFT start(%f,%f) vel(%f,%f)", f.startX, f.startY, f.velocityX, f.velocityY));

		    }

		    @Override
		    public void onDown(DirectionListener.FlingInfo f) {
		        Log.d("input", String.format("screen swiped DOWN start(%f,%f) vel(%f,%f)", f.startX, f.startY, f.velocityX, f.velocityY));
		    }

            @Override
            public void completePan(float sx, float sy, float ex, float ey) {
                Log.d("input", String.format("completePan from (%f,%f) to (%f,%f)", sx, sy, ex, ey));
            }

            @Override
            public void panStarted(float sx, float sy) {
                Log.d("input", String.format("pan started at (%f, %f)", sx, sy));
            }

            @Override
            public void panInProgress(float curx, float cury) {
                Log.d("input", String.format("pan continues at (%f, %f)", curx, cury));
            }
}
