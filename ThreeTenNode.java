//********************************************************************************
//   DO NOT EDIT ANYTHING BELOW THIS LINE
//********************************************************************************
import org.apache.commons.collections15.Factory;

/**
 *  This class represents a node for the SimGUI graph. Remember
 *  that your graph class is generic, so it shouldn't assume
 *  that all nodes are ThreeTenNodes.
 *  
 *  @author Katherine (Raven) Russell
 */
class ThreeTenNode extends ThreeTenGraphComponent {
	/**
	 *  The ID for the next node to be created. This auto-increments
	 *  with ever constructor call to this class.
	 */
	public static int nodeCount = 0;
	
	/**
	 *  Creates a new ThreeTenNode with default settings.
	 */
	public ThreeTenNode() { super(nodeCount++); }
	
	/**
     * Creates a {@code Factory} that in turn creates an instance of this node type.
     * @return the created factory
	 */
	public static Factory<ThreeTenNode> getFactory() { 
		return new Factory<ThreeTenNode> () {
			public ThreeTenNode create() {
				return new ThreeTenNode();
			}
		};
	}
}
