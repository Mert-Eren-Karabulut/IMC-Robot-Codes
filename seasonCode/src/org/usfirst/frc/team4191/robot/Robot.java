package org.usfirst.frc.team4191.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	final String centerAuto = "Center";
	final String rightAuto = "Right";
	final String tankDrive = "TANK Drive";
	final String arcadeDrive = "ARCADE Drive";
	String autoSelected, driveSelected;
	RobotDrive drive;
	Spark first, second;
	SendableChooser<String> chooser = new SendableChooser<>();
	SendableChooser<String> driveChooser = new SendableChooser<>();
	Joystick left,right, gamepad;
	CANTalon gatherer, feeder, shooter, mixerOne, mixerTwo;
	double x,z;
	Functions functions;
	static boolean successfull;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		chooser.addObject("Center", centerAuto);
		chooser.addObject("Right", rightAuto);
		driveChooser.addDefault("TANK Drive", tankDrive);
		driveChooser.addObject("ARCADE Drive", arcadeDrive);
		SmartDashboard.putData("Auto choices", chooser);
		SmartDashboard.putData("Drive Choices", driveChooser );
		drive = new RobotDrive(6,7,8,9);
		first = new Spark(3);
		second = new Spark(4);
		right = new Joystick(0);
		left = new Joystick(1);
		gamepad = new Joystick(2);
		gatherer = new CANTalon(2);
		feeder = new CANTalon(1);
		shooter = new CANTalon(3);
		mixerOne = new CANTalon(4);
		mixerTwo = new CANTalon(5);
		functions = new Functions(drive, first, second, gatherer, feeder, shooter, mixerOne, mixerTwo);
		drive.setSafetyEnabled(false);
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
		System.out.println("Auto selected: " + autoSelected);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		SmartDashboard.putNumber("Lol", 15);
		switch (autoSelected) {
		case customAuto:
			SmartDashboard.putNumber("asd", 17);
			// Put custom auto code here
			// Go for 3 seconds
			drive.arcadeDrive(-0.7, 0);
			Timer.delay(1.5);
			autoSelected = defaultAuto;
			break;
		case centerAuto:
			SmartDashboard.putNumber("asd", 17);
			// Put custom auto code here
			// Go for 3 seconds
			drive.arcadeDrive(-0.6, 0.23);
			Timer.delay(1.5);
			drive.arcadeDrive(-0.4,0.23);
			Timer.delay(6);
			autoSelected = defaultAuto;
			break;
		case rightAuto:
			SmartDashboard.putNumber("asd", 17);
			// Put custom auto code here
			// Go for 3 seconds
			drive.arcadeDrive(-0.6, 0.23);
			Timer.delay(2);
			drive.arcadeDrive(0, 0.6);
			SmartDashboard.putNumber("tank", 0.3);
			Timer.delay(1.4);
			drive.arcadeDrive(-0.6, 0.23);
			Timer.delay(1.5);
			drive.arcadeDrive(-0.4,0.23);
			Timer.delay(6);
			autoSelected = defaultAuto;
			break;
		case defaultAuto:
		default:
			// Put default auto code here
			drive.tankDrive(0, 0);
			break;
		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		successfull = true;
		x = left.getRawAxis(1)*left.getRawAxis(3);
		z = left.getRawAxis(2);
		if(!left.getRawButton(3)){
			x *= 0.7;
			z /= 4;
			z *= 3;
		}
		if(left.getRawButton(1)){
			x /= 2;
			if(z<0){
				z /= 5;
				z *= 3;
			}else{
				z /= 2;
				
			}
		}
		if(left.getRawButton(2)){
			x *= -1;
			z *= -1;
		}
		
		if(gamepad.getRawButton(4)){
			functions.feed(true);
		}else{
			functions.feed(false);
		}
		
		// X Tusu sut atiyo
		if(gamepad.getRawButton(3)){
			functions.shoot(true);
		}else{
			functions.shoot(false);
		}
		/*
		// B ile vision li gear
		if(gamepad.getRawButton(2)){
			//successfull = functions.turn();
			while((gamepad.getRawButton(2))&&(successfull)){
				drive.tankDrive(0.2, 0.2);
			}
		}
		*/
		// Y ile karistiriyo
		if(gamepad.getRawButton(3)){
			functions.mix(true);
		}else{
			functions.mix(false);	
		}
		/*
		// A vision li sut
		if(gamepad.getRawButton(1)){
			//successfull = functions.turn();
			while((gamepad.getRawButton(3))&&(successfull)){
				functions.shoot(true);
			}
		}
		*/
		// R2 lift
		Functions.lift(gamepad.getRawAxis(3));
		// solun y si gatherer
		Functions.gather(gamepad.getRawAxis(1));
		
		drive.arcadeDrive(x,(z*(-1)));
		
		SmartDashboard.putNumber("Motor Value", x);
		SmartDashboard.putNumber("Rotate Value", z);
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}

