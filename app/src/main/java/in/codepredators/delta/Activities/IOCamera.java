package in.codepredators.delta.Activities;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import in.codepredators.delta.Classes.ImageSurfaceView;
import in.codepredators.delta.R;


/**
 * {@link #checkDeviceCamera()}                                                Handles whether any exception occurs while opening the device camera
 * {@link #scaleDownBitmapImage(Bitmap, int, int)}                             Scales the image to new width and height
 */
public class IOCamera extends AppCompatActivity {

    private ImageSurfaceView mImageSurfaceView;
    private Camera camera;
    private FrameLayout cameraPreviewLayout;
    private ImageView capturedImageHolder;
int a=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        cameraPreviewLayout = (FrameLayout)findViewById(R.id.camera_preview);
        capturedImageHolder = (ImageView)findViewById(R.id.captured_image);

        camera = checkDeviceCamera();
        mImageSurfaceView = new ImageSurfaceView(IOCamera.this, camera);
        cameraPreviewLayout.addView(mImageSurfaceView);

        Button captureButton = (Button)findViewById(R.id.button);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                camera.takePicture(null, null, pictureCallback);
                a=1;
            }
        });
    }
    @Override
    public void onBackPressed() {
        //Include the code here
        if(a==1)
        {
            Chat.cameraImageBitmap(bitmap);
        }
        super.onBackPressed();
    }
    private Camera checkDeviceCamera(){
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mCamera;
    }
    Bitmap bitmap;
    PictureCallback pictureCallback = new PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            if(bitmap==null){
                Toast.makeText(IOCamera.this, "Captured image is empty", Toast.LENGTH_LONG).show();
                return;
            }
          //  capturedImageHolder.setImageBitmap(scaleDownBitmapImage(bitmap, 300, 200 ));
            capturedImageHolder.setImageBitmap(bitmap);
        }
    };

    private Bitmap scaleDownBitmapImage(Bitmap bitmap, int newWidth, int newHeight){
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        return resizedBitmap;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}