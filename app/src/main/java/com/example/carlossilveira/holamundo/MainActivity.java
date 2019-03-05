package com.example.carlossilveira.holamundo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
//    Button b1,b2,b3,b4,b5,b6,btnComenzar;
String n1="clean",n2="clean",n3="clean";
    Button[] barray=new Button[6];
    Button btnComenzar;
    int cont[]=new int[6];
    boolean used=false;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private SurfaceView cameraView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cameraView=findViewById(R.id.surfaceView);
        barcodeDetector=new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build();
        cameraSource=new CameraSource.Builder(this,barcodeDetector).setRequestedPreviewSize(1500,1024).setAutoFocusEnabled(true).build();
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try{
                    if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.CAMERA)){

                        }else{
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA},0);
                        }
                    }
                    cameraSource.start(cameraView.getHolder());
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes=detections.getDetectedItems();
                if(barcodes.size() !=0){
                    btnComenzar.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),barcodes.valueAt(0).displayValue+"",Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });
        barray[0]=findViewById(R.id.b1);
        barray[1]=findViewById(R.id.b2);
        barray[2]=findViewById(R.id.b3);
        barray[3]=findViewById(R.id.b4);
        barray[4]=findViewById(R.id.b5);
        barray[5]=findViewById(R.id.b6);
        btnComenzar=findViewById(R.id.btnComenzar);

        btnComenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                assignValue();
            }
        });
    }
    public void assignValue(){
//        //fill narray from 1 to 6
//        for(int a=1;a<narray.length;a++){
//            narray[a]=a;
//        System.out.println("narray: "+narray[a]);
//        }

        //fill cont with 0 except position 0
        Arrays.fill(cont,0);
        boolean flag = true;

        String first = "burned";
        //run until cont all values are 2
        while(flag){
            //loop by cont to
            //check if cont are the same
            for(int i = 1; i < cont.length && flag; i++)
            {
                if(cont[i]==0){
                    changeIt();
                }
                if (n1 == first && n2==first && n3==first) flag = false;
            }
        }
    }
    public void changeIt(){

        int rnd = new Random().nextInt(cont.length);
        Random random=new Random();
        int r=random.nextInt(3 - 1 + 1) + 1;

        if(r==1){
            if(n1=="clean"){
            barray[rnd].setText("1");
            System.out.println("n1clean");
            n1="once";
            }
            if(n1=="once"){
                barray[rnd].setText(""+1);
                n1="burned";
            }
        }
        if(r==2){
            if(n2=="clean"){
                barray[rnd].setText(""+2);
                System.out.println("n2clean");
                n2="once";
            }
            if(n2=="once"){
                barray[rnd].setText(""+2);
                n2="burned";
            }
        }
        if(r==3){
            if(n1=="clean"){
                barray[rnd].setText(""+3);
                System.out.println("n3clean");
                n3="once";
            }
            if(n1=="once"){
                barray[rnd].setText(""+3);
                n3="burned";
            }
        }
    }
}
