package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.Elevator_Drive;
import frc.robot.settings.RobotMap;
import frc.robot.settings.State;
import frc.robot.settings.State.ELEVATOR_POS;
import frc.robot.settings.State.GAME_PIECE;

/**
 *
 */
public class Elevator extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private Victor motor;
	private DigitalInput bottomLimit;

	public Elevator() {
		motor = new Victor(RobotMap.ELEVATOR_MOTOR);
		bottomLimit = new DigitalInput(RobotMap.BOTTOM_LIMIT);
	}

	public void setPower(double power) {
		power = heightCheck(power);
		power = safetyCheck(power);
		motor.set(power);
	}

	// code should get current position of elevator and then decide which direction
	// to go until correct limit switch is hit.
	public void goTo(ELEVATOR_POS pos, GAME_PIECE piece) {
		if (piece == State.GAME_PIECE.CARGO) {
			if (pos == State.ELEVATOR_POS.TOP) { // GOTO top slot for cargo

			} else if (pos == State.ELEVATOR_POS.MIDDLE) { // GOTO middle slot for cargo

			} else { // GOTO bottom slot for cargo

			}
		} else {
			if (pos == State.ELEVATOR_POS.TOP) { // GOTO top slot for panel

			} else if (pos == State.ELEVATOR_POS.MIDDLE) { // GOTO middle slot for panel

			} else { // GOTO bottom slot for panel

			}
		}

	}

	public void stop() {
		setPower(0.0);
	}

	private double safetyCheck(double power) {
		power = Math.min(1.0, power);
		power = Math.max(-1.0, power);
		
		if((!bottomLimit.get() && power > 0)) {
			return 0.0; 
		} 
		else { 
			return power; 
		}
	}

	private double heightCheck(double power) {
		return power; // TODO: implement top and bottom limit switches and check to make sure elevator
						// doesn't move past them.
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new Elevator_Drive());
	}
}
