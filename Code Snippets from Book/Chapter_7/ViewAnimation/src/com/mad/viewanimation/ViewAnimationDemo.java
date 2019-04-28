package com.mad.viewanimation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class ViewAnimationDemo extends Activity implements OnClickListener {

	private Button hideButton;
	private ImageView greenBotImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_animation_demo);
		hideButton = (Button) findViewById(R.id.button1);
		hideButton.setOnClickListener(this);
		greenBotImageView = (ImageView) findViewById(R.id.ImageView1);
	}

	@Override
	public void onClick(View v) {
		Animation hideAnim =AnimationUtils.loadAnimation(this, R.anim.hide_anim);
		greenBotImageView.startAnimation(hideAnim);
	}

}
