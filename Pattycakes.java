package pattycakes;
import robocode.*;
import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * Pattycakes - a robot by (your name here)
 */
public class Pattycakes extends Robot
{
	boolean peek; // Don't turn if there's a robot there
	
	double moveAmountHeight;
	double moveAmountWidth; 
	double originalMoveAmountHeight;
	double originalMoveAmountWidth;
	double maxMoveAmount;
	double move90percent;
	double move80percent;

	/**
	 * run: Move around the walls
	 */
	public void run() {
		// Set colors
		setBodyColor(Color.black);
		setGunColor(Color.black);
		setRadarColor(Color.black);
		setBulletColor(Color.black);
		setScanColor(Color.black);

		// Initialize moveAmount to the maximum possible for this battlefield.
		maxMoveAmount = Math.max(getBattleFieldWidth(), getBattleFieldHeight());
		//minMoveAmount = Math.max(getBattleFieldWidth(), getBattleFieldHeight());
		move90percent = maxMoveAmount * 0.9;
		move80percent = maxMoveAmount * 0.8;
		originalMoveAmountWidth = getBattleFieldWidth();
		originalMoveAmountHeight = getBattleFieldHeight();
		moveAmountWidth = originalMoveAmountWidth;
		moveAmountHeight = originalMoveAmountHeight;
		// Initialize peek to false
		peek = false;

		// turnLeft to face a wall.
		// getHeading() % 90 means the remainder of
		// getHeading() divided by 90.
		turnLeft(getHeading() % 90);
		ahead(maxMoveAmount);
		// Turn the gun to turn right 90 degrees.
		peek = false;
		turnGunRight(90);
		turnRight(90);
		
		// Move to the upper wall
		ahead(maxMoveAmount);
		turnRight(90);
		
		// Since it's always random which direction we start at, the following code is unpredictable.
		// We're always bumping into other robots so it's fine and random. 
		// The robot always tries to get back to a wall though.

		// Set moveAmount to 90%
		moveAmountWidth = originalMoveAmountWidth * 0.9;
		moveAmountHeight = originalMoveAmountHeight * 0.9;

		// Move 90% in width
		ahead(moveAmountWidth);
		turnRight(90);
		
		// Move 90% in height
		ahead(moveAmountHeight);
		turnRight(90);
		

		while (true) {
			// Look before we turn when ahead() completes.
			peek = true;
			// Move up the wall
			ahead(move90percent);
			// Don't look now
			peek = false;
			// Turn to the next wall
			turnRight(90);
		}
	}

	/**
	 * onHitRobot:  Move away a bit.
	 */
	public void onHitRobot(HitRobotEvent e) {
		// Don't do anything, we don't want to backup or move forward. 
		// Just ignore the event and the robot will eventually turnRight
		
		// The reason for this is because we don't want the robot to be
		// bouncing between robots too many times which is inevitable
		// when there are 8+ robots on the battlefield.
	}

	/**
	 * onScannedRobot:  Fire!
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		fire(2);
		// Note that scan is called automatically when the robot is moving.
		// By calling it manually here, we make sure we generate another scan event if there's a robot on the next
		// wall, so that we do not start moving up it until it's gone.
		if (peek) {
			scan();
		}
	}
}
