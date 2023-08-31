import java.util.ArrayList;

public class Node{
    //attributes
    Node left;
    Node right;
    Node up;
    Node down;

    Node parent;

    int x;
    int y;

    Direction direction;

    boolean is_obstacle;
    int no_of_spaces;

    float current_cost;
    float heuristic_cost;

    ArrayList<Node> neighbours_list = new ArrayList<Node>();

    //constructors
    public Node(){ //start node
        this.x = 0;
        this.y = 0;
    }

    public Node(int x, int y){//other nodes
        this.x = x;
        this.y = y;
    }

    //utility functions
    public int compare_cost(Node node){
        float total_cost = get_total_cost();
        float node_total_cost = node.get_total_cost();
        float diff = total_cost - node_total_cost;
        return diff > 0 ? 1 : diff < 0 ? -1 : 0;
    }
    public int compare_x(Node node){
        return node.x > this.x ? 1 : node.x < this. x ? -1 : 0;
    }

    public int compare_y(Node node){
        return node.y > this.y ? 1 : node.y < this.y ? -1 : 0;
    }

    public float get_heuristic_cost(Node end){
        return Math.abs(this.x - end.x) + Math.abs(this.y - end.y);
    }
    public float get_total_cost(){
        return this.current_cost + this.heuristic_cost;
    }

    public float get_current_cost(Node node, boolean is_start){ //total cost to next closest node
        return this.current_cost + get_weight(node, is_start);
    }

    public float get_weight(Node node, boolean is_start){
        set_direction();
        
        if(compare_y(node) == 1 && this.direction == Direction.UP || compare_y(node) == -1 && this.direction == Direction.DOWN || compare_x(node) == 1 && this.direction == Direction.RIGHT || compare_x(node) == -1 && this.direction == Direction.DOWN && !is_start){
            return 100;
        }

        return 1500;
    }

    public void set_direction(){ //of robot
        if(this.parent == null){
            this.direction = Direction.RIGHT;
        } else if(compare_x(this.parent) == 1){
            this.direction = Direction.LEFT;
        } else if(compare_x(this.parent) == -1){
            this.direction = Direction.RIGHT;
        } else if(compare_y(this.parent) == 1){
            this.direction = Direction.DOWN;
        } else if(compare_x(this.parent) == 1){
            this.direction = Direction.UP;
        }
        
    }

    //accessors/mutators
    public void add_neighbours(Node node){
        neighbours_list.add(node);
    }

    public ArrayList<Node> get_neighbours(){
        return neighbours_list;
    }

    public void set_direction(Direction direction){
        this.direction = direction;
    }

    public Direction get_direction(){
        return this.direction;
    }

    public void set_X(int x){
        this.x = x;
    }

    public int get_X(){
        return this.x;
    }

    public void set_Y(int y){
        this.y = y;
    }

    public int get_Y(){
        return this.y;
    }

    public void set_num_spaces(int no_of_spaces){
        this.no_of_spaces = no_of_spaces;
    }

    public int get_num_spaces(){
        return this.no_of_spaces;
    }

    public void set_obstacle(boolean is_obs){
        this.is_obstacle = is_obs;
    }

    public boolean is_obstacle(){
        return this.is_obstacle;
    }

    public void set_up(Node up){
        this.up = up;
    }

    public Node get_up(){
        return this.up;
    }

    public void set_down(Node down){
        this.down = down;
    }

    public Node get_down(){
        return this.down;
    }

    public void set_left(Node left){
        this.left = left;
    }

    public Node get_left(){
        return this.left;
    }

    public void set_right(Node right){
        this.right = right;
    }

    public Node get_right(){
        return this.right;
    }

    
}