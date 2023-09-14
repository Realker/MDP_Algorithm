import java.awt.*;
import javax.swing.*;


// inherit from JComponent, the base class for all Swing components
// use this the visualize and create the robot_simulator for various tasks
public class Visualize extends JComponent {
    public RobotInterface robot;

	// default constuctor
	public Visualize() {
	}

	// paramterized constructor
	public Visualize(RobotInterface robot) {
		this.robot = robot;
	}

	// return the robot
	public RobotInterface get_robot() {
		return robot;
	}

	// specify the robot
	public void set_robot(RobotInterface robot) {
		this.robot = robot;
	}

	// method to draw the components and perform visualization
	protected void paintComponent(Graphics g) {
		robot.map.grid_painting(g);
		robot.paint_robot(g);
		Path_Drawer.grid_draw(g);
		Path_Drawer.draw(g);
		super.paintComponent(g);
	}
}
