import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable {

    // Node variables (representing relationships between nodes in graph/grid)
    Node left;
    Node right;
    Node up;
    Node down;
    Node parentNode; // parent node to the existing node
    List list_of_neighbors = new ArrayList<Node> (); // Stores references of neighboring nodes of current node

    // Cost of path computation
    float current_cost; // Total cost of path to reach this node from start node
    float heuristic_cost; // Estimated cost from current goal to goal node
    
    // Grid variables
    boolean isObs; // Determine whether current node represents an obstacle in the grid
    int space;

    // Direction/Positional coordinates
    Facing face_dir; //Current face position of robot
    final int x; // x-coordinate of current robot
    final int y; // y-coordinate of current robot

    // Compare node costs
    public int compareTo(Object other_node) { //determine if current node or other node has a greater total cost
        float current_value = this.getTotalCost();
        float other_value = ((Node) other_node).getTotalCost();

        //Cost difference to determine the order in which the nodes should be sorted
        float v = current_value - other_value; 
        return (v > 0) ? 1 : (v < 0) ? -1 : 0; // 1 = current_node after other_node
                                               // -1 = current_node before other_node
                                               // 0 = current_node has the same cost as other_node
    }

    // GET method: Cost
    public float getTotalCost() {
        return current_cost + heuristic_cost;
    }

    //Initialise node coordinates - When a 'Node' object is instantiated using this constructor, initial coordinates is (0,0)
    public Node() {
        x = 0;
        y = 0;
    }

   // Assign node coordinates
    public Node(int x_coordinate, int y_coordinate) {
        this.x = x_coordinate;
        this.y = y_coordinate;
    }

    public void addNeighbors(Node node) {
        list_of_neighbors.add(node); //Add neighboring node to list of neighbors for current node
    }

    // Compute path cost
    public float getCost(Node node /*,Node end_node, boolean is_start_node*/) {
        return this.current_cost + getWeight( node);
    }

    // Returns the list of neighboring nodes of the current node.
    public List getNeighbors() {
        return list_of_neighbors;
    }

    // Compute node cost
    public float heuristic_cost(Node node) {
        Node goal = (Node) node;

        float dx = Math.abs(this.x - goal.x);
        float dy = Math.abs(this.y - goal.y);
        return (dx + dy); // Determined by the manhattan distance between current and goal node.
    }

    // Compute node cost
    /* 
    public float getWeight(boolean is_start_node, Node some_node) {
        Node node = (Node) some_node;

        if ((compareY(node) == 1 && face_dir == Facing.UP ||
        compareX(node) == 1 && face_dir == Facing.RIGHT ||
        compareX(node) == -1 && face_dir == Facing.LEFT || 
        compareY(node) == -1 && face_dir == Facing.DOWN)
        && !is_start_node) {
            return Math.abs(node.current_cost - this.current_cost);
        }
    return 1500;
    }

    */

    public float getWeight(Node some_node) {
        Node node = (Node) some_node;
        return Math.abs(node.current_cost - this.current_cost);
    }

    // Initialise robot's orientation
    public void setFacing() {
        if(this.parentNode == null) { //check if parent of current node is null
            this.face_dir = Facing.RIGHT;
            return;
        }

        //if not null, calculate orientation based on the relationship btw current and parent node
        else if (compareX((Node) this.parentNode) == -1) {
            this.face_dir = Facing.RIGHT;
        } else if (compareY((Node) this.parentNode) == 1) {
            this.face_dir = Facing.DOWN;
        } else if (compareX((Node) this.parentNode) == 1) {
            this.face_dir = Facing.LEFT;
        } else if (compareY((Node) this.parentNode) == -1) {
            this.face_dir = Facing.UP;
        }
    }

    public int getClearance() { //accessor - clearance/available space at this node within the grid
        return space;
    }

    public void setIfClear(int space) { //method - update clearance value associated with a node
        this.space = space;
    }

    // Grid map navigation methods
    public void setObs(boolean val) { // see whether node is an obstacle in the grid or not
        this.isObs = val;
    }

    public boolean isObs() { //determine whether a node is an obstacle or not
        return isObs;
    }

    // Compare node coordinates in the X dimension
    public int compareX (Node node) {
        return node.x > this.x ? 1 : node.x < this.x ? -1 : 0;
    }

    public int compareY(Node node) {
        return node.y > this.y ? 1 : node.y < this.y ? -1 : 0;
    }

    public void setLeft(Node left) {  // sets 'left' neighbor of the current node to provided 'left' node.
        this.left = left;
    }

    public Node getRight() { // returns 'right' neighbor of the current node
        return right;
    }

    public void setDown(Node down) { // sets 'down' neighbor of the current node to the provided down node
        this.down = down;
    }

    public Node getDown() { // returns 'down' neighbor of the current node
        return down;
    }

    public Node getUp () { // returns 'up' neighbor of the current node
        return up;
    }

    // GET methods: Directions
    public Node getLeft() { // returns 'left' neighbor of the current node
        return left;
    }

    public void setRight(Node right) { // sets 'right' neighbor of the current node to the 'right' node
        this.right = right; 
    }

    public void setUp(Node up) { // sets 'up' neighbor of the current node to the provided 'up' node
        this.up = up;
    }

    // GET methods: X & Y coordinates
    public int getX() { // returns X coordinate of current node
        return x;     
    }

    public int getY() { // returns y coordinate of current node
        return y;
    }

}