import toxi.physics2d.*;
import processing.core.PApplet;
import java.util.*;

public class Ring {
	boolean addParticles = false;
	float x;
	float y;
	float radius;
	int n_arches;
	ArrayList<Particle> particles;
	ArrayList<Spring> springs;
	PApplet sketch;
	float strength;
	VerletPhysics2D physics;
	
	public Ring(float _x, float _y, float _radius, int _n_arches, PApplet _sketch, VerletPhysics2D _physics) {
		x = _x;
		y = _y;
		radius = _radius;
		n_arches = _n_arches;
		sketch = _sketch;
		particles = new ArrayList<Particle>();
		springs = new ArrayList<Spring>();
		strength = (float) 0.1;
		physics = _physics;
		
		
		if (radius > 0) {
			// first calculate distance between arches
			float arch_d = (radius*2) / (n_arches-1); 

			// add one particle for every arch
			for (int i = 0; i < n_arches; i++) {
				Particle p = new Particle(x-radius+(arch_d*i), y, sketch);
				particles.add(p);
				
				if (addParticles) {
					physics.addParticle(p);
				}
				
				if ( i > 0) {
					Particle prev = particles.get(i-1);
					VerletSpring2D spring = new VerletSpring2D(p, prev, arch_d, strength);
					physics.addSpring(spring);
				}
			}
		} else {
			Particle p = new Particle(x, y, sketch);
			particles.add(p);
			
			if (addParticles) {
				physics.addParticle(p);
			}
		}	
		
	}
	
	public void display() {
		
		int list_size = particles.size();
		
		// draw first particle
		Particle p = particles.get(0);
		p.display();
		
		// draw rest of particles and lines between them
		for (int i = 1; i < list_size; i++) {
			// draw particle
			p = particles.get(i);
			p.display();
			
			// draw line between particle and previously drawn particle
			Particle prev = particles.get(i-1);
			sketch.stroke(1);
			sketch.strokeWeight((float) 1);
			sketch.line(p.x, p.y, prev.x,prev.y);
		}
	}
	
	// this function assumes the given ring has the same number of particles
	public void displayRingConnections(Ring r) {
		
		Particle p1;			// particle in given ring
		Particle p2;			// particle in this ring
		
		sketch.stroke(1);
		sketch.strokeWeight((float) 1);
		
		
		
		if (r.particles.size() == 1) {
			// check if given ring is the tip of the body
			p1 = r.particles.get(0);
			
			
			for (int i = 0; i < n_arches; i++) {
				p2 = particles.get(i);
				sketch.line(p1.x, p1.y, p2.x, p2.y);
			}
		} else if (r.n_arches != n_arches) {
			/* given that the given ring is not the head
			 * check if the number of particles in the given r
			 * ing is different than the one in this ring
			 * if that's the case, return an error
			 */
			PApplet.print("Number of particles in given ring has to be one or the same as caller");
		} else {
			
			for (int i = 0; i < n_arches; i++) {
				p1 = r.particles.get(i);
				p2 = particles.get(i);
				sketch.line(p1.x, p1.y, p2.x, p2.y);
			}
		}
		
		
	}
	
	public void addRingConnections(Ring r) {
		Particle p1;			// particle in given ring
		Particle p2;			// particle in this ring
		
		
		if (r.particles.size() == 1) {
			// check if given ring is the tip of the body
			p1 = r.particles.get(0);
			
			
			for (int i = 0; i < n_arches; i++) {
				p2 = particles.get(i);
				float length = (float) Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
				VerletSpring2D spring = new VerletSpring2D(p1, p2, length, strength);
				physics.addSpring(spring);
			}
		} else if (r.n_arches != n_arches) {
			/* given that the given ring is not the head
			 * check if the number of particles in the given r
			 * ing is different than the one in this ring
			 * if that's the case, return an error
			 */
			PApplet.print("Number of particles in given ring has to be one or the same as caller");
		} else {
			
			for (int i = 0; i < n_arches; i++) {
				p1 = r.particles.get(i);
				p2 = particles.get(i);
				float length = (float) Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
				VerletSpring2D spring = new VerletSpring2D(p1, p2, length, strength);
				physics.addSpring(spring);
			}
		}
	}
	
	public void contract() {
		PApplet.print("radius before is:", radius, "\n");
		float c_radius = (float) (radius / (float) 2);					  // contracted radius
		PApplet.print("Contracted radius is: ", c_radius, "\n");
		float c_arch_d = (c_radius*2) / (n_arches-1);   // contracted arch_d
		for (int i= 0; i < particles.size(); i++) {
			Particle p = particles.get(i);
			p.lock();
			p.x = x-c_radius+(c_arch_d*i);
		}
	}
	
	public void relax() {
		// return ring to original values
		float r_radius = (float) radius*((float) 1);		// relaxed radius
		PApplet.print("Relaxed radius is:", r_radius, "\n");
		float r_arch_d = (float) (r_radius*2) / (n_arches-1);     // relaxed arch_d
		for (int i= 0; i < particles.size(); i++) {
			Particle p = particles.get(i);
			p.x = x-r_radius+(r_arch_d*i);
			p.unlock();
		}
	}
}
