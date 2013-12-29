package com.github.adsgray.gdxtry1.input;

import android.util.Log;

import com.badlogic.gdx.input.GestureDetector;


// from: 
// http://truongtx.me/2013/04/27/simple-swipe-gesture-detection-for-libgdx/
/*
 * 
 * in create():
 * 
  Gdx.input.setInputProcessor(new SimpleDirectionGestureDetector(new SimpleDirectionGestureDetector.DirectionListener() {
        
    @Override
    public void onUp() {
        // TODO Auto-generated method stub
    }

    @Override
    public void onRight() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onLeft() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onDown() {
        // TODO Auto-generated method stub

    }
}));
 */
public class SimpleDirectionGestureDetector extends GestureDetector {
    

    
    public interface DirectionListener {
        void onLeft(FlingInfo f);
        void onRight(FlingInfo f);
        void onUp(FlingInfo f);
        void onDown(FlingInfo f);
        
        void panStarted(float sx, float sy);
        
        void panInProgress(float curx, float cury);

        // a completed pan from (sx,sy) to (ex,ey)
        void completePan(float sx, float sy, float ex, float ey);
        
        // useful info when getting a fling event
        public static class FlingInfo {
            public float startX;
            public float startY;
            public float velocityX;
            public float velocityY;
            
            public FlingInfo(float sx, float sy, float vx, float vy) {
                startX = sx;
                startY = sy;
                velocityX = vx;
                velocityY = vy;
            }
        }
    }

    public SimpleDirectionGestureDetector(DirectionListener directionListener) {
        super(new DirectionGestureListener(directionListener));
    }
    
    private static class DirectionGestureListener extends GestureAdapter{
        DirectionListener directionListener;
        public class PanState {
            public PanState() {
                panBegun = false;
                sx = 0f;
                sy = 0f;
            }
            public Boolean panBegun;
            public float sx;
            public float sy;
        }
        
        private PanState panState;
        
        public DirectionGestureListener(DirectionListener directionListener){
            this.directionListener = directionListener;
            panState = new PanState();
        }
        
        //Called when the user drags a finger over the screen.
        @Override
        public boolean pan(float x, float y, float deltaX, float deltaY) {
            //Log.d("input", String.format("pan: x:%f y:%f dX:%f dY:%f", x, y, deltaX, deltaY));
            if (!panState.panBegun) {
                panState.panBegun = true;
                panState.sx = x;
                panState.sy = y;
                
                directionListener.panStarted(x, y);
            } else {
                directionListener.panInProgress(x, y);
            }
            return super.pan(x,y,deltaX,deltaY);
        }

        //Called when no longer panning.
        @Override
        public boolean panStop(float x, float y, int pointer, int button) {
            //Log.d("input", String.format("panStop: x:%f y:%f p:%d b:%d", x,y,pointer,button));
            panState.panBegun = false;
            directionListener.completePan(panState.sx, panState.sy, x, y);
            return super.panStop(x,y,pointer,button);
        }

        @Override
        public boolean fling(float velocityX, float velocityY, int button) {
            DirectionListener.FlingInfo f = new DirectionListener.FlingInfo(panState.sx, panState.sy, velocityX, velocityY);

            if(Math.abs(velocityX)>Math.abs(velocityY)){
                if(velocityX>0){
                        directionListener.onRight(f);
                }else{
                        directionListener.onLeft(f);
                }
            }else{
                if(velocityY>0){
                        directionListener.onDown(f);
                }else{                                  
                        directionListener.onUp(f);
                }
            }
            return super.fling(velocityX, velocityY, button);
        }

    }
}
