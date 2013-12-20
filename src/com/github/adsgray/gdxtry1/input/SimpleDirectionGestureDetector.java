package com.github.adsgray.gdxtry1.input;

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
        void onLeft();
        void onRight();
        void onUp();
        void onDown();
    }

    public SimpleDirectionGestureDetector(DirectionListener directionListener) {
        super(new DirectionGestureListener(directionListener));
    }
    
    private static class DirectionGestureListener extends GestureAdapter{
        DirectionListener directionListener;
        
        public DirectionGestureListener(DirectionListener directionListener){
            this.directionListener = directionListener;
        }
        
        @Override
        public boolean fling(float velocityX, float velocityY, int button) {
            if(Math.abs(velocityX)>Math.abs(velocityY)){
                if(velocityX>0){
                        directionListener.onRight();
                }else{
                        directionListener.onLeft();
                }
            }else{
                if(velocityY>0){
                        directionListener.onDown();
                }else{                                  
                        directionListener.onUp();
                }
            }
            return super.fling(velocityX, velocityY, button);
        }

    }
}
