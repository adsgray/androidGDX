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
import com.github.adsgray.gdxtry1.engine.blob.BlobPath;
import com.github.adsgray.gdxtry1.engine.blob.TextBlobIF;
import com.github.adsgray.gdxtry1.engine.blob.decorator.ShowExtentDecorator;
import com.github.adsgray.gdxtry1.engine.position.BlobPosition;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.game.BlobFactory;
import com.github.adsgray.gdxtry1.game.Game;
import com.github.adsgray.gdxtry1.game.GameFactory;
import com.github.adsgray.gdxtry1.game.PathFactory;
import com.github.adsgray.gdxtry1.game.testgame1.blobs.DamagableIF;
import com.github.adsgray.gdxtry1.game.testgame1.blobs.DamagerIF;
import com.github.adsgray.gdxtry1.game.testgame1.blobs.DefaultEnemy;
import com.github.adsgray.gdxtry1.game.testgame1.blobs.EnemyFactory;
import com.github.adsgray.gdxtry1.game.testgame1.blobs.EnemyIF;
import com.github.adsgray.gdxtry1.game.testgame1.blobs.FiringBlobDecorator;
import com.github.adsgray.gdxtry1.game.testgame1.blobs.ScoreTextDisplay;
import com.github.adsgray.gdxtry1.input.DragAndFlingDirectionListener;
import com.github.adsgray.gdxtry1.input.Draggable;
import com.github.adsgray.gdxtry1.input.Flingable;
import com.github.adsgray.gdxtry1.output.Renderer;
import com.github.adsgray.gdxtry1.output.Renderer.RectConfig;
import com.github.adsgray.gdxtry1.output.Renderer.TextConfig;
import com.github.adsgray.gdxtry1.output.Renderer.TriangleConfig;
import com.github.adsgray.gdxtry1.output.SoundIF;

public class FiringGameTest implements Game {

    DragAndFlingDirectionListener input;
    WorldIF world;
    Renderer renderer;
    static final int numEnemies = 6; // TODO: make this go up as your score goes up?
    FiringBlobDecorator defender;
    protected int score;
    ScoreTextDisplay scoreDisplay;
    protected int bonusDropperChance = 5;
    protected SoundIF sound;

    // TODO: encapsulate this crap somewhere:
    protected int shieldScoreIncrement = 500;
    protected int scoreForNextShield = shieldScoreIncrement;
    protected int bossScoreIncrement = 1500; // you'll meet a boss every 1500 points
    protected int scoreForNextBoss = bossScoreIncrement;

    public class EnemyCreator implements GameCommand {
        @Override 
        public void execute(int points) {
            FiringGameTest.this.createEnemies();
            score += points;
            scoreDisplay.incScore(points);
            
            if (score >= scoreForNextShield) {
                defender.incrementNumShields(1);
                scoreDisplay.incNumShields(1);
                EnemyFactory.flashMessage(world, renderer, "Shield Bonus!", 50);
                sound.bonusShieldReceive();
                scoreForNextShield += shieldScoreIncrement;
            }

            Log.d("testgame1", String.format("Enemy destroyed for %d! %d total", points, score));
        }
    }
    
    public class IncShield implements GameCommand {
        @Override public void execute(int arg) {
            scoreDisplay.incNumShields(arg);
        }
    }
    
    public class DamageDefender implements GameCommand {
        @Override 
        public void execute(int hitPoints) {
            int hitPointsLeft = ((DamagableIF)defender).decHitPoints(hitPoints);
            scoreDisplay.setHitPoints(hitPointsLeft);

            if (hitPointsLeft <= 0) {
                Log.d("testgame1", String.format("Defender destroyed! Final score: %d", score));
                tearDownGame();
                setupGame();
            } else {
                Log.d("testgame1", String.format("Defender hit for %d! %d left", hitPoints, hitPointsLeft));
            }
        }
    }

    public FiringGameTest(DragAndFlingDirectionListener dl, WorldIF w, Renderer r, SoundIF s) {
        input = dl;
        world = w;
        renderer = r;
        sound = s;
        setupGame();
    }
 
    private BlobIF createDefender() {
        PositionIF p = new BlobPosition(TargetUtils.rnd.nextInt(GameFactory.BOUNDS_X - 500) + 200,100);
        TriangleConfig rc = renderer.new TriangleConfig(Color.RED, 80);
        BlobIF b = BlobFactory.triangleBlob(p, PathFactory.stationary(), rc, renderer);
        //b = new ShowExtentDecorator(b);
        Log.d("testgame1", "creating firingblobdecorator");
        b = new FiringBlobDecorator(b, new EnemyCreator(), new IncShield());
        b.setSound(sound);
        defender = (FiringBlobDecorator)b;
        b.registerCollisionTrigger(new DefenderCollisionTrigger(new DamageDefender()));
        b.setLifeTime(1000000);
        world.addMissileToWorld(b);
        return b;
    }
   
    // if we're within 500 points of the next boss then there is a chance
    // to get a bonus dropper
    protected Boolean createBonusDropper() {
        return (scoreForNextBoss - score <= 500 && TargetUtils.rnd.nextInt() < bonusDropperChance);
    }

    private void createEnemies() {
        int numToAdd = numEnemies - world.getNumTargets();

        if (numToAdd <= 0) return;
        
        if (score >= scoreForNextBoss) {
            scoreForNextBoss += bossScoreIncrement;
            EnemyIF boss = (EnemyIF)EnemyFactory.bossEnemy(world, renderer, defender.getPosition());
            ((BlobIF)boss).setSound(sound);
            numToAdd -= boss.getWeight();
            EnemyFactory.flashMessage(world, renderer, "Here's the Boss!", 60);
            sound.enemycreated();
        }

        if (numToAdd <= 0) return;
        
        if (createBonusDropper()) {
            EnemyIF bonusdropper = (EnemyIF)EnemyFactory.bonusDropper(world, renderer);
            ((BlobIF)bonusdropper).setSound(sound);
            numToAdd -= bonusdropper.getWeight();
            EnemyFactory.flashMessage(world, renderer, "Bonus Dropper!", 30);
            sound.bonusDropperAppear();
        }
        
        /*
        while (numToAdd >= 3) {
            BlobIF cluster = EnemyFactory.createThreeCluster(world, renderer);
            numToAdd -= 3;
        }
        */

        while(numToAdd > 0) {
            EnemyIF b = (EnemyIF)EnemyFactory.defaultEnemy(world, renderer);
            ((BlobIF)b).setSound(sound);
            sound.enemycreated();
            numToAdd -= b.getWeight();
        }
    }
    

    private ScoreTextDisplay createScoreDisplay() {
        //PositionIF p = new BlobPosition(10,50);
        PositionIF p = new BlobPosition(25,GameFactory.BOUNDS_Y - 50);
        //PositionIF p = new BlobPosition(500,500);
        BlobPath path = PathFactory.stationary();
        TextConfig rc = renderer.new TextConfig(Color.WHITE, 2.0f);
        ScoreTextDisplay t = new ScoreTextDisplay(p, path.vel, path.acc, renderer, rc);
        t.setWorld(world);
        t.setLifeTime(1000000);
        world.addBlobToWorld(t);
        return t;
    }

    private void tearDownGame() {
        input.deregisterDraggable((Draggable) defender);
        input.deregisterFlingable((Flingable) defender);
        world.killAllBlobs();
    }

    private void setupGame() {
        sound.welcome();
        // have to do this first because defender executes commands
        // on the scoreboard when shield number is initialized:
        scoreDisplay = createScoreDisplay();
        EnemyFactory.flashMessage(world,  renderer, "Good Luck!", 100);

        BlobIF defender = createDefender();

        input.registerDraggable((Draggable) defender);
        input.registerFlingable((Flingable) defender);
        scoreDisplay.setLastScore(score);
        score = 0;
        scoreDisplay.setScore(score);
        createEnemies();
    }

    @Override public int getFinalScore() { return score; }
}
