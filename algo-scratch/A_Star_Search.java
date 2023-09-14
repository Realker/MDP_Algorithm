import java.util.List;
import java.util.LinkedList;
import java.util.Stack;

public class A_Star_Search {
    Node start; //start node
    Node end; //end node
    int path_cost; //path cost between 2 nodes

    //Robot robot;

    //constructor
    public A_Star_Search(Node start, Node end){
        this.start = start;
        this.end = end;
        this.path_cost = 0;
    }

    //search functions

    //open list - arrange nodes according to increasing total path cost 
    public static class PriorityList extends LinkedList{
        public void add(Node node){
            for(int i = 0; i< size(); i++){
                if(node.compareTo((Node)(get(i))) <= 0){
                    add(i, node);
                    return;
                }
            }
            addLast(node);
        }
    }

    //find an order of nodes to traverse that will form the shortest path
    public Stack<Node> find_shortest_path(Node start, Node end){
        boolean is_start = true;
        int size = 5; //jus a test value

        PriorityList open_list = new PriorityList();
        LinkedList<Node> closed_list = new LinkedList<Node>();

        start.current_cost = 0;
        start.heuristic_cost = start.heuristic_cost(end);
        start.parentNode = null;
        open_list.add(start);

        while(!open_list.isEmpty()){
            Node node = (Node) open_list.removeFirst();

            if (node == start){
                is_start = true;
            } else {
                is_start = false;
            }

            node.setFacing();

            // Determine if node is goal node
            if(node == end){
                return form_path(end);
            }

            // Get list of node neighbours
            List neighbors_list = node.getNeighbors();

            // Iterate through list of node neighbours
            for (int i = 0; i<neighbors_list.size(); i++){

                // Extract neighbour node information
                Node neighbour = (Node) neighbors_list.get(i);
                boolean in_open = open_list.contains(neighbour);
                boolean in_closed = closed_list.contains(neighbour);
                boolean is_obstacle = neighbour.isObs();
                int num_free_spaces = neighbour.getClearance();
                float current_cost = node.getCost(neighbour) + 1;

                // Check 1. if node neighbours have not been explored OR 2. If shorter path to
                // neighbour node exists
                if( (!in_open && !in_closed) || current_cost < neighbour.current_cost){
                    neighbour.parentNode = node;
                    neighbour.current_cost = current_cost;
                    neighbour.heuristic_cost = neighbour.heuristic_cost(end);

                    if(!in_open && !is_obstacle && size == num_free_spaces){
                        open_list.add(neighbour);
                    }

                }
            }
            closed_list.add(node);

        }
        return new Stack<Node>();
       

    }

    // Return path of nodes to given goal node as stack
    protected Stack<Node> form_path(Node node){
        Stack<Node> path = new Stack<Node>();

        while(node.parentNode != null){
            path.push(node);
            node = node.parentNode;
            path_cost++;
        }

        return path;
    }

    public Facing get_robot_direction(Node node, Facing face_dir){
        Node parent_node = (Node) node.parentNode;

        if (parent_node == null) {
            return face_dir;
        }

        // Calculate the vector from the parent node to the current node
        int deltaX = node.getX() - parent_node.getX();
        int deltaY = node.getY() - parent_node.getY();

        // Determine the direction based on the vector
        if (deltaX == 1) {
            return Facing.LEFT; 
        } else if (deltaX == -1) {
            return Facing.RIGHT;
        } else if (deltaY == 1) {
            return Facing.DOWN; 
        } else if (deltaY == -1) {
            return Facing.UP;
        } else {
            return null; // Handle unexpected cases
        } 
    }

    //accessor/mutators
    public int get_path_cost(){
        return this.path_cost;
    }

    public Stack<Node> get_shortest_path(){
        return find_shortest_path(this.start, this.end);
    }
}
