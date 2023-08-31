import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class A_star_search {
    Node start;
    Node end;
    int path_cost;

    Robot robot;

    //constructor
    public A_star_search(Node start, Node end){
        this.start = start;
        this.end = end;
        this.path_cost = 0;
    }

    //search functions
    public static class PriorityList extends LinkedList{
        public void add_node(Node node){
            for(int i = 0; i< size(); i++){
                if(node.compare_cost((Node)(get(i))) <= 0){
                    add(i, node);
                    return;
                }
            }
            addLast(node);
        }
    }
    public Stack<Node> find_shortest_path(Node start, Node end){
        boolean is_start = true;
        int size = 5;

        PriorityList open_list = new PriorityList();
        LinkedList<Node> closed_list = new LinkedList<Node>();

        start.current_cost = 0;
        start.heuristic_cost = start.get_heuristic_cost(end);
        start.parent = null;
        open_list.add(start);

        while(!open_list.isEmpty()){
            Node node = (Node) open_list.removeFirst();

            if (node == start){
                is_start = true;
            } else {
                is_start = false;
            }

            node.set_direction();

            if(node == end){
                return form_path(end);
            }

            ArrayList<Node> neighbours_list = node.get_neighbours();
            for (int i = 0; i<neighbours_list.size(); i++){

                Node neighbour = neighbours_list.get(i);
                boolean in_open = open_list.contains(neighbour);
                boolean in_closed = closed_list.contains(neighbour);
                boolean is_obstacle = neighbour.is_obstacle();
                int num_free_spaces = neighbour.get_num_spaces();
                float current_cost = node.get_current_cost(neighbour, is_start) + 1;

                if( (!in_open && !in_closed) || current_cost < neighbour.current_cost){
                    neighbour.parent = node;
                    neighbour.current_cost = current_cost;
                    neighbour.heuristic_cost = neighbour.get_heuristic_cost(end);

                    if(!in_open && !is_obstacle && size == num_free_spaces){
                        open_list.add(neighbour);
                    }

                }
            }
            closed_list.add(node);

        }
        return new Stack<Node>();
       

    }

    protected Stack<Node> form_path(Node node){
        Stack<Node> path = new Stack<Node>();

        while(node.parent != null){
            path.push(node);
            node = node.parent;
            path_cost++;
        }

        return path;
    }

    public Direction get_robot_direction(Node node, Direction direction){
        Node parent = node.parent;
        if(parent == null){
            return direction;
        } else if (node.compare_x(parent) == 1){
            return Direction.LEFT;
        } else if(node.compare_x(parent) == -1){
            return Direction.RIGHT;
        } else if(node.compare_y(parent) == 1){
            return Direction.DOWN;
        } else if(node.compare_y(node) == -1){
            return Direction.UP;
        }

        return null;
    }

    //accessor/mutators
    public float get_path_cost(){
        return this.path_cost;
    }

    public Stack<Node> get_shortest_path(){
        return find_shortest_path(this.start, this.end);
    }
}
