/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.settings.ShuffleboardController;
import frc.robot.subsystems.Intake;

public class IntakeCargo extends Command {
  public static ShuffleboardController SB = Robot.mSBController;
  private Intake intake = Robot.sIntake;
  private boolean forward;

  public IntakeCargo(boolean forward) {
    // Use requires() here to declare subsystem dependencies
    requires(intake);
    this.forward = forward;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(forward) {
      intake.setIntakePower(SB.INTAKE_SPEED);
    }
    else {
      intake.setEjectPower(-1 * SB.INTAKE_SPEED);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    intake.stopIntake();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    intake.stopIntake();
  }

  @Override
  public void cancel() {
    super.cancel();
    intake.stopIntake();
  }
}
