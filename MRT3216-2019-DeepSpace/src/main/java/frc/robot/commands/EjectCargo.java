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

public class EjectCargo extends Command {
  private Intake intake = Robot.sIntake;
  public static ShuffleboardController SB = Robot.mSBController;

  public EjectCargo() {
    // Use requires() here to declare subsystem dependencies
    //requires(intake);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //intake.setIntakePower(SB.EJECT_SPEED);
    intake.setEjectPower(SB.EJECT_SPEED);
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
    intake.stopEject();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    intake.stopIntake();
    intake.stopEject();
  }

  @Override
  public void cancel() {
    super.cancel();
    intake.stopIntake();
    intake.stopEject();
  }
}
