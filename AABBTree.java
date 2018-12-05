//written by dovol002, used by pidap008

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class AABBTree extends JPanel {

	// A Point is a 2D location in screen space
	private Node rootNode;
	private int winWidth;
	private int winHeight;
	private Stack<Shape> shapeStack;

	// Register listeners for the mouse and initial geometry
	public AABBTree(int max_shapes) {

		winWidth = 800;
		winHeight = 600;

		// Needs to be focus for key listener
		this.setFocusable(true);
		this.requestFocusInWindow();

		// Shape queue used for rendering
		shapeStack = new Stack<Shape>();
		double rad = 30;

		// Randomly generate shapes
		Stack<Shape> stack = new Stack<Shape>();
		Random rand = new Random();
		for( double x = rad; x < (winWidth-rad); x += rad*2.1 ){
			for( double y = rad; y < (winHeight-rad); y += rad*2.1 ){
				x += 1;
				y += 1;
				if( stack.size() >= max_shapes ){ continue; }
				Circle c =  new Circle( new Vec2(x,y), rad );
				stack.push(c);
				shapeStack.push(c);
			}
		}

		// Create a BVH tree
		rootNode = new Node(stack,0);

		// When the mouse button is pressed, do a traversal.
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int[] counter = {0};

				boolean left_click = SwingUtilities.isLeftMouseButton(e);
				boolean right_click = SwingUtilities.isRightMouseButton(e);

				if( left_click ){
					boolean hitSomething = rootNode.select(e.getX(),e.getY(),counter);
				} else if( right_click ){
					Shape[] shapeRef = {null};
					double[] currentMin = {Double.MAX_VALUE};
					boolean hitSomething = rootNode.nearest(e.getX(),e.getY(),
						currentMin,shapeRef,counter);
					if( shapeRef[0] == null ){
						throw new NullPointerException();
					}
					shapeRef[0].setSelected( !shapeRef[0].getSelected() );
				}

				System.out.println("Visited " + counter[0] + " nodes");
				repaint(); // repaint the frame.
			}
		});

		// When escape is pressed, close the program
		addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent k) {
				if(k.getKeyCode() == KeyEvent.VK_ESCAPE) {
			                System.exit(0);
				}
			}
		});
	}

	// Called when the frame needs to be rendered
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		// If they have generated a tree, render it.
		if( rootNode.getLeft() != null &&
			rootNode.getRight() != null ){ rootNode.paint(g2);
		}

		Iterator<Shape> iter = shapeStack.iterator();
		while( iter.hasNext() ) {
			iter.next().paint(g2);
		}
	}

	// Create JFrame and start application
	public static void main(String[] args) {

		int max_shapes = 100;
		if( args.length > 0 ){
			max_shapes = Math.max( 1, Integer.parseInt(args[0]) );
		}

		AABBTree tree = new AABBTree(max_shapes);
		JFrame frame = new JFrame("AABBTree");
		frame.getContentPane().add(tree, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(tree.winWidth, tree.winHeight);
	        frame.requestFocus();
		frame.setVisible(true);
	}

} // end class AABBTree
