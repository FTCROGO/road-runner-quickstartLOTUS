
package org.firstinspires.ftc.teamcode;

// RR-specific imports

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Config
@Autonomous(name = "LOTUSAuto2", group = "Autonomous")
public class LOTUSAuto2 extends LinearOpMode {
    double startPosition;
    public DcMotor  mArm = null;

    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(0, 66   , Math.toRadians(270));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        mArm = hardwareMap.get(DcMotor.class, "mArm");
        mArm.setDirection(DcMotor.Direction.REVERSE);
        mArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

//        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose)
//                .lineToX(-72);
////                .lineToYSplineHeading(33, Math.toRadians(0))
////                .waitSeconds(2)
////                .setTangent(Math.toRadians(90))
////                .lineToY(48)
////                .setTangent(Math.toRadians(0))
////                .lineToX(32)
////                .strafeTo(new Vector2d(44.5, 30))
////                .turn(Math.toRadians(180))
////                .lineToX(47.5)
////                .waitSeconds(3);

//        TrajectoryActionBuilder tab2 = drive.actionBuilder(initialPose)
//                .lineToX(37)
//                .setTangent(Math.toRadians(0))
//                .lineToX(18)
//                .waitSeconds(3)
//                .setTangent(Math.toRadians(0))
//                .lineToXSplineHeading(46, Math.toRadians(180))
//                .waitSeconds(3);
//
//        TrajectoryActionBuilder tab3 = drive.actionBuilder(initialPose)
//                .lineToYSplineHeading(33, Math.toRadians(180))
//                .waitSeconds(2)
//                .strafeTo(new Vector2d(46, 30))
//                .waitSeconds(3);

//        Action trajectoryActionCloseOut = tab1.endTrajectory().fresh()
//                .strafeTo(new Vector2d(-72, 72))
//                .build();
        TrajectoryActionBuilder tab = drive.actionBuilder(initialPose)
                .lineToY(16)
                .turn(Math.toRadians(-90))
                .lineToX(-24);
                //.build();


                //.strafeTo(new Vector2d(0, 14));
//                .build();

        if (isStopRequested())
            return;

//        Action trajectoryActionChosen;
//        if (startPosition == 1) {
//            trajectoryActionChosen = tab1.build();
//        } else if (startPosition == 2) {
//            trajectoryActionChosen = tab2.build();
//        } else {
//            trajectoryActionChosen = tab3.build();
//        }

        waitForStart();

        mArm.setPower(0.4);
        mArm.setTargetPosition(1800);
        mArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        Actions.runBlocking(
                new SequentialAction(
                        tab.build()));
                    //trajectoryActionChosen,
//                    trajectoryActionCloseOut));

    }
}