//TAKEN FROM GITHUB REPO FOR NOW
// specify required imports
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Stack;

public final class Path_Drawer {

	// declare variables like x and y coordinates of robot, the path etc
	static Stack<Node> path;
	static Stack<int[]> stack_unexplored;
	static int robot_X;
	static int robot_Y;

	// default constructor for the class
	Path_Drawer() {
	}

	// update class variables
	public static void update(int robotx, int roboty, Stack<Node> inputPath) {
		robot_X = robotx;
		robot_Y = roboty;
		path = inputPath;
	}

	// update unexplored areas
	public static void update_unexplored(Stack<int[]> unexplored_areas) {
		stack_unexplored = unexplored_areas;
	}

	// remove path after drawing
	public static void remove_path() {
		path.removeAllElements();
	}

	// method to draw the grid for the robot_simulator
	public static void grid_draw(Graphics graphs) {

		// if no unexplored area, stop further processing and return
		if (stack_unexplored == null || stack_unexplored.empty())
			return;

		// set color to cyan
		graphs.setColor(Color.RED);

		// draw the the grids
		for (int i = 0; i < stack_unexplored.size(); i++) {
			graphs.fillRect(10 + (stack_unexplored.get(i)[0]) * Map.size_of_square,
					10 + (stack_unexplored.get(i)[1]) * Map.size_of_square, 38, 38);
		}
	}

	// method to draw path based on graphs specifications obtained
	public static void draw(Graphics graphs) {

		// set the stroke size
		Graphics2D g = (Graphics2D) graphs;
		g.setStroke(new BasicStroke(10));

		// set color to cyan
		graphs.setColor(Color.CYAN);

		// draw the first line from the robot to the first node
		graphs.drawLine(30 + robot_X * Map.size_of_square, 30 + robot_Y * Map.size_of_square,
				30 + path.get(path.size() - 1).getX() * Map.size_of_square,
				30 + path.get(path.size() - 1).getY() * Map.size_of_square);

		// draw the rest of the lines
		for (int i = 0; i < path.size() - 1; i++) {
			graphs.drawLine(30 + path.get(i).getX() * Map.size_of_square, 30 + path.get(i).getY() * Map.size_of_square,
					30 + path.get(i + 1).getX() * Map.size_of_square, 30 + path.get(i + 1).getY() * Map.size_of_square);
		}
	}
}