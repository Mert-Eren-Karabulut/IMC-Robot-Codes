package org.usfirst.frc.team4191.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.VictorSP;
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
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();
	Joystick gamepad;
	Spark first,second;
	//VictorSP one, two,three,four;
	RobotDrive drive;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);
		gamepad = new Joystick(1);
		
		/*one = new VictorSP(9);
		two = new VictorSP(8);
		three = new VictorSP(7);
		four = new VictorSP(6);
		*/
		drive = new RobotDrive(9, 8, 7, 6);
		first = new Spark(9);
		second = new Spark(8);
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
		drive.tankDrive(gamepad.getRawAxis(5), gamepad.getRawAxis(1));
		SmartDashboard.putNumber("Left", gamepad.getRawAxis(5));
		SmartDashboard.putNumber("Right", gamepad.getRawAxis(1));
		if(gamepad.getRawButton(1)){
		first.set(0.7*gamepad.getRawAxis(3));
		second.set(0.7*gamepad.getRawAxis(3));
		SmartDashboard.putNumber("Lift", 0.7*gamepad.getRawAxis(1));
		}else if(gamepad.getRawButton(2)){
			first.set(-0.7*gamepad.getRawAxis(3));
			second.set(-0.7*gamepad.getRawAxis(3));
			SmartDashboard.putNumber("Lift", -0.7*gamepad.getRawAxis(3));
		}else{
			first.set(0);
			second.set(0);
			SmartDashboard.putNumber("Lift", 0);
		}
		/*
		if(gamepad.getRawButton(1)){
			one.set(1);
			SmartDashboard.putNumber("Controller:", 9);
		}else{
			one.set(0);
		}
		
		if(gamepad.getRawButton(2)){
			two.set(1);
			SmartDashboard.putNumber("Controller:", 8);
		}else{
			two.set(0);
		}

		if(gamepad.getRawButton(3)){
			three.set(1);
			SmartDashboard.putNumber("Controller:", 7);
		}else{
			three.set(0);
		}

		if(gamepad.getRawButton(4)){
			four.set(1);
			SmartDashboard.putNumber("Controller:", 6);
		}else{
			four.set(0);
		}*/
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}

