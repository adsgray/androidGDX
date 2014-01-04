package com.github.adsgray.gdxtry1.testgame1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

import com.github.adsgray.gdxtry1.engine.util.GameCommand;

/*
 * To add a new bonus type:
 * 1. Add GameCommand as a protected field
 * 2. Add bonus type to Type enum
 * 3. Init GameCommand class from FiringGameTest in constructor
 * 4. Create protected function which returns a GameCommand that executes (3)
 * and makes a sound, flashes a message, etc.
 * 5. Add that to the bonusMap
 */
public class BonusFactory {

    protected GameCommand incShield;
    protected GameCommand incHitPoints;
    protected GameCommand incScore;
    
    protected enum Type {
        shield,
        hitPoints,
        score
    }

    protected EnumMap<Type, GameCommand> bonusMap;

    // http://stackoverflow.com/questions/1972392/java-pick-a-random-value-from-an-enum
    private static final List<Type> VALUES = Collections.unmodifiableList(Arrays.asList(Type.values()));
    private static final int SIZE = VALUES.size();
    protected Type randomBonusEnum() {
        return VALUES.get(TargetUtils.rnd.nextInt(SIZE));
    }

    
    public BonusFactory(FiringGameTest game) {
        incShield = game.new IncShield();
        incHitPoints = game.new IncHitPoints();
        incScore = game.new IncScore();
        
        bonusMap = new EnumMap<Type, GameCommand>(Type.class);
        bonusMap.put(Type.shield, shieldBonus());
        bonusMap.put(Type.hitPoints, hitPointBonus());
        bonusMap.put(Type.score, scoreBonus());
    }

    
    public GameCommand randomBonus() {
        return bonusMap.get(randomBonusEnum());
    }
    
    // TODO: put the number of shields/hitpoints/score that you get for
    // a bonus in GameConfig. Different configs can return either a fixed
    // number or a choice from a set of numbers (eg. points == 10,25,50)
    protected GameCommand shieldBonus() {
        return new GameCommand() {
            @Override public void execute(int num) {
                incShield.execute(num);
                // display flash message
                // play sound
            }
        };
    } 

    protected GameCommand hitPointBonus() {
        return new GameCommand() {
            @Override public void execute(int num) {
                incHitPoints.execute(num);
                // display flash message
                // play sound
            }
        };
    }
   
    protected GameCommand scoreBonus() {
        return new GameCommand() {
            @Override public void execute(int num) {
                incScore.execute(num);
                // display flash message
                // play sound
            }
        };
    }
    
    // singleton
    protected static BonusFactory instance;
    public static BonusFactory createInstance(FiringGameTest game) {
        return new BonusFactory(game);
    }
    public static BonusFactory get() { return instance; }
}
