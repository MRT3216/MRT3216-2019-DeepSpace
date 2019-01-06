package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.Robot;
import frc.robot.settings.*;
import frc.robot.OI;

/**
 *
 */
public class Elevator extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	OI m_oi = Robot.m_oi;
	private Talon motor;

	public Elevator() {
		motor = new Talon(RobotMap.ELEVATOR_MOTOR);
	}

	public void setPower(double power) {
		power = heightCheck(power);
		power = safetyCheck(power);
		motor.set(power);
	}

	public void stop() {
		setPower(0.0);
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

	private double heightCheck(double power) {
		return power; //TODO: implement top and bottom limit switches and check to make sure elevator doesn't move past them.
	}

	@Override
	public void initDefaultCommand() {
		//setDefaultCommand(new Elevator_Move());
	}
}
