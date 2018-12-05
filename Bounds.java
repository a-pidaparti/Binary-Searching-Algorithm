//written by dovol002 and pidap008

import java.awt.*;

public class Bounds {

	// The extrema of our rectangle
	private Vec2 min;
	private Vec2 max;

	// Getters, no setters. Use extend instead.
	public Vec2 getMin(){ return min; }
	public Vec2 getMax(){ return max; }

	// Default min and max to null, so that they are
	// initialized on the first call to extend.
	public Bounds(){
		min = null;
		max = null;
	}

	// TODO
	// Check if a point is outside the bounds of the box
	// Ignore z axis for this function
	public boolean isOutside(double x, double y){
		if(x > max.x || x < min.x || y > max.y || y < min.y){
			return true;
		}
		return false;
	}

	public boolean isOutsideCircle(double x, double y){
		double avgX = (min.x + max.x)/2 - x;
		double avgY = (min.y + max.y)/2 - y;
		double distance = Math.sqrt(avgX * avgX + avgY * avgY);
		boolean isOutside = distance > Math.abs(min.x - max.x)/2;
		return isOutside;
	}

	// TODO
	// Extend the size of the box to include a new point
	public void extend(double x, double y){
		// If we haven't set min or max yet, do so now.
		if(min == null || max == null){
			min = new Vec2(x,y);
			max = new Vec2(x,y);
			return;
		}
		if(x > max.x){
			max.x = x;
		}

		if(x < min.x){
			min.x = x;
		}

		if(y > max.y){
			max.y = y;
		}

		if(y < min.y){
			min.y = y;
		}

	}

	// TODO
	// Returns the distance from the box surface to a point
	// Return 0 if the point is inside the box!
	public double exteriorDistance(double x, double y){
		double xDistance = 0;
		double yDistance = 0;
		if(x > max.x){	//if x is greater than the max x
			xDistance = x - max.x;
		}
		 else if(x < min.x){	// if x is less than the min x
			xDistance = min.x - x;
		}

		if(y > max.y){	// if y is greater than the max y
			yDistance = y - max.y;
		}

		else if(y < min.y){	// if y is less than the min y
			yDistance = min.y - y;
		}
		return Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));	//pythagorean theorem
	}

	// TODO
	// Extend the size of the box to include a new bounds
	public void extend(Bounds b){
		this.extend(b.getMax().x, b.getMax().y);
		this.extend(b.getMin().x, b.getMin().y);
	}

	// Draw a black opaque rectangle
	public void paint(Graphics2D g){
		g.setColor(Color.black);
		g.drawRect((int)min.x, (int)min.y, (int)(max.x-min.x), (int)(max.y-min.y));
	}

	public String toString(){
		String outString = "min: " + min + ", max: " + max;
		return outString;
	}

} // end class Bounds
