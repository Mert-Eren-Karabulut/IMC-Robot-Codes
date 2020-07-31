package org.usfirst.frc.team4191.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.lang.Math;

import com.ctre.CANTalon;

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
	final String tankDrive = "TANK Drive";
	final String arcadeDrive = "ARCADE Drive";
	String autoSelected, driveSelected;
	RobotDrive drive;
	Spark first, second;
	SendableChooser<String> chooser = new SendableChooser<>();
	SendableChooser<String> driveChooser = new SendableChooser<>();
	Joystick left,right, gamepad;
	CANTalon gatherer, feeder, shooter, mixerOne, mixerTwo;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		driveChooser.addDefault("TANK Drive", tankDrive);
		driveChooser.addObject("ARCADE Drive", arcadeDrive);
		SmartDashboard.putData("Auto choices", chooser);
		SmartDashboard.putData("Drive Choices", driveChooser );
		drive = new RobotDrive(6,7,8,9);
		first = new Spark(0);
		second = new Spark(1);
		right = new Joystick(0);
		left = new Joystick(1);
		gamepad = new Joystick(2);
		gatherer = new CANTalon(2);
		feeder = new CANTalon(1);
		shooter = new CANTalon(3);
		mixerOne = new CANTalon(4);
		mixerTwo = new CANTalon(5);
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
		try{
		driveSelected = driveChooser.getSelected();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			driveSelected = "";
		}
		switch(driveSelected){
		case tankDrive:
			if((left.getRawButton(1))||(right.getRawButton(1))){
				drive.tankDrive((left.getRawAxis(1)*left.getRawAxis(3))/2, (right.getRawAxis(1)*right.getRawAxis(3))/2);
			}else{
				drive.tankDrive((left.getRawAxis(1)*left.getRawAxis(3)), (right.getRawAxis(1)*right.getRawAxis(3)));
			}
			break;
		case arcadeDrive:
			drive.arcadeDrive(left.getRawAxis(1), left.getRawAxis(3));
			break;
		default:
			drive.tankDrive(0, 0);
			break;
		}
		SmartDashboard.putNumber("left", left.getRawAxis(1)*left.getRawAxis(3));
		SmartDashboard.putNumber("right", right.getRawAxis(1)*right.getRawAxis(1));
		gatherer.set(gamepad.getRawAxis(1));
		SmartDashboard.putNumber("gatherer", gamepad.getRawAxis(1));
		

		shooter.set(ApplyDeadZone(gamepad.getRawAxis(2)));
		feeder.set(ApplyDeadZone(gamepad.getRawAxis(3))*(-1));
		SmartDashboard.putNumber("Shooter", gamepad.getRawAxis(2));
		SmartDashboard.putNumber("Feeder", gamepad.getRawAxis(3));
		first.set(ApplyDeadZone(gamepad.getRawAxis(5)));
		second.set(ApplyDeadZone(gamepad.getRawAxis(5)));
		
		if(gamepad.getRawButton(2)){
			SmartDashboard.putBoolean("Mixer", true);
			mixerOne.set(0.5);
		}else{
			SmartDashboard.putBoolean("Mixer", false);
			mixerOne.set(0);
		}
	}
	
	double ApplyDeadZone (double input) {
		
		if(Math.abs(input) <= 0.15f)
			return 0f;
		
		return input;
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}

