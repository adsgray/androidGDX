package com.github.adsgray.gdxtry1;

/*
import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
*/


import android.content.Context;
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
import com.github.adsgray.gdxtry1.engine.input.DefaultDirectionListener;
import com.github.adsgray.gdxtry1.engine.input.DragAndFlingDirectionListener;
import com.github.adsgray.gdxtry1.engine.input.SimpleDirectionGestureDetector;
import com.github.adsgray.gdxtry1.engine.input.SimpleDirectionGestureDetector.DirectionListener;
import com.github.adsgray.gdxtry1.engine.output.Renderer;
import com.github.adsgray.gdxtry1.engine.output.SoundIF;
import com.github.adsgray.gdxtry1.engine.output.SoundPoolPlayer;
import com.github.adsgray.gdxtry1.game.Game;
import com.github.adsgray.gdxtry1.game.GameCommand;
import com.github.adsgray.gdxtry1.game.GameFactory;
import com.github.adsgray.gdxtry1.game.testgame1.FiringGameTest;

public class MainPanel implements ApplicationListener {

    private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 1422;
	private static final int numBlobs = 10;

	private WorldIF world;
	private Renderer renderConfig;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private ShapeRenderer shapes;
	Context context;
	
	public MainPanel(Context context) {
	    super();
	    this.context = context;
	}
	
	private void populateWorld() {
	    //GameFactory.populateWorldWithBlobs(world, numBlobs, renderConfig);
	    //GameFactory.populateWorldNonRandom(world, renderConfig);
	    //GameFactory.populateWorldNonRandomBlobSet(world, renderConfig);
	    //GameFactory.populateWorldLaunchUp(world, renderConfig);
	    //GameFactory.populateWorldOoze(world, renderConfig);
	    //GameFactory.populateWorldCollisionTest(world, renderConfig);
	    //GameFactory.populateWorldTestTriggers(world, renderConfig);
	    //GameFactory.populateWorldTestTriggersAgain(world, renderConfig);
	    //GameFactory.populateWorldGameTestOne(world, renderConfig);
	    //GameFactory.populateWorldTestOffsetPosition(world, renderConfig);
	    //GameFactory.populateWorldTestBumpAccel(world, renderConfig);
	    //GameFactory.populateWorldTestNewBlobSet(world, renderConfig);
	    //GameFactory.populateWorldTestTriangle(world,  renderConfig);
	    //GameFactory.populateWorldTestMultiplyPosition(world,  renderConfig);
	    GameFactory.populateWorldTestText(world, renderConfig);
	}

	// make a DirectionListener that can affect the world
	private class TestDirectionListener extends DefaultDirectionListener {
	    protected WorldIF world;
	    public TestDirectionListener(WorldIF world) {
	        this.world = world;
	    }
	        
	    @Override
	    public void onUp(DirectionListener.FlingInfo f) {
	        super.onUp(f);
	        // velocities are "backwards"
	        if (f.startY < 200 && f.velocityY < -800) {
	            populateWorld();
	        }
	    }

	    @Override
	    public void onDown(DirectionListener.FlingInfo f) {
	        Log.d("input", String.format("screen swiped DOWN start(%f,%f) vel(%f,%f)", f.startX, f.startY, f.velocityX, f.velocityY));
	        if (f.startY > 1000) {
	            world.killAllBlobs();
	        }
	    }
	}	


	@Override
	public void create() {
		shapes = new ShapeRenderer();
		batch = new SpriteBatch();

		Renderer.createRealInstance(shapes, batch);
	    renderConfig = Renderer.getRealInstance();
	    world = GameFactory.defaultWorld();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT); // the camera is like a window into our game world
		
		// Setup swipe/touch handling:
		// The SimpleDirectionGestureDetector processes events, mangles the coordinates
		// so that they're in relation to the camera, and fires the events in
		// the DirecitonListener

		//DirectionListener dl = new TestDirectionListener(world);
	    //populateWorld();

		DirectionListener dl = new DragAndFlingDirectionListener();
		Game game = new FiringGameTest((DragAndFlingDirectionListener)dl, world, renderConfig, context);
		GameCommand toggleSound = game.getSoundToggle(); // get sound toggler command
		toggleSound.execute(1); // enable sound
		game.start();
		Gdx.input.setInputProcessor(new SimpleDirectionGestureDetector(camera, dl));
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
	    //Gdx.gl.glClearColor(0.199f, 0.398f, 0.598f, 0.4f);	// OpenGL code to make the screen blue
	    Gdx.gl.glClearColor(0f, 0f, 0f, 0.4f);	
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);			// OpenGL code to clear the screen
	    camera.update();
	    world.tick();
	    batch.setProjectionMatrix(camera.combined);
	    shapes.setProjectionMatrix(camera.combined);
	    world.render();
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
