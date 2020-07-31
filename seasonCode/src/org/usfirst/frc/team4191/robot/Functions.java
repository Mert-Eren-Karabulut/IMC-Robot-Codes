package org.usfirst.frc.team4191.robot;

import java.sql.Time;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Functions {
	SerialPort serial = new SerialPort(115200, SerialPort.Port.kOnboard, 8, SerialPort.Parity.kNone, SerialPort.StopBits.kOne);
	static CANTalon gatherer, feeder, shooter, mixerOne, mixerTwo;
	static RobotDrive drive;
	static Spark first, second;
	static int lol = 0;
	static double position = 0;
	public Functions(RobotDrive drive, Spark first, Spark second, CANTalon gatherer, CANTalon feeder, CANTalon shooter, CANTalon mixerOne, CANTalon mixerTwo) {
		// TODO Auto-generated constructor stub
		this.drive = drive;
		this.first = first;
		this.second = second;
		this.gatherer = gatherer;
		this.feeder = feeder;
		this.shooter = shooter;
		this.mixerOne = mixerOne;
		this.mixerTwo = mixerTwo;
		//table = NetworkTable.getTable("driverAssist");
		//table.putNumber("lol", 0);
		SmartDashboard.putNumber("annen", 0);
		
	}
	
	public static void shoot(boolean shoot){
		if(shoot){
			shooter.set(0.45);
			SmartDashboard.putNumber("Shooter",0.5);
		}else{
			shooter.set(0);
			SmartDashboard.putNumber("Shooter",0);
		}
	}
	
	public static void feed(boolean shoot){
		if(shoot){
			feeder.set(-1);
			SmartDashboard.putBoolean("Feeder", true);
		}else{
			feeder.set(0);
			SmartDashboard.putBoolean("Feeder", false);	
		}
	}
	
	
	public static void mix(boolean bool){
		if(bool){
			mixerOne.set(0.1);
			mixerTwo.set(0.1);
		}else{
			mixerOne.set(0);
			mixerTwo.set(0);	
		}
		SmartDashboard.putBoolean("Mixer", bool);
	}
	
	
	/*public boolean turn(){
		try{
			table.putNumber("lol", 1);
			SmartDashboard.putNumber("annen", table.getNumber("annen"));
			SmartDashboard.putBoolean("assist", true);
			position = table.getNumber("position");
			position -= 200;
			while(Math.abs(position)>80){
				if(position<0){
					drive.arcadeDrive(0,0.5);
					SmartDashboard.putNumber("arcade z", 0.3);
				}else{
					drive.arcadeDrive(0,-0.5);	
					SmartDashboard.putNumber("arcade z", -0.3);
				}
				position = table.getNumber("position");
				SmartDashboard.putNumber("Position", position);
				SmartDashboard.putNumber("haha", lol);
				lol++;

				if(table.getNumber("error")==1){
					table.putNumber("lol", 0);
					position = 0;
					return false;
				}
			}

			SmartDashboard.putBoolean("assist", false);
			position = 0;
			return true;
		}catch(Exception e){
			e.printStackTrace();
			table.putNumber("lol", 0);
			SmartDashboard.putBoolean("assist", false);
			position = 0;
			return false;
		}
		
	}
	*/
	public static void lift(double value){
		if(value<0.1){
			first.set(0);
			second.set(0);
			SmartDashboard.putNumber("Lift", 0);
		}else if(value<0.5){
			first.set(0.3);
			second.set(0.3);
			SmartDashboard.putNumber("Lift", 0.3);
		}else{
			first.set(1);
			second.set(1);
			SmartDashboard.putNumber("Lift", 1);
		}
	}
	public static void gather(double value){
		gatherer.set(value);
		SmartDashboard.putNumber("Gatherer", value);
	}
}
