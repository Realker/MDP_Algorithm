import java.awt.*;

public class Map {
    int explored_map = 0;
    double percentage_explored;
    static int map_width = 20;
    static int map_height = 20;
	static int size_of_square = 30;

    int[][] grid_map; //final map
    int[][] map_score;

    public Map(int[][] map) {
		grid_map = map;
		create_map(this.grid_map);
        set_map_score();
	}
    
    //Node Map: purpose to set up nodes for every grid and identify if it is a free space or an obstacle
    public static Node[][] node_map = new Node[map_height][map_width]; //create a node map
    public void set_nodes() { //for every node set obstacle is there is 
		for (int i = 0; i < map_height; i++) {
			for (int j = 0; j < map_width; j++) {

				node_map[i][j] = new Node(j, i);

				if (grid_map[i][j] != 0) { 
					node_map[i][j].setObs(true);
				} else
					node_map[i][j].setObs(false);
			}
		}

	}

	public void set_neighbours() { //set node neighbours

		for (int i = 0; i < map_height; i++) {
			for (int j = 0; j < map_width; j++) {

				// set node.up for node ontop on the current node
				if (i > 0) {
					Node up = node_map[i - 1][j];
					node_map[i][j].addNeighbors(up);
					node_map[i][j].setUp(up);
				}
				// set node.right for node to the right of current node
				if (j < map_width - 1) {
					Node right = node_map[i][j + 1];
					node_map[i][j].addNeighbors(right);
					node_map[i][j].addNeighbors(right);

				}
				
                // set node.left for node to the left of the current node
				if (j > 0) {
					Node left = node_map[i][j - 1];
					node_map[i][j].addNeighbors(left);
					node_map[i][j].addNeighbors(left);
				}
				
                // set node.down for node below the current node
				if (i < map_height - 1) {
					Node down = node_map[i + 1][j];
					node_map[i][j].addNeighbors(down);
					node_map[i][j].setDown(down);
				}
			}
		}
	}

    public void calculate_space() {
		Node node;
		for (int i = 0; i < map_height; i++) {
			loop_column: for (int j = 0; j < map_width; j++) {
				node = node_map[i][j];
				node.setIfClear(0); //num of free space 

				if (node.isObs()) { //if the node is obstacle set free space to 0
					node.setIfClear(0);
					continue;
				}

				for (int x = -1; x < 2; x++) { //for every 3 by 3 grid around the node identify it is a boundary or an obstacle if it is number of free spaces is 0
					for (int y = -1; y < 2; y++) {
						if (i + x < 0 || j + y >= map_width || i + x >= map_height || j + y < 0) {
							continue loop_column;
						}
						if (node_map[i + x][j + y].isObs())
							continue loop_column;
					}
				}
				node.setIfClear(3); //else it means that the 3 by 3 grid around the node is empty 
			}
		}
	}

    //TO: create node map and update it with obstacles, free spaces, node neighbours
    public void update_map() {
		calculate_space(); //calculate the number of free spaces around the map
		set_nodes(); //set node obstacles
		set_neighbours(); //set node neighbours
	}

    //final map --> create node map, init orig grid map and final simulated map
    public void create_map(int[][] grid_map){
        set_nodes(); //set node obstacles
        set_neighbours(); //set neighbours
        this.grid_map = grid_map; //initialise a grid_map
    }

    public void set_empty(){ //set an empty map
        this.grid_map = new int[][] { 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
    }

    public void reset_map(){ //reset map (simply specify that it is not empty)
        this.grid_map = new int[][]{
            {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
            {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
            {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
            {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
            {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
            {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
            {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
            {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
            {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
            {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
            {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
            {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
            {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
            {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
            {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
            {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
            {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
            {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
            {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
            {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2}
        };
        set_map_score(); 
        update_map(); //update map with free spaces + nodes and neighbours again
    }


    //SET SCORES
    //given a specific x, y cood check if surrounding nodes are within the boundary
    public void set_node_waypoint(int x, int y){ //3by3 grid around the node --> check if is within boundaries
		int score = -9999;
		for (int i=-1; i<2; i++){
			for (int j=-1; j<2; j++){
				if(y + i < map_height && x + j < map_width && (x + j) >= 0){
					map_score[y+i][x+j] = score;
				}
			}
		}
	}

    public void set_map_score() { //set scores for areas in map
		map_score = new int[map_height][map_width];

		for (int y = 0; y < map_score.length; y++) {
			for (int x = 0; x < map_score[y].length; x++) {
				map_score[y][x] = 0;

			}
		}

		int score = -2000; //means cfm empty
		map_score[17][0] = score;
		map_score[19][0] = score;
		map_score[18][0] = score;
		map_score[17][1] = score;
		map_score[19][1] = score;
		map_score[18][1] = score;
		map_score[17][2] = score;
		map_score[19][2] = score;
		map_score[18][2] = score;
		map_score[0][17] = score;
		map_score[0][17] = score;
		map_score[0][17] = score;
		map_score[1][18] = score;
		map_score[1][18] = score;
		map_score[1][18] = score;
		map_score[2][19] = score;
		map_score[2][19] = score;
		map_score[2][19] = score;

	}


    public void set_score_type(){ //convert string to int value 
		for(int y = 0; y < grid_map.length; y++ ){
			for(int x = 0; x < grid_map[y].length; x++){
				if(map_score[y][x] == 0){ //not empty score is 0
					grid_map[y][x] = ExplorationTypes.convert_type_to_int("NOT_EMPTY");
				} else if(map_score[y][x] > 0){ //obstacle score is > 0
					grid_map[y][x] = ExplorationTypes.convert_type_to_int("OBSTACLE");
				} else if(grid_map[y][x] < 0) { //empty score is 0
					grid_map[y][x] = ExplorationTypes.convert_type_to_int("EMPTY");
				}

			}
		}
	}

    //getters/setters
    public void set_score(int x, int y, int score) {
		map_score[y][x] += score;
	}

    public int[][] get_grid_map() {
		return grid_map;
	}

    public Node get_node_coordinates(int x, int y) {
		return node_map[y][x];
	}


    //PAINT MAP
    public void grid_painting(Graphics g) {

		// Initialise map variables
		int distanceY = 0;
		int distanceX = 0;
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		for (int i = 0; i < grid_map.length; i++) {
			// Paint Y
			distanceX = 0;
			g.drawRect(10, 10 + distanceY, size_of_square, size_of_square);

			for (int j = 0; j < grid_map[i].length; j++) {
				// Paint X
				g.setColor(Color.WHITE);
				g.drawRect(10 + distanceX, 10 + distanceY, size_of_square, size_of_square);

				if ((i == 0 && j == 17) || (i == 2 && j == 18)
						|| (i == 2 && j == 19 || (i == 0 && j == 18) || (i == 0 && j == 19) || (i == 1 && j == 17)
								|| (i == 1 && j == 18) || (i == 1 && j == 19) || (i == 2 && j == 17))) {
					// Draw goal position
					g.setColor(Color.GREEN);
					g.fillRect(10 + distanceX, 10 + distanceY, size_of_square, size_of_square);

				} else if ((i == 18 && j == 2) || (i == 17 && j == 0) || (i == 18 && j == 0) || (i == 17 && j == 2)
						|| (i == 19 && j == 0) || (i == 18 && j == 1) || (i == 17 && j == 1) || (i == 19 && j == 1)
						|| (i == 19 && j == 2)) {
					// Draw start position
					g.setColor(Color.YELLOW);
					g.fillRect(10 + distanceX, 10 + distanceY, size_of_square, size_of_square);

				} else if (grid_map[i][j] == ExplorationTypes.convert_type_to_int("OBSTACLE")) {
					// Draw obstacle
					g.setColor(Color.BLACK);
					g.fillRect(10 + distanceX, 10 + distanceY, size_of_square, size_of_square);

				} else if (grid_map[i][j] == ExplorationTypes.convert_type_to_int("NOT_EMPTY")) {
					g.setColor(Color.LIGHT_GRAY);
					g.fillRect(10 + distanceX, 10 + distanceY, size_of_square, size_of_square);

				}

				g.setColor(Color.BLACK);
				g2d.drawString(Integer.toString(map_score[i][j]), 20 + distanceX, 30 + distanceY);
				distanceX += size_of_square;
			}

			distanceY += size_of_square;
		}

	}
}
