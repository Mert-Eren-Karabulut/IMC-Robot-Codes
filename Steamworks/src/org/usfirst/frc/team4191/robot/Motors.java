package org.usfirst.frc.team4191.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Motors {
	CANTalon mixerOne, mixerTwo, thrower, feeder, gatherer;
	Spark lifterOne, lifterTwo;
	RobotDrive drive;
	double distancePerPulse;
	int robotSpeed = 0; // How much meters does the robot go in 1 second! with a given speed
	int angularSpeed = 0; // How much meters does it take to turn 1 angle;
	NetworkTable table;
	
	public Motors(RobotDrive drive, int thrower, int feeder, int gatherer,int lifterOne,int lifterTwo, int mixerOne, int mixerTwo, NetworkTable table){
		this.drive = drive;
		this.thrower = new CANTalon(thrower);
		this.feeder = new CANTalon(feeder);
		this.gatherer = new CANTalon(gatherer);
		this.lifterOne = new Spark(lifterOne);
		this.lifterTwo = new Spark(lifterTwo);
		this.mixerOne = new CANTalon(mixerOne);
		this.mixerTwo = new CANTalon(mixerTwo);
		this.table = table;
	}
	

	public void throwingBall(int speed){
		thrower.set(speed);
		feeder.set(speed);
		mixerOne.set(speed/4);
		mixerTwo.set(speed/4);
		SmartDashboard.putNumber("Thrower", speed);
		SmartDashboard.putNumber("Feeder", speed);
	}
	
	public void collector(boolean bool){
		if(bool){
			gatherer.set(0.6);
			SmartDashboard.putNumber("Gatherer:", 0.6);
		}else{
			gatherer.set(0);
			SmartDashboard.putNumber("Gatherer:", 0);
		}
	}
	
	public void rise(double speed){
		lifterOne.set(speed);
		lifterTwo.set(speed);
		
		SmartDashboard.putNumber("Lifter:", speed);
	}

	public void turnToAngle(double aim){
		try{
			double time = Math.abs(aim)/angularSpeed;
			if(aim<0){
				drive.tankDrive(0.2, -0.2);
			}else{
				drive.tankDrive(-0.2, 0.2);
			}
			Timer.delay(time);
			drive.tankDrive(0, 0);
		}catch (Exception e){
			e.printStackTrace();
			SmartDashboard.putBoolean("ERROR", false);
		}
	}
	
	public void goToDistance(double distance){
		try{
			double time = distance/robotSpeed;
			drive.tankDrive(robotSpeed, robotSpeed);
			Timer.delay(time);
			drive.tankDrive(0, 0);
		}catch (Exception e) {
			e.printStackTrace();
			SmartDashboard.putBoolean("ERROR", false);
		}
	}
	public void releaseGear(){
		try{
			table.putNumber("driveAssist", 3);
			Timer.delay(0.1);
			while(!table.getBoolean("finished")){
				Timer.delay(0.1);
			}
			double aim = table.getNumber("angle");
			if(!(aim<3)){
				turnToAngle(aim);
			}
			double distance = table.getNumber("distance");
			if(!(Math.abs(distance)<20)){
				goToDistance(distance);
			}
		}catch (Exception e){
			e.printStackTrace();
			SmartDashboard.putBoolean("ERROR", false);
		}	
	}

	// hehe :D
	public void aimForBalls(){
		try{
			table.putNumber("driveAssist", 2);
			Timer.delay(0.1);
			while(!table.getBoolean("finished")){
				Timer.delay(0.1);
			}
			double aim = table.getNumber("angle");
			if(!(aim<3)){
				turnToAngle(aim);
			}
			double distance = table.getNumber("distance");
			if(!(Math.abs(distance)<20)){
				goToDistance(distance);
			}
			throwingBall(1);
		}catch (Exception e){
			e.printStackTrace();
			SmartDashboard.putBoolean("ERROR", false);
		}
	}
}
