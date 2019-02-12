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
	public static final int BOTTOM_LIMIT = 9;

	// PWM Ports
	public static final int ELEVATOR_MOTOR = 7;
	public static final int INTAKE_MOTOR = 3;//?
	public static final int EJECT_MOTOR = 4;//?

	// CAN Device IDS
	public static final int CAN_LEFT_TALONSRX = 1;
	public static final int CAN_LEFT_VICTORSPX = 1;
	public static final int CAN_RIGHT_TALONSRX = 0;
	public static final int CAN_RIGHT_VICTORSPX = 0;

	// PCM Ports
	public static final int PANEL_RELEASE_C = 0;
	public static final int PANEL_RELEASE_O = 1;
	public static final int FRONT_LIFT_C = 2;
	public static final int FRONT_LIFT_O = 3;
	public static final int REAR_LIFT_C = 4;
	public static final int REAR_LIFT_O = 5;
	public static final int PANEL_EXTEND_C = 3;
	public static final int PANEL_EXTEND_O = 2;
}
