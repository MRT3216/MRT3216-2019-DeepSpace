/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.settings.RobotMap;

/**
 * Add your docs here.
 */
public class Pneumatics extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private final DoubleSolenoid.Value forward = DoubleSolenoid.Value.kForward; //forward position and open
	private final DoubleSolenoid.Value reverse = DoubleSolenoid.Value.kReverse; //reversed position and closed

  private DoubleSolenoid panel_release;
  private DoubleSolenoid front_lift;
  private DoubleSolenoid rear_lift;

  public Pneumatics() {
    panel_release = new DoubleSolenoid(RobotMap.PANEL_RELEASE_O, RobotMap.PANEL_RELEASE_C);
    front_lift = new DoubleSolenoid(RobotMap.FRONT_LIFT_O, RobotMap.FRONT_LIFT_C);
    rear_lift = new DoubleSolenoid(RobotMap.REAR_LIFT_O, RobotMap.REAR_LIFT_C);
    initPneumatics();
  }

  public void initPneumatics() {
    releasePanel(false);
    raiseFront(false);
    raiseRear(false );
  }

  /*
  * @param release a boolean (true = release/open) (false = retract/close)
  */
  public void releasePanel(boolean release) {
    if(release) {
      if (panel_release.get() != forward) {
        panel_release.set(forward);
      }
    }
    else {
      if (panel_release.get() != reverse) {
        panel_release.set(reverse);
      }
    }
  }

public void raiseFront(boolean raise) {
  if(raise) {
    if (front_lift.get() != forward) {
      front_lift.set(forward);
    }
  }
  else {
    if (front_lift.get() != reverse) {
      front_lift.set(reverse);
    }
  }
}

public void raiseRear(boolean raise) {
  if(raise) {
    if (rear_lift.get() != forward) {
      rear_lift.set(forward);
    }
  }
  else {
    if (rear_lift.get() != reverse) {
      rear_lift.set(reverse);
    }
  }
}

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
