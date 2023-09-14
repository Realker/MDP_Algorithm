public enum ExplorationTypes {
    EMPTY(0), OBSTACLE(1), NOT_EMPTY(2), NOT_OBSTACLE(3);

    int type = 2; //initially set all nodes to unexplored

    private ExplorationTypes(int type){
        this.type = type;
    }

    public static int convert_type_to_int(String str){
        switch(str){
            case "NOT_OBSTACLE":
                return 3;
            case "NOT_EMPTY":
                return 2;
            case "OBSTACLE":
                return 1;
            case "EMPTY":
                return 0;
        }

        return -1;
    }

    public int get_type(){
        return this.type;
    }


    
}
