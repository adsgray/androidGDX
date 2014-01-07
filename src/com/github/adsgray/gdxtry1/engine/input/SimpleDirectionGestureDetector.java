package com.github.adsgray.gdxtry1.engine.input;

import android.util.Log;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector3;
import com.github.adsgray.gdxtry1.engine.input.SimpleDirectionGestureDetector.DirectionListener.FlingInfo;


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
public class SimpleDirectionGestureDetector extends GestureDetector  {
    
    protected DirectionListener directionListener;
    
    @Override
    public boolean keyDown(int keycode) {
        directionListener.keyDown(keycode);
        return false;
    }

    public interface DirectionListener {
        
        void keyDown(int key);

        void onLeft(FlingInfo f);
        void onRight(FlingInfo f);
        void onUp(FlingInfo f);
        void onDown(FlingInfo f);
        
        void panStarted(float sx, float sy);
        
        void panInProgress(float curx, float cury);

        // a completed pan from (sx,sy) to (ex,ey)
        void completePan(float sx, float sy, float ex, float ey);
        
        void onTap(float x, float y, int count);
        
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


    public SimpleDirectionGestureDetector(OrthographicCamera camera, DirectionListener directionListener) {
        super(new DirectionGestureListener(camera, directionListener));
        this.directionListener = directionListener;
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

        private OrthographicCamera camera;
        private PanState panState;
        
        public DirectionGestureListener(OrthographicCamera camera, DirectionListener directionListener){
            this.camera = camera;
            this.directionListener = directionListener;
            panState = new PanState();
        }
      	
        private Vector3 convertCoords(float x, float y) {
            Vector3 pos = new Vector3();
            pos.set(x, y, 0);
            camera.unproject(pos);
            return pos;
        }
		    
        private FlingInfo convertFlingInfoCoords(FlingInfo f) {
            Vector3 startpos = convertCoords(f.startX, f.startY);
            f.startX = startpos.x;
            f.startY = startpos.y;
            return f;
        }
	    
        //Called when the user drags a finger over the screen.
        @Override
        public boolean pan(float x, float y, float deltaX, float deltaY) {
            //Log.d("input", String.format("pan: x:%f y:%f dX:%f dY:%f", x, y, deltaX, deltaY));

            Vector3 posConverted = convertCoords(x,y);
            x = posConverted.x;
            y = posConverted.y;

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
            Vector3 posConverted = convertCoords(x,y);
            panState.panBegun = false;
            directionListener.completePan(panState.sx, panState.sy, posConverted.x, posConverted.y);
            return super.panStop(x,y,pointer,button);
        }

        @Override
        public boolean fling(float velocityX, float velocityY, int button) {
            // panState coords were converted at panBegun time.
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
        
        @Override
        public boolean tap(float x, float y, int count, int button) {
            Vector3 posConverted = convertCoords(x,y);
            directionListener.onTap(posConverted.x,posConverted.y,count);
            return super.tap(x,y,count,button);
        }

    }
}
