import processing.core.*;
import toxi.physics2d.*;
import toxi.geom.*;


public class World extends PApplet {

	Body b;
	VerletPhysics2D physics;

	
	public void settings() {
		size(600, 600);
	}
	
	public void setup() {
		physics = new VerletPhysics2D();
		physics.setWorldBounds(new Rect(0, 0, width, height));
		//physics.addBehavior(new GravityBehavior(new Vec2D((float) 0, (float) 0.05)));
		b = new Body(width/2, (float) 20, (float) 200, 50, 50, this, physics);
		
		
	}
	
	public void draw() {
		background(255);
		physics.update();
		
		b.display();
	}
	
	public void mousePressed( ) {
		b.contract();
	}
	
	public void mouseReleased() {
		b.relax();
	}
	
	public static void main(String[] args) {
		String[] processingArgs = {"MySketch"};
		World mySketch = new World();
		PApplet.runSketch(processingArgs, mySketch);
	}
	
}
