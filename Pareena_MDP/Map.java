public class Map {
    int explored_map = 0;
    double percentage_explored;
    static int map_width = 15;
    static int map_height = 20;
    int grid_size = 30; 
    
    int[][] grid_map;
    int[][] simulated_map;
    int[][] map_ranks;
    int[][] map_array_2;

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
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
    }

    public void reset_map(){
        this.grid_map = new int[][]{
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}
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

    public void set_map_ranking() {
		map_ranks = new int[map_height][map_width];

		for (int y = 0; y < map_ranks.length; y++) {
			for (int x = 0; x < map_ranks[y].length; x++) {
				map_ranks[y][x] = 0;

			}
		}

		int confirmed_score = -1000;
		map_ranks[17][0] = confirmed_score;
		map_ranks[19][0] = confirmed_score;
		map_ranks[18][0] = confirmed_score;
		map_ranks[17][1] = confirmed_score;
		map_ranks[19][1] = confirmed_score;
		map_ranks[18][1] = confirmed_score;
		map_ranks[17][2] = confirmed_score;
		map_ranks[19][2] = confirmed_score;
		map_ranks[18][2] = confirmed_score;

		map_ranks[0][12] = confirmed_score;
		map_ranks[0][13] = confirmed_score;
		map_ranks[0][14] = confirmed_score;
		map_ranks[1][12] = confirmed_score;
		map_ranks[1][13] = confirmed_score;
		map_ranks[1][14] = confirmed_score;
		map_ranks[2][12] = confirmed_score;
		map_ranks[2][13] = confirmed_score;
		map_ranks[2][14] = confirmed_score;

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

    

    //need to create a method to update map with undersirable ranks to 'obstacles'/'not empty'
    //do we need to create another duplicate grid map
}
