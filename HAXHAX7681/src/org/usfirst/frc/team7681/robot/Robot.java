package org.usfirst.frc.team7681.robot;

import edu.wpi.first.wpilibj.IterativeRobot; 

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.MotorSafety; 
@SuppressWarnings("unused")
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
			Victor upMotor1 = new Victor(6); 
			Victor upMotor2 = new Victor(7);
			Victor inMotor = new Victor(4); 
			Victor outMotor  = new Victor(5);
			private Object upMotor12;
			public void robotInit() {
				
			      
				Victor leftFront = new Victor(0);
			       
				Victor leftBack = new Victor(1);
			      
				Victor rightFront = new Victor(2);
			    
				Victor rightBack = new Victor(3);
			    
				SpeedControllerGroup leftDrive = new SpeedControllerGroup(leftFront, leftBack);
			    
				SpeedControllerGroup rightDrive = new SpeedControllerGroup(rightFront, rightBack);
			    
				DifferentialDrive differentialDrive = new DifferentialDrive(leftDrive, rightDrive);
			        XboxController xboxController = new XboxController(0);
			}
			public void autonomousInit() {
			}
			public void autonomousPeriodic() {
			}
			public void teleopPeriodic() {	
				
					inMotor.set(-xboxController.getRawAxis(1));
					outMotor.set(-xboxController.getRawAxis(1));
					
					upMotor12 = null;
					if (xboxController.getYButton()) {
						
						((Victor) upMotor12).set(0.6);
						upMotor2.set(0.4);
						
					} else if (xboxController.getYButton()) {
						
						((Victor) upMotor12).set(-0.6);
						upMotor2.set(-0.6);
						
					} else {
						
						((Victor) upMotor12).set(0.0);
						upMotor2.set(0.0);
						
					}
					
				
				 differentialDrive.arcadeDrive(-xboxController.getRawAxis(5)*hiz, xboxController.getRawAxis(4)*donmehizi);
			}}