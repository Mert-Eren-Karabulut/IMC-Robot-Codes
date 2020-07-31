package org.usfirst.frc.team4191.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
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
	NetworkTable table;
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	final String shootingAuto = "Shoot!";
	final String gearAuto = "Gear!";
	Joystick left, right, gamepad;
	double right_speed, left_speed;
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();
	RobotDrive drive;
	boolean finished;
	Motors motors;
	AnalogGyro gyro;
	double kp = 0.03;
	boolean collect;
	double angle, aim, distance, ballDistance, gearDistance;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		ballDistance = 150;
		gearDistance = 10;
		drive = new RobotDrive(6, 7, 8, 9);
		left = new Joystick(1);
		right = new Joystick(2);
		gamepad = new Joystick(3);
		table = NetworkTable.getTable("driverAssist");
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		chooser.addObject("Shoot!", shootingAuto);
		chooser.addObject("Gear!", gearAuto);
		gyro = new AnalogGyro(1);
		SmartDashboard.putData("Auto choices", chooser);
		motors = new Motors(drive, 3, 1, 2, 0, 1, 4, 5, table);
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
		switch (autoSelected) {
		case customAuto:
			// Put custom auto code here
			autoSelected = defaultAuto;
			break;
		case gearAuto:
			motors.goToDistance(450);
			motors.releaseGear();
			autoSelected = defaultAuto;
			break;
		case shootingAuto:
			motors.goToDistance(300);
			motors.turnToAngle(135);
			motors.aimForBalls();
			autoSelected = defaultAuto;
			break;
		case defaultAuto:
		default:
			// Put default auto code here
			break;
		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		collect = true;
		
		// Get Drive Values From Joystick
		left_speed = left.getRawAxis(0);
		right_speed= right.getRawAxis(0);
		
		// Slow the Drive
		if(left.getRawButton(0)||right.getRawButton(0)){
			left_speed /= 2;
			right_speed /= 2;
		}
		
		// Reverse the Drive
		if(left.getRawButton(1)||right.getRawButton(1)){
			right_speed *= -1;
			left_speed *= -1;
		}
		
		// Lift
		if(gamepad.getRawButton(0)){
			motors.rise(1);
		}else if(gamepad.getRawButton(1)){
			motors.rise(-1);
		}else{
			motors.rise(0);
		}
		
		// Throw Balls Manually
		if(gamepad.getRawButton(3)){
			collect = false;
			motors.throwingBall(1);
		}else{
			motors.throwingBall(0);
		}
		
		// Throwing Balls
		while(gamepad.getRawButton(8)){
			motors.aimForBalls();
		}
		
		// Release Gear
		if(gamepad.getRawButton(8)){
			motors.releaseGear();
		}
		
		// Collect balls from ground
		motors.collector(collect);
		drive.tankDrive(left_speed, right_speed);
		
		SmartDashboard.putNumber("Left Speed", left_speed);
		SmartDashboard.putNumber("Right Speed", right_speed);
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}

