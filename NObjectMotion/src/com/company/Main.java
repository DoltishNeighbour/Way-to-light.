package com.company;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    public double G=6.67, A0, R, T=1, scale=3; //Mass is multiplied by 10^11
    public int n=7, a=3, b=a*a, centre=350;
    public double[][]XY= new double[n][2];
    public double[][]V= new double[n][2];
    public double[]M= new double[n];
    public double[][]A= new double[n][2];
    public boolean stop=false, go=true;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Motion");
        Group root = new Group();
        Canvas canvas = new Canvas(700, 700);
        GraphicsContext gc=canvas.getGraphicsContext2D();
        Button btn1=new Button(); Button btn2=new Button();
        btn1.setText("Start"); btn2.setText("Stop");
        btn1.setLayoutX(2); btn2.setLayoutX(50);
        btn1.setLayoutY(2); btn2.setLayoutY(2);
        XY[0][0]=centre; XY[0][1]=centre; V[0][0]=0; V[0][1]=0; M[0]=20; //Sun
        XY[1][0]=centre-147.09; XY[1][1]=centre; V[1][0]=0; V[1][1]=0.96; M[1]=20/332946; //Earth
        XY[2][0]=centre-740; XY[2][1]=centre; V[2][0]=0; V[2][1]=0.434798; M[2]=M[0];//317.8*M[1]; //Jupiter
        XY[3][0]=XY[0][1]-0.03626; XY[3][1]=centre; V[3][0]=0; V[3][1]=0.0000340934; M[3]=M[1]/81; //Moon
        XY[4][0]=centre-46; XY[4][1]=centre; V[4][0]=0; V[4][1]=1.86998; M[4]=0.055274*M[1]; //Mercury
        XY[5][0]=centre-107.476; XY[5][1]=centre; V[5][0]=0; V[5][1]=1.1179; M[5]=0.815*M[1]; //Venus
        XY[6][0]=centre-206.655; XY[6][1]=centre; V[6][0]=0; V[6][1]=0.840123; M[6]=0.107*M[1]; //Mars
        for (int i = 0; i < n; i++) A[i][0]=A[i][1]=0;
        gc.setFill(Color.BLACK);

        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stop=false;
                if (go) {
                    Timer timer = new Timer();
                    TimerTask timerTask = new TimerTask() {
                        public void run() {
                            gc.clearRect(0, 0, 700, 700);
                            for (int i=0; i<n; i++) {
                                for (int j=0; j<n; j++) {
                                    if (j!=i) {
                                        R=Math.sqrt(Math.pow((XY[j][0]-XY[i][0]),2) + Math.pow((XY[j][1]-XY[i][1]),2));
                                        A0=G*M[j]/(R*R);
                                        A[i][0]+=A0*(XY[j][0]-XY[i][0])/R;
                                        A[i][1]+=A0*(XY[j][1]-XY[i][1])/R;
                                    }
                                }
                                XY[i][0]+=V[i][0]*T+A[i][0]*T*T; XY[i][1]+=V[i][1]*T+A[i][1]*T*T;
                                V[i][0]+=A[i][0]*T; V[i][1]+=A[i][1]*T;
                                A[i][0]=A[i][1]=0;
                                DrawOval(gc,XY[i][0],XY[i][1]);
                            }
                            go=false;
                            if (stop) { go=true; timer.cancel(); }
                        }
                    };
                    timer.schedule(timerTask, 0,10);
                }
            }});

        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { stop=true; }
        });

        root.getChildren().add(canvas);
        root.getChildren().add(btn1);
        root.getChildren().add(btn2);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void DrawOval(GraphicsContext gc, double x , double y){
        gc.fillOval((x-centre)/scale-a+centre,(y-centre)/scale-a+centre,b,b);
    }
}