//written by dovol002, used by pidap008

public class Vec2 {

	// Getters and setters too verbose for such a simple class.
	// Just going to do public variables.
	public double x;
	public double y;

	// Default
	public Vec2(){
		x = 0;
		y = 0;
	}

	// Copy
	public Vec2(Vec2 other){
		x = other.x;
		y = other.y;
	}

	// With vals
	public Vec2(double x_, double y_){
		x = x_;
		y = y_;
	}

	// Clamps the value to a given min and max
	public void clamp(Vec2 min, Vec2 max){
		if( x > max.x ){ x = max.x; }
		if( y > max.y ){ y = max.y; }
		if( x < min.x ){ x = min.x; }
		if( y < min.y ){ y = min.y; }
	}

	// Dot product between self and other vector
	public double dot(Vec2 other){
		return (other.x*x + other.y*y);
	}

	// Distance between self and other vector
	public double distance(Vec2 other){
		Vec2 diff = new Vec2(other.x-x, other.y-y);
		return Math.sqrt(diff.dot(diff));
	}

	// Return length of the vector
	public double length(){
		return Math.sqrt(this.dot(this));
	}

	// Vector as a string
	public String toString(){
		return x + ", " + y;
	}

} // end class Vec2
