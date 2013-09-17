package ryan.view;

import java.util.HashSet;
import java.util.Iterator;

import ryan.activity.R;
import ryan.entity.Ball;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class BallView extends View implements OnTouchListener {
	// 一个HashSet(无法重复元素)，存放要用来添加的小球
	private HashSet<Ball> basket;
	// 上下文对象，即将要使用此视图的对象
	private Context context;
	private float mX;
	private float mY;
	private float deltaX = 0;
	private float deltaY = 0;
	private Ball drag;
	private Canvas mCanvas;

	public BallView(Context context) {
		super(context);
		this.context = context;
		basket = new HashSet<Ball>();
		setOnTouchListener(this);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 绘制logo
		if(deltaX != 0 || deltaY != 0) {
			canvas = mCanvas;
			canvas.translate(deltaX, deltaY);
			return;
		}
		canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(),
				R.drawable.title), 0, 0, null);
		for (Iterator<Ball> it = basket.iterator(); it.hasNext();) {
			Ball ball = it.next();
			// 解码成Bitmap
			Bitmap bitmap = BitmapFactory.decodeResource(
					context.getResources(), ball.drawableId);
			// 图片50*50
			/*
			 * Log.i("info", "image width:" + bitmap.getWidth() + "image height"
			 * + bitmap.getHeight());
			 */
			// 绘图
			/*
			 * Paint paint = new Paint(); paint.setColor(Color.CYAN); //
			 * paint.set
			 * 
			 * canvas.drawCircle(100, 300, 20, paint);
			 */
			// canvas.d
			canvas.drawBitmap(bitmap, ball.x, ball.y, null);
			//Paint paint = new Paint();
			//paint.reset();
			//paint.setColor(Color.BLACK);
			//paint.setStyle(Paint.Style.STROKE);
			//paint.setStrokeWidth(5);
			//canvas.drawCircle(ball.x, ball.y, 20, paint);

		}
		mCanvas = canvas;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		mX = event.getX();
		mY = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 若为按下鼠标，判断拖拽点在哪个球的上面
			getDragBall(mX, mY);
			break;
		case MotionEvent.ACTION_MOVE:
			// 如果超出了界限的话，不进行下面的操作

			if (mX >= (getWidth() - 50) || mY >= (getHeight() - 50)) {
				break;
			}
			// 设置被拖动球的位置
			if (drag != null) {
				deltaX = drag.x - mX;
				deltaY = drag.y = mY;
				drag.x = mX;
				drag.y = mY;
			}
			// 重绘
			break;
		case MotionEvent.ACTION_UP:
			// 不在按下状态时，清空drag对象
			drag = null;
			break;

		}
		invalidate();
		((Activity) context).setTitle("DragTheBall:x:"
				+ (float) (Math.round(mX * 100)) / 100 + " y:"
				+ (float) (Math.round(mY * 100)) / 100);
		return true;
	}

	public boolean addComponent(Ball ball) {
		// true when this HashSet did not already contain the object, false
		// otherwise
		return basket.add(ball);
	}

	public void getDragBall(float x, float y) {
		for (Iterator<Ball> it = basket.iterator(); it.hasNext();) {

			Ball ball = it.next();
			// 如果拖拽点在球的区域内，返回该球
			if (x > ball.x && y > ball.y && x < (ball.x + 50)
					&& y < (ball.y + 50)) {
				this.drag = ball;
				// 震动提示用户已经触摸到球
				try {
					/**
					 * vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
					 * long[] pattern = {800, 50, 400, 30}; 
					 * 根据指定的模式进行震动
					 * 第一个参数：该数组中第一个元素是等待多长的时间才启动震动，
					 * 之后将会是开启和关闭震动的持续时间，单位为毫秒
					 * 第二个参数：重复震动时在pattern中的索引，
					 * 如果设置为-1则表示不重复震动
					 * 
					 * //vVi.vibrate( 500 ); 　// 震动0.5 秒 //启动震动，并持续指定的时间
					 * vibrator.vibrate(pattern, 2);
					 * //-1不重复，非-1为从pattern的指定下标开始重复
					 */
					Vibrator vibrator = (Vibrator) context
							.getApplicationContext().getSystemService(
									Service.VIBRATOR_SERVICE);

					//vibrator.vibrate(new long[] { 100, 10, 100, 1000 }, -1);
					vibrator.vibrate(50);
				} catch (Exception e) {
					System.err.println(e);
				}
			}
		}
	}
}
