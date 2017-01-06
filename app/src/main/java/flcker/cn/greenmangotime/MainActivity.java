package flcker.cn.greenmangotime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import flcker.cn.greenmangotime.activities.MessageActivity;
import flcker.cn.greenmangotime.location.LocationService;
import flcker.cn.greenmangotime.util.common;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    // Example of a call to a native method
    // TextView tv = (TextView) findViewById(R.id.sample_text);
    // tv.setText(stringFromJNI());

    }

    @Override
    protected void onResume() {
        super.onResume();
        LocationService.getInstance().registerLocationService();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocationService.getInstance().unregisterLocationService();
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    public void sendMessage(View view) {
        EditText editText = (EditText) findViewById(R.id.edit_message);
//        String message = editText.getText().toString();;

        double latitude = LocationService.getInstance().getLatitude();
        double longitude = LocationService.getInstance().getLongitude();

        String message = "latitude " + latitude + "\n longitude " + longitude;
        editText.setText("");
//        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        Intent intent = new Intent( this, MessageActivity.class);
        intent.putExtra(common.EXTERA_MESSAGE, message);
        intent.putExtra(common.EXTERA_LOCATION_FROM, 1);
        startActivity(intent);
    }
}
