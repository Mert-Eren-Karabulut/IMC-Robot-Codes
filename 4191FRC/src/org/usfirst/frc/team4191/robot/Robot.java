 
package org.usfirst.frc.team4191.robot;

import org.usfirst.frc.team4191.robot.commands.ExampleCommand;
import org.usfirst.frc.team4191.robot.subsystems.ExampleSubsystem;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
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

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;

	Command autonomousCommand;
	RobotDrive imcDrive;
	VictorSP frontLeft, frontRight, rearLeft, rearRight ;
	double move, rotation;
	SmartDashboard dash;
	Joystick joy;
	/**
	Spark first, second, third, fourth;
	*/
	SendableChooser<Command> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		dash = new SmartDashboard();
		imcDrive = new RobotDrive (frontLeft, frontRight, rearLeft, rearRight);
		joy = new Joystick(0);
		frontLeft = new VictorSP(0);
		frontRight = new VictorSP(2);
		rearLeft = new VictorSP(1);
		rearRight = new VictorSP(3);
		
		/*
		first = new Spark(4);
		second = new Spark(5);
		*/
		chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */  
	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		
		move = joy.getRawAxis(1);
		rotation = joy.getRawAxis(2);
		
		if(joy.getRawButton(1)){
			move /= 2;
			rotation /= 2;
		}
		
		if(joy.getRawButton(2)){
			move *= joy.getRawAxis(3);
			rotation *= -1;
			}}
		
		/** imdilik yok
		imcDrive.arcadeDrive(move, rotation);
		first.set(joy.getRawAxis(5));
		
		if(joy.getRawButton(10)){
			second.set(-0.5);
		}else if(joy.getRawButton(11)){
			second.set(0.5);
		}else{
			second.set(0);
		}
		
		SmartDashboard.putNumber("rotation", (int)rotation*100);
		SmartDashboard.putNumber("move", (int)move*100);
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
