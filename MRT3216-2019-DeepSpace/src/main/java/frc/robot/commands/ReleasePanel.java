/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.subsystems.PanelManipulator;

public class ReleasePanel extends InstantCommand {
  private boolean extend;
  private PanelManipulator sPanelManipulator = Robot.sPanelManipulator;

  public ReleasePanel(boolean extend) {
    super();
    requires(sPanelManipulator);
    // Use requires() here to declare subsystem dependencies
    this.extend = extend;
  }

  @Override
  protected void initialize() {
    sPanelManipulator.releasePanel(extend);
  }
}
