import processing.core.*;

public class Spring {
	/* A Spring object represents the physical link between any two particles
	 * It stores a reference to the two particles as well as the Processing sketch
	 * on which the lines will be drawn
	 */
	Particle p1;
	Particle p2;
	PApplet sketch;
	
	public Spring(Particle _p1, Particle _p2, PApplet _sketch) {
		p1 = _p1;
		p2 = _p2;
		sketch = _sketch;
	}
	
	public void display() {
		sketch.stroke(1);
		sketch.strokeWeight((float) 1);
		sketch.line(p1.x, p1.y, p2.x, p2.y);
	}

}
