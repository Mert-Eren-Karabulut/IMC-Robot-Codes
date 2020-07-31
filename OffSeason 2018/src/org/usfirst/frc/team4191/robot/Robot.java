package org.usfirst.frc.team4191.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	final String orta = "orta";
	final String sag = "sag";
	final String sol = "sol";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();
	RobotDrive imcDrive;
	double move, rotation;
	Joystick kol;
	Joystick xbox;
	CANTalon gearb;
	CANTalon geara;
	CANTalon asmac;
	CANTalon asmad;
	CANTalon firlat;
	Spark topal3;
	Spark topal1;
	Victor topal2;
	Timer timer;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		imcDrive = new RobotDrive (0, 1, 2, 3);
		kol = new Joystick(0);
		xbox = new Joystick(1);
		gearb = new CANTalon(2);
		geara = new CANTalon(3);
		asmac = new CANTalon(1);
		asmad = new CANTalon(4);
		firlat = new CANTalon(5);
		topal3 = new Spark(5);
		topal1 = new Spark(6);
		topal2 = new Victor(7);
		timer = new Timer();
		SmartDashboard.putData("Auto choices", chooser);
		CameraServer.getInstance().startAutomaticCapture(0);
		CameraServer.getInstance().startAutomaticCapture(1);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		autoSelected = chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		imcDrive.drive(0.2, 0.0086);
	}
	/**p
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		move = kol.getRawAxis(1)*(-1);
		rotation = kol.getRawAxis(2)*(-1);
		imcDrive.arcadeDrive(move, rotation);
		
		if(kol.getRawButton(1)){
			move /= 5;
			rotation /= 30;
		}
		
		if(kol.getRawButton(2)){
			move *= kol.getRawAxis(3);
			rotation *= -1;
			}
		
		if(xbox.getRawButton(7)){
			gearb.set(1);	
			}
		
			else if(xbox.getRawButton(8)){
				gearb.set(0.7*(-1));
				}
		
			else {
				gearb.set(0);
			}			
		
		
		if(kol.getRawButton(3)){
			geara.set(0.5);
			}
			
			else if(kol.getRawButton(5)){
				geara.set(-0.5);
				}
				else {
						geara.set(0);
			}
		
		if(xbox.getRawButton(10)){
			asmad.set(1);
			asmac.set(1);
			}
		
			else{
				asmad.set(0);
				asmac.set(0);
			}
	
		if(xbox.getRawButton(9)){
			
			topal3.set(-0.7);
			
			}
		
			else if(xbox.getRawButton(5)){
			topal1.set(-0.4);
			firlat.set(-1);
			topal3.set(-0.7);
			topal2.set(1);
			}
				else{
					
					firlat.set(0);
					topal1.set(0);
					topal2.set(0);
					topal3.set(0);
			}
		
		}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}

