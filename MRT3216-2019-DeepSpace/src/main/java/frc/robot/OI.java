/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.EjectCargo;
import frc.robot.commands.IntakeCargo;
import frc.robot.settings.Constants;
import frc.robot.settings.RobotMap;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
  // Joystick stick = new Joystick(port);
  // Button button = new JoystickButton(stick, buttonNumber);

  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create custom triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());

  private Gamepad gamepad;
  private ControlStick controlStick;

  public OI() {

    gamepad = new Gamepad(RobotMap.USB_GAMEPAD);
    controlStick = new ControlStick(RobotMap.USB_CONTROL_STICK);
    controlStick.Trigger.whileHeld(new IntakeCargo());//not sure the difference between whileHeld() and whileActive() Java Docs aren't too helpful or clear
    controlStick.button2.whileHeld(new EjectCargo());

  }

  // Gamepad Functions
  public double getLeftY() {
    double joystickValue = gamepad.getRawAxis(Gamepad.LEFT_JOY_Y_AXIS);
    // joystickValue = checkDeadZone(joystickValue);
    joystickValue = scaleJoystick(joystickValue);
    // log.add("getLeftY (" + joystickValue + ")", LOG_LEVEL);
    // log.add("Deadzone = " + RobotMap.JOYSTICK_DEADZONE, LOG_LEVEL);
    return joystickValue;
  }

  public double getRightX() {
    double joystickValue = gamepad.getRawAxis(Gamepad.RIGHT_JOY_X_AXIS);
    // joystickValue = checkDeadZone(joystickValue);
    joystickValue = scaleJoystick(joystickValue);
    // log.add("getRightX (" + joystickValue + ")", LOG_LEVEL);
    return joystickValue;
  }

  // Control Stick Functions
  public double getStickY() {
    double joystickValue = controlStick.getRawAxis(ControlStick.JOYSTICK_Y_AXIS);
    // TODO - add checkDeadZone (if needed)
    // log.add("StickY: " + joystickValue, LOG_LEVEL);
    return joystickValue;
  }

  private double scaleJoystick(double joystickValue) {
    joystickValue = checkDeadZone(joystickValue);
    joystickValue = scaleSensitivity(joystickValue);
    return joystickValue;
  }

  // Scale Joystick Sensitivity
  // a = sensitivity, and x is the power parameter
  // y = a(x^3) + (1-a)x
  private double scaleSensitivity(double x) {
    double a = Constants.JOYSTICK_SENSITIVITY;
    return a * (Math.pow(x, 3)) + (a - 1) * x;
  }

  private double checkDeadZone(double joystickValue) {
    if (Math.abs(joystickValue) < Constants.JOYSTICK_DEADZONE) {
      joystickValue = 0.0;
    }
    return joystickValue;
  }
}
