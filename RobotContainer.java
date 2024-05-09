package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.commands.SwerveJoystickCmd;
import frc.robot.subsystems.SwerveSubsystem;

public class RobotContainer {

 public static final SwerveSubsystem swerveSubsystem = new SwerveSubsystem();


    public static XboxController driverJoystick = new XboxController(0);
    public static Joystick driverJoystick2 = new Joystick(1);
    Compressor comp = new Compressor(PneumaticsModuleType.CTREPCM);
    public RobotContainer() {
        swerveSubsystem.setDefaultCommand(new SwerveJoystickCmd(
                swerveSubsystem,
                 () -> driverJoystick.getLeftY(),
                 () -> driverJoystick.getLeftX(),
                 () -> driverJoystick.getRightX(),
                 () -> !driverJoystick.getRightBumper()));

        configureButtonBindings();
    }

    private void configureButtonBindings() {


        if (driverJoystick.getRawButtonPressed(6)){

             swerveSubsystem.zeroHeading();
     }
      
    }

    public Command getAutonomousCommand() {
      
return null; 
}
}
