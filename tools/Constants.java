package tools;

public class Constants {

    public static class UI {
        public static class Buttons {
            public static final int b_width_default = 33;
            public static final int b_height_default = 16;
            public static final int b_width = (int) (b_width_default);
            public static final int b_height = (int) (b_height_default);
        }

        public static class dragonImages {
            public static final int img_width_default = 128;
            public static final int img_height_default = 120;
            public static final int img_width = (int)(img_width_default);
            public static final int img_height = (int)(img_height_default);
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
        public static final int RUNNING_UP = 1;
        public static final int RUNNING_DOWN = 0;
        public static final int RUNNING_LEFT = 2;
        public static final int RUNNING_RIGHT = 3;
        // public static final int IDLE = 4;
    

        public static int GetSpriteAmount(int playerAction) {
            switch (playerAction) {
                // case IDLE:
                //      return 0;
                case RUNNING_UP:
                case RUNNING_DOWN:
                case RUNNING_LEFT:
                case RUNNING_RIGHT:
                    return 3;
                default:
                    return 0;
            }
        }
    }
}
