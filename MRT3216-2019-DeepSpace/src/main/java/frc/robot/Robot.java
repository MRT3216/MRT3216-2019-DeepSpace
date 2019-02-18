/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.settings.ShuffleboardController;
import frc.robot.settings.State.BOT;
import frc.robot.subsystems.ADIS16448_IMU;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Lifter;
import frc.robot.subsystems.PanelManipulator;
import frc.robot.subsystems.Shifters;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static BOT currentBot = BOT.MAINBOT;
  public static ADIS16448_IMU imu;
  public static Drivetrain sDrivetrain = new Drivetrain();
  public static Elevator sElevator = new Elevator();
  public static Intake sIntake = new Intake();
  public static PanelManipulator sPanelManipulator = new PanelManipulator();
  public static Shifters sShifters = new Shifters();
  public static Lifter sLift = new Lifter();
  public static Compressor sCompressor;
  public static boolean pressureSwitch;
  public static OI mOI;
  public static ShuffleboardController mSBController;
  public static SerialPort arduino;
  public static Timer arduinoTimer;
  public static DriverStation ds;
  public static DigitalInput dI;

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    mSBController = ShuffleboardController.getInstance();
    mOI = new OI();
    imu = new ADIS16448_IMU();
		imu.calibrate();
		imu.reset();
    sCompressor = new Compressor(1);
    sCompressor.setClosedLoopControl(true);
    pressureSwitch = sCompressor.getPressureSwitchValue();
    sShifters.shift(true);
    sLift.raiseFront(false);
    sLift.raiseRear(false);
    try {
      arduino = new SerialPort(115200, SerialPort.Port.kUSB);
    } catch (Exception e) {
    }

    arduinoTimer = new Timer();
    arduinoTimer.start();
    ds = DriverStation.getInstance();

    // m_chooser.addDefault("Default Auto", new ExampleCommand());
    // chooser.addObject("My Auto", new MyAutoCommand());
    // SmartDashboard.putData("Auto mode", m_chooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    pressureSwitch = sCompressor.getPressureSwitchValue();
    mSBController.update();
    updateArduino();
  }

  /**
   * This function is called once each time the robot enters Disabled mode. You
   * can use it to reset any subsystem information you want to clear when the
   * robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString code to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons to
   * the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
     * switch(autoSelected) { case "My Auto": autonomousCommand = new
     * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
     * ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    /*if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }*/
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    sDrivetrain.setDrive(.5, 0);
  }

  public void updateArduino() {
		try {
			if (arduinoTimer.get() > mSBController.ARDUINO_TIMER) { // send periodically to avoid buffer overflows
				byte mode1 = 0;  //////// structure: 0b<red><blue><fms><auton><teleop><disabled><enabled><attached>
				if (ds.getAlliance() == DriverStation.Alliance.Red)  mode1 |= 0b10000000; // on the red alliance
				if (mSBController.VISION_RING) mode1 |= 0b01000000; // blue alliance 
				if (ds.isAutonomous() && ds.isEnabled())             mode1 |= 0b00100000; // auton mode
				if (ds.isOperatorControl() && ds.isEnabled())        mode1 |= 0b00010000; // teleop mode
				if (false)/*front_vision + 0.5 > matchTimer.get())*/           mode1 |= 0b00001000; // enable front vision leds
				//if (rear_vision + 0.5 > matchTimer.get())            mode1 |= 0b00000100; // enable rear vision leds
				if (ds.isDisabled())                                 mode1 |= 0b00000010; // tell if robot is disabled so we can rainbow at idle

				byte[] mode2 = {mode1}; // needs to be a list
				arduino.write(mode2,1); // send the byte of status over
				
				arduinoTimer.reset(); // reset the timer so we can 
				arduinoTimer.start(); // probably don't need to do this
			}
		} catch (RuntimeException a) { }
	}
}
