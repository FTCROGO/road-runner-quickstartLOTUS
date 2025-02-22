
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
import com.qualcomm.robotcore.hardware.Servo;

import java.net.ServerSocket;

@Config
@Autonomous(name = "LotusAuto_Basket", group = "Autonomous")
public class LotusAuto_Basket extends LinearOpMode {
    double startPosition;
    public DcMotor  mArm = null;
    public DcMotor  mLS = null;
    public Servo    claw = null;

    public Servo    ROTclaw=null;


    double ROTclawOffset = 0.45;


    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(0, 66   , Math.toRadians(270));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        mArm = hardwareMap.get(DcMotor.class, "mArm");
        mLS = hardwareMap.get(DcMotor.class, "mLS");
        claw = hardwareMap.get(Servo.class, "claw");
        ROTclaw = hardwareMap.get(Servo.class, "ROTclaw");


        mArm.setDirection(DcMotor.Direction.REVERSE);
        mLS.setDirection(DcMotor.Direction.REVERSE);

        mArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mLS.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mLS.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        mArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mLS.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //claw.setPosition(0.5);

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
                .strafeTo(new Vector2d(33, 60))
                .turn(Math.toRadians(145))
                .strafeTo(new Vector2d(39, 70));
//                .lineToY(40)
//                .turn(Math.toRadians(145))
//                .lineToX(-24);
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
        mArm.setTargetPosition(2800);
        mArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        mLS.setPower(1);
        mLS.setTargetPosition(22836);
        mLS.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        ROTclaw.setPosition(0.7);

        sleep(10000);


        Actions.runBlocking(
                new SequentialAction(
                        tab.build()));
                    //trajectoryActionChosen,
//                    trajectoryActionCloseOut));y



        //claw.setPosition(0.8);
        claw.setPosition(0);
        sleep(3000);



    }
}