/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.settings;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;

/**
 * Add your docs here.
 */
public class NetworkTablesController {
    private static NetworkTablesController instance;

    private NetworkTableInstance mNTInstance;
    private NetworkTable mTable;

    //Singleton Code
    public static NetworkTablesController getInstance() {
        if(instance == null) {
            instance = new NetworkTablesController();
        }
        return instance;
    }

    private NetworkTablesController() {
        mNTInstance = NetworkTableInstance.getDefault();
        mTable = mNTInstance.getTable(ntTABLE_NAME);
        populateNT();
    }

    public NetworkTable getNetworkTable() {
        return mTable;
    }

    // Set all the default values of objects here
    private void populateNT() {
        mTable.getEntry(ntACCELERATION_MAX).getDouble(3.0);
        
        
        
        
    }

    public void update() {
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

    /*** These are all the values for the NetworkTables ****/
        // Driving Constants
        public double ACCELERATION_MAX =  mTable.getEntry(ntACCELERATION_MAX).getDouble(3.0); // TODO: Calculate new max for new robot
        public double DRIVE_STRAIGHT_KP = mTable.getEntry(ntDRIVE_STRAIGHT_KP).getDouble(0.01);; // TODO: Tune new robot

        // Control Constants
        public double JOYSTICK_SENSITIVITY = mTable.getEntry(ntJOYSTICK_SENSITIVITY).getDouble(0.8);

        public double JOYSTICK_DEADZONE = mTable.getEntry(ntJOYSTICK_DEADZONE).getDouble(0.05);

        // Intake Constants
        public double INTAKE_SPEED = mTable.getEntry(ntINTAKE_SPEED).getDouble(0.5);;
        public double EJECT_SPEED = mTable.getEntry(ntEJECT_SPEED).getDouble(0.5);;




}
