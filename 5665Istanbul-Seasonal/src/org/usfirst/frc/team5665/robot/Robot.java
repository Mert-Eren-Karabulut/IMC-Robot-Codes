/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5665.robot;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;


public class Robot extends IterativeRobot {
	private DifferentialDrive robotDrive
			= new DifferentialDrive(new Spark(0), new Spark(1));
	private Joystick stick = new Joystick(0);
	private Timer timer = new Timer();
    Spark vinc = new Spark(2);
    Talon asansor = new Talon(5);
    //Talon gripper = new Talon(4);
    DigitalInput switch1 = new DigitalInput(0);
    DigitalInput switch2 = new DigitalInput(1);
    Solenoid solenoid;
    Compressor comp;

    //c.setClosedLoopControl(true);
    //c.setClosedLoopControl(false);
    //boolean enabled = c.enabled();
    //boolean pressureSwitch = c.getPressureSwitchValue();
    //double current = c.getCompressorCurrent();
  
	public void      robotInit() {
		comp = new Compressor();
		comp.start();
		solenoid = new Solenoid(0);
	}

	/**
	 * This function is run once each time the robot enters autonomous mode.
	 */

	public void autonomousInit() {
		timer.reset();
		timer.start();
	}

	
	public void autonomousPeriodic() {
		// Drive for 2 seconds
		if (timer.get() < 5.0) {
			robotDrive.arcadeDrive(0.5, 0.0); // drive forwards half speed
		} else {
			robotDrive.arcadeDrive(0.0, 0.0); // stop robot
		}
	}


	public void teleopInit() {
		
	}

	

	
	public void teleopPeriodic() {
		System.out.println("Compresor durumu " +comp.getCompressorCurrent());

		
		robotDrive.arcadeDrive(stick.getY(), stick.getX());
		if (stick.getRawButton(2)){
			vinc.set(1);
		} 
		else if(stick.getRawButton(3)) {
			vinc.set(-1);
		}
	
		else if (stick.getRawButton(5)){
			asansor.set(-1);
		} 
		else if(stick.getRawButton(6)) {
			asansor.set(1);
		}
		
		else if (stick.getRawButton(4)){
			
		    solenoid.set(false);
		} 
		else  {
		    solenoid.set(true);
		    asansor.set(0.0);
		    vinc.set(0.0);
		}

		if(switch1.get()) {
			asansor.set(0.0);
		}
		else if(switch2.get()) {
			asansor.set(0.0);
		}
		
	}

	/**
	 * This function is called periodically during test mode.
	 */
	
	public void testPeriodic() {
	}
}
