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
public class Shifters extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private DoubleSolenoid shifter;

  private final DoubleSolenoid.Value high = DoubleSolenoid.Value.kForward;
	private final DoubleSolenoid.Value low = DoubleSolenoid.Value.kReverse;
  
  public Shifters() {
    shifter = new DoubleSolenoid(1, RobotMap.SHIFTER_HIGH, RobotMap.SHIFTER_LOW);
  }

  public void shift(boolean high) {
    if(high) {
      shifter.set(this.high);
    }
    else {
      shifter.set(this.low);
    }
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
  }
}
