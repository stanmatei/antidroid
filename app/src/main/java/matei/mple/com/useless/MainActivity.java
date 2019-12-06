package matei.mple.com.useless;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.database.Cursor;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener {

    DatabaseHelper myDb;
    private Button but;
    private EditText txt;
    private View decorView;
    private List<ApplicationInfo> applist = null;
    private GestureDetectorCompat gesture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        but=(Button)findViewById(R.id.but);
        txt=(EditText)findViewById(R.id.apptext);
         decorView = getWindow().getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        decorView.setOnSystemUiVisibilityChangeListener
                (new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {

                        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                            // TODO: The system bars are visible. Make any desired
                            // adjustments to your UI, such as showing the action bar or
                            // other navigational controls.


                        } else {
                            // TODO: The system bars are NOT visible. Make any desired
                            // adjustments to your UI, such as hiding the action bar or


                        }
                    }
                });
        gesture= new GestureDetectorCompat(this,this);
        gesture.setOnDoubleTapListener(this);
        Typeface mytf3 =Typeface.createFromAsset(getAssets(), "coolvetica.ttf");
        but.setVisibility(View.INVISIBLE);
        but.setEnabled(false);

        txt.setTypeface(mytf3);
        txt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (i == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    String huba= txt.getText().toString().toLowerCase();
                    if(huba.substring(0,2).equals("g:")){

                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/#q=" +huba.substring(2) )));
                    }

                    if(huba.substring(0,2).equals("w:")){

                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org/wiki/" +huba.substring(2) )));
                    }
                    if(huba.substring(0,2).equals("r:")){

                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.reddit.com/r/" +huba.substring(2) )));
                    }
                    if(huba.substring(0,2).equals("b:")){

                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bing.com/search?q=" +huba.substring(2) )));
                    }
                    if(huba.substring(0,2).equals("t/")) {
                        Date currentTime = Calendar.getInstance().getTime();
                        txt.setText(currentTime.toString());
                    }
                    if(huba.substring(0,2).equals("w/")) {
                        find_weather(huba.substring(2));
                    }
                    if(huba.substring(0,2).equals("h/")) {
                        newz();
                    }
                    if(huba.substring(0,2).equals("c/")) {
                        onCall();
                    }
                    if(huba.substring(0,2).equals("s/")) {

                       doit();
                    }
                    if(huba.substring(0,2).equals("n/")) {
                        AddData();
                    }
                    if(huba.substring(0,2).equals("d/")) {
                        DeleteData();
                    }
                    if(huba.substring(0,2).equals("a/")) {
                        viewAll();
                    }

                    if(huba.equals("suggestion")) {
                        Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Suggestion for Antidroid Launcher");
                        intent.setData(Uri.parse("mailto:13sofware.com")); // or just "mailto:" for blank
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                        startActivity(intent);
                    }




                    if(txt.getText().toString().toLowerCase().equals("help")){
                        Intent r=new Intent(MainActivity.this,Main3Activity.class);
                        startActivity(r);
                    }
                    if(txt.getText().toString().toLowerCase().equals("menu")){
                        Intent o=new Intent(MainActivity.this,Main2Activity.class);
                        startActivity(o);
                    }
                    else {


                        try{
                            Intent intent = getPackageManager().getLaunchIntentForPackage(txt.getText().toString().toLowerCase());

                            if(intent != null) {
                                startActivity(intent);
                            }
                        } catch(ActivityNotFoundException e) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        } catch(Exception e) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                    return true;
                }
                return false;
            }
        });

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txt.getText().toString().toLowerCase().equals("help")){
                    Intent i=new Intent(MainActivity.this,Main2Activity.class);
                    startActivity(i);
                }else {


                    try{
                        Intent intent = getPackageManager().getLaunchIntentForPackage(txt.getText().toString().toLowerCase());

                        if(intent != null) {
                            startActivity(intent);
                        }
                    } catch(ActivityNotFoundException e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    } catch(Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        }
        });
    }
    public void onCall() {
        Intent callIntent = new Intent(Intent.ACTION_CALL); //use ACTION_CALL class
        callIntent.setData(Uri.parse("tel:"+txt.getText().toString().substring(2)));    //this is the phone number calling
        //check permission
        //If the device is running Android 6.0 (API level 23) and the app's targetSdkVersion is 23 or higher,
        //the system asks the user to grant approval.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //request permission from user if the app hasn't got the required permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},   //request specific permission from user
                    10);
            return;
        }else {     //have got permission
            try{
                startActivity(callIntent);  //call activity and make phone call
            }
            catch (android.content.ActivityNotFoundException ex){
                Toast.makeText(getApplicationContext(),"yourActivity is not founded",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void find_weather(String siti){
        String url="http://api.openweathermap.org/data/2.5/weather?q=" +siti+"&appid=12140728e865310fc26df9425a934e95&units=metric";
        JsonObjectRequest jor= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONObject main_object=response.getJSONObject("main");
                    JSONArray array=response.getJSONArray("weather");
                    JSONObject object=array.getJSONObject(0);
                    String temp=String.valueOf(main_object.getDouble("temp"));
                    String description=object.getString("description");
                    String city= response.getString("name");

                    txt.setText(temp+"°C in "+city +" "+description);
                }catch (JSONException e){
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(jor);

    }
    public void newz(){
        String url="https://www.reddit.com/r/worldnews/top.json?limit=1";
        JsonObjectRequest jor= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONObject main_object=response.getJSONObject("data");
                    JSONArray array=main_object.getJSONArray("children");
                   JSONObject object=array.getJSONObject(0);
                   JSONObject f=object.getJSONObject("data");
                    String title=f.getString("title");
                    txt.setText(title);
                }catch (JSONException e){
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(jor);

    }
    private int getPosition(JSONArray jsonArray) throws JSONException {
        for(int index = 0; index < jsonArray.length(); index++) {
            JSONObject jsonObject = jsonArray.getJSONObject(index);
            if(jsonObject.equals("kind")) {
                return index; //this is the index of the JSONObject you want
            }
        }
        return -1; //it wasn't found at all
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        gesture.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {

        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {

        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {

        return false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        if(txt.getText().toString().toLowerCase().equals("help")){
            Intent e = new Intent(MainActivity.this,Main2Activity.class);
            startActivity(e);
        }else {

            Intent launchIntent = getPackageManager().getLaunchIntentForPackage(txt.getText().toString().toLowerCase());
            startActivity(launchIntent);
        }
        return true;
    }

    public void doit(){
        String filename=txt.getText().toString().substring(2);

        txt.setText(myDb.hep(filename));




    }

    public void viewAll() {

                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0) {
                            // show message
                            showMessage("Error","Nothing found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Shortcut :"+ res.getString(1)+"\n");
                            buffer.append("For :"+ res.getString(2)+"\n");
                        }

                        // Show all data
                        showMessage("Shortcuts",buffer.toString());

    }
    public  void AddData() {

        String filename=txt.getText().toString().substring(2);
        int iend = filename.indexOf("=");

        String b;
        String c;
        b= filename.substring(0 , iend);
        c=filename.substring(iend+1);
                        boolean isInserted = myDb.insertData(b,c);
                        if(isInserted == true)
                            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();

    }
    public void DeleteData() {
        String filename=txt.getText().toString().substring(2);

                        Integer deletedRows = myDb.deleteData(filename);
                        if(deletedRows > 0)
                            Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Deleted",Toast.LENGTH_LONG).show();

    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


}


