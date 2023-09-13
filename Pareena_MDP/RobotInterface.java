import java.awt.Color;
import java.awt.Graphics;
import java.util.Stack;

public abstract class RobotInterface {
    Visualize viz;
    
    //robot coordinates
    int x, y;

    //coordinates to draw the robot
    int x_g, y_g;

    //radius of circile to draw the robot
    final static int radius = 90;

    //direction of the robot
    Direction direction;

    //map info
    Map map;
    final int map_width = 20;
    final int map_height = 20;

    //intialise stack to store fastest path between 2 points
    Stack<Node> fastest_path = new Stack<Node>();

    //convert path from nodes to int to give instructions on how to traverse map
    Stack<Integer> instruct_fastest_path = new Stack<Integer>();

    //abstract functions for future implementations
    public abstract void add_sensors(Real_Sensor[] sensors);

    public abstract void add_sensors(Sensor[] sensors);

     public abstract void remove_sensors();

    public abstract void sense_surroundings();

    public abstract void sense_robot_location();

	public abstract void move_robot();

    public abstract void forwards();

    public abstract void backwards();

    public abstract void left_turn();

    public abstract void right_turn();

    public abstract void calibrate_front();

    public abstract void calibrate_back();

    public abstract void calibrate_right();

    public abstract void calibrate_left();

    public abstract void init_calibration();

    public abstract boolean fp_steps(); //number of steps for fastest path

	public abstract void sendDesc();

	public abstract void setSpeed(float robot_steps_per_second);

    public abstract boolean get_fastest_instruction(Stack<Node> fast);

    //set the fastest path for the robot to travel
    public void set_fastest_instruction(Stack<Node> fastest, int target_x, int target_y){
        
        //input instructions into the stack
        Direction dir = direction;
        int temp_x = x;
        int temp_y = y;
        Node next = null;

        //loop until the stack is empty
        while(true){
            //if the stack is empty then the nodes have been fully converted
            if(fastest.empty()){
                break;
            } else {
                //retrieve the next node from the stack
                next = (Node)fastest.pop();

                //conditional statements to decide which direction to turn to
                //call get_shortest_turn to get fastest steps to turn
                if(next.get_X() > temp_x){
                    if(dir != Direction.RIGHT){
                        dir = get_shortest_turn(dir, Direction.RIGHT);
                    }
                    temp_x += 1;
                } else if(next.get_X() < temp_x){
                    if(dir != Direction.LEFT){
                        dir = get_shortest_turn(dir, Direction.LEFT);
                    }
                    temp_x -= 1;
                } else if(next.get_Y() > temp_y){
                    if(dir != Direction.DOWN){
                        dir = get_shortest_turn(dir, Direction.DOWN);
                    }
                    temp_x += 1;
                } else if(next.get_Y() < temp_y){
                    if(dir != Direction.UP){
                        dir = get_shortest_turn(dir, Direction.UP);
                    }
                    temp_x -= 1;
                }

                //add instructions for the fastest path to the stack
                instruct_fastest_path.add(Packet.forward_inst);
            }
        }

    }

    //change direction to simulate a right turn 
    public Direction simulate_turn_right(Direction dir){
        switch(dir){
            case RIGHT:
                dir = Direction.DOWN;
                break;
            case LEFT:
                dir = Direction.UP;
                break;
            case UP:
                dir = Direction.RIGHT;
                break;
            case DOWN:
                dir = Direction.LEFT;
                break;
        }
        return dir;
    }

    //change direction to simulate a left turn 
    public Direction simulate_turn_left(Direction dir){
        switch(dir){
            case RIGHT:
                dir = Direction.UP;
                break;
            case LEFT:
                dir = Direction.DOWN;
                break;
            case UP:
                dir = Direction.LEFT;
                break;
            case DOWN:
                dir = Direction.RIGHT;
                break;
        }
        return dir;
    }

    //method to get shortest way to turn to a specific direction
    
    //var specific to current direction is dir --> to communicate w/ android/rpi
    public Direction get_shortest_turn(Direction dir, Direction target_dir){
        //condiitonal statements specifying directions to turn
        if(dir == Direction.RIGHT){
            if(target_dir == Direction.UP){
                instruct_fastest_path.add(Packet.left_inst);
            } else if(target_dir == Direction.DOWN){
                instruct_fastest_path.add(Packet.right_inst);
            } else if(target_dir == Direction.LEFT){
                instruct_fastest_path.add(Packet.left_inst);
                instruct_fastest_path.add(Packet.left_inst);
            }
        } else if(dir == Direction.LEFT){
            if(target_dir == Direction.UP){
                instruct_fastest_path.add(Packet.right_inst);
            } else if(target_dir == Direction.DOWN){
                instruct_fastest_path.add(Packet.left_inst);
            } else if(target_dir == Direction.RIGHT){
                instruct_fastest_path.add(Packet.left_inst);
                instruct_fastest_path.add(Packet.left_inst);
            }
        } else if(dir == Direction.UP){
            if(target_dir == Direction.LEFT){
                instruct_fastest_path.add(Packet.left_inst);
            } else if(target_dir == Direction.RIGHT){
                instruct_fastest_path.add(Packet.right_inst);
            } else if(target_dir == Direction.DOWN){
                instruct_fastest_path.add(Packet.left_inst);
                instruct_fastest_path.add(Packet.left_inst);
            }
        } else if(dir == Direction.DOWN){
            if(target_dir == Direction.RIGHT){
                instruct_fastest_path.add(Packet.left_inst);
            } else if(target_dir == Direction.RIGHT){
                instruct_fastest_path.add(Packet.right_inst);
            } else if(target_dir == Direction.DOWN){
                instruct_fastest_path.add(Packet.left_inst);
                instruct_fastest_path.add(Packet.left_inst);
            }
        }

        return target_dir;
    }

    //returns true if the left side has blocks to calibrate
	public boolean possible_calibrate_right() {
		if (direction == Direction.LEFT && is_blocked(x - 1, y - 2) && is_blocked(x + 1, y - 2))
			return true;
		else if (direction == Direction.RIGHT && is_blocked(x - 1, y + 2) && is_blocked(x + 1, y + 2))
			return true;
		else if (direction == Direction.DOWN && is_blocked(x - 2, y - 1) && is_blocked(x - 2, y + 1))
			return true;
		else if (direction == Direction.UP && is_blocked(x + 2, y - 1) && is_blocked(x + 2, y + 1))
			return true;

		return false;
	}

    //returns true if the front of the robot has blocks to calibrate
	public boolean possible_calibrate_front() {
		if (direction == Direction.LEFT && is_blocked(x - 2, y - 1) && is_blocked(x - 2, y) && is_blocked(x - 2, y + 1))
			return true;
		else if (direction == Direction.RIGHT && is_blocked(x + 2, y - 1) && is_blocked(x + 2, y)
				&& is_blocked(x + 2, y + 1))
			return true;
		else if (direction == Direction.DOWN && is_blocked(x - 1, y + 2) && is_blocked(x, y + 2)
				&& is_blocked(x + 1, y + 2))
			return true;
		else if (direction == Direction.UP && is_blocked(x - 1, y - 2) && is_blocked(x, y - 2) && is_blocked(x + 1, y - 2))
			return true;

		return false;
	}

    // returns true if the left side of the robot has block to calibrate
	public boolean possible_calibrate_left() {
		if (direction == Direction.LEFT && is_blocked(x - 1, y + 2) && is_blocked(x + 1, y + 2))
			return true;
		else if (direction == Direction.RIGHT && is_blocked(x - 1, y - 2) && is_blocked(x + 1, y - 2))
			return true;
		else if (direction == Direction.DOWN && is_blocked(x + 2, y - 1) && is_blocked(x + 2, y + 1))
			return true;
		else if (direction == Direction.UP && is_blocked(x - 2, y - 1) && is_blocked(x - 2, y + 1))
			return true;

		return false;
	}

    //accessors
	public int get_x() {
		return x;
	}

	public int get_y() {
		return y;
	}

    // set position of the robot and face_dir direction
	public void set_robot_position(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		this.direction = dir;
	}

	// get the visualizaton
	public Visualize get_viz() {
		return viz;
	}

	// set robot's direction
	public void set_direction(Direction dir) {
		this.direction = dir;
	}

	// set visualization
	public void set_visualization(Visualize viz) {
		this.viz = viz;
	}

    // method to return whether front has a wall/obstacle
	public boolean is_obstacle_or_wall() {
		switch (direction) {
		case UP:
			if (is_blocked(x - 1, y - 2) || is_blocked(x, y - 2) || is_blocked(x + 1, y - 2))
				return true;
			break;
		case DOWN:
			if (is_blocked(x - 1, y + 2) || is_blocked(x, y + 2) || is_blocked(x + 1, y + 2))
				return true;
			break;
		case LEFT:
			if (is_blocked(x - 2, y - 1) || is_blocked(x - 2, y) || is_blocked(x - 2, y + 1))
				return true;
			break;
		case RIGHT:
			if (is_blocked(x + 2, y - 1) || is_blocked(x + 2, y) || is_blocked(x + 2, y + 1)) {
				return true;
			}
			break;

		}
		return false;
	}

    //return true if left bound is being violated
	public boolean check_left_bound(int xi, int yi) {
		if ((xi < 0)) {
			return true;
		}
		return false;
	}

	// return true if top bound is being violated
	public boolean check_top_bound(int xi, int yi) {
		if ((yi < 0)) {
			return true;
		}
		return false;
	}

    // return true if bottom bound is being violated
	public boolean check_bottom_bound(int xi, int yi) {
		if (yi > (map_height)) {
			return true;
		}
		return false;
	}

	// return true if right bound is being violated
	public boolean check_right_bound(int xi, int yi) {
		if (xi > (map_width)) {
			return true;
		}
		return false;
	}

	// return true if out of bounds or obstacle at given coordinates
	public boolean check_obstacle(int xi, int yi) {
		if (yi >= map_height || xi >= map_width || yi < 0 || xi < 0) {
			return true;
		} else if (map.get_grid_map()[yi][xi] == ExplorationTypes.convert_type_to_int("OBSTACLE")) {
			return true;
		}
		return false;
	}

    // return true if robot can move in specified direction
	public boolean can_move(Direction dir) {
		return can_move(dir, this.x, this.y);
	}

	public boolean can_move(Direction dir, int x, int y) {
		boolean move = true;

		switch (dir) {
		case LEFT:
			for (int i = -1; i < 2; i++) {
				// if part of the robot is out of bounds or going to hit a wall
				if (check_left_bound(x - 1, y) || check_obstacle(x - 1 - 1, y + i)) {
					move = false;
					break;
				}
			}
			break;

		case RIGHT:
			for (int i = -1; i < 2; i++) {
				// if part of the robot is out of bounds or going to hit a wall
				if (check_right_bound(x + 1, y) || check_obstacle(x + 1 + 1, y + i)) {
					move = false;
					break;
				}
			}
			break;

		case UP:
			for (int i = -1; i < 2; i++) {
				// if part of the robot is out of bounds or going to hit a wall
				if (check_top_bound(x, y - 1) || check_obstacle(x + i, y - 1 - 1)) {
					move = false;
					break;
				}
			}
			break;

		case DOWN:

			for (int i = -1; i < 2; i++) {
				// if part of the robot is out of bounds or going to hit a wall
				if (check_bottom_bound(x, y + 1) || check_obstacle(x + i, y + 1 + 1)) {
					move = false;
					break;
				}
			}
			break;
		}
		return move;
	}

    // return 1 if obstacle present, 0 if no obstacle and -1 if out of bounds
	public boolean is_blocked(int xi, int yi) {
		boolean rflag = false, tflag = false, bflag = false, lflag = false, obflag = false;
		lflag = check_left_bound(xi, yi);
		tflag = check_top_bound(xi, yi);
		rflag = check_right_bound(xi, yi);
		bflag = check_bottom_bound(xi, yi);
		obflag = check_obstacle(xi, yi);
		if (lflag || tflag || rflag || bflag || obflag) {
			return true;
		}

		if (map.get_grid_map()[yi][xi] == ExplorationTypes.convert_type_to_int("NOT_EMPTY")
				|| map.get_grid_map()[yi][xi] == ExplorationTypes.convert_type_to_int("NOT_OBSTACLE"))
			return true;

		return false;
	}

	// return false if robot cannot move to given coordinates
	public boolean can_robot_move_here(int x, int y) {
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (is_blocked(x + i, y + j))
					return false;
			}
		}

		return true;
	}

	// draw the robot, using graphics
	public void paint_robot(Graphics graphics) {
		graphics.setColor(Color.RED);
		x_g = 10 + (x - 1) * Map.size_of_square;
		y_g = 10 + (y - 1) * Map.size_of_square;
		graphics.fillArc(x_g, y_g, radius, radius, 0, 360);

		graphics.setColor(Color.BLUE);

		int dir_offsetX = 0;
		int dir_offsetY = 0;

		if (direction == Direction.UP)
			dir_offsetY = -30;
		else if (direction == Direction.DOWN)
			dir_offsetY = 38;
		else if (direction == Direction.LEFT)
			dir_offsetX = -30;
		else if (direction == Direction.RIGHT)
			dir_offsetX = 38;

		graphics.fillArc(x_g + 30 + dir_offsetX, y_g + 30 + dir_offsetY, 20, 20, 0, 360);
	}

	public abstract boolean retrieve_reset_wanted();

}
