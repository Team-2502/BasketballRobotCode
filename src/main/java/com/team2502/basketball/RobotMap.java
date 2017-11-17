package com.team2502.basketball;

public class RobotMap
{
    public static final int UNDEFINED = -1;

    private RobotMap() {}

    public static final class Joystick
    {
        public static final int JOYSTICK_DRIVE_LEFT = 1;
        public static final int JOYSTICK_DRIVE_RIGHT = 0;
        public static final int JOYSTICK_FUNCTION = 2;

        private Joystick() {}

        public static final class Button
        {
            //TODO: Find button for reverse drive
            public static final int REVERSE_DRIVE  = UNDEFINED;
            private Button() {}
        }
    }

    public static final class Electrical
    {
        private Electrical() {}
    }

    public static final class Motor
    {
        public static final int LEFT_DRIVE_TALON_0 = UNDEFINED;
        public static final int LEFT_DRIVE_TALON_1 = UNDEFINED;
        public static final int RIGHT_DRIVE_TALON_0 = UNDEFINED;
        public static final int RIGHT_DRIVE_TALON_1 = UNDEFINED;
        private Motor() {}
    }
}
