// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax;

//import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
//import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import com.revrobotics.CANSparkLowLevel.MotorType;
//import com.revrobotics.CANSparkMax;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;


  DifferentialDrive m_Drive;
  private RobotContainer m_robotContainer;

  CANSparkMax Upperintake = new CANSparkMax(9, MotorType.kBrushless);  
  CANSparkMax Lowerintake = new CANSparkMax(10, MotorType.kBrushless);  

  CANSparkMax OShooter = new CANSparkMax(11, MotorType.kBrushless);  
  CANSparkMax GShooter = new CANSparkMax(12, MotorType.kBrushless);  
  

  

// CANSparkMax leftfront = new CANSparkMax(1, MotorType.kBrushless);

// CANSparkMax rightfront = new CANSparkMax(3, MotorType.kBrushless);

// CANSparkMax leftback = new CANSparkMax(5, MotorType.kBrushless);

// CANSparkMax rightback = new CANSparkMax(7, MotorType.kBrushless);




// MotorControllerGroup leftMotorControllerGroup = new MotorControllerGroup(leftfront, leftback);
// MotorControllerGroup rightMotorControllerGroup = new MotorControllerGroup(rightfront, rightback);
PWMVictorSPX Arm = new PWMVictorSPX(0);


Spark claw1  = new Spark(2);

Spark claw2 = new Spark(1);




 DoubleSolenoid armSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 4, 5);






  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();

    //CameraServer.startAutomaticCapture("USB1", 0);
    //CameraServer.startAutomaticCapture("USB2", 1);

    // rightMotorControllerGroup.setInverted(true);
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
   
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
XboxController driver = new XboxController(0);

//XboxController driverJoystick2 = new XboxController(1);


// m_drive.tankDrive(driverJoystick.getLeftY(), driverJoystick.getRightY());


//if(driverJoystick.getRawButtonPressed(7)){
 // armSolenoid.set(Value.kForward);
 //} else if (driverJoystick.getRawButtonReleased(7)){
  //armSolenoid.set(Value.kOff);
 //} else if(driverJoystick.getRawButtonPressed(8)){
  // armSolenoid.set(Value.kReverse);
   //} else if (driverJoystick.getRawButtonReleased(8)){
  //armSolenoid.set(Value.kOff);
 // }



 //if (driverJoystick2.getAButton()){
 //    Arm.set(0.75);
 //} else if (driverJoystick2.getYButton()){
 //  Arm.set(-0.75);
 //} else{
 //  Arm.set(0);
 //}

//if (driverJoystick2.getBButton()){
  //claw1.set(0.8);
//  claw2.set(-0.8);
  
//} else if (driverJoystick2.getXButton()){
 // claw1.set(-0.8);
 // claw2.set(0.8);
  
//} else{
  //claw1.set(0);
 //claw2.set(0);
//}
if(driver.getRightTriggerAxis() >= 0.15){
   Upperintake.set(-.80);  
   Lowerintake.set(.60);
  }else if(driver.getRightTriggerAxis() <0.05){
   Upperintake.stopMotor();
   Lowerintake.stopMotor();
   
  }
   if(driver.getYButton()){
   OShooter.set(-.25);  
   GShooter.set(.30);

  }else if(driver.getXButton()){
   OShooter.stopMotor();
   GShooter.stopMotor();
   
  }

}



  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
