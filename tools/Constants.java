package tools;

import main.GamePanel;

public class Constants {

    public static class UI {
        public static class Buttons {
            public static final int b_width_default = 33;
            public static final int b_height_default = 16;
            public static final int b_width = (int) (b_width_default * 4);
            public static final int b_height = (int) (b_height_default * 4);



        }
    }

    public static class Directions {
        public static final int UP = 0;
        public static final int DOWN = 1;
        public static final int LEFT = 2;
        public static final int RIGHT = 3;
        public static final int LEFT_UP = 4;
        public static final int LEFT_DOWN = 5;
        public static final int RIGHT_UP = 6;
        public static final int RIGHT_DOWN = 7;
    }


    public static class PlayerConstants {
        public static final int IDLE = 0;
        public static final int RUNNING_DOWN = 4;
        public static final int RUNNING_LEFT = 3;
        public static final int RUNNING_RIGHT = 2;



        public static int GetSpriteAmount(int playerAction) {
            switch (playerAction) {
                case IDLE:
                    return 0;
                case RUNNING_DOWN:
                    return 2;
                case RUNNING_LEFT:
                    return 2;
                case RUNNING_RIGHT:
                    return 2;
                default:
                    return 0;
            }
        }

    }
}
