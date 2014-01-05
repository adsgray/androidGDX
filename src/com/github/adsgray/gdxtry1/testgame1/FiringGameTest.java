package com.github.adsgray.gdxtry1.testgame1;

/**
 * James calls this game "Bomb-Bomb"
 * "Because the triangle is angry and wants to shoot bombs"
 */
import android.content.Context;
import android.util.Log;

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

public class FiringGameTest implements Game {

    DragAndFlingDirectionListener input;
    WorldIF world;
    Renderer renderer;
    protected int numEnemies = 6; // TODO: make this go up as your score goes
                                  // up?
    FiringBlobDecorator defender;
    protected int score;
    ScoreTextDisplay scoreDisplay;
    protected int bonusDropperChance = 5;
    Context context;
    GameCommand incShield;
    GameCommand incHitPoints;
    GameCommand incScore;

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
                Vibrate.setRealInstance(context);
            } else {
                GameSound.setFakeInstance();
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
            // Log.d("testgame1",
            // String.format("Enemy destroyed for %d! %d total", points,
            // score));
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
                tearDownGame();
                setupGame();
            } else {
                // Log.d("testgame1",
                // String.format("Defender hit for %d! %d left", hitPoints,
                // hitPointsLeft));
            }
        }
    }

    public FiringGameTest(DragAndFlingDirectionListener dl, WorldIF w,
            Renderer r, Context context) {
        input = dl;
        world = w;
        renderer = r;
        this.context = context;
        CreateEnemyTrigger.createInstance(new EnemyCreator());
        BonusFactory.createInstance(this, world, renderer);
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
        b.setLifeTime(1000000);
        world.addMissileToWorld(b);
        return b;
    }

    // if we're within 500 points of the next boss then there is a chance
    // to get a bonus dropper
    protected Boolean createBonusDropper() {
        return (scoreForNextBoss - score <= GameConfig.get().bonusDropperBossPointDiff() && TargetUtils.rnd.nextInt() < bonusDropperChance);
    }

    private void createEnemies() {
        int numToAdd = numEnemies - world.getNumTargets();

        if (numToAdd <= 0) return;

        if (score >= scoreForNextBoss) {
            scoreForNextBoss += bossScoreIncrement;
            EnemyIF boss = (EnemyIF) EnemyFactory.bossEnemy(world, renderer,
                    defender.getPosition());
            numToAdd -= boss.getWeight();
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
        GameSound.get().playSoundId(SoundId.welcome);
        // have to do this first because defender executes commands
        // on the scoreboard when shield number is initialized:
        scoreDisplay = createScoreDisplay();
        EnemyFactory.flashMessage(world, renderer, "Good Luck!", 100);

        BlobIF defender = createDefender();

        // TODO: put these into gameconfig. Easy starts with more shields and
        // more hitpoints?
        incShield.execute(GameConfig.get().initialShields());
        incHitPoints.execute(GameConfig.get().initialHitPoints());

        input.registerDraggable((Draggable) defender);
        input.registerFlingable((Flingable) defender);
        scoreDisplay.setLastScore(score);
        score = 0;
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
        tearDownGame();
    }

    @Override
    public GameCommand getSoundToggle() {
        return new ToggleSound();
    }

    @Override
    public GameCommand getDifficultySetter() {
        return new DifficultySetter();
    }

    @Override
    public GameState getState() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void restoreState(GameState state) {
        // TODO Auto-generated method stub
    }
}
