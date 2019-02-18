package frc.robot.subsystems;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
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
    private static final double kP = 0.134;     // The feedforward gain for the Talon SRX
    private static final double WHEEL_M = 0.1016;  

	private static MotionProfileStatus leftMPStatus;
	private static MotionProfileStatus rightMPStatus;
	private static ArrayList<double[]> leftProfile;     // pos., vel., accel.
    private static ArrayList<double[]> rightProfile;

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

	public void initAuto() {/*
		rightMaster.configPeakOutputForward(1, 0);
		rightMaster.configPeakOutputReverse(-1, 0);
		leftMaster.configPeakOutputForward(1, 0);
		leftMaster.configPeakOutputReverse(-1, 0);
		
		leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);

        resetEncoders();

		leftMPStatus = new MotionProfileStatus();
		rightMPStatus = new MotionProfileStatus();
		
		leftMaster.getMotionProfileStatus(leftMPStatus);
        rightMaster.getMotionProfileStatus(rightMPStatus);

        leftMaster.config_kF(0, kP, 20);
        rightMaster.config_kF(0, kP, 20);

        leftProfile = readCSVMotionProfileFile("/home/lvuser/paths/path_left.csv");
		rightProfile = readCSVMotionProfileFile("/home/lvuser/paths/path_right.csv");
		if (leftMPStatus.hasUnderrun) {
			leftMaster.clearMotionProfileHasUnderrun(0);
		}
		if (rightMPStatus.hasUnderrun){
			rightMaster.clearMotionProfileHasUnderrun(0);
		}
		leftMaster.clearMotionProfileTrajectories();
		leftMaster.configMotionProfileTrajectoryPeriod(0, 10);
		rightMaster.clearMotionProfileTrajectories();
		rightMaster.configMotionProfileTrajectoryPeriod(0, 10);

	loadTrajectoryToTalon(leftMaster, leftProfile);
	loadTrajectoryToTalon(leftMaster, rightProfile);*/
	}

	private void resetEncoders() {
        leftMaster.setSelectedSensorPosition(0, 0, 10);
        rightMaster.setSelectedSensorPosition(0, 0, 10);
	}

	private void loadTrajectoryToTalon(TalonSRX talonSRX, ArrayList<double[]> profile) {
        /*TrajectoryPoint point = new TrajectoryPoint();

        for (int i = 0; i < profile.size(); i++) {
            point.position = metersToTicks(profile.get(i)[0]);     // meters -> rotations -> ticks
            point.velocity = metersToTicks(profile.get(i)[1]) / 10.0;     // meters/second -> ticks/sec -> ticks/100ms
            point.timeDur = TrajectoryPoint.TrajectoryDuration.Trajectory_Duration_50ms;
            point.profileSlotSelect0 = 0;

            point.zeroPos = i == 0;
            point.isLastPoint = (i + 1) == profile.size();

            talonSRX.pushMotionProfileTrajectory(point);
		}*/
		}
		
	private static ArrayList<double[]> readCSVMotionProfileFile(String path) {

        ArrayList<double[]> pathSegments = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String line;
            String csvDelimiter = ",";

            while ((line = br.readLine()) != null) {
                String[] segment = line.split(csvDelimiter);

                double[] convertedSegment = Arrays.stream(segment)
                        .mapToDouble(Double::parseDouble)
                        .toArray();

                pathSegments.add(convertedSegment);
            }

        } catch (IOException ex) {
        }

        return pathSegments;

    }
}