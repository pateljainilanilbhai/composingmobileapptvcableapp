package com.example.drawableanimation;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

public class DrawableAnimationDemo extends Activity implements OnClickListener {

	private ImageView image;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.draw_anim);
		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(this);
		image = (ImageView) findViewById(R.id.ImageView1);
		image.setBackgroundResource(R.drawable.drawable_anim);
	}

	public void onClick(View v) {
		AnimationDrawable frameAnimation = (AnimationDrawable) image
				.getBackground();
		frameAnimation.start();
	}
}
