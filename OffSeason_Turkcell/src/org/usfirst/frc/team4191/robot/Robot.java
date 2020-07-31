package org.usfirst.frc.team4191.robot;


import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/* The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 * 
 * 
 * 
 * 
 * 
 * 
 */

public class Robot extends IterativeRobot {
    final String defaultAuto = "Default";
    final String customAuto = "Straight";
    final String secondAuto = "Left Side";
    final String thirdAuto = "Right Side";
    String autoSelected;
    SendableChooser chooser;
    Joystick gamepad;
    JoystickButton up;
    JoystickButton down;
    JoystickButton slowb;
    Talon armCamRight;
    Talon armCamLeft;
    Compressor compressor;
    Spark andyMarkRight;
    Spark andyMarkLeft;
	RobotDrive imcDrive;
    Joystick joystick;
    public static DoubleSolenoid solenoid;
    JoystickButton solenoidToggle;
    double andyMarks, hand, driveRight, driveLeft, snowBlowers;
    
    static boolean solenoidExtended = true;
    static boolean pressed;
    static boolean first = true;
	
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", defaultAuto);
        //chooser.addObject("My Auto", customAuto);
        //chooser.addObject("My Auto", secondAuto);
        //chooser.addObject("My Auto", thirdAuto);
        SmartDashboard.putData("Auto choices", chooser);
        gamepad = new Joystick(1);
        up = new JoystickButton(gamepad, 6);
        down = new JoystickButton(gamepad, 8);
        slowb = new JoystickButton(joystick, 1);
        armCamRight = new Talon(8);
        armCamLeft = new Talon(9);
        compressor = new Compressor();
        compressor.setClosedLoopControl(true);
        andyMarkLeft = new Spark(7);
        andyMarkRight = new Spark(6);
        imcDrive = new RobotDrive(3, 0, 1, 2);
        solenoid = new DoubleSolenoid(7, 5);
        solenoid.set(Value.kForward);
        joystick = new Joystick(0);
        solenoidToggle = new JoystickButton(gamepad, 1);
        andyMarks = 0;
        hand = 0;
        driveLeft = 0;
        driveRight = 0;
        snowBlowers = 0;
        pressed = true;
    }
    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomousInit() {
    	autoSelected = (String) chooser.getSelected();
//		autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
		compressor.setClosedLoopControl(true);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	switch(autoSelected) {
    	case customAuto:
    		break;
    		
    	case defaultAuto:
    	default:
    	//Put default auto code here
            break;
    	}
    	
    }

    /**
     * This function is called periodically during operator control
     */
    
    
    public void teleopPeriodic() { 
    	
    	//If anything goes wrong they shall be 0 :D
    	andyMarks = 0;
        hand = 0;
        driveLeft = 0;
        driveRight = 0;
        snowBlowers = 0;
        
        //Elevator ve arm
        switch(gamepad.getPOV()){
        case -1://Basili degil
        	if(!first )
        		break;
        	andyMarks = 0;
        	first = false;
        	break;
        case 0://Yukari
        	andyMarks = 1;
        	first = true;
        	break;
        case 45://Yukari ve sag
        	andyMarks = 1;
        	hand = .8;
        	first = true;
        	break;
        case 315://Yukari ve sol
        	andyMarks = 1;
        	hand = -0.8;
        	first = true;
        	break;
        case 225://Asagi ve sol
        	andyMarks = -0.6;
        	hand = -0.8;
        	first = true;
        	break;
        case 135://Asagi ve sag
        	andyMarks = -0.6;
        	hand = .8;
        	first = true;
        	break;
        case 180://Asagi
        	andyMarks = -0.6;
        	first = true;
        	break;
        case 90: //Sag
        	hand = .8;
        	first = true;
        	break;
        case 270: //Sol
        	hand = -0.8;
        	first = true;
        	break;
        default:
        	System.out.print("Undefined POV " + gamepad.getPOV() + "\n");
        }
      
        
        //driveLeft = stickL.getRawAxis(1)*stickL.getRawAxis(3);
        //driveRight = stickR.getRawAxis(1)*stickR.getRawAxis(3);
    	//snowBlowers = gamepad.getRawAxis(1);
    	snowBlowers = 0;
        /*
    	if(stickL.getRawButton(1)||stickR.getRawButton(1)){
    		driveLeft /= 2;
    		driveRight /= 2;
    	}
    	
    	if(stickR.getRawButton(2) || stickL.getRawButton(2)){
    		driveLeft *= -1;
    		driveRight *= -1;
    	}
    */
    	if(gamepad.getRawButton(6)){
    		if(pressed){
	    		solenoid.set(solenoidExtended ? Value.kReverse : Value.kForward);
	    		solenoidExtended = !solenoidExtended;
	    		pressed = false;
    		}
    	}else{
    		pressed = true;
    	}
    	SmartDashboard.putBoolean("Pressed", pressed);
    	SmartDashboard.putBoolean("Solenoid", solenoidExtended);
    	//imcDrive.arcadeDrive(joystick.getRawAxis(1)*(-1), joystick.getRawAxis(2)*(-1));
    	imcDrive.tankDrive(gamepad.getRawAxis(1)*(-3)/(4), gamepad.getRawAxis(5)*(-3)/(4));
    	enableSnowBlowers(snowBlowers);
    	enableAndyMarks(andyMarks);
    
    	SmartDashboard.putNumber("Drive Left", driveLeft);
    	SmartDashboard.putNumber("Drive Right", driveRight);
    	SmartDashboard.putNumber("CAM", snowBlowers);    	
    	SmartDashboard.putNumber("Uctaki Cam", hand);
    	SmartDashboard.putNumber("AndyMark", andyMarks);
    	
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
    public void enableAndyMarks(double input){
    	andyMarkRight.set(input);
    	andyMarkLeft.set(input);
    }
    
    public void enableSnowBlowers(double input){
    	if(input<0){
    		input /= 2;
    	}
    	armCamLeft.set(input);
    	armCamRight.set(input);
    }
    
}
