package jack.wantband;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;
import android.net.Uri;
import com.google.android.maps.MapView;
import at.abraxas.amarino.Amarino;
import at.abraxas.amarino.AmarinoIntent;
/**
 * 
 * Amarino based interface for lilypad powered "Want-Band"
 * 
 * Based on the Sensorgraph.java template program written by Bonifaz Kaufmann
 * 
 * @author Jack Anderson - December 2010
 *
 */
public class WantBand extends Activity {
	
	//device address
    private static final String DEVICE_ADDRESS = "00:06:66:04:B0:A5";
    
    private TextView inputButton;
    
    private ArduinoReceiver arduinoReceiver = new ArduinoReceiver();
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       inputButton = (TextView) findViewById(R.id.buttonId);
        
    }
    
	@Override
	protected void onStart() {
		super.onStart();
		// in order to receive broadcasted intents we need to register our receiver
		registerReceiver(arduinoReceiver, new IntentFilter(AmarinoIntent.ACTION_RECEIVED));
		
		// this is how you tell Amarino to connect to a specific BT device from within your own code
		Amarino.connect(this, DEVICE_ADDRESS);
	}


	@Override
	protected void onStop() {
		super.onStop();
		
		// if you connect in onStart() you must not forget to disconnect when your app is closed
		Amarino.disconnect(this, DEVICE_ADDRESS);
		
		// do never forget to unregister a registered receiver
		unregisterReceiver(arduinoReceiver);
	}
    
	/**
	 * ArduinoReceiver is responsible for catching broadcasted Amarino
	 * events.
	 * 
	 * It extracts data from the intent and updates the graph accordingly.
	 */
	public class ArduinoReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String data = null;
			String query = null;
			// the device address from which the data was sent, we don't need it here but to demonstrate how you retrieve it
			final String address = intent.getStringExtra(AmarinoIntent.EXTRA_DEVICE_ADDRESS);
			final int dataType = intent.getIntExtra(AmarinoIntent.EXTRA_DATA_TYPE, -1);
			
	
			if (dataType == AmarinoIntent.STRING_EXTRA){
				data = intent.getStringExtra(AmarinoIntent.EXTRA_DATA);
				
				if (data != null){
					try {

						inputButton.setText(data);
						if (data.equals("1")){query = "restaurants";};
						if (data.equals("2")){query = "entertainment";};
						if (data.equals("3")){query = "attractions";};
						if (data.equals("4")){query = "bars";};
						Intent intent2 = new Intent(android.content.Intent.ACTION_VIEW, 
								Uri.parse("geo:0,0?q="+query));
								startActivity(intent2);
					} 
					catch (NumberFormatException e) { /* oh data was not an integer */ }
				}
			}
		}
	}
}
