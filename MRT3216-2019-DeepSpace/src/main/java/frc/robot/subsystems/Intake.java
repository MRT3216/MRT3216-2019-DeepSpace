/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.settings.*;

/**
 * Add your docs here.
 */
public class Intake extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private VictorSP intakeMotor;
  private VictorSP ejectMotor;

  public Intake() {
    intakeMotor = new VictorSP(RobotMap.INTAKE_MOTOR);
    ejectMotor = new VictorSP(RobotMap.EJECT_MOTOR);
  }

  public void setIntakePower(double power) {
    power = safetyCheck(power);
    intakeMotor.set(power);
  }  
  
  public void setEjectPower(double power) {
    power = safetyCheck(power);
    ejectMotor.set(power);
  }

  private double safetyCheck(double power) {
    power = Math.min(1.0, power);
    power = Math.max(-1.0, power);
    /*
     * if((!topSwitch.get() && power > 0) || (!bottomSwitch.get() && power < 0)) {
     * return power; } else { return 0.0; }
     */
    return power;
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void stopIntake() {
    intakeMotor.set(0);
  }

  public void stopEject() {
    ejectMotor.set(0);
  }

  public void stop() {
    stopIntake();
    stopEject();
  }
}
