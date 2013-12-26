package com.github.adsgray.gdxtry1.engine;

public class OffsetPosition extends PositionDecorator {

    int x;
    int y;

    public OffsetPosition(PositionIF component, int x, int y) {
        super(component);
        this.x = x;
        this.y = y;
    }

    @Override public int getX() { return component.getX() + x; }
    @Override public int getY() { return component.getY() + y; }
    // don't set anything in component
    @Override public int setX(int x) { return x + this.x; }
    @Override public int setY(int y) { return y + this.y; }

    @Override public PositionIF updateByVelocity(VelocityIF vel) {
        // nobody uses the return value of this function...
        // don't do any updating as we're parasites of the component
        // position
        return this;
    }
}
