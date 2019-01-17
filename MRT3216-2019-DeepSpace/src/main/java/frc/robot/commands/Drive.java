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
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.settings.*;

public class Drive extends Command {
	private OI m_oi = Robot.mOI;
	private Drivetrain s_Drivetrain = Robot.sDrivetrain;
	private static NetworkTablesController NT = Robot.mNTController;

	private double leftPowerOld, rightPowerOld;
	private Timer timer = new Timer();
	private double throttleOld;
	private double angleOld;
	private double currentAngle;
	private double angleAdjustment;
	private double heading;
	private boolean hasHeading;

	public Drive() {
		// Use requires() here to declare subsystem dependencies
		requires(s_Drivetrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		s_Drivetrain.stop();
		leftPowerOld = 0.0;
		rightPowerOld = 0.0;

		timer.start();
		timer.reset();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double throttle = m_oi.getLeftY();
		double turn = m_oi.getRightX();
		/*
		 * if (imu != null) { if (turn == 0 && !hasHeading && Math.abs(imu.getAngleZ() -
		 * angleOld) < RobotMap.TURN_RATE_THRESHOLD) { hasHeading = true; heading =
		 * imu.getAngleZ(); } angleOld = imu.getAngleZ();
		 * 
		 * if (turn == 0 && throttle != 0) { driveStraight(throttle, heading); } else {
		 * hasHeading = false; execute(throttle, turn); } } else { execute(throttle,
		 * turn); }
		 */
		execute(throttle, turn);
	}

	private void execute(double throttle, double turn) {
		throttle *= -1;
		double dt = timer.get();
		timer.reset();

		throttle = restrictAcceleration(throttle, throttleOld, dt);

		s_Drivetrain.setPower(throttle - turn, throttle + turn);

		throttleOld = throttle;
	}

	protected void driveStraight(double throttle, double heading) {
		/*
		 * currentAngle = imu.getAngleZ(); angleAdjustment = heading - currentAngle;
		 * log.add("Heading: " + heading, LOG_LEVEL); log.add("Current: " +
		 * currentAngle, LOG_LEVEL); log.add("Adjustment: " + angleAdjustment,
		 * LOG_LEVEL);
		 * 
		 * double turn = angleAdjustment * Constants.DRIVESTRAIGHT_KP; log.add("Turn: "
		 * + turn, LOG_LEVEL); execute(throttle, turn);
		 */
	}

	public static double restrictAcceleration(double goalPower, double currentPower, double dt) {
		double maxDeltaPower = NT.ACCELERATION_MAX * dt;
		double deltaPower = Math.abs(goalPower - currentPower);
		double deltaSign = (goalPower < currentPower) ? -1.0 : 1.0;

		deltaPower = Math.min(maxDeltaPower, deltaPower);
		goalPower = currentPower + deltaSign * deltaPower;

		return goalPower;
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		s_Drivetrain.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		s_Drivetrain.stop();
	}
}
