package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.subsystems.Elevator;

/**
 *
 */
public class Elevator_Drive extends Command {

	Elevator elevator = Robot.sElevator;
	OI oi = Robot.mOI;

	public Elevator_Drive() {
		requires(elevator);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		elevator.stop();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double power = oi.getStickY();
		// log.add("Joystick Power: " + power, LOG_LEVEL);
		elevator.setPower(power);
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