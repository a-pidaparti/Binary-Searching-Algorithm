//written by dovol002, used by pidap008

import java.util.*;
//
//
// To run the tests:
//
// Drop the AABBTest class into the project folder.
// Compile it with "javac AABBTreeTest.java".
// Run the tests with "java AABBTreeTest".
//
//
public class AABBTreeTest {
	public static void say( String s ){ System.out.println(s); }
	public static boolean near(double a, double b){ return ( Math.abs(a-b) < 1e-12 ); }

	//
	// Test the Bounds class
	//
	public static boolean testBounds(){

		boolean success = true;

		//
		// Test extend:
		//
		try {
			Bounds b = new Bounds();
			b.extend(0,-1);
			b.extend(1,2);
			Vec2 min = b.getMin();
			Vec2 max = b.getMax();
			if( !near(min.x,0) || !near(min.y,-1) || !near(max.x,1) || !near(max.y,2) ){
				say( "   **Failed Bounds::extend(double x, double y)" );
				success = false;
			}

		} catch (Exception e){ say( "   **Exception in Bounds::extend(double x, double y): "+e.getMessage() ); }

		//
		// Test bounds extend
		//
		try {
			Bounds b = new Bounds();
			b.extend(0,-1);
			b.extend(1,2);
			Bounds b2 = new Bounds();
			b2.extend(b);
			Vec2 min = b2.getMin();
			Vec2 max = b2.getMax();
			if( !near(min.x,0) || !near(min.y,-1) || !near(max.x,1) || !near(max.y,2) ){
				say( "   **Failed Bounds::extend(Bounds b)" );
				success = false;
			}

		} catch (Exception e){ say( "   **Exception in Bounds::extend(Bounds b): "+e.getMessage() ); }

		//
		// Test isOutside
		//
		try {
			Bounds b = new Bounds();
			b.extend(0,0);
			b.extend(1,1);
			// isOutside returns true if the point is outside
			boolean isIn = b.isOutside(0.5,0.5); // false
			boolean isOut = b.isOutside(-1,-1); // true
			if( isIn ){
				say( "   **Failed Bounds::isOutside(double x, double y) when (x,y) is inside" );
				success = false;
			}
			if( !isOut ){
				say( "   **Failed Bounds::isOutside(double x, double y) when (x,y) is outside" );
				success = false;
			}

		} catch (Exception e){ say( "   **Exception in Bounds::isOutside: "+e.getMessage() ); }

		//
		// Test exteriorDistance
		//
		try {
			Bounds b = new Bounds();
			b.extend(0,0);
			b.extend(1,1);
			double extDist = b.exteriorDistance(2,2);
			if( !near(extDist,Math.sqrt(2)) ){
				System.out.println(extDist);
				say( "   **Failed Bounds::exteriorDistance(double x, double y) for (2, 2)" );
				success = false;
			}
			extDist = b.exteriorDistance(0,-2);
			if( !near(extDist,2) ){
				say( "   **Failed Bounds::exteriorDistance(double x, double y) for (0, -2)" );
				success = false;
			}
			extDist = b.exteriorDistance(0.5,0.5);
			if( !near(extDist,0) ){
				say( "   **Failed Bounds::exteriorDistance(double x, double y) for (0.5, 0.5)" );
				success = false;
			}
		} catch (Exception e){ say( "   **Exception in Bounds::exteriorDistance: "+e.getMessage() ); }

		return success;
	}

	//
	// Test node constructor
	//
	public static boolean testNodeConstructor(){

		boolean success = true;

		//
		// Test with one element in the stack. Check to make sure it is a leaf
		//
		try {
			Stack<Shape> stack = new Stack<Shape>();
			Circle c =  new Circle( new Vec2(0,0), 1 );
			stack.push(c);
			Node n = new Node(stack,0);

			if( n.getLeft() != null || n.getRight() != null ){
				say( "   **Failed Node constructor, should not have children when a leaf." );
				success = false;
			}
			if( n.getShape() == null ){
				say( "   **Failed Node constructor, should have a shape as a leaf node." );
				success = false;
			}

		} catch (Exception e){ say( "   **Exception in Node constructor with stack size=1: "+e.getMessage() ); }

		//
		// Test with two elements in the stack. Check to make sure it is not a leaf
		// and has two children.
		//
		try {
			Stack<Shape> stack = new Stack<Shape>();
			Circle c1 =  new Circle( new Vec2(0,0), 1 );
			Circle c2 =  new Circle( new Vec2(4,1), 1 );
			stack.push(c1);
			stack.push(c2);
			Node n = new Node(stack,0);

			if( n.getLeft() == null || n.getRight() == null ){
				say( "   **Failed Node constructor, should have both children when not a leaf." );
				success = false;
			}
			if( n.getShape() != null ){
				say( "   **Failed Node constructor, should not have a shape unless a leaf." );
				success = false;
			}

		} catch (Exception e){ say( "   **Exception in Node constructor with stack size=1: "+e.getMessage() ); }

		//
		// Check to make sure the bounds was set
		//
		try {
			Stack<Shape> stack = new Stack<Shape>();
			Circle c1 =  new Circle( new Vec2(0,0), 1 );
			Circle c2 =  new Circle( new Vec2(4,1), 1 );
			stack.push(c1);
			stack.push(c2);
			Node n = new Node(stack,0);
			Bounds b = n.getBounds();
			if( b == null ){
				say( "   **Failed Node constructor, Bounds not set." );
				success = false;
			}
			Vec2 bmin = b.getMin();
			Vec2 bmax = b.getMax();

			if( !near(bmin.x,-1) || !near(bmin.y,-1) ){
				say( "   **Failed Node constructor, Bounds min not correct." );
				success = false;
			}
			if( !near(bmax.x,5) || !near(bmax.y,2) ){
				say( "   **Failed Node constructor, Bounds max not correct." );
				success = false;
			}

		} catch (Exception e){ say( "   **Exception in Node constructor when testing bounds: "+e.getMessage() ); }

		return success;
	}

	//
	// Test splitStack method
	//
	public static boolean testSplitStack(){

		boolean success = true;

		//
		// Test along x axis, two shapes
		//
		try {
			// Centroid should be 2
			Stack<Shape> stack = new Stack<Shape>();
			Circle c1 =  new Circle( new Vec2(0,0), 1 );
			Circle c2 =  new Circle( new Vec2(4,0), 1 );
			stack.push(c1);
			stack.push(c2);

			// I should have made splitStack a static method...
			// The node constructor may have removed elements from the
			// stack, we we'll recreate it after the constructor.
			// Whether you did or did not clear out the original stack
			// doesn't matter.
			Node n = new Node(stack,0);
			if( stack.size() == 0 ){
				stack.push(c1);
				stack.push(c2);
			}
			Stack<Shape> l = new Stack<Shape>();
			Stack<Shape> r = new Stack<Shape>();
			n.splitStack(stack,0,l,r);

			// Make sure both left and right have an element
			if( l.size() != 1 ){
				say( "   **Failed splitStack, left not size 1." );
				success = false;
			}
			if( r.size() != 1 ){
				say( "   **Failed splitStack, right not size 1." );
				success = false;
			}

		} catch (Exception e){ say( "   **Exception in splitStack when axis=0: "+e.getMessage() ); }

		//
		// Test to make sure it moved the OBJECT REFERENCE and didn't
		// just make copies of the circle.
		//
		try {
			// Centroid should be 2
			Stack<Shape> stack = new Stack<Shape>();
			Circle c1 =  new Circle( new Vec2(0,0), 1 );
			Circle c2 =  new Circle( new Vec2(4,0), 1 );
			stack.push(c1);
			stack.push(c2);

			// I should have made splitStack a static method...
			// The node constructor may have removed elements from the
			// stack, we we'll recreate it after the constructor.
			// Whether you did or did not clear out the original stack
			// doesn't matter.
			Node n = new Node(stack,0);
			if( stack.size() == 0 ){
				stack.push(c1);
				stack.push(c2);
			}
			Stack<Shape> l = new Stack<Shape>();
			Stack<Shape> r = new Stack<Shape>();
			n.splitStack(stack,0,l,r);

			// Make sure it copied
			// make a copy with the same center/radius.
			if( l.peek() != c1 ){
				say( "   **Failed splitStack, left shape not correct." );
				success = false;
			}
			if( r.peek() != c2 ){
				say( "   **Failed splitStack, right shape not correct." );
				success = false;
			}

		} catch (Exception e){ say( "   **Exception in splitStack when axis=0: "+e.getMessage() ); }

		//
		// Test along y axis, three shapes
		//
		try {
			// Centroid should be ~2.6
			Stack<Shape> stack = new Stack<Shape>();
			Circle c1 =  new Circle( new Vec2(0,1), 0.1 );
			Circle c2 =  new Circle( new Vec2(0.1,3), 0.1 );
			Circle c3 =  new Circle( new Vec2(2,4), 0.1 );
			stack.push(c1);
			stack.push(c2);
			stack.push(c3);

			// I should have made splitStack a static method.
			// The node constructor may have removed elements from the
			// stack, we we'll recreate it after the constructor.
			// Whether you did or did not clear out the original stack
			// doesn't matter.
			Node n = new Node(stack,0);
			if( stack.size() == 0 ){
				stack.push(c1);
				stack.push(c2);
				stack.push(c3);
			}

			Stack<Shape> l = new Stack<Shape>();
			Stack<Shape> r = new Stack<Shape>();
			n.splitStack(stack,1,l,r);

			// Make sure partitioned stacks are the correct size
			if( l.size() != 1 ){
				say( "   **Failed splitStack, left not size 1." );
				success = false;
			}
			if( r.size() != 2 ){
				say( "   **Failed splitStack, right not size 2." );
				success = false;
			}

		} catch (Exception e){ say( "   **Exception in splitStack when axis=1: "+e.getMessage() ); }

		return success;
	}

	//
	// Test select method
	//
	public static boolean testSelect(){

		boolean success = true;

		//
		// Test false if outside
		//
		try {
			Stack<Shape> stack = new Stack<Shape>();
			Circle c1 =  new Circle( new Vec2(0,0), 1 );
			stack.push(c1);
			Node n = new Node(stack,0);

			// Force node to be the way we want it
			n.setShape(c1);
			n.setLeft(null);
			n.setRight(null);

			int[] counter = new int[1];
			counter[0] = 0;
			boolean shouldBeFalse = n.select(-2,-2,counter);
			if( shouldBeFalse ){
				say( "   **Failed select, returns true when it should be false." );
				success = false;
			}	

			// Value of isSelected did not change.
			if( c1.getSelected() ){
				say( "   **Failed select, shape changed when it should not have." );
				success = false;
			}	
			
		} catch (Exception e){ say( "   **Exception in select when outside bounds: "+e.getMessage() ); }

		//
		// True if inside
		//
		try {
			Stack<Shape> stack = new Stack<Shape>();
			Circle c1 =  new Circle( new Vec2(0,0), 1 );
			stack.push(c1);
			Node n = new Node(stack,0);

			// Force node to be the way we want it
			n.setShape(c1);
			n.setLeft(null);
			n.setRight(null);

			// Bounds are (-1,-1) to (1,1) because radius of circle is 1.
			int[] counter = new int[1];
			counter[0] = 0;
			boolean shouldBeTrue = n.select(0,0,counter);
			if( !shouldBeTrue ){
				say( "   **Failed select, returns false when it should be true." );
				success = false;
			}

			// Value of isSelected should be true.
			if( !c1.getSelected() ){
				say( "   **Failed select, shape's selected variable not changed when it should have." );
				success = false;
			}

		} catch (Exception e){ say( "   **Exception in select when inside bounds: "+e.getMessage() ); }


		//
		// Test inside the AABB but not inside the shape
		//
		try {
			Stack<Shape> stack = new Stack<Shape>();
			Circle c1 =  new Circle( new Vec2(0,0), 1 );
			stack.push(c1);
			Node n = new Node(stack,0);

			// Force node to be the way we want it
			n.setShape(c1);
			n.setLeft(null);
			n.setRight(null);
			n.setBounds(c1.getBounds());

			// Bounds are (-1,-1) to (1,1) because radius of sphere is 1.
			// The point (0.999,0.999) is inside the bounds, but outside the circle.
			int[] counter = new int[1];
			counter[0] = 0;
			boolean shouldBeFalse = n.select(0.999,0.999,counter);
			if( shouldBeFalse ){
				say( "   **Failed select, returns true when it should be false." );
				success = false;
			}

			// Value of selected should be false.
			if( c1.getSelected() ){
				say( "   **Failed select, shape's changed when it shouldn't have." );
				success = false;
			}

		} catch (Exception e){ say( "   **Exception in select when inside bounds, but outside shape: "+e.getMessage() ); }

		//
		// General traversal
		//
		try {
			int n_shapes = 1000;
			Stack<Shape> stack = new Stack<Shape>();
			Shape[] origStack = new Shape[n_shapes];
			for( int i=0; i<n_shapes; ++i ){
				Circle c =  new Circle( new Vec2(i,i), 0.4 );
				stack.push(c);
				origStack[i] = c;
			}

			Node n = new Node(stack,0);

			int[] counter = new int[1];
			counter[0] = 0;
			boolean shouldBeTrue = n.select(10,10,counter);

			if( !shouldBeTrue ){
				say( "   **Failed select with large stack, returns false when it should be true." );
				success = false;
			}

			// Check to make sure the correct shape has been selected
			if( !origStack[10].getSelected() ){
				say( "   **Failed select, the selected shape didnt change." );
				success = false;
			}

			// Check to make sure our counter is lower than the number of shapes,
			// otherwise it was (possibly) an exhaustive search and no branches were culled.
			if( counter[0] > n_shapes ){
				say( "   **Failed select, visited too many nodes." );
				success = false;
			}

		} catch (Exception e){ say( "   **Exception with select on a large stack: "+e.getMessage() ); }

		return success;
	}

	// Create JFrame and start application
	public static void main(String[] args) {
		boolean success = true;
		if( !testBounds() ){ success = false; }
		if( !testNodeConstructor() ){ success = false; }
		if( !testSplitStack() ){ success = false; }
		if( !testSelect() ){ success = false; }
		if( success ){ System.out.println("SUCCESS!"); }
	}

} // end class AABBTree
