/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.settings;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class ShuffleboardController {
    private static ShuffleboardController instance;
    private static NetworkTableInstance ntInstance = NetworkTableInstance.getDefault();
    private static NetworkTable vision;

    //Singleton Code
    public static ShuffleboardController getInstance() {
        if(instance == null) {
            instance = new ShuffleboardController();
        }
        return instance;
    }

    private ShuffleboardController() {        
        vision = ntInstance.getTable(ntVISION_TABLE);
        SmartDashboard.putString("DB/String 9", "Hello World");
        SmartDashboard.putBoolean(ntVISION_RING, VISION_RING);
    }

    




    /**** These are all the variables for NetworkTables names and paths ****/
        public final String ntTABLE_NAME = "data";

        //Driving Constants
        public final String ntACCELERATION_MAX = "acceleration_max";
        public final String ntDRIVE_STRAIGHT_KP = "drive_straight_kp";
        // Control Constants
        public String ntJOYSTICK_SENSITIVITY = "joystick_sensitivity";
        public String ntJOYSTICK_DEADZONE = "joystick_deadzone";

        // Intake Constants
        public String ntINTAKE_SPEED = "intake_speed";
        public String ntEJECT_SPEED = "eject_speed";

        // Parking
        //public String ntPISTON_SPEED = "piston_speed";

        // Vision
        public String ntVISION_TABLE = "ChickenVision";
        public String ntVISION_RING = "ring";
        public String ntTAPE_DETECTED = "tapeDetected";
        public String ntTAPE_YAW = "tapeYaw";

    /*** These are all the values for the NetworkTables ****/
        // Driving Constants
        public double ACCELERATION_MAX = 3.0;//mTable.getEntry(ntACCELERATION_MAX).getDouble(3.0); // TODO: Calculate new max for new robot
        public double DRIVE_STRAIGHT_KP = 0.01;//mTable.getEntry(ntDRIVE_STRAIGHT_KP).getDouble(0.01);; // TODO: Tune new robot

        // Control Constants
        public double JOYSTICK_SENSITIVITY = 0.8;//mTable.getEntry(ntJOYSTICK_SENSITIVITY).getDouble(0.8);

        public double JOYSTICK_DEADZONE = 0.05;//mTable.getEntry(ntJOYSTICK_DEADZONE).getDouble(0.05);

        // Intake Constants
        public double INTAKE_SPEED = 0.5;//mTable.getEntry(ntINTAKE_SPEED).getDouble(0.5);;
        public double EJECT_SPEED = 0.5;//mTable.getEntry(ntEJECT_SPEED).getDouble(0.5);;

        // Parking Settings
        //public double PISTON_SPEEDS = 0.05;

        // Vision Settings
        public double ARDUINO_TIMER = 0.1;
        public boolean VISION_RING = false;

        public boolean TAPE_DETECTED = false;
        public double TAPE_YAW = 0;
    

        public void update() {
            VISION_RING = SmartDashboard.getBoolean(ntVISION_RING, VISION_RING);
            TAPE_DETECTED = vision.getEntry(ntTAPE_DETECTED).getBoolean(false);
            TAPE_YAW = vision.getEntry(ntTAPE_YAW).getDouble(TAPE_YAW);
           // PISTON_SPEEDS = SmartDashboard.getNumber(ntPISTON_SPEED, PISTON_SPEEDS);
            SmartDashboard.putBoolean("Green Ring", SmartDashboard.getBoolean(ntVISION_RING, false));
            //SmartDashboard.putNumber("PSpeeds", SmartDashboard.getNumber(ntPISTON_SPEED, 0.05));
        }

}
