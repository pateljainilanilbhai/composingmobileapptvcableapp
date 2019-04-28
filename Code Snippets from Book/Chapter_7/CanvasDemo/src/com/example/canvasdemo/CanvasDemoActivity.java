package com.example.canvasdemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class CanvasDemoActivity extends Activity {

	@Override
 	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
 		setContentView(new MyView(this, null));
 		Toast.makeText(this, "Touch anywhere to change the location of image",Toast.LENGTH_LONG).show(); 
 	}

	class MyView extends View {
		Bitmap image;
		Paint paint;
		float left = 0, top = 0;

		public MyView(Context context, AttributeSet attrs) {
			super(context, attrs);
			image = BitmapFactory.decodeResource(getResources(),
					R.drawable.ic_launcher);
			paint = new Paint();
			paint.setDither(true);
			paint.setAntiAlias(true);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			canvas.drawBitmap(image, left, top, paint);
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			left = event.getX();
			top = event.getY();
			invalidate();
			return true;
		}
	}
}
