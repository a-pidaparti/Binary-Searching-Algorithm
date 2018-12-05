//written by dovol002, used by pidap008

import java.awt.*;

public class Circle extends Shape {

	private Vec2 center;
	private double radius;
	private Bounds bounds;
	private boolean selected;

	// Getters and setters. For non-existent setters,
	// use the constructor.
	public double getRadius(){ return radius; }
	public Vec2 getCenter(){ return center; }
	public Bounds getBounds(){ return bounds; }
	public boolean getSelected(){ return selected; }
	public void setBounds( Bounds b ){ bounds = b; }
	public void setSelected( boolean b ){ selected = b; }

	// Constructor: init memeber variables and boundary.
	public Circle(Vec2 center_, double rad_ ){
		center = new Vec2(center_);
		radius = rad_;
		selected = false;

		// Compute our bounds
		Vec2 min = new Vec2(center.x-radius, center.y-radius);
		Vec2 max = new Vec2(center.x+radius, center.y+radius);
		bounds = new Bounds();
		bounds.extend(min.x,min.y);
		bounds.extend(max.x,max.y);
	}

	// Draw the Circle. Fill with red if selected is true, gray otherwise.
	public void paint(Graphics2D g){
		if( selected ){ g.setColor(Color.red); }
		else { g.setColor(Color.gray); }
		g.fillOval((int)(center.x-radius), (int)(center.y-radius), (int)(radius*2), (int)(radius*2)); // inside
		g.setColor(Color.black);
		g.drawOval((int)(center.x-radius), (int)(center.y-radius), (int)(radius*2), (int)(radius*2)); // outline
	}

	// If the selection point is inside the Circle, change color and return true!
	public boolean select(double x, double y){
		Vec2 vec = new Vec2(x-center.x,y-center.y);
		double len = vec.length();
		if( len < radius ){
			selected = !selected;
			return true;
		}
		return false;
	}

	// Returns distance from a point to the surface
	public double exteriorDistance(double x, double y){
		Vec2 vec = new Vec2(x-center.x,y-center.y);
		double dist = vec.length();
		dist -= radius;
		return Math.max(dist,0);
	}

	public String toString(){
		return "circle at " + center + ", rad = " + radius;
	}

} // end class Circle
