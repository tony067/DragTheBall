package ryan.activity;

import ryan.entity.Ball;
import ryan.view.BallView;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

public class MainActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		/*requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
		BallView view = new BallView(this);
		view.setBackgroundDrawable(getResources().getDrawable(R.drawable.back));
		view.addComponent(new Ball(0, 10, R.drawable.ball_blue));
		view.addComponent(new Ball(80, 10, R.drawable.bol_geel));
		view.addComponent(new Ball(160, 10, R.drawable.bol_groen));
		view.addComponent(new Ball(240, 10, R.drawable.bol_rood));
		super.onCreate(savedInstanceState);
		setContentView(view, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		int width = getWindowManager().getDefaultDisplay().getWidth();
		int height = getWindowManager().getDefaultDisplay().getHeight();
		Toast.makeText(this, "width:" + width + "\nheight:" + height,
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if(item.getItemId() == R.id.exit)
			finish();
		return super.onMenuItemSelected(featureId, item);
	}
	
	
	
}