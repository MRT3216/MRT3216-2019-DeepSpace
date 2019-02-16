package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Robot;
import frc.robot.commands.Drive;
import frc.robot.settings.RobotMap;
import frc.robot.settings.ShuffleboardController;

/**
 *
 */
public class Drivetrain extends Subsystem {
	// DRIVETRAIN SUBSYSTEM COPIED OVER FROM 2018. ALL ROBOT SPECIFIC CODE IS
	// COMMENTED OUT
	// LOGGING INFORMATION IS ALSO COMMENTED OUT

	/** Configuration Constants ***********************************************/
	// private static final Logger.Level LOG_LEVEL = RobotMap.LOG_DRIVETRAIN;

	/** Instance Variables ****************************************************/
	// private Logger log = new Logger(LOG_LEVEL, getName());
	double leftPowerOld, rightPowerOld;
	Timer timer = new Timer();
	private TalonSRX leftMaster, rightMaster;
	private VictorSPX leftSlave, rightSlave;
	DifferentialDrive m_drive;
	private ShuffleboardController SB = ShuffleboardController.getInstance();

	public Drivetrain() {
		// log.add("Constructor", LOG_LEVEL);
		
		 //if (Robot.currentBot == BOT.MAINBOT) { 
			leftMaster = new TalonSRX(RobotMap.CAN_LEFT_TALONSRX);
			leftSlave = new VictorSPX(RobotMap.CAN_LEFT_VICTORSPX);
			leftSlave.follow(leftMaster);
			rightMaster = new TalonSRX(RobotMap.CAN_RIGHT_TALONSRX);
			rightMaster.setInverted(true);
			rightSlave = new VictorSPX(RobotMap.CAN_RIGHT_VICTORSPX);
			rightSlave.setInverted(true);
			rightSlave.follow(rightMaster);
	}

	private void initMotor(Talon motor, boolean reverse) {
		motor.setInverted(reverse); // affects percent Vbus mode???
	}

	@Override
	public void initDefaultCommand() {
		/*
		 * if (RobotMap.DriveMode.TANK == RobotMap.currentDriveMode) {
		 * setDefaultCommand(new Drivetrain_TankDrive()); } else { setDefaultCommand(new
		 * Drivetrain_ArcadeDrive()); }
		 */
		setDefaultCommand(new Drive());
	}

	/** Methods for setting the motors *************************************/

	public void setDrive(double forward, double turn) {
		if(SB.DRIVE_MODE) {
			m_drive.arcadeDrive(forward, turn);
		} else {
			leftMaster.set(ControlMode.PercentOutput, forward, DemandType.ArbitraryFeedForward, +turn);
			rightMaster.set(ControlMode.PercentOutput, forward, DemandType.ArbitraryFeedForward, -turn);
		}
	}

	public void driveStraight(double power) {

	}

	public void stop() {
		setDrive(0.0, 0.0);
	}

	private double safetyCheck(double power) {
		power = Math.min(1.0, power);
		power = Math.max(-1.0, power);
		return power;
	}
}