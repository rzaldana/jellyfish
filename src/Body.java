import processing.core.*;
import java.util.*;
import toxi.physics2d.*;


public class Body {
	/* The body is composed of a list of Ring objects and the springs connecting
	 * the rings to each other.
	 * the body is shaped like a semi-circle
	 */
	
	ArrayList<Ring> rings; 	     // List of rings that compose the body
	ArrayList<Spring> springs;	 // the springs connecting each ring to the ones adjacent to it
	float x;				     // x-coordinate of the tip of the body
	float y;					 // y-coordinate of the tip of the body
	float radius; 				 // radius of bottom ring
	int n_rings;				 // number of rings in body
	int n_arches;				 // number of vertical arches connecting the rings
	PApplet sketch;				 // Processing sketch where body will be drawn
	VerletPhysics2D physics;
	
	public Body(float _x, float _y, float _radius, int _n_rings, int _n_arches, PApplet _sketch, VerletPhysics2D _physics) {
		// initialize variables
		x = _x;
		y = _y;
		radius = (float) _radius;
		n_rings = _n_rings;
		n_arches = _n_arches;
		sketch = _sketch;
		rings = new ArrayList<Ring>();
		physics = _physics;
		
		
		/* calculate the height of each ring
		 * we're assuming all rings have the same height and the body
		 * is a semi-circle
		 */
		
		float ringHeight = radius/n_rings;
		
		for (int i = 0; i < n_rings; i++) {
			/* calculate y-coordinate of ring, yi.
			 * All particles in any one ring have the same y-coordinate
			 */
			float yi = ringHeight*i;
			
			/* calculate the radius of the ring, ri.
			 * used pythagorean theorem to solve for the radius
			 * so that all rings fit within a semi-circle
			 */
			float ri = (float) Math.sqrt(Math.pow(radius, 2) - Math.pow(radius - yi, 2));
			
			Ring r = new Ring(x, y+yi, ri, n_arches, sketch, physics);
			rings.add(r);
			
			if (i > 0) {
				r.addRingConnections(rings.get(i-1));
			}
			
		}
	}
	
	public void display() {
		
		// Draw the first ring
		Ring r = rings.get(0);
		r.display();
		
		// Draw the rest of the rings and the lines between them
		for (int i = 1; i < n_rings; i++) {
			r = rings.get(i);
			
			// display the ring
			r.display();
			
			// display the lines between this ring and the one drawn before it
			sketch.stroke(0);
			sketch.strokeWeight((float) 1);
			Ring r_prev = rings.get(i-1);
			r.displayRingConnections(r_prev);
		}
		
	}
	
	public void contract() {
		Ring r = rings.get(rings.size()-10);
		r.contract();
	}
	
	public void relax() {
		Ring r = rings.get(rings.size()-10);
		r.relax();
	}
	
}
