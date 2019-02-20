/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.settings;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;

    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;

    // USB Ports
    public static final int USB_GAMEPAD = 0;
    public static final int USB_CONTROL_STICK = 1;

    // Digital Input
    public static final int BOTTOM_LIMIT = 8;
    public static final int TOP_LIMIT = 9;

    // PWM Ports
    public static final int ELEVATOR_MOTOR = 7;
    public static final int INTAKE_MOTOR = 8;
    public static final int EJECT_MOTOR = 9;
    public static final int LIFT_MOTOR = 0;

    // CAN Device IDS
    public static final int CAN_LEFT_TALONSRX = 1;
    public static final int CAN_LEFT_VICTORSPX = 1;
    public static final int CAN_RIGHT_TALONSRX = 0;
    public static final int CAN_RIGHT_VICTORSPX = 0;

    // PCM Ports
    public static final int PANEL_RELEASE_C = 3;// 0
    public static final int PANEL_RELEASE_O = 2;// 0
    public static final int FRONT_LIFT_C = 5;// 0
    public static final int FRONT_LIFT_O = 4;// 0
    public static final int REAR_LIFT_C = 3;// 1
    public static final int REAR_LIFT_O = 2;// 1
    public static final int PANEL_EXTEND_C = 1;// 0
    public static final int PANEL_EXTEND_O = 0;// 0
    public static final int SHIFTER_LOW = 4;// 1
    public static final int SHIFTER_HIGH = 5;// 1
}
