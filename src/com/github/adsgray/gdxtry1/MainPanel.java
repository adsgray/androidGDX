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

public class MainPanel implements ApplicationListener {

    private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 800;
	private static final int numBlobs = 25;

	private WorldIF world;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private ShapeRenderer shapes;
	
	@Override
	public void create() {
	    world = GameFactory.defaultWorld();
	    GameFactory.populateWorldWithBlobs(world, numBlobs);

		camera = new OrthographicCamera();
		camera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT); // the camera is like a window into our game world
		batch = new SpriteBatch();
		shapes = new ShapeRenderer();
		
		/** Instantiate smiley face image **/
		/*
		smileyface = new Texture(Gdx.files.internal("128px-Yellow_Happy.jpg"));
		rect_smiley = new Rectangle();
		rect_smiley.width = 128;										// "Yellow_Happy.jpg" is 128x128 pixels
		rect_smiley.height = 128;
		rect_smiley.x = (CAMERA_WIDTH / 2) - (rect_smiley.width / 2);	// center the image horizontally
		rect_smiley.y = (CAMERA_HEIGHT / 2) - (rect_smiley.height / 2);	// center the image vertically
		*/

		
		/** Instantiate hello world image **/
		/*
		helloworld = new Texture(Gdx.files.internal("helloworld.png"));
		rect_hello = new Rectangle();
		rect_hello.width = 256;											// "helloworld.png" is 256x64 pixels
		rect_hello.height = 64;
		rect_hello.x = (CAMERA_WIDTH / 2) - (rect_hello.width / 2);		// center the image horizontally
		rect_hello.y = rect_smiley.y + 200;								// place helloworld just above the smiley face
		*/
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
	    world.renderWithShapeRenderer(shapes);
	    //shapeRenderer.setColor(0, 1, 0, 1);
	    //shapeRenderer.rect(x, y, width, height);
	    //shapeRenderer.circle(x, y, radius);
	    shapes.end();
	    
	    
	    /** Simple technique to detect user input on the touch screen **/
	    if(Gdx.input.isTouched()) {
	        Vector3 touchPos = new Vector3();
	        touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
	        camera.unproject(touchPos);
	        
	        Log.d("input", "screen touched");
	        
	        // add some more blobs
	        GameFactory.populateWorldWithBlobs(world, numBlobs);
	        
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
