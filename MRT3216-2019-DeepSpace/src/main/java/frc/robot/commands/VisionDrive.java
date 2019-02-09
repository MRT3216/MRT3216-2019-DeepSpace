/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.settings.ShuffleboardController;
import frc.robot.subsystems.Drivetrain;

public class VisionDrive extends Command {
  private Drivetrain s_Drivetrain = Robot.sDrivetrain;
  public static ShuffleboardController SB = Robot.mSBController;

  public VisionDrive() {
    requires(s_Drivetrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    s_Drivetrain.stop();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    OI m_oi = Robot.mOI; //needed or else the command throws a null pointer.
    double throttle = m_oi.getLeftY();
    double turn = 0.0;
    if(SB.TAPE_DETECTED) {
      double yaw = SB.TAPE_YAW;
      turn = yaw * SB.TAPE_kP;
    }
    s_Drivetrain.setDrive(throttle, turn);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
