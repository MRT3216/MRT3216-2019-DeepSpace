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
	private final DoubleSolenoid.Value forward = DoubleSolenoid.Value.kForward; // forward position and open
	private final DoubleSolenoid.Value reverse = DoubleSolenoid.Value.kReverse; // reversed position and closed

	private DoubleSolenoid panelRelease;
	private DoubleSolenoid panelExtend;
	private DoubleSolenoid frontLift;
	private DoubleSolenoid rearLift;

	public Pneumatics() {
		panelRelease = new DoubleSolenoid(RobotMap.PANEL_RELEASE_O, RobotMap.PANEL_RELEASE_C);
		panelExtend = new DoubleSolenoid(RobotMap.PANEL_EXTEND_O, RobotMap.PANEL_EXTEND_C);
		frontLift = new DoubleSolenoid(RobotMap.FRONT_LIFT_O, RobotMap.FRONT_LIFT_C);
		rearLift = new DoubleSolenoid(RobotMap.REAR_LIFT_O, RobotMap.REAR_LIFT_C);
		initPneumatics();
	}

	public void initPneumatics() {
		releasePanel(false);
		raiseFront(false);
		raiseRear(false);
	}

	/*
	 * @param release a boolean (true = release/open) (false = retract/close)
	 */
	public void releasePanel(boolean release) {
		if (release) {
			if (panelRelease.get() != forward) {
				panelRelease.set(forward);
			}
		} else {
			if (panelRelease.get() != reverse) {
				panelRelease.set(reverse);
			}
		}
	}

	public void raiseFront(boolean raise) {
		if (raise) {
			if (frontLift.get() != forward) {
				frontLift.set(forward);
			}
		} else {
			if (frontLift.get() != reverse) {
				frontLift.set(reverse);
			}
		}
	}

	public void raiseRear(boolean raise) {
		if (raise) {
			if (rearLift.get() != forward) {
				rearLift.set(forward);
			}
		} else {
			if (rearLift.get() != reverse) {
				rearLift.set(reverse);
			}
		}
	}

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
