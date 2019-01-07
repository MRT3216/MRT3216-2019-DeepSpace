package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.settings.RobotMap;
import frc.robot.settings.State;
import frc.robot.setting.Constants;

/**
 *
 */
public class Elevator extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private Talon motor;

	public Elevator() {
		motor = new Talon(RobotMap.ELEVATOR_MOTOR);
	}

	public void setPower(double power) {
		power = heightCheck(power);
		power = safetyCheck(power);
		motor.set(power);
	}

	
	//code should get current position of elevator and then decide which direction to go until correct limit switch is hit.

	public void goto(ELEVATOR_POS pos, GAME_PIECE piece) {
		if(piece == State.GAME_PIECE.CARGO) {
			if(pos == State.ELEVATOR_POS.TOP) { 		//GOTO top slot for cargo

			}
			else if(pos == State.ELEVATOR_POS.MIDDLE) { //GOTO middle slot for cargo

			}
			else { 										//GOTO bottom slot for cargo

			}
		}
		else {
			if(pos == State.ELEVATOR_POS.TOP) { 		//GOTO top slot for panel

			}
			else if(pos == State.ELEVATOR_POS.MIDDLE) { //GOTO middle slot for panel

			}
			else { 										//GOTO bottom slot for panel

			}
		}

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
