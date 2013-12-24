package com.github.adsgray.gdxtry1;

/*
import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
*/


import android.util.Log;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.github.adsgray.gdxtry1.engine.*;
import com.github.adsgray.gdxtry1.game.GameFactory;
import com.github.adsgray.gdxtry1.input.SimpleDirectionGestureDetector;
import com.github.adsgray.gdxtry1.output.RenderConfig;

public class MainPanel implements ApplicationListener {

    private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 1422;
	private static final int numBlobs = 10;

	private WorldIF world;
	private RenderConfig renderConfig;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private ShapeRenderer shapes;
	
	private void populateWorld() {
	    //GameFactory.populateWorldWithBlobs(world, numBlobs, renderConfig);
	    //GameFactory.populateWorldNonRandom(world, renderConfig);
	    //GameFactory.populateWorldNonRandomBlobSet(world, renderConfig);
	    //GameFactory.populateWorldLaunchUp(world, renderConfig);
	    //GameFactory.populateWorldOoze(world, renderConfig);
	    GameFactory.populateWorldCollisionTest(world, renderConfig);
	}

	@Override
	public void create() {
		shapes = new ShapeRenderer();
		batch = new SpriteBatch();

	    renderConfig = new RenderConfig(shapes, batch);
	    world = GameFactory.defaultWorld();
	    populateWorld();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT); // the camera is like a window into our game world
		
		// Setup swipe/touch handling:
		Gdx.input.setInputProcessor(new SimpleDirectionGestureDetector(new SimpleDirectionGestureDetector.DirectionListener() {
	        
		    @Override
		    public void onUp() {
		        // TODO Auto-generated method stub
		        Log.d("input", "screen swiped UP");
		        populateWorld();
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
		        world.killAllBlobs();
		    }
		}));
		
		
		
	}

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
        
    }

	@Override
	public void render() {
	    // TODO: config class for colours
	    Gdx.gl.glClearColor(0.199f, 0.398f, 0.598f, 0.4f);	// OpenGL code to make the screen blue
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);			// OpenGL code to clear the screen
	    camera.update();
	    
	    //batch.setProjectionMatrix(camera.combined);
	    //batch.begin();
	    //world.getSpriteRenderer().render(batch);
	    //batch.draw(smileyface, rect_smiley.x, rect_smiley.y);
	    //batch.draw(helloworld, rect_hello.x, rect_hello.y);
	    //batch.end();

	    world.tick();
	    shapes.begin(ShapeType.Filled);
	    shapes.setProjectionMatrix(camera.combined);
	    //shapeRenderer.setProjectionMatrix(camera.combined);

	    world.render();
	    //shapeRenderer.setColor(0, 1, 0, 1);
	    //shapeRenderer.rect(x, y, width, height);
	    //shapeRenderer.circle(x, y, radius);
	    shapes.end();
	    //world.handleCollisions();
	    
	    
	    /** Simple technique to detect user input on the touch screen **/
	    if(Gdx.input.isTouched()) {
	        Vector3 touchPos = new Vector3();
	        touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
	        camera.unproject(touchPos);
	        
	        //Log.d("input", "screen touched");
	        
	        // add some more blobs
	        //GameFactory.populateWorldWithBlobs(world, numBlobs, renderConfig);
	        
	        /** center the smiley face on the touch (x,y) coordinates **/
	        //rect_smiley.x = touchPos.x - 128 / 2;
	        //rect_smiley.y = touchPos.y - 128 / 2;
	    }
	}

    @Override
    public void resize(int arg0, int arg1) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
        
    }

}
