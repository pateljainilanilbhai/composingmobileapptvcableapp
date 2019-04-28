package com.app3c;

import com.app3c.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

public class ChannelsDialogFragment extends DialogFragment{
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		String title = getArguments().getString(CategoriesListFragment.CATEGORY_NAME);
		int itemsId;
		if(title.equalsIgnoreCase("Movies")){
			itemsId = R.array.Movies;
		}
		else if(title.equalsIgnoreCase("Music")){
			itemsId = R.array.Music;
		}
		else if(title.equalsIgnoreCase("Sports")){
			itemsId = R.array.Sports;
		}
		else if(title.equalsIgnoreCase("Lifestyle")){
			itemsId = R.array.Lifestyle;
		}
		else if(title.equalsIgnoreCase("Kids")){
			itemsId = R.array.Kids;
		}
		else{
			itemsId = R.array.Entertainment;
		}
		
		builder.setTitle(title)
			.setItems(itemsId, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					
				}
			})
			.setNegativeButton("OK", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					
				}
			});
		return builder.create();
	}

	

}
