package com.github.adsgray.gdxtry1.engine;

/** this blob has a mass, size, position, velocity and can be given
 * impulses.
 * @author andrew
 *
 */
public class Blob {
    
    private Integer mass;
    private PositionIF position;
    private VelocityIF velocity;
    private ExtentIF extent;
    private AccelIF acceleration;
    
    public Blob(Integer massin, PositionIF posin, VelocityIF velin, AccelIF accel) {
        mass = massin;
        position = posin;
        velocity = velin;
        acceleration = accel;
    }

    public PositionIF getPosition() { return position; }
    public Integer getMass() { return mass; }
    
    /* called by outside controller to tell this Blob
     * to advance one time unit.
     */
    public void tick() {
        position.updateByVelocity(velocity);
        // update velocity with its accelleration
        acceleration.accellerate(velocity);
    }
        
    /* return true if we overlap with "with" 
     * delegate to our "extent" which knows our shape.
     * */
    public boolean intersects(Blob with) {
        return extent.intersects(position, with.getPosition());
    }
    
    public Blob collision(Blob with) {
        /* do physics to change properties of "this" based on the
         * Blob "with" that we are colliding with. Note that that other
         * Blob will have to call .collision(us) to change its properties.
         */
        if (this.intersects(with)) {
            
        }
        return this;
    }
    

}
