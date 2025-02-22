package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="Robot: LOTUS_TeleOp", group="Robot")
//@Disabled
public class LOTUS_TeleOp extends LinearOpMode {

    /* Declare OpMode members. */
    public DcMotor  leftFront  = null;
    public DcMotor  rightFront  = null;
    public DcMotor  leftBack  = null;
    public DcMotor  rightBack  = null;
    public DcMotor  mArm = null;
    public DcMotor  mLS  = null;
    public DcMotor  mACT  = null;

    public Servo claw  = null;

    public Servo ROTclaw  = null;



    double ClawOffset = 0.5;
    double ROTclawOffset = 0.45;




    @Override
    public void runOpMode() {
        double frontLeft;
        double frontRight;
        double backLeft;
        double backRight;
        double drive;
        double strafeLeft;
        double strafeRight;
        double turn;
        double max;
        double maxx;

        leftFront  = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront  = hardwareMap.get(DcMotor.class, "rightFront");
        leftBack  = hardwareMap.get(DcMotor.class, "leftBack");
        rightBack  = hardwareMap.get(DcMotor.class, "rightBack");
        mArm = hardwareMap.get(DcMotor.class, "mArm");
        mLS  = hardwareMap.get(DcMotor.class, "mLS");
        mACT  = hardwareMap.get(DcMotor.class, "mACT");


        leftFront.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.FORWARD);
        mArm.setDirection(DcMotor.Direction.REVERSE);
        mLS.setDirection(DcMotor.Direction.REVERSE);
        mACT.setDirection(DcMotor.Direction.FORWARD);


        mArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mLS.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mACT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        mArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mLS.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mACT.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        claw  = hardwareMap.get(Servo.class, "claw");
        //claw.setPosition(0);
        ROTclaw  = hardwareMap.get(Servo.class, "ROTclaw");
        //ROTclaw.setPosition(0.5);

        telemetry.addData(">", "Robot Ready.  Press START.");    //
        telemetry.update();



        waitForStart();

        while (opModeIsActive()) {

            // Drivetrain
            drive  = -gamepad1.left_stick_y/1.5;
            turn   =  gamepad1.right_stick_x/1.5;
            strafeLeft  =  - gamepad1.left_trigger;
            strafeRight =  gamepad1.right_trigger;

            frontLeft  = drive + strafeLeft + strafeRight + turn;
            frontRight = drive - strafeLeft - strafeRight - turn;
            backLeft   = drive - strafeLeft - strafeRight + turn;
            backRight  = drive + strafeLeft + strafeRight - turn;

            max = Math.max(Math.abs(backLeft), Math.abs(backRight));
            if (max > 1.0)
            {
                backLeft   /= max;
                backRight  /= max;
            }

            maxx = Math.max(Math.abs(frontLeft), Math.abs(frontRight));
            if (maxx > 1.0)
            {
                frontLeft  /= maxx;
                frontRight /= maxx;
            }


            leftFront.setPower(frontLeft);
            rightFront.setPower(frontRight);
            leftBack.setPower(backLeft);
            rightBack.setPower(backRight);


// Arm - up (Y), down (A)
            if (gamepad2.y)
                mArm.setPower(0.8);
            else if (gamepad2.a)
                mArm.setPower(-0.8);
            else
                mArm.setPower(0.0);

            // Arm lower limit 2800
            if (mArm.getCurrentPosition() < -2850) {
                mArm.setTargetPosition(-2850);
                mArm.setPower(0.3);
            }
            else if (mArm.getCurrentPosition() < -2900) {
                mArm.setPower(0);
            }

            // Arm upper limit
            if (mArm.getCurrentPosition() > 7130) {
                mArm.setTargetPosition(7130);
                mArm.setPower(-0.3);
            }
            else if (mArm.getCurrentPosition() > 7140) {
                mArm.setPower(0);
            }

// Actuator Code
            if (gamepad2.x)
                mACT.setPower(0.8);
            else if (gamepad2.b)
                mACT.setPower(-0.8);
            else
                mACT.setPower(0.0);

            // Actuator lower limit
            if (mACT.getCurrentPosition() < -2265) {
                mACT.setTargetPosition(-2265);
                mACT.setPower(0.3);
            }
            else if (mACT.getCurrentPosition() < -2270) {
                mACT.setPower(0);
            }

            // Actuator upper limit
            if (mACT.getCurrentPosition() > 2540) {
                mACT.setTargetPosition(2540);
                mACT.setPower(-0.3);
            }
            else if (mACT.getCurrentPosition() > 2550) {
                mACT.setPower(0);
            }


// Linear Slide - up (dpad up), down (dpad down), zero position (x)
            if (gamepad2.dpad_up)
                mLS.setPower(1);
            else if (gamepad2.dpad_down)
                mLS.setPower(-1);
            else if (gamepad2.dpad_down) {
                mLS.setTargetPosition(0);
                mLS.setPower(-1);
            }
            else
                mLS.setPower(0.0);

            // Linear lower limit 22836
            if (mLS.getCurrentPosition() < -23000) {
                mLS.setTargetPosition(-23000);
                mLS.setPower(0.5);
            }
            else if (mLS.getCurrentPosition() < -23500) {
                mLS.setPower(0);
            }

            // Linear upper limit
            if (mLS.getCurrentPosition() > 29140) {
                mLS.setTargetPosition(29140);
                mLS.setPower(0.5);
            }
            else if (mLS.getCurrentPosition() > 29150)
                mLS.setPower(0);


//Intake - forward (left bumper), backward (right bumpers) -----------------check
            if (gamepad2.right_bumper)
                ClawOffset += 0.2;
            else if (gamepad2.left_bumper)
                ClawOffset -= 0.2;
            //else
                //ClawOffset = 0;

            ClawOffset = Range.clip(ClawOffset, -0.3, -0.1);
            claw.setPosition(0.5 + ClawOffset);


            if (gamepad1.y)
                ROTclawOffset += 0.05;
            else if (gamepad1.a)
                ROTclawOffset -= 0.05;

            ROTclawOffset = Range.clip(ROTclawOffset, -0.5, 0.5);
            ROTclaw.setPosition(0.5 + ROTclawOffset);



// Send telemetry message to signify robot running;
            telemetry.addData("Claw",  "Offset = %.2f", ClawOffset);
            telemetry.addData("ROTclaw",  "Offset = %.2f", ROTclawOffset);

            telemetry.addData("arm", mArm.getCurrentPosition());
            telemetry.addData("linear", mLS.getCurrentPosition());
            telemetry.addData("Actuator", mACT.getCurrentPosition());

            telemetry.update();

        }
    }
}