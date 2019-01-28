package frc.robot.subsystems;


import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.commands.Drive;
import frc.robot.settings.RobotMap;
import frc.robot.settings.State.BOT;


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
	private SpeedController leftMotors, rightMotors;
	// private Logger log = new Logger(LOG_LEVEL, getName());
	double leftPowerOld, rightPowerOld;
	Timer timer = new Timer();

	public Drivetrain() {
		// log.add("Constructor", LOG_LEVEL);
		
		 if (Robot.currentBot == BOT.MAINBOT) { 
			leftMotors = new TalonSRX(2);
		 	rightMotors = new VictorSP(RobotMap.PWM_RIGHT_MOTOR);
		 
		 	initMotor((VictorSP) leftMotors, RobotMap.REVERSE_LEFT_MOTOR);
			initMotor((VictorSP) rightMotors, RobotMap.REVERSE_RIGHT_MOTOR); } 
		else {
			 leftMotors = new Talon(RobotMap.DRIVETRAIN_LEFT_MOTOR);
			 rightMotors = new Talon(RobotMap.DRIVETRAIN_RIGHT_MOTOR);
		 
			 initMotor((Talon) leftMotors, false); 
			 initMotor((Talon) rightMotors, false);
			 }
		rightMotors.setInverted(true);
		leftMotors.stopMotor();
		rightMotors.stopMotor();
	}

	private void initMotor(Talon motor, boolean reverse) {
		motor.setInverted(reverse); // affects percent Vbus mode???
	}

	private void initMotor(VictorSP motor, boolean reverse) {
		motor.setInverted(reverse);
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
	public void setPower(double leftPower, double rightPower) {
		this.setPower(leftPower, rightPower, false);
	}

	public void setPower(double leftPower, double rightPower, boolean driveStraight) {
		leftPower = safetyCheck(leftPower);
		rightPower = safetyCheck(rightPower);

		leftMotors.set(leftPower);
		rightMotors.set(rightPower);

		leftPowerOld = leftPower;
		rightPowerOld = rightPower;
	}

	public void driveStraight(double power) {

	}

	public void stop() {
		setPower(0.0, 0.0);
	}

	private double safetyCheck(double power) {
		power = Math.min(1.0, power);
		power = Math.max(-1.0, power);
		return power;
	}
}