/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4191.robot;
import java.lang.Math;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	NetworkTableEntry centeY;
	NetworkTableEntry centeX;
	NetworkTableEntry genis;
	NetworkTableInstance inst;
	NetworkTable table;
	double centerX;
	double centerY;
	double width;
	double distance;
	double theta;
	double hypo;
	double uzakkenar;
	boolean corrected;
	SendableChooser<Integer> chooser = new SendableChooser<>();
	
	MecanumDrive imcDrive;
	double move_X, move_Y, rotation_Z;
	Joystick kol;
	Joystick kol2;
	Talon m_frontLeft;
	Talon m_rearLeft;
	Talon m_frontRight;
	Talon m_rearRight;
	
	UsbCamera camera;
	Timer zaman;
	AnalogGyro gyro;
	double angle;
	
	
	DriverStation ds;
	DriverStation.Alliance color;
	int station;
	String gameData;
	String message;
	double voltage;
	double Kp = 0.07;
	
	double annen;
	double baban;
	double abin;
	

	
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		inst = NetworkTableInstance.getDefault();
		table = inst.getTable("GRIP/myContoursReport");
		camera = CameraServer.getInstance().startAutomaticCapture(0);
		camera.setResolution(320, 240);
		camera.setBrightness(0);
		camera.setFPS(20);
		camera.setExposureManual(0);
		
		
		kol = new Joystick(0);
		kol2 = new Joystick(1);

		m_frontLeft = new Talon(4);
		m_rearLeft = new Talon(0);
		m_frontRight = new Talon(2);
		m_rearRight = new Talon(1);
		imcDrive = new MecanumDrive(m_frontLeft, m_rearLeft, m_frontRight, m_rearRight);
		
		

		zaman = new Timer();
		gyro = new AnalogGyro(0);
		
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
		gyro.reset();	
	
		
		
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopInit() {
		
		gyro.reset();
		color = DriverStation.getInstance().getAlliance();
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		camera.setResolution(320, 240);
		camera.setBrightness(0);
		camera.setFPS(20);
		camera.setExposureManual(0);
	}
	
	
	@Override
	public void teleopPeriodic() {
		table.getInstance();
		
		//Vision-Progress
		genis = table.getEntry("width");
		try{
			abin = genis.getDoubleArray(new double[] {0})[0];
		}catch(ArrayIndexOutOfBoundsException aioobe) {
			abin = 0;
		}
		
		centeX = table.getEntry("centerX");
		try{
			annen = centeX.getDoubleArray(new double[] {0})[0];
		}catch(ArrayIndexOutOfBoundsException aioobe) {
			annen = 0;
		}
		centeY = table.getEntry("centerY");
		try{
			baban = centeY.getDoubleArray(new double[] {0})[0];
		}catch(ArrayIndexOutOfBoundsException aioobe) {
			baban = 0;
		}
		
		
		
		
		distance = (140*24)/abin;
		uzakkenar = Math.abs(160-annen);
		hypo = Math.hypot(distance, uzakkenar);
		theta = Math.toDegrees( Math.atan(uzakkenar/distance));
		//end
		if(abin < 9 ) {
			distance = 350;
			theta = theta+30;
			corrected = true;
		}else {
			corrected = false;
		}
		
		
		voltage = RobotController.getBatteryVoltage();
		SmartDashboard.putNumber("uzakkenar", uzakkenar);
		SmartDashboard.putNumber("hipo", hypo);
		SmartDashboard.putNumber("uzaklik", distance);
		SmartDashboard.putNumber("aci", theta);
		SmartDashboard.putNumber("width", abin);
		SmartDashboard.putNumber("centerX", annen);
		SmartDashboard.putNumber("centerY" , baban);
		SmartDashboard.putNumber("Gyro", angle);
		SmartDashboard.putString("GameData", gameData);
		SmartDashboard.putNumber("Station", station);
		SmartDashboard.putNumber("Voltage", voltage);
		SmartDashboard.putBoolean("corrected", corrected);
		
		angle = gyro.getAngle();
		if(kol.getRawAxis(0) > 0.10) {
		move_Y = kol.getRawAxis(0);
		}else if (kol.getRawAxis(0)< -0.10) {
		move_Y= kol.getRawAxis(0);
		}else {
		move_Y= 0;
			}
		if(kol.getRawAxis(1) > 0.10) {
		move_X= -kol.getRawAxis(1);
		}else if (kol.getRawAxis(1)< -0.10) {
		move_X= -kol.getRawAxis(1);
		}else {
		move_X= 0;
		}
		
		if(kol.getRawAxis(2) > 0.15 ) {
		rotation_Z= kol.getRawAxis(2)/3;
		}else if (kol.getRawAxis(2)< -0.15) {
		rotation_Z= kol.getRawAxis(2)/3;
		}else {
		rotation_Z= 0;
		}
		
		
		imcDrive.driveCartesian(move_Y, move_X, rotation_Z);
		
		

		
		
		if(kol.getRawButton(12)) {
			if (gyro.getAngle() < -2.5) {
				angle = -2.5;
			}
			if (gyro.getAngle() > 2.5) {
				angle = 2.5;
			}
			if(annen > 160 ) {
			imcDrive.drivePolar(0.25, theta, -angle*Kp);
			}else if(annen < 160 ) {
			imcDrive.drivePolar(0.25, -theta, -angle*Kp);
			}
			//if(annen > 170 ) {
				//if(angle > 4) {
				//imcDrive.drivePolar(0.2, 90, 0.3);
				//}else if (angle < -4) {
				//imcDrive.drivePolar(0.2, 90, -0.3);
				//}else {
				//imcDrive.drivePolar(0.65, 90, 0.0);
				//imcDrive.driveCartesian(0.2, 0.0 , -angle*Kp);
				//}
			//}else if(annen < 150) {
				//if(angle > 4) {
				//imcDrive.drivePolar(0.2, -90, 0.3);
				//}else if (angle < -4) {
				//imcDrive.drivePolar(0.2, -90, -0.3);
				//}else {
				//imcDrive.drivePolar(0.65, -90, 0.0);
				//imcDrive.driveCartesian(-0.2, 0.0 , -angle*Kp);
				//}
			//}else if(abin < 80 ) {
				//imcDrive.drivePolar(0.3, 0, -angle*Kp);
			//}else {
				//imcDrive.drivePolar(0.0, 0.0, 0.0);
			//}	
		}
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	
	}
}
