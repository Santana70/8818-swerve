package frc.robot.subsystems;


import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.RelativeEncoder; 

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.ModuleConstants;

public class SwerveModule extends SubsystemBase{
 private CANSparkMax driveMotor;
 private CANSparkMax turningMotor; 

 private RelativeEncoder driveEncoder;
 private RelativeEncoder turningEncoder;

 private CANCoder absoluteEncoder;
    private  boolean absoluteEncoderReversed;
    private  double absoluteEncoderOffsetRad;

    private PIDController turningPidController = new PIDController(ModuleConstants.kPTurning, 0, 0);

    public SwerveModule(int driveMotorId, int turningMotorId, boolean driveMotorReversed, boolean turningMotorReversed,
            int absoluteEncoderId, double absoluteEncoderOffset, boolean absoluteEncoderReversed) {

        this.absoluteEncoderOffsetRad = absoluteEncoderOffset;
        this.absoluteEncoderReversed = absoluteEncoderReversed;
        absoluteEncoder = new CANCoder(absoluteEncoderId);

        driveMotor = new CANSparkMax(driveMotorId, MotorType.kBrushless);
        turningMotor = new CANSparkMax(turningMotorId, MotorType.kBrushless);

        driveMotor.setInverted(true);
        turningMotor.setInverted(true);

        driveEncoder = driveMotor.getEncoder();
        turningEncoder = turningMotor.getEncoder();

        turningPidController.enableContinuousInput(-Math.PI, Math.PI);

        resetEncoders();
    }

    public double getDrivePosition() {
        return driveEncoder.getPosition() * ModuleConstants.kDriveEncoderRot2Meter;
    }

    public double getTurningPosition() {
        return turningEncoder.getPosition() * ModuleConstants.kTurningEncoderRot2Rad;
    }

    public double getDriveVelocity() {
        return driveEncoder.getVelocity()* ModuleConstants.kDriveEncoderRPM2MeterPerSec;
    }

    public double getTurningVelocity() {
        return turningEncoder.getVelocity() * ModuleConstants.kTurningEncoderRPM2RadPerSec;
    }

    public double getAbsoluteEncoderRad() {
        double angle = (absoluteEncoder.getAbsolutePosition());
        angle -= absoluteEncoderOffsetRad;
        angle = Math.toRadians(angle);
        return angle * (absoluteEncoderReversed ? -1.0 : 1.0);
    }

    public SwerveModulePosition getPosition(){
        return new SwerveModulePosition(getDrivePosition(),new Rotation2d(getTurningPosition()));
    }

    public void resetEncoders() {
        driveEncoder.setPosition(0);
        turningEncoder.setPosition(getAbsoluteEncoderRad()/ModuleConstants.kTurningEncoderRot2Rad);
    }



    public SwerveModuleState getState() {
        return new SwerveModuleState(getDriveVelocity(), new Rotation2d(getTurningPosition()));
    }

    public void setDesiredState(SwerveModuleState state) {
        if (Math.abs(state.speedMetersPerSecond) < 0.001) {
            stop();
            return;
        }
        state = SwerveModuleState.optimize(state, getState().angle);
        driveMotor.set(state.speedMetersPerSecond / DriveConstants.kPhysicalMaxSpeedMetersPerSecond);
        turningMotor.set(turningPidController.calculate(getTurningPosition(), state.angle.getRadians()));
        SmartDashboard.putString("Swerve[" + absoluteEncoder.getDeviceID() + "] state", state.toString());
    }

    public void stop() {
        driveMotor.set(0);
        turningMotor.set(0);
    }

    @Override
    public void periodic(){
    }
}
