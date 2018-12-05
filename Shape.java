//written by dovol002, used by pidap008

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.*;


public abstract class Shape {

	public abstract boolean getSelected();
	public abstract void setSelected( boolean b );

	// Returns center of the shape
	public abstract Vec2 getCenter();

	// Returns bounds of the shape
	public abstract Bounds getBounds();

	// Paints the shape
	public abstract void paint(Graphics2D g);

	// Returns true if a shape is selected
	public abstract boolean select(double x, double y);

	// Returns the distance from the surface, ignoring z axis.
	public abstract double exteriorDistance(double x, double y);

} // end class Shape
