import java.awt.*;
public class Map {
    int explored_map = 0;
    double percentage_explored;
    static int map_width = 20;
    static int map_height = 20;
	static int size_of_square = 30; //idk why

    
    int[][] grid_map;
    int[][] simulated_map;
    int[][] map_ranks;
    int[][] optimised_map;


    public static Node[][] node_map = new Node[map_height][map_width];
 
    public void create_map(int[][] grid_map){
        set_nodes();
        set_neighbours();
        this.grid_map = grid_map; 

        //creating another map to experiment and modify without altering the original map
        for(int y = 0; y <simulated_map.length; y++){
            for (int x = 0; x < simulated_map[y].length; x++){
                simulated_map[y][x] = grid_map[y][x];
            }
        }
    }

    public void set_empty(){
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

    public void reset_map(){
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

        set_map_ranking();
        update_map();
    }

    public void update_map() {
		calculate_space();
		set_nodes();
		set_neighbours();
	}

    public void set_nodes() {
		for (int i = 0; i < map_height; i++) {
			for (int j = 0; j < map_width; j++) {

				node_map[i][j] = new Node(j, i);

				if (grid_map[i][j] != 0) {
					node_map[i][j].set_obstacle(true);
				} else
					node_map[i][j].set_obstacle(false);
			}
		}

	}

    public void set_neighbours() {

		for (int i = 0; i < map_height; i++) {
			for (int j = 0; j < map_width; j++) {

				// set node.up for node ontop on the current node
				if (i > 0) {
					Node up = node_map[i - 1][j];
					node_map[i][j].add_neighbours(up);
					node_map[i][j].set_up(up);
				}
				// set node.right for node to the right of current node
				if (j < map_width - 1) {
					Node right = node_map[i][j + 1];
					node_map[i][j].add_neighbours(right);
					node_map[i][j].add_neighbours(right);

				}
				
                // set node.left for node to the left of the current node
				if (j > 0) {
					Node left = node_map[i][j - 1];
					node_map[i][j].add_neighbours(left);
					node_map[i][j].add_neighbours(left);
				}
				
                // set node.down for node below the current node
				if (i < map_height - 1) {
					Node down = node_map[i + 1][j];
					node_map[i][j].add_neighbours(down);
					node_map[i][j].set_down(down);
				}
			}
		}
	}

	public void set_node_waypoint(int x, int y){
		int out_of_bounds = -9999;
		for (int i=-1; i<2; i++){
			for (int j=-1; j<2; j++){
				if(y + i < map_height && x + j < map_width && (x + j) >= 0){
					map_ranks[y+i][x+j] = out_of_bounds;
				}
			}
		}
	}

    public void set_map_ranking() {
		map_ranks = new int[map_height][map_width];

		for (int y = 0; y < map_ranks.length; y++) {
			for (int x = 0; x < map_ranks[y].length; x++) {
				map_ranks[y][x] = 0;

			}
		}

		int out_of_bounds = -1000;
		map_ranks[17][0] = out_of_bounds;
		map_ranks[19][0] = out_of_bounds;
		map_ranks[18][0] = out_of_bounds;
		map_ranks[17][1] = out_of_bounds;
		map_ranks[19][1] = out_of_bounds;
		map_ranks[18][1] = out_of_bounds;
		map_ranks[17][2] = out_of_bounds;
		map_ranks[19][2] = out_of_bounds;
		map_ranks[18][2] = out_of_bounds;

		map_ranks[0][17] = out_of_bounds;
		map_ranks[0][17] = out_of_bounds;
		map_ranks[0][17] = out_of_bounds;
		map_ranks[1][18] = out_of_bounds;
		map_ranks[1][18] = out_of_bounds;
		map_ranks[1][18] = out_of_bounds;
		map_ranks[2][19] = out_of_bounds;
		map_ranks[2][19] = out_of_bounds;
		map_ranks[2][19] = out_of_bounds;

		simulated_map = new int[map_height][map_width];

	}

    public Map(int[][] map) {
		grid_map = map;
		set_nodes();
		set_neighbours();
		set_map_ranking();

	}

    public void calculate_space() {
		Node node;
		for (int i = 0; i < map_height; i++) {
			loop_column: for (int j = 0; j < map_width; j++) {
				node = node_map[i][j];
				node.set_num_spaces(0);

				if (node.is_obstacle()) {
					node.set_num_spaces(0);
					continue;
				}

				for (int x = -1; x < 2; x++) {
					for (int y = -1; y < 2; y++) {
						if (i + x < 0 || j + y >= map_width || i + x >= map_height || j + y < 0) {
							continue loop_column;
						}
						if (node_map[i + x][j + y].is_obstacle())
							continue loop_column;
					}
				}
				node.set_num_spaces(3);
			}
		}
	}

    public void set_ranking(int x, int y, int rank) {
		map_ranks[y][x] += rank;
	}

    public int[][] get_grid_map() {
		return grid_map;
	}

    public Node get_node_coordinates(int x, int y) {
		return node_map[y][x];
	}

    public void set_score_type(){
		for(int y = 0; y < map_ranks.length; y++ ){
			for(int x = 0; x < map_ranks[y].length; x++){
				if(map_ranks[y][x] == 0){
					grid_map[y][x] = ExplorationTypes.convert_type_to_int("NOT_EMPTY");
				} else if(map_ranks[y][x] > 0){
					grid_map[y][x] = ExplorationTypes.convert_type_to_int("OBSTACLE");
				} else if(map_ranks[y][x] < 0) {
					grid_map[y][x] = ExplorationTypes.convert_type_to_int("EMPTY");
				}

			}
		}
	}

	public void set_negative_type(){ //update 
		for (int i = 0; i < grid_map.length; i++){
			for (int j = 0; j < grid_map[i].length; j++){
				if(map_ranks[i][j] == -1){
					optimised_map[i][j] = ExplorationTypes.convert_type_to_int("NOT_EMPTY");
				}
			}
		}
	}

	public void set_map() {
		for(int i = 0; i < optimised_map.length; i++ ){
			for(int j = 0; j < optimised_map[i].length; j++ ){
				optimised_map[i][j] = grid_map[i][j];
			}
		}
	}

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

				if ((i == 0 && j == 12) || (i == 2 && j == 13)
						|| (i == 2 && j == 14 || (i == 0 && j == 13) || (i == 0 && j == 14) || (i == 1 && j == 12)
								|| (i == 1 && j == 13) || (i == 1 && j == 14) || (i == 2 && j == 12))) {
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
				g2d.drawString(Integer.toString(map_ranks[i][j]), 20 + distanceX, 30 + distanceY);
				distanceX += size_of_square;
			}

			distanceY += size_of_square;
		}

	}
}
