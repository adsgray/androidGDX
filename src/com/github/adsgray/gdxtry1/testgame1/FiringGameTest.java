package com.github.adsgray.gdxtry1.testgame1;

/**
 * James calls this game "Bomb-Bomb"
 * "Because the triangle is angry and wants to shoot bombs"
 */
import android.content.Context;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.github.adsgray.gdxtry1.engine.WorldIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF.BlobTrigger;
import com.github.adsgray.gdxtry1.engine.blob.BlobPath;
import com.github.adsgray.gdxtry1.engine.blob.TextBlobIF;
import com.github.adsgray.gdxtry1.engine.blob.decorator.ShowExtentDecorator;
import com.github.adsgray.gdxtry1.engine.input.DragAndFlingDirectionListener;
import com.github.adsgray.gdxtry1.engine.input.Draggable;
import com.github.adsgray.gdxtry1.engine.input.Flingable;
import com.github.adsgray.gdxtry1.engine.input.KeyListener;
import com.github.adsgray.gdxtry1.engine.input.Tappable;
import com.github.adsgray.gdxtry1.engine.output.Renderer;
import com.github.adsgray.gdxtry1.engine.output.SoundIF;
import com.github.adsgray.gdxtry1.engine.output.Renderer.RectConfig;
import com.github.adsgray.gdxtry1.engine.output.Renderer.TextConfig;
import com.github.adsgray.gdxtry1.engine.output.Renderer.TriangleConfig;
import com.github.adsgray.gdxtry1.engine.position.BlobPosition;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.engine.util.BlobFactory;
import com.github.adsgray.gdxtry1.engine.util.Game;
import com.github.adsgray.gdxtry1.engine.util.GameCommand;
import com.github.adsgray.gdxtry1.engine.util.GameFactory;
import com.github.adsgray.gdxtry1.engine.util.PathFactory;
import com.github.adsgray.gdxtry1.engine.util.StateIF;
import com.github.adsgray.gdxtry1.testgame1.GameSound.SoundId;
import com.github.adsgray.gdxtry1.testgame1.TargetUtils.Difficulty;
import com.github.adsgray.gdxtry1.testgame1.blobs.DamagableIF;
import com.github.adsgray.gdxtry1.testgame1.blobs.DamagerIF;
import com.github.adsgray.gdxtry1.testgame1.blobs.DefaultEnemy;
import com.github.adsgray.gdxtry1.testgame1.blobs.EnemyFactory;
import com.github.adsgray.gdxtry1.testgame1.blobs.EnemyIF;
import com.github.adsgray.gdxtry1.testgame1.blobs.FiringBlobDecorator;
import com.github.adsgray.gdxtry1.testgame1.blobs.ScoreTextDisplay;
import com.github.adsgray.gdxtry1.testgame1.config.GameConfig;
import com.github.adsgray.gdxtry1.testgame1.config.GameConfigIF;
import com.github.adsgray.gdxtry1.testgame1.config.SavedGame;

public class FiringGameTest implements Game, KeyListener {

    DragAndFlingDirectionListener input;
    WorldIF world;
    Renderer renderer;
    protected Boolean stopped = false;
    protected int numEnemies = 6; 
    protected int enemyTrackingId;
    FiringBlobDecorator defender;
    protected int score;
    ScoreTextDisplay scoreDisplay;
    protected int bonusDropperChance = 5;
    protected int difficultyLevel = 1;
    Context context;
    protected GameCommand incShield;
    protected GameCommand incHitPoints;
    protected GameCommand incScore;
    protected GameCommand gameFinished;

    // config parameters singleton class. easy, normal, insane.
    // TODO: encapsulate this crap somewhere:
    protected int bossScoreIncrement = 1500; // you'll meet a boss every 1500
                                             // points
    protected int scoreForNextBoss = bossScoreIncrement;

    public class ToggleSound implements GameCommand {
        @Override
        public void execute(int onOrOff) {
            if (onOrOff == 1) {
                GameSound.setRealInstance(context);
            } else {
                GameSound.setFakeInstance();
            }
        }
    }
    
    public class ToggleVibrate implements GameCommand {

        @Override
        public void execute(int onOrOff) {
             if (onOrOff == 1) {
                Vibrate.setRealInstance(context);
            } else {
                Vibrate.setFakeInstance();
            }           
        }
        
    }

    protected void doSettingsKnobs() {
        numEnemies = GameConfig.get().numEnemies();
        scoreForNextBoss = GameConfig.get().bossScoreIncrement();
    }

    public class DifficultySetter implements GameCommand {
        @Override
        public void execute(int arg) {

            // set game config requires BonusFactory
            BonusFactory.createInstance(FiringGameTest.this, world, renderer);
            
            difficultyLevel = arg;

            switch (arg) {
            case 0:
                GameConfig.set(GameConfigIF.Difficulty.easy);
                break;
            case 1:
                GameConfig.set(GameConfigIF.Difficulty.normal);
                break;
            case 2:
                GameConfig.set(GameConfigIF.Difficulty.insane);
                break;
            }

            doSettingsKnobs();
        }
    }

    // todo: rename EnemyKilled ?
    public class EnemyCreator implements GameCommand {
        @Override
        public void execute(int points) {
            createEnemies();
            incScore.execute(points);
        }
    }

    public class IncShield implements GameCommand {
        @Override
        public void execute(int arg) {
            defender.incrementNumShields(arg);
            scoreDisplay.incNumShields(arg);
        }
    }

    public class IncHitPoints implements GameCommand {
        @Override
        public void execute(int arg) {
            defender.incHitPoints(arg);
            scoreDisplay.setHitPoints(defender.getHitPoints());
        }
    }

    public class IncScore implements GameCommand {
        @Override
        public void execute(int arg) {
            score += arg;
            scoreDisplay.setScore(score);
        }

    }

    public class DamageDefender implements GameCommand {
        @Override
        public void execute(int hitPoints) {
            if (!GameConfig.get().damageDefender())
                return;

            incHitPoints.execute(-hitPoints);
            int hitPointsLeft = defender.getHitPoints();
            
            if (hitPointsLeft <= 0) {
                // Log.d("testgame1",
                // String.format("Defender destroyed! Final score: %d", score));
                // TODO: call a command supplied by outer creator of Game to
                // save high score or something
                gameFinished.execute(score);
            } else {
                // Log.d("testgame1",
                // String.format("Defender hit for %d! %d left", hitPoints,
                // hitPointsLeft));
            }
        }
    }

    public FiringGameTest(DragAndFlingDirectionListener dl, WorldIF w,
            Renderer r, Context context, GameCommand gameFinished) {
        input = dl;
        world = w;
        renderer = r;
        enemyTrackingId = world.createTrackableBlobList();
        this.context = context;
        this.gameFinished = gameFinished;
        incShield = new IncShield();
        incHitPoints = new IncHitPoints();
        incScore = new IncScore();
    }

    private BlobIF createDefender() {
        PositionIF p = new BlobPosition(TargetUtils.rnd.nextInt(GameFactory.BOUNDS_X - 500) + 200, 100);
        TriangleConfig rc = renderer.new TriangleConfig(Color.RED, 80);
        BlobIF b = BlobFactory.triangleBlob(p, PathFactory.stationary(), rc, renderer);
        // b = new ShowExtentDecorator(b);
        Log.d("testgame1", "creating firingblobdecorator");
        b = new FiringBlobDecorator(b, new EnemyCreator(), new IncShield());
        defender = (FiringBlobDecorator) b;
        b.registerCollisionTrigger(new DefenderCollisionTrigger(new DamageDefender()));
        // TODO: need immortal blobs!
        b.setImmortal(true);
        world.addMissileToWorld(b);
        return b;
    }

    // if we're within 500 points of the next boss then there is a chance
    // to get a bonus dropper
    protected Boolean createBonusDropper() {
        return (scoreForNextBoss - score <= GameConfig.get().bonusDropperBossPointDiff() && TargetUtils.rnd.nextInt() < bonusDropperChance);
    }

    private void createEnemies() {
        int numToAdd = numEnemies - world.trackableBlobListCount(enemyTrackingId);

        if (numToAdd <= 0) return;

        if (score >= scoreForNextBoss) {
            scoreForNextBoss += bossScoreIncrement;
            EnemyIF boss = (EnemyIF) EnemyFactory.bossEnemy(world, renderer,
                    defender.getPosition());
            numToAdd -= boss.getWeight();
            world.addBlobToTrackableBlobList(enemyTrackingId, (BlobIF)boss); 
            EnemyFactory.flashMessage(world, renderer, "Here's the Boss!", 60);
            GameSound.get().playSoundId(SoundId.enemyCreated);
        }

        if (numToAdd <= 0) return;

        if (createBonusDropper()) {
            EnemyIF bonusdropper = (EnemyIF) EnemyFactory.bonusDropper(world, renderer);
            numToAdd -= bonusdropper.getWeight();
            EnemyFactory.flashMessage(world, renderer, "Bonus Dropper!", 30);
            GameSound.get().playSoundId(SoundId.bonusDropperAppear);
        }

        /*
         * while (numToAdd >= 3) { BlobIF cluster =
         * EnemyFactory.createThreeCluster(world, renderer); numToAdd -= 3; }
         */

        while (numToAdd > 0) {
            EnemyIF b = (EnemyIF) EnemyFactory.defaultEnemy(world, renderer);
            world.addBlobToTrackableBlobList(enemyTrackingId, (BlobIF)b); 
            GameSound.get().playSoundId(SoundId.enemyCreated);
            numToAdd -= b.getWeight();
        }
    }

    private ScoreTextDisplay createScoreDisplay() {
        // PositionIF p = new BlobPosition(10,50);
        PositionIF p = new BlobPosition(25, GameFactory.BOUNDS_Y - 50);
        // PositionIF p = new BlobPosition(500,500);
        BlobPath path = PathFactory.stationary();
        TextConfig rc = renderer.new TextConfig(Color.WHITE, 2.0f);
        ScoreTextDisplay t = new ScoreTextDisplay(p, path.vel, path.acc, renderer, rc);
        t.setWorld(world);
        t.setImmortal(true);
        world.addBlobToWorld(t);
        return t;
    }

    private void tearDownGame() {
        input.deregisterDraggable((Draggable) defender);
        input.deregisterFlingable((Flingable) defender);
        input.deregisterTappable((Tappable) defender);
        world.killAllBlobs();
    }

    private void setupGame() {
        GameSound.get().playSoundId(SoundId.welcome);
        // have to do this first because defender executes commands
        // on the scoreboard when shield number is initialized:
        scoreDisplay = createScoreDisplay();
        CreateEnemyTrigger.createInstance(new EnemyCreator());
        EnemyFactory.flashMessage(world, renderer, "Good Luck!", 100);

        defender = (FiringBlobDecorator)createDefender();

        input.registerDraggable((Draggable) defender);
        input.registerFlingable((Flingable) defender);
        input.registerTappable((Tappable)defender);
        
        // restore from saved state if present
        SavedGame savegame = SavedGame.get();
        if (savegame.getSavedGamePresent()) {
            SavedGame.GameState gs = savegame.getState();
            score = gs.score;
            incShield.execute(gs.shields);
            incHitPoints.execute(gs.hitPoints);
            GameConfig.get().setBossesKilled(gs.bossesKilled);
            (new DifficultySetter()).execute(gs.difficulty);
            defender.setPosition(new BlobPosition(gs.defenderPos));
            scoreForNextBoss = bossScoreIncrement * (gs.bossesKilled + 1);
        } else {
            score = 0;
            incShield.execute(GameConfig.get().initialShields());
            incHitPoints.execute(GameConfig.get().initialHitPoints());
        }

        //scoreDisplay.setLastScore(score);

        scoreDisplay.setScore(score);
        // scoreDisplay.setHitPoints(((DamagableIF)defender).getHitPoints()); //
        // TODO: test
        doSettingsKnobs();
        createEnemies();
    }

    @Override
    public int getFinalScore() {
        return score;
    }

    @Override
    public void init() {
    }

    @Override
    public void start() {
        setupGame();
    }

    @Override
    public void stop() {
        stopped = true;
        tearDownGame();
    }

    @Override
    public GameCommand getSoundToggle() {
        return new ToggleSound();
    }

    @Override
    public GameCommand getVibrateToggle() {
        return new ToggleVibrate();
    }

    @Override
    public GameCommand getDifficultySetter() {
        return new DifficultySetter();
    }

    @Override
    public void keyDown(int key) {
        if (key == Keys.BACK) {
            Log.d("trace", "BACK pressed");
            // save game state
        }
    }

    // Knows about SavedGame and GameState
    @Override 
    public void save() {
        // don't save the game if it is complete
        if (stopped) return;
        SavedGame savegame = SavedGame.get();
        SavedGame.GameState gs = new SavedGame.GameState();
        
        gs.hitPoints = defender.getHitPoints();
        gs.shields = defender.getShields();
        gs.bossesKilled = GameConfig.get().getNumBossesKilled();
        gs.difficulty = difficultyLevel;
        gs.score = score;
        gs.defenderPos = new BlobPosition(defender.getPosition());
        
        savegame.setGameState(gs).save();
    }

}
