package com.woalk.apps.xposed.translucentstyle;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TableRow;

public class ConfigController extends DialogFragment {
	
	public interface ConfigControllerListener {
		public abstract void onResult(int color);
	}
	
	private final Context context;
	
	protected final boolean isNav;
	protected final int style;
	
	protected int color;
	
	private ConfigControllerListener listener;
	
	public ConfigController(Context context, boolean isNav, int style, int preset) {
		this.isNav = isNav;
		this.style = style;
		this.color = preset;
		this.context = context;
	}
	
	public ConfigControllerListener setConfigControllerListener(ConfigControllerListener listener) {
		this.listener = listener;
		return listener;
	}
	
	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedStateInstance) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    View v = inflater.inflate(R.layout.colorconfig, null);
	    
	    final View view1 = v.findViewById(R.id.view1);
	    final View view2 = v.findViewById(R.id.view2);
	    if (isNav)
	    	view1.setVisibility(View.GONE);
	    else
	    	view2.setVisibility(View.GONE);
	    
	    final SeekBar seek_a = (SeekBar) v.findViewById(R.id.seek_a);
	    final SeekBar seek_r = (SeekBar) v.findViewById(R.id.seek_r);
	    final SeekBar seek_g = (SeekBar) v.findViewById(R.id.seek_g);
	    final SeekBar seek_b = (SeekBar) v.findViewById(R.id.seek_b);
	    final EditText edit_hex = (EditText) v.findViewById(R.id.edit_hex);
	    final Spinner spinner_predef = (Spinner) v.findViewById(R.id.spinner_predef);
	    
	    if (style != Statics.VALUE_SOLID)
	    	v.findViewById(R.id.tableRow_predef).setVisibility(View.GONE);
	    
	    SeekBar.OnSeekBarChangeListener seek_l = new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) { }
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) { }
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if (fromUser) {
					spinner_predef.setSelection(0);
					color = Color.argb(seek_a.getProgress(), seek_r.getProgress(), seek_g.getProgress(), seek_b.getProgress());
				}
				edit_hex.setText(Statics.getColorHexString(color));
				seek_a.setProgressDrawable(new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
						new int[]{Color.argb(0, Color.red(color), Color.green(color), Color.blue(color)),
						Color.argb(255, Color.red(color), Color.green(color), Color.blue(color))}));
				seek_r.setProgressDrawable(new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
						new int[]{Color.argb(Color.alpha(color), 0, Color.green(color), Color.blue(color)),
						Color.argb(Color.alpha(color), 255, Color.green(color), Color.blue(color))}));
				seek_g.setProgressDrawable(new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
						new int[]{Color.argb(Color.alpha(color), Color.red(color), 0, Color.blue(color)),
						Color.argb(Color.alpha(color), Color.red(color), 255, Color.blue(color))}));
				seek_b.setProgressDrawable(new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
						new int[]{Color.argb(Color.alpha(color), Color.red(color), Color.green(color), 0),
						Color.argb(Color.alpha(color), Color.red(color), Color.green(color), 255)}));
				updateViews(view1, view2);
			}
		};
		seek_a.setOnSeekBarChangeListener(seek_l);
		seek_r.setOnSeekBarChangeListener(seek_l);
		seek_g.setOnSeekBarChangeListener(seek_l);
		seek_b.setOnSeekBarChangeListener(seek_l);
		
		spinner_predef.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				case 1:
					color = 0x60000000;
					break;
				case 2:
					color = 0x26000000;
					break;
				}
				updateBars(seek_a, seek_r, seek_g, seek_b);
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) { }
		});
		
		updateBars(seek_a, seek_r, seek_g, seek_b);
		
		edit_hex.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) { }
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString().startsWith("#") && s.length() == 9 ||s.length() == 8) {
					color = Statics.getColor(s.toString());
					updateBars(seek_a, seek_r, seek_g, seek_b);
				}
			}
		});
	    
	    builder.setView(v)
        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (listener != null) listener.onResult(color);
			}
        })
        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ConfigController.this.getDialog().cancel();
            }
        });
	    return builder.create();
	}
	
	private void updateBars(SeekBar... seek) {
		if (seek.length == 4) {
			seek[0].setProgress(Color.alpha(color));
			seek[1].setProgress(Color.red(color));
			seek[2].setProgress(Color.green(color));
			seek[3].setProgress(Color.blue(color));
		} else {
			throw new IllegalArgumentException("Not the correct SeekBars.");
		}
	}
	
	private void updateViews(View... views) {
		for (int i = 0; i < views.length; i++) {
			views[i].setBackground(Statics.getDrawable(style, isNav, color, context.getApplicationContext()));
		}
	}
}
