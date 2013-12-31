package com.github.adsgray.gdxtry1.game.testgame1;

/**
 * James calls this game "Bomb-Bomb"
 * "Because the triangle is angry and wants to shoot bombs"
 */
import android.util.Log;

import com.badlogic.gdx.graphics.Color;
import com.github.adsgray.gdxtry1.engine.WorldIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF.BlobTrigger;
import com.github.adsgray.gdxtry1.engine.blob.decorator.ShowExtentDecorator;
import com.github.adsgray.gdxtry1.engine.position.BlobPosition;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.game.BlobFactory;
import com.github.adsgray.gdxtry1.game.Game;
import com.github.adsgray.gdxtry1.game.GameFactory;
import com.github.adsgray.gdxtry1.game.PathFactory;
import com.github.adsgray.gdxtry1.game.testgame1.blobs.Damagable;
import com.github.adsgray.gdxtry1.game.testgame1.blobs.Damager;
import com.github.adsgray.gdxtry1.game.testgame1.blobs.EnemyDecorator;
import com.github.adsgray.gdxtry1.game.testgame1.blobs.EnemyFactory;
import com.github.adsgray.gdxtry1.game.testgame1.blobs.FiringBlobDecorator;
import com.github.adsgray.gdxtry1.input.DragAndFlingDirectionListener;
import com.github.adsgray.gdxtry1.input.Draggable;
import com.github.adsgray.gdxtry1.input.Flingable;
import com.github.adsgray.gdxtry1.output.Renderer;
import com.github.adsgray.gdxtry1.output.Renderer.RectConfig;
import com.github.adsgray.gdxtry1.output.Renderer.TriangleConfig;

public class FiringGameTest implements Game {

    DragAndFlingDirectionListener input;
    WorldIF world;
    Renderer renderer;
    static final int numEnemies = 8;
    FiringBlobDecorator defender;
    protected int score;

    protected int shieldScoreIncrement = 500;
    protected int scoreForNextShield = shieldScoreIncrement;

    public class EnemyCreator implements GameCommand {
        @Override 
        public void execute(int points) {
            FiringGameTest.this.createEnemies();
            score += points;
            
            if (score >= scoreForNextShield) {
                defender.incrementNumShields(1);
                scoreForNextShield += shieldScoreIncrement;
            }

            Log.d("testgame1", String.format("Enemy destroyed for %d! %d total", points, score));
        }
    }
    
    public class DamageDefender implements GameCommand {
        @Override 
        public void execute(int hitPoints) {
            int hitPointsLeft = ((Damagable)defender).decHitPoints(hitPoints);

            if (hitPointsLeft <= 0) {
                Log.d("testgame1", String.format("Defender destroyed! Final score: %d", score));
                tearDownGame();
                setupGame();
            } else {
                Log.d("testgame1", String.format("Defender hit for %d! %d left", hitPoints, hitPointsLeft));
            }
        }
    }

    public FiringGameTest(DragAndFlingDirectionListener dl, WorldIF w, Renderer r) {
        input = dl;
        world = w;
        renderer = r;
        setupGame();
    }
 
    private BlobIF createDefender() {
        PositionIF p = new BlobPosition(TargetUtils.rnd.nextInt(GameFactory.BOUNDS_X - 500) + 200,100);
        TriangleConfig rc = renderer.new TriangleConfig(Color.RED, 80);
        BlobIF b = BlobFactory.triangleBlob(p, PathFactory.stationary(), rc, renderer);
        //b = new ShowExtentDecorator(b);
        b = new FiringBlobDecorator(b, new EnemyCreator());
        defender = (FiringBlobDecorator)b;
        b.registerCollisionTrigger(new DefenderCollisionTrigger(new DamageDefender()));
        b.setLifeTime(1000000);
        world.addMissileToWorld(b);
        return b;
    }
   
    private void createEnemies() {
        int numToAdd = numEnemies - world.getNumTargets();
        if (numToAdd < 0) numToAdd = 0;
        
        /*
        while (numToAdd >= 3) {
            BlobIF cluster = EnemyFactory.createThreeCluster(world, renderer);
            numToAdd -= 3;
        }
        */

        while(numToAdd > 0) {
            BlobIF b = EnemyFactory.defaultEnemy(world, renderer);
            numToAdd -= 1;
        }
    }
    

    private void tearDownGame() {
        input.deregisterDraggable((Draggable) defender);
        input.deregisterFlingable((Flingable) defender);
        world.killAllBlobs();
    }

    private void setupGame() {
        BlobIF defender = createDefender();
        input.registerDraggable((Draggable) defender);
        input.registerFlingable((Flingable) defender);
        score = 0;
        createEnemies();
    }

    @Override public int getFinalScore() { return score; }
}
