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
	// һ��HashSet(�޷��ظ�Ԫ��)�����Ҫ������ӵ�С��
	private HashSet<Ball> basket;
	// �����Ķ��󣬼���Ҫʹ�ô���ͼ�Ķ���
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
		// ����logo
		if(deltaX != 0 || deltaY != 0) {
			canvas = mCanvas;
			canvas.translate(deltaX, deltaY);
			return;
		}
		canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(),
				R.drawable.title), 0, 0, null);
		for (Iterator<Ball> it = basket.iterator(); it.hasNext();) {
			Ball ball = it.next();
			// �����Bitmap
			Bitmap bitmap = BitmapFactory.decodeResource(
					context.getResources(), ball.drawableId);
			// ͼƬ50*50
			/*
			 * Log.i("info", "image width:" + bitmap.getWidth() + "image height"
			 * + bitmap.getHeight());
			 */
			// ��ͼ
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
			// ��Ϊ������꣬�ж���ק�����ĸ��������
			getDragBall(mX, mY);
			break;
		case MotionEvent.ACTION_MOVE:
			// ��������˽��޵Ļ�������������Ĳ���

			if (mX >= (getWidth() - 50) || mY >= (getHeight() - 50)) {
				break;
			}
			// ���ñ��϶����λ��
			if (drag != null) {
				deltaX = drag.x - mX;
				deltaY = drag.y = mY;
				drag.x = mX;
				drag.y = mY;
			}
			// �ػ�
			break;
		case MotionEvent.ACTION_UP:
			// ���ڰ���״̬ʱ�����drag����
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
			// �����ק������������ڣ����ظ���
			if (x > ball.x && y > ball.y && x < (ball.x + 50)
					&& y < (ball.y + 50)) {
				this.drag = ball;
				// ����ʾ�û��Ѿ���������
				try {
					/**
					 * vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
					 * long[] pattern = {800, 50, 400, 30}; 
					 * ����ָ����ģʽ������
					 * ��һ���������������е�һ��Ԫ���ǵȴ��೤��ʱ��������𶯣�
					 * ֮�󽫻��ǿ����͹ر��𶯵ĳ���ʱ�䣬��λΪ����
					 * �ڶ����������ظ���ʱ��pattern�е�������
					 * �������Ϊ-1���ʾ���ظ���
					 * 
					 * //vVi.vibrate( 500 ); ��// ��0.5 �� //�����𶯣�������ָ����ʱ��
					 * vibrator.vibrate(pattern, 2);
					 * //-1���ظ�����-1Ϊ��pattern��ָ���±꿪ʼ�ظ�
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
