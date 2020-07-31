/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team7681.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	double hiz = (0.5); 
	double donmehizi = (0.6); 
	SpeedControllerGroup leftDrive;
	SpeedControllerGroup rightDrive;
	DifferentialDrive differentialDrive;
	XboxController xboxController;
	Victor leftFront;
	Victor leftBack;
	Victor rightFront; 
	Victor rightBack;	
	Victor upMotor2;
	Victor upMotor12;
	Victor inMotor;
	Victor outMotor;
	Timer zaman;
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		upMotor12 = new Victor(6); 
		upMotor2 = new Victor(7);
		inMotor = new Victor(4); 
		outMotor  = new Victor(5);
		leftFront = new Victor(0);	
		leftBack = new Victor(1);
	     zaman = new Timer();
		rightFront = new Victor(2);
	    
		rightBack = new Victor(3);
	    
		SpeedControllerGroup leftDrive = new SpeedControllerGroup(leftFront, leftBack);
	    
		SpeedControllerGroup rightDrive = new SpeedControllerGroup(rightFront, rightBack);
	    
		 differentialDrive = new DifferentialDrive(leftDrive, rightDrive);
	     xboxController = new XboxController(0);
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		zaman.reset();
		zaman.start();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		if(zaman.get() < 5) {
			differentialDrive.arcadeDrive(0.5, 0.0);
			
		}
		}
	

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		
		inMotor.set(-xboxController.getRawAxis(1));
		outMotor.set(-xboxController.getRawAxis(1));
		
		if (xboxController.getAButton()) {
			
			((Victor) upMotor12).set(0.6);
			upMotor2.set(0.4);
			
		} else if (xboxController.getYButton()) {
			
			((Victor) upMotor12).set(-0.6);
			upMotor2.set(-0.6);
			
		} else {
			
			((Victor) upMotor12).set(0.0);
			upMotor2.set(0.0);
			
		}
		
	
	 differentialDrive.arcadeDrive(-xboxController.getRawAxis(3)*hiz, xboxController.getRawAxis(2)*donmehizi);
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
