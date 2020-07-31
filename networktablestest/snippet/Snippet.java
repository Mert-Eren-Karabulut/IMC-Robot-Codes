package snippet;

public class Snippet {
	try{
					table.putNumber("driveAssist", 2);
					Thread.sleep(100);
					while(!table.getBoolean("finished")){
						Thread.sleep(100);
					}
					aim = table.getNumber("angle");
					angle = gyro.getAngle();
					if(!(aim<3)){
						motors.turnToAngle(angle, aim);
					}
					distance = table.getNumber("distance");
					if(!(Math.abs(distance)<20)){
						motors.goToDistance(distance);
					}
					motors.throwingBall(1);
				}catch (Exception e){
					e.printStackTrace();
					SmartDashboard.putBoolean("ERROR", false);
				}
}

