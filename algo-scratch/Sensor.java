// specifying imports
import java.util.HashMap;

public class Sensor {

    int map_width = 20;
    int map_height = 20;

    boolean check_hit_obstacle;

    int[] sensor_XY = new int[2];
    HashMap<int[], int[]> left_coordinates;
    HashMap<int[], int[]> right_coordinates;

    int sense_range;

    Location_Sensor curr_direction;

    int robot_locationX;
    int robot_locationY;
    int robot_X;
	int robot_Y;

    // parameterized constructor to initialize Sensor
	public Sensor(int sense_range, Location_Sensor curr_direction, int robot_locationX, int robot_locationY, int robot_X,
			int robot_Y) {
		super();
		this.robot_locationX = robot_locationX;
		this.robot_locationY = robot_locationY;
		this.robot_X = robot_X;
		this.robot_Y = robot_Y;
		this.check_hit_obstacle = false;
		this.sense_range = sense_range;
		this.curr_direction = curr_direction;
	}

    // set initial direction
	public void initial_direction() {
		switch (curr_direction) {
		case DOWN:
		    robot_locationX = 1;
			robot_locationY = 0;
			break;
		case BOTTOM_LEFT:
			robot_locationX = 1;
			robot_locationY = -1;
			break;
		case LEFT:
			robot_locationX = 0;
			robot_locationY = -1;
			break;
		case TOP_LEFT:
			robot_locationX = -1;
			robot_locationY = -1;
			break;
		case TOP:
			robot_locationX = -1;
			robot_locationY = 0;
			break;
		case TOP_RIGHT:
			robot_locationX = -1;
			robot_locationY = 1;
			break;
		case RIGHT:
			robot_locationX = 1;
			robot_locationY = -1;
			break;
		case BOTTOM_RIGHT:
			robot_locationX = 1;
			robot_locationY= 1;
			break;
		}
	}

    // change direction to left
	public void change_to_left() {
		switch (curr_direction) {
		case TOP:
			curr_direction = curr_direction.LEFT;
			break;
		case TOP_RIGHT:
			curr_direction = curr_direction.TOP_LEFT;
			break;
		case RIGHT:
			curr_direction = curr_direction.TOP;
			break;
		case BOTTOM_RIGHT:
			curr_direction = curr_direction.TOP_RIGHT;
			break;
		case DOWN:
			curr_direction = curr_direction.RIGHT;
			break;
		case BOTTOM_LEFT:
			curr_direction = curr_direction.BOTTOM_RIGHT;
			break;
		case LEFT:
			curr_direction = curr_direction.DOWN;
			break;
		case TOP_LEFT:
			curr_direction = curr_direction.BOTTOM_LEFT;
			break;
		}

		if (robot_locationX == 1 && robot_locationY == -1) {
			robot_locationX = -1;
			robot_locationY = -1;
		} else if (robot_locationX == 1 && robot_locationY == 0) {
			robot_locationX = 0;
			robot_locationY = -1;
		} else if (robot_locationX == 1 && robot_locationY == 1) {
			robot_locationX = 1;
			robot_locationY = -1;
		} else if (robot_locationX == 0 && robot_locationY == 1) {
			robot_locationX = 1;
			robot_locationY = 0;
		} else if (robot_locationX == -1 && robot_locationY == 1) {
			robot_locationX = 1;
			robot_locationY = 1;
		} else if (robot_locationX == -1 && robot_locationY == 0) {
			robot_locationX = 0;
			robot_locationY = 1;
		} else if (robot_locationX == -1 && robot_locationY == -1) {
			robot_locationX = -1;
			robot_locationY = 1;
		} else if (robot_locationX == 0 && robot_locationY == -1) {
			robot_locationX = -1;
			robot_locationY = 0;
		}
	}

	// change direction to right
	public void change_to_right() {
		switch (curr_direction) {
		case TOP:
			curr_direction = curr_direction.RIGHT;
			break;
		case TOP_RIGHT:
			curr_direction = curr_direction.BOTTOM_RIGHT;
			break;
		case RIGHT:
			curr_direction = curr_direction.DOWN;
			break;
		case BOTTOM_RIGHT:
			curr_direction = curr_direction.BOTTOM_LEFT;
			break;
		case DOWN:
			curr_direction = curr_direction.LEFT;
			break;
		case BOTTOM_LEFT:
			curr_direction = curr_direction.TOP_LEFT;
			break;
		case LEFT:
			curr_direction = curr_direction.TOP;
			break;
		case TOP_LEFT:
			curr_direction = curr_direction.TOP_RIGHT;
			break;
		}

		if (robot_locationX == 1 && robot_locationY == -1) {
			robot_locationX = 1;
			robot_locationY = 1;
		} else if (robot_locationX == 1 && robot_locationY == 0) {
			robot_locationX = 0;
			robot_locationY = 1;
		} else if (robot_locationX == 1 && robot_locationY == 1) {
			robot_locationX = -1;
			robot_locationY = 1;
		} else if (robot_locationX == 0 && robot_locationY == 1) {
			robot_locationX = -1;
			robot_locationY = 0;
		} else if (robot_locationX == -1 && robot_locationY == 1) {
			robot_locationX = -1;
			robot_locationY = -1;
		} else if (robot_locationX == -1 && robot_locationY == 0) {
			robot_locationX = 0;
			robot_locationY = -1;
		} else if (robot_locationX == -1 && robot_locationY == -1) {
			robot_locationX = 1;
			robot_locationY = -1;
		} else if (robot_locationX == 0 && robot_locationY == -1) {
			robot_locationX = 1;
			robot_locationY = 0;
		}

	}

    // update location of the robot
	public void update_location(int x, int y) {
		robot_X = x;
		robot_Y = y;
	}

	// make robot sense the location
	public boolean sense_location(Map map, int x, int y, int robot_distance) {
		boolean hit_obstacle = false;

		int score = 0;

		if (robot_distance == 1)
			score = -80;
		else if (robot_distance  == 2)
			score = -30;
		else if (robot_distance  == 3)
			score = -9;
		else if (robot_distance  == 4)
			score = -4;
		else if (robot_distance  == 5)
			score = -2;
		else if (robot_distance  == 6)
			score = -2;
		else if (robot_distance  == 7)
			score = -2;
		else if (robot_distance  == 8)
			score = -2;
		else if (robot_distance  == 9)
			score = -2;

		if (x >= 0 && y >= 0 && x < map_width && y < map_height) {
			// make the score positive to indicate that it is a block
			if (map.grid_map[y][x] == ExplorationTypes.convert_type_to_int("OBSTACLE")
					|| map.grid_map[y][x] == ExplorationTypes.convert_type_to_int("NOT_OBSTACLE")) {
				score = -score;
				hit_obstacle = true;
			}
			map.set_score(x, y, score);
		} else
			hit_obstacle = true;

		return hit_obstacle;
	}

	public boolean Sense(Map map, int data, int[][] not_working) {
		// have to make sure does not overshoot boundary of environment
		int new_X = 0;
		int new_Y = 0;

		// is true after robot hits an obstacle, to prevent further sensing
		boolean hit_obstacle = false;
		boolean wall_hit_extra = false;

		for (int i = 1; i < sense_range + 1; i++) {
			// make sure it is in the map sense_range and bound
			if (curr_direction == Location_Sensor.LEFT) {
				new_X = robot_X + robot_locationX - i;
				new_Y = robot_Y + robot_locationY;
			} else if (curr_direction == Location_Sensor.TOP) {
				new_X = robot_X + robot_locationX;
				new_Y = robot_Y + robot_locationY - i;
			} else if (curr_direction == Location_Sensor.RIGHT) {
				new_X = robot_X + robot_locationX + i;
				new_Y = robot_Y + robot_locationY;
			} else {
				new_X = robot_X + robot_locationX;
				new_Y = robot_Y + robot_locationY + i;
			}

			// hit_obstacle will be true when sense_location returns a true
			// that indicates an obstacle has been encountered
			if (!hit_obstacle) {
				hit_obstacle = sense_location(map, new_X, new_Y, i);
				if (sense_location(map, new_X, new_Y, 0) && i == 1)
					wall_hit_extra = true;
			} else
				// send a 0 to signify that this is behind a wall
				sense_location(map, new_X, new_Y, 0);
		}

		// update the map score after sensing
		map.set_score_type();
		return wall_hit_extra;
	}

}
