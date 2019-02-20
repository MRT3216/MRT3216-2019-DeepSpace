/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.settings.RobotMap;

/**
 * Add your docs here.
 */
public class PanelManipulator extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private final DoubleSolenoid.Value forward = DoubleSolenoid.Value.kForward; // forward position and open
    private final DoubleSolenoid.Value reverse = DoubleSolenoid.Value.kReverse; // reversed position and closed

    private DoubleSolenoid panelRelease;
    private DoubleSolenoid panelExtend;

    public PanelManipulator() {
        System.out.println("PanelMan. Constructor start");
        panelRelease = new DoubleSolenoid(0, RobotMap.PANEL_RELEASE_O, RobotMap.PANEL_RELEASE_C);
        panelExtend = new DoubleSolenoid(0, RobotMap.PANEL_EXTEND_O, RobotMap.PANEL_EXTEND_C);
        System.out.println("PanelMan. Initialized panel pneumatics");
        initPneumatics();
    }

    public void initPneumatics() {
        releasePanel(false);
        grabPanel(false);
    }

    public void grabPanel(boolean grab) {
        if (grab) {
            if (panelExtend.get() != forward) {
                panelExtend.set(forward);
            }
        } else {
            if (panelExtend.get() != reverse) {
                panelExtend.set(reverse);
            }
        }
    }

    /*
     * @param release a boolean (true = release/open) (false = retract/close)
     */
    public void releasePanel(boolean release) {
        if (release) {
            if (panelRelease.get() != forward) {
                panelRelease.set(forward);
            }
        } else {
            if (panelRelease.get() != reverse) {
                panelRelease.set(reverse);
            }
        }
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
}
