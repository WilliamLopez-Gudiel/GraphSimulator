//********************************************************************************
//   DO NOT EDIT ANYTHING BELOW THIS LINE
//********************************************************************************
import org.apache.commons.collections15.Factory;

import java.awt.Color;

/**
 *  This class represents an edge for the SimGUI graph. Remember
 *  that your graph class is generic, so it shouldn't assume
 *  that all edges are ThreeTenEdges.
 *  
 *  @author Katherine (Raven) Russell
 */
public final class ThreeTenEdge extends ThreeTenGraphComponent {
	/**
	 *  The ID for the next edge to be created. This auto-increments
	 *  with ever constructor call to this class.
	 */
	public static int edgeCount = 0;
	
	/**
	 *  Creates a new ThreeTenNode with default settings.
	 */
	public ThreeTenEdge() { super(edgeCount++); setColor(Color.BLACK); }
	
	/**
     * Creates a {@code Factory} that in turn creates an instance of this edge type.
     * @return the created factory
     */
	public static Factory<ThreeTenEdge> getFactory() { 
		return new Factory<ThreeTenEdge> () {
			public ThreeTenEdge create() {
				return new ThreeTenEdge();
			}
		};
	}
}

