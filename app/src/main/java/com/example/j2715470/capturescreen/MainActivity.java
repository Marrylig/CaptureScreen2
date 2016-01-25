package com.example.j2715470.capturescreen;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               View v=getWindow().getDecorView();
                v.setDrawingCacheEnabled(true);
                v.buildDrawingCache();

                Bitmap srcBitmap=v.getDrawingCache();
                Rect frame=new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);;
                int statusBarHeight=frame.top;
                Point outSize=new Point();
                getWindowManager().getDefaultDisplay().getSize(outSize);
                int width=outSize.x;
                int heigth=outSize.y;
                Bitmap bitmap=Bitmap.createBitmap(srcBitmap,0,statusBarHeight,
                                                   width,heigth-statusBarHeight);
                v.destroyDrawingCache();
                FileOutputStream fos=null;
                try{
                    File file=File.createTempFile("capture",".jpg",new File("/sdcard"));
                    fos=new FileOutputStream(file);
                    if(fos!=null){
                        bitmap.compress(Bitmap.CompressFormat.PNG,90,fos);
                        fos.flush();
                        Toast.makeText(MainActivity.this, "已经成功截屏，截屏文件名："
                                + file.getName(), Toast.LENGTH_LONG).show();
                    }
                    fos.close();
                }catch (Exception e){

                }
            }
            });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
