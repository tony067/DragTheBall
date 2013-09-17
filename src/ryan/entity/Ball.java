package ryan.entity;

import android.util.Log;

/**
 * @author Ryan Hoo
 * @date: 2012/3/11
 * */
public class Ball {
	public int id;// Ψһ��ʾ
	public float x;// ������
	public float y;// ������

	public int drawableId;

	public Ball(float x, float y, int drawableId) {
		this.x = x;
		this.y = y;
		this.drawableId = drawableId;
		id = this.hashCode();
		Log.d("ball init", "id:" + id + " x:" + x + " y:" + y + " drawableId:"
				+ drawableId);
	}
}
