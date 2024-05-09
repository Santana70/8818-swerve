package frc.robot.subsystems;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.ModuleConstants;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveSubsystem extends SubsystemBase {
  
    private final SwerveModule frontLeft =new SwerveModule(
        DriveConstants.kFrontLeftDriveMotorPort,
        DriveConstants.kFrontLeftTurningMotorPort,
        DriveConstants.kFrontLeftDriveEncoderReversed,
        DriveConstants.kFrontLeftTurningEncoderReversed,
        DriveConstants.kFrontLeftDriveAbsoluteEncoderPort,
        DriveConstants.kFrontLeftDriveAbsoluteEncoderOffsetRad,
        DriveConstants.kFrontLeftDriveAbsoluteEncoderReversed);

    private final SwerveModule frontRight = new SwerveModule(
            DriveConstants.kFrontRightDriveMotorPort,
            DriveConstants.kFrontRightTurningMotorPort,
            DriveConstants.kFrontRightDriveEncoderReversed,
            DriveConstants.kFrontRightTurningEncoderReversed,
            DriveConstants.kFrontRightDriveAbsoluteEncoderPort,
            DriveConstants.kFrontRightDriveAbsoluteEncoderOffsetRad,
            DriveConstants.kFrontRightDriveAbsoluteEncoderReversed);

    private final SwerveModule backLeft = new SwerveModule(
            DriveConstants.kBackLeftDriveMotorPort,
            DriveConstants.kBackLeftTurningMotorPort,
            DriveConstants.kBackLeftDriveEncoderReversed,
            DriveConstants.kBackLeftTurningEncoderReversed,
            DriveConstants.kBackLeftDriveAbsoluteEncoderPort,
            DriveConstants.kBackLeftDriveAbsoluteEncoderOffsetRad,
            DriveConstants.kBackLeftDriveAbsoluteEncoderReversed);

    private final SwerveModule backRight = new SwerveModule(
            DriveConstants.kBackRightDriveMotorPort,
            DriveConstants.kBackRightTurningMotorPort,
            DriveConstants.kBackRightDriveEncoderReversed,
            DriveConstants.kBackRightTurningEncoderReversed,
            DriveConstants.kBackRightDriveAbsoluteEncoderPort,
            DriveConstants.kBackRightDriveAbsoluteEncoderOffsetRad,
            DriveConstants.kBackRightDriveAbsoluteEncoderReversed);


    private AHRS gyro = new AHRS(I2C.Port.kOnboard); 


    private final SwerveDriveOdometry odometer = new SwerveDriveOdometry(DriveConstants.kDriveKinematics,
    new Rotation2d(0), getModulePositions());


public SwerveSubsystem() {
    new Thread(() -> {
        try {
            Thread.sleep(1000);
            zeroHeading();
        } catch (Exception e) {
        }
    }).start();
}

public SwerveModulePosition[] getModulePositions(){

    SwerveModulePosition[] positions = new SwerveModulePosition[4];

   positions[0] = frontLeft.getPosition();
   positions[1] = frontRight.getPosition();
   positions[2] = backLeft.getPosition();
   positions[3] = backRight.getPosition();

   return positions;
}

public void zeroHeading() {
    gyro.reset();
}

public double getHeading() {
    return Math.IEEEremainder(gyro.getAngle(), 360);
}

public Rotation2d getRotation2d() {
    return Rotation2d.fromDegrees(getHeading());
}
public Pose2d getPose() {
    return odometer.getPoseMeters();
}

public void resetOdometry(Pose2d pose) {
    odometer.resetPosition( getRotation2d(), getModulePositions(), pose);
}

@Override
public void periodic() {
    odometer.update(getRotation2d(), getModulePositions());
    SmartDashboard.putNumber("Robot Heading", getHeading());
    SmartDashboard.putNumber("S1 position", frontLeft.getAbsoluteEncoderRad());
    SmartDashboard.putNumber("S2 position", frontRight.getAbsoluteEncoderRad());
    SmartDashboard.putNumber("S3 position", backLeft.getAbsoluteEncoderRad());
    SmartDashboard.putNumber("S4 position", backRight.getAbsoluteEncoderRad());
    SmartDashboard.putString("Robot Location", getPose().getTranslation().toString());

    SmartDashboard.putNumber("Drive Position", frontLeft.getDrivePosition() / (Math.PI * ModuleConstants.kWheelDiameterMeters));
    SmartDashboard.putNumber("Turn Position", frontLeft.getTurningPosition() / (2 * Math.PI));
}


public void stopModules() {
    frontLeft.stop();
    frontRight.stop();
    backLeft.stop();
    backRight.stop();
}

public void setModuleStates(SwerveModuleState[] desiredStates) {
    SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, DriveConstants.kPhysicalMaxSpeedMetersPerSecond);
    frontLeft.setDesiredState(desiredStates[0]);
    frontRight.setDesiredState(desiredStates[1]);
    backLeft.setDesiredState(desiredStates[2]);
    backRight.setDesiredState(desiredStates[3]);
}

public void resetAllEncoders(){
    frontLeft.resetEncoders();
    frontRight.resetEncoders();
    backLeft.resetEncoders();
    backRight.resetEncoders();
}
}
