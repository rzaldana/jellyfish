import processing.core.*;
import toxi.physics2d.*;
import toxi.geom.*;


public class Particle extends VerletParticle2D{

					//Particle class. Particles form the components of the lattice
	PApplet sketch; // Processing sketch where particle will be drawn
	
	public Particle(float _x, float _y, PApplet _sketch) {
		super(new Vec2D(_x, _y));
		sketch = _sketch;
		
	}
	
	public void display() {
		sketch.stroke(0);
		sketch.fill(0);
		sketch.ellipse(x, y, 1, 1);
	}
}
