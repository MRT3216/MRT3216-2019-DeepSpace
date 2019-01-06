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
  public static int USB_GAMEPAD = 0;

  //Motor Controller Ports
  public static int DRIVETRAIN_LEFT_MOTOR = 0;
  public static int DRIVETRAIN_RIGHT_MOTOR = 1;
  public static int ELEVATOR_MOTOR = 2;

  //PCM Ports
  public static int PANEL_RELEASE_C = 0;
  public static int PANEL_RELEASE_O = 1;
  public static int FRONT_LIFT_C = 2;
  public static int FRONT_LIFT_O = 3;
  public static int REAR_LIFT_C = 4;
  public static int REAR_LIFT_O = 5;
  public static int INTAKE_ARM_C = 6;
  public static int INTAKE_ARM_O = 7;
}
