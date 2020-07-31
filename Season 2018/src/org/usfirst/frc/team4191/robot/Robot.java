package org.usfirst.frc.team4191.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.AnalogGyro;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	// final String orta = "orta";
	// final String sag = "sag";
	// final String sol = "sol";
	// String autoSelected;
	// SendableChooser<String> chooser = new SendableChooser<>();
	DifferentialDrive imcDrive;
	double move, rotation;
	Joystick kol;
	Joystick kol2;

	Talon m_frontLeft;
	Talon m_rearLeft;
	SpeedControllerGroup m_left;

	Talon m_frontRight;
	Talon m_rearRight;
	SpeedControllerGroup m_right;

	Timer zaman;
	Spark kaldir1;
	Spark kaldir2;
	Spark ledblue;
	Spark ledred;
	Compressor comp;
	DoubleSolenoid solen;
	DoubleSolenoid solen1;
	DoubleSolenoid solen2;
	DriverStation ds;
	DriverStation.Alliance color;
	int station;
	String gameData;
	String message;
	AnalogGyro gyro;
	double Kp;
	double angle;
	double voltage;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		kol = new Joystick(0);
		kol2 = new Joystick(1);

		m_frontLeft = new Talon(6);
		m_rearLeft = new Talon(7);
		m_left = new SpeedControllerGroup(m_frontLeft, m_rearLeft);

		m_frontRight = new Talon(8);
		m_rearRight = new Talon(9);
		m_right = new SpeedControllerGroup(m_frontRight, m_rearRight);

		imcDrive = new DifferentialDrive(m_right, m_left);

		zaman = new Timer();
		comp = new Compressor();
		solen = new DoubleSolenoid(0, 1);
		solen1 = new DoubleSolenoid(2, 3);
		solen2 = new DoubleSolenoid(4, 5);
		comp.start();
		kaldir1 = new Spark(5);
		kaldir2 = new Spark(4);
		ledblue = new Spark(1);
		ledred = new Spark(0);

		CameraServer.getInstance().startAutomaticCapture(0);
		

		gyro = new AnalogGyro(0);
		voltage = RobotController.getBatteryVoltage();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString line to get the
	 * auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the SendableChooser
	 * make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {

		
		



		//	gameData = DriverStation.getInstance().getGameSpecificMessage();
		//	if (gameData.length() > 0) {
		//		message = gameData;
		//	}
		zaman.reset();
		zaman.start();
		gyro.reset();

		Kp = 0.15;
		while(DriverStation.getInstance().getGameSpecificMessage().length() <= 0);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		station = DriverStation.getInstance().getLocation();
		angle = gyro.getAngle();
		

		
		SmartDashboard.putNumber("Gyro", gyro.getAngle());
		SmartDashboard.putString("GameData",gameData);
		SmartDashboard.putNumber("Station",station);
		SmartDashboard.putNumber("Voltage",voltage);
		
		
		color = DriverStation.getInstance().getAlliance();
		if (color == DriverStation.Alliance.Blue) {
			ledblue.set(1);
		}

		else {
			ledblue.set(0);
			ledred.set(1);
		}


		if (color == DriverStation.Alliance.Blue) {
			ledblue.set(1);
		} else {
			ledblue.set(0);
			ledred.set(1);
		}
		
		
		
		
		if (gameData.length() > 0) {
			message = gameData;
		}
		

		if (station == 3)  { // sol taraf 1 ise
			
			if ((-angle * Kp)> 0.7) {
				angle = -2;
			}
			else if ((-angle * Kp)< -0.7) {
				angle = 2;
			}
			
			
			if (message.length() > 0) {
				if (message.charAt(0) == 'L') {
					imcDrive.setSafetyEnabled(false);

					if (zaman.get() < 3.6) {

						imcDrive.arcadeDrive(-0.5, -angle * Kp);
					} else {
						imcDrive.arcadeDrive(0.0, 0.0);

					}
					// Put left auto code here
				} else {
					imcDrive.setSafetyEnabled(false);

					if (zaman.get() < 3.6) {

						imcDrive.arcadeDrive(-0.5, -angle * Kp);
					} else {
						imcDrive.arcadeDrive(0.0, 0.0);
						if (zaman.get() < 4.6) {
							solen.set(DoubleSolenoid.Value.kForward);
						}

						// Put right auto code here
					}
				}

			}
			
		}

		else if (station == 2) {
			
			
			
			if (message.length() > 0) {
				if (message.charAt(0) == 'L') {
					imcDrive.setSafetyEnabled(false);

					if (gyro.getAngle() > -45) {

						imcDrive.arcadeDrive(0, -0.45);
					} else if (zaman.get() < 3.0) {
						gyro.reset();
						imcDrive.arcadeDrive(-0.5, -angle * Kp);
					} else if (gyro.getAngle() < 45) {
						imcDrive.arcadeDrive(0, 0.45);
					} else {
						if (zaman.get() < 5.0) {

							imcDrive.arcadeDrive(0, 0);
							Timer.delay(0.5);
							solen.set(DoubleSolenoid.Value.kForward);
							
						}

					}
					// Put left auto code here
				} else {
					imcDrive.setSafetyEnabled(false);

					if (gyro.getAngle() < 45) {

						imcDrive.arcadeDrive(0, 0.45);
					} else if (zaman.get() < 3.0) {
						gyro.reset();
						imcDrive.arcadeDrive(-0.5, -angle * Kp);
					} else if (gyro.getAngle() > -45) {
						imcDrive.arcadeDrive(0, -0.45);
					} else {
						if (zaman.get() < 5.0) {

							imcDrive.arcadeDrive(0, 0);
							Timer.delay(0.5);
							solen.set(DoubleSolenoid.Value.kForward);
						}
					}

					// Put right auto code here
				}
			}
		} else if (station == 1) {
			
			if ((-angle * Kp)> 0.7) {
				angle = -2;
			}
			else if ((-angle * Kp)< -0.7) {
				angle = 2;
			}
			
			
			
			if (message.length() > 0) {
				if (message.charAt(0) == 'L') {
					imcDrive.setSafetyEnabled(false);
					if (zaman.get() < 5.0) {
						imcDrive.arcadeDrive(-0.5, -angle * Kp);
					} else {
						imcDrive.arcadeDrive(0.0, 0.0);
						Timer.delay(0.5);
						solen.set(DoubleSolenoid.Value.kForward);

					}
					// Put left auto code here
				} else {
					imcDrive.setSafetyEnabled(false);
					if (zaman.get() < 5.0) {
						imcDrive.arcadeDrive(-0.5, -angle * Kp);
					}
					imcDrive.arcadeDrive(0.0, 0.0);
					Timer.delay(0.5);

					// Put right auto code here
				}
			}
		}

	}

	/**
	 * p This function is called periodically during operator control
	 */
	@Override
	public void teleopInit() {
		gyro.reset();
		color = DriverStation.getInstance().getAlliance();
		gameData = DriverStation.getInstance().getGameSpecificMessage();

		if (color == DriverStation.Alliance.Blue) {
			ledblue.set(1);
		}

		else {
			ledblue.set(0);
			ledred.set(1);
		}
		station = DriverStation.getInstance().getLocation();
	}

	@Override
	public void teleopPeriodic() {
		SmartDashboard.putNumber("Gyro", gyro.getAngle());
		SmartDashboard.putString("GameData",gameData);
		SmartDashboard.putNumber("Station",station);
		SmartDashboard.putNumber("Voltage",voltage);
		
		move = kol.getRawAxis(1);
		rotation = kol.getRawAxis(2);
		imcDrive.arcadeDrive(move, rotation);

		if (kol.getRawButton(1)) {
			move /= 5;
			rotation /= 30;
		}

		if (kol2.getRawButton(1)) {
			solen.set(DoubleSolenoid.Value.kReverse);
		} else if (kol2.getRawButton(2)) {
			solen.set(DoubleSolenoid.Value.kForward);

		} else {
			solen.set(DoubleSolenoid.Value.kOff);
		}

		if (kol2.getRawButton(3)) {
			solen1.set(DoubleSolenoid.Value.kReverse);
		} else if (kol2.getRawButton(4)) {
			solen1.set(DoubleSolenoid.Value.kForward);

		} else {
			solen1.set(DoubleSolenoid.Value.kOff);
		}

		if (kol2.getRawButton(5)) {
			solen2.set(DoubleSolenoid.Value.kReverse);
		} else if (kol2.getRawButton(6)) {
			solen2.set(DoubleSolenoid.Value.kForward);

		} else {
			solen2.set(DoubleSolenoid.Value.kOff);
		}

		if (kol.getRawButton(11)) {
			kaldir1.set(1);
			kaldir2.set(-1);
		}

		else if (kol.getRawButton(12)) {
			kaldir1.set(-1);
			kaldir2.set(1);
		}

		else {
			kaldir1.set(0);
			kaldir2.set(0);
		}

		if (kol2.getRawButton(7)) {
			ledblue.set(1);
		} else if (kol2.getRawButton(8)) {
			ledblue.set(0);

		}

		if (kol2.getRawButton(9)) {
			ledred.set(1);
		} else if (kol2.getRawButton(10)) {
			ledred.set(0);

		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}
