import java.util.Stack;
public class Robot extends RobotInterface{
    // declare class variables
	Sensor[] sensor;
	boolean hit_wall_front = false;
	boolean hit_wall_right = false;
	int count_calibrate_right = 0;
	int count_calibrate_front = 0;
	int num_calibrate_side = 3;
	int num_calibrate_front = 3;
	float robot_steps_per_second = 1f;
	boolean front_calibrated = false;
	boolean side_calibrated = false;

    // parameterized contructor to initialize robot variables
	public Robot(int x, int y, Direction face_dir, Map map) {
		// starting postition
		super();
		this.x = x;
		this.y = y;
		this.direction = face_dir;
		this.map = map;
		hit_wall_front = false;
		hit_wall_right = false;
		instruct_fastest_path = new Stack<Integer>();
		sense_robot_location();
	}

	// add sensors to the dummy robot
	public void add_sensors(Sensor[] sensors) {
		this.sensor = sensors;
		// sense the surrounding
		sense_surroundings();
	}

	// update robot sensors with the new location
	public void update_sensor() {
		for (int i = 0; i < sensor.length; i++) {
			sensor[i].update_location(x, y);
		}
	}

    // deactivate sensors
	public void remove_sensors() {
		sensor = new Sensor[0];
	}

	// specify robot speed as steps per time_second
	public void set_speed(float robot_steps_per_second) {
		this.robot_steps_per_second = robot_steps_per_second;
	}

	// move robot
	public void move_robot() {
		int movement_distance = 1;
		if (direction == Direction.UP)
			y -= movement_distance;
		else if (direction == Direction.DOWN)
			y += movement_distance;
		else if (direction == Direction.RIGHT)
			x += movement_distance;
		else if (direction == Direction.LEFT)
			x -= movement_distance;

		// update the location for the robot in the sensors
		update_sensor();

		// make robot sense surroundings
		sense_surroundings();

	}

    // method to allow to robot to sense the surrounding
	public void sense_surroundings() {
		boolean sense1;
		int countF = 0;
		int countR = 0;

		for (int i = 0; i < sensor.length; i++) {
			sense1 = sensor[i].Sense(map, 0, null);
			// front wall hit
			if ((i <= 1 || i == 3) && sense1) {
				countF++;
				if (countF == 3)
					hit_wall_front = true;
				else
					hit_wall_front = false;
			}

			// right wall hit
			if ((i == 2 || i == 4) && sense1) {
				countR++;
				if (countR == 2)
					hit_wall_right = true;
				else
					hit_wall_right = false;
			}
		}

		if (hit_wall_front && hit_wall_right) {
			// calibrate both front and side
			calibrate_front();
			calibrate_right();
			hit_wall_front = false;
			hit_wall_right = false;
		}
    }

    public void sense_robot_location() {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++){
                map.get_grid_map()[y + i][x + j] = ExplorationTypes.convert_type_to_int("EMPTY");
            }
        }
    }

    // make the robot turn right
	public void right_turn() {
		switch (direction) {
		case RIGHT:
			direction = Direction.DOWN;
			break;
		case LEFT:
			direction = Direction.UP;
			break;
		case UP:
			direction = Direction.RIGHT;
			break;
		case DOWN:
			direction = Direction.LEFT;
			break;
		}
		for (int i = 0; i < sensor.length; i++) {
			sensor[i].change_to_right();
		}
		sense_surroundings();
	}

	// make the robot turn left
	public void left_turn() {
		switch (direction) {
		case RIGHT:
			direction = Direction.UP;
			break;
		case LEFT:
			direction = Direction.DOWN;
			break;
		case UP:
			direction = Direction.LEFT;
			break;
		case DOWN:
			direction = Direction.RIGHT;
		}
		// change sensor direction to follow robot
		for (int i = 0; i < sensor.length; i++) {
			sensor[i].change_to_left();
		}
		sense_surroundings();
	}

    // get fastest path instructions
	public boolean retrieve_fastest_instruction(Stack<Node> fast) {
		if (fast == null)
			return true;
		while (!fast.isEmpty()) {
			Node two = (Node) fast.pop();
			try {
				Thread.sleep((long) (1000 / robot_steps_per_second));
				if (two.get_X() > x) {
					while (direction != Direction.RIGHT) {
						right_turn();
					}
					move_robot();
				} else if (two.get_X() < x) {
					while (direction != Direction.LEFT) {
						left_turn();
					}
					move_robot();
				} else if (two.get_Y() < y) {
					while (direction != Direction.UP) {
						left_turn();
					}
					move_robot();
				} else { // if(two.getY() < one.getY())
					while (direction != Direction.DOWN) {
						right_turn();
					}
					move_robot();
				}
				viz.repaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

    // perform step indicated in the fastest path
	public boolean fp_steps() {

		// if no more instructions, then fastest path is performed
		if (instruct_fastest_path.isEmpty())
			return true;
		else {
			front_calibrated = false;
			side_calibrated = false;
			int instruction = (Integer) instruct_fastest_path.remove(0);
			switch (instruction) {
			case Packet.right_inst:
				right_turn();
				break;
			case Packet.left_inst:
				left_turn();
				break;
			case Packet.forward_inst:
				if (count_calibrate_right >= num_calibrate_side) {
					if (possible_calibrate_right()) {

						calibrate_right();
						count_calibrate_right = 0;
					} else if (possible_calibrate_left()) {

						calibrate_left();
						count_calibrate_right = 0;
					}
					side_calibrated = true;
				}
				if (count_calibrate_front >= num_calibrate_front) {
					if (possible_calibrate_front()) {

						calibrate_front();
						count_calibrate_front = 0;
						front_calibrated = true;
					}
				}
				if (!front_calibrated)
					count_calibrate_front++;
				if (!side_calibrated)
					count_calibrate_right++;
				move_robot();
				break;
			}
		}
		return false;
	}

    @Override
    public void add_sensors(Real_Sensor[] sensors) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add_sensors'");
    }

    @Override
    public void forwards() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'forwards'");
    }

    @Override
    public void backwards() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'backwards'");
    }

    @Override
    public void calibrate_front() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calibrate_front'");
    }

    @Override
    public void calibrate_back() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calibrate_back'");
    }

    @Override
    public void calibrate_right() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calibrate_right'");
    }

    @Override
    public void calibrate_left() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calibrate_left'");
    }

    @Override
    public void init_calibration() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'init_calibration'");
    }

    @Override
    public boolean get_fastest_instruction(Stack<Node> fast) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get_fastest_instruction'");
    }

    @Override
    public boolean retrieve_reset_wanted() {
        return false;
    }

    @Override
    public boolean is_obstacle_or_wall() {
        return false;
    }

	@Override
	public void sendDesc() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'sendDesc'");
	}

	@Override
	public void setSpeed(float robot_steps_per_second) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'setSpeed'");
	}

    
	
}






