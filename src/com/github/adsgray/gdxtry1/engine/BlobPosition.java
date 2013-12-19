package com.github.adsgray.gdxtry1.engine;

/** x,y position
 * 
 * @author andrew
 *
 */

public class BlobPosition implements PositionIF {
    private Integer x = 0;
    private Integer y = 0;
    
    public BlobPosition(Integer xin, Integer yin) {
        x = xin;
        y = yin;
    }
    
    public Integer getX() { return x; }
    public Integer getY() { return y; }
    
    public Integer setX(Integer xin) { x = xin; return x; }
    public Integer setY(Integer yin) { y = yin; return y; }

    public PositionIF updateByVelocity(VelocityIF vel) {
        this.x = vel.deltaX(this.x);
        this.y = vel.deltaY(this.y);
        return this;
    }
}
