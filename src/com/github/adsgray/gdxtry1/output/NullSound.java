package com.github.adsgray.gdxtry1.output;

public class NullSound implements SoundIF {
    @Override public int load(int resid) { return 0; }
    @Override public void play(int soundid) { }
    @Override public void play(Integer soundid) { }
}
