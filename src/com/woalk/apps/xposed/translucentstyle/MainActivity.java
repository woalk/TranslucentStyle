package com.woalk.apps.xposed.translucentstyle;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {

	protected Spinner spinner_status;
	protected Spinner spinner_nav;

	protected View view_status;
	protected View view_nav;

	@SuppressLint("WorldReadableFiles")
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		spinner_status = (Spinner) findViewById(R.id.spinner1);
		spinner_nav = (Spinner) findViewById(R.id.spinner2);

		view_status = findViewById(R.id.view1);
		view_nav = findViewById(R.id.view2);

		PackageInfo pInfo = null;
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			((TextView) findViewById(R.id.textView_info))
					.setText(getString(R.string.copyright_info) + "\n\n"
							+ getString(R.string.prefix_version) + " "
							+ pInfo.versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		final SharedPreferences sPref = getApplicationContext()
				.getSharedPreferences(Statics.PREFERENCE_NAME,
						Context.MODE_WORLD_READABLE);

		spinner_status.setSelection(sPref
				.getInt(Statics.PREF_STATUSDRAWABLE, 0));
		spinner_nav.setSelection(sPref.getInt(Statics.PREF_NAVDRAWABLE, 0));

		spinner_status.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				SharedPreferences.Editor edit = sPref.edit();
				edit.putInt(Statics.PREF_STATUSDRAWABLE, position);
				edit.apply();
				int color = sPref.getInt(Statics.PREF_COLOR_STATUS, -1);
				if (color == -1)
					color = 0x50000000;
				view_status.setBackground(Statics.getDrawable(position, false,
						color, MainActivity.this.getApplicationContext()));
				findViewById(R.id.button2).setEnabled(
						Statics.isCustomizable(position));
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		spinner_nav.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				SharedPreferences.Editor edit = sPref.edit();
				edit.putInt(Statics.PREF_NAVDRAWABLE, position);
				edit.apply();
				int color = sPref.getInt(Statics.PREF_COLOR_NAV, -1);
				if (color == -1)
					color = 0x50000000;
				view_nav.setBackground(Statics.getDrawable(position, true,
						color, MainActivity.this.getApplicationContext()));
				findViewById(R.id.button3).setEnabled(
						Statics.isCustomizable(position));
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				builder.setTitle(R.string.title_restart);
				builder.setMessage(R.string.msg_restart);
				builder.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								try {
									Process proc = Runtime
											.getRuntime()
											.exec(new String[] {
													"su",
													"-c",
													"busybox killall "
															+ Statics.SYSTEMUI });
									proc.waitFor();
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							}
						});
				builder.setNegativeButton(android.R.string.no, null);
				builder.show();
			}
		});

		findViewById(R.id.button2).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final int position = spinner_status.getSelectedItemPosition();
				int color = sPref.getInt(Statics.PREF_COLOR_STATUS, -1);
				if (color == -1)
					color = 0x50000000;
				ConfigController cc = new ConfigController(MainActivity.this,
						false, position, color);
				cc.setConfigControllerListener(new ConfigController.ConfigControllerListener() {
					@Override
					public void onResult(int color) {
						SharedPreferences.Editor edit = sPref.edit();
						edit.putInt(Statics.PREF_COLOR_STATUS, color);
						edit.apply();
						view_status.setBackground(Statics.getDrawable(position,
								false, color,
								MainActivity.this.getApplicationContext()));
					}
				});
				cc.show(getFragmentManager(), "cc");
			}
		});

		findViewById(R.id.button3).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final int position = spinner_nav.getSelectedItemPosition();
				int color = sPref.getInt(Statics.PREF_COLOR_NAV, -1);
				if (color == -1)
					color = 0x50000000;
				ConfigController cc = new ConfigController(MainActivity.this,
						true, position, color);
				cc.setConfigControllerListener(new ConfigController.ConfigControllerListener() {
					@Override
					public void onResult(int color) {
						SharedPreferences.Editor edit = sPref.edit();
						edit.putInt(Statics.PREF_COLOR_NAV, color);
						edit.apply();
						view_nav.setBackground(Statics.getDrawable(position,
								true, color,
								MainActivity.this.getApplicationContext()));
					}
				});
				cc.show(getFragmentManager(), "cc");
			}
		});

		boolean res1 = sPref.getBoolean(Statics.PREF_RES1, false);
		boolean res2 = sPref.getBoolean(Statics.PREF_RES2, false);
		boolean res3 = sPref.getBoolean(Statics.PREF_RES3, false);
		boolean res4 = sPref.getBoolean(Statics.PREF_RES4, false);
		boolean m = sPref.getBoolean(Statics.PREF_M, false);
		boolean cm = sPref.getBoolean(Statics.PREF_CM, false);

		CheckBox check1 = ((CheckBox) findViewById(R.id.checkBox1));
		CheckBox check2 = ((CheckBox) findViewById(R.id.checkBox2));
		CheckBox check3 = ((CheckBox) findViewById(R.id.checkBox3));
		CheckBox check4 = ((CheckBox) findViewById(R.id.checkBox4));
		CheckBox check5 = ((CheckBox) findViewById(R.id.checkBox5));
		CheckBox check6 = ((CheckBox) findViewById(R.id.checkBox6));

		check1.setChecked(res1);
		check2.setChecked(res2);
		check3.setChecked(m);
		check4.setChecked(cm);
		check5.setChecked(res3);
		check6.setChecked(res4);

		check1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				SharedPreferences.Editor edit = sPref.edit();
				edit.putBoolean(Statics.PREF_RES1, isChecked);
				edit.apply();
			}
		});
		check2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				SharedPreferences.Editor edit = sPref.edit();
				edit.putBoolean(Statics.PREF_RES2, isChecked);
				edit.apply();
			}
		});
		check3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				SharedPreferences.Editor edit = sPref.edit();
				edit.putBoolean(Statics.PREF_M, isChecked);
				edit.apply();
			}
		});
		check4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				SharedPreferences.Editor edit = sPref.edit();
				edit.putBoolean(Statics.PREF_CM, isChecked);
				edit.apply();
			}
		});
		check5.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				SharedPreferences.Editor edit = sPref.edit();
				edit.putBoolean(Statics.PREF_RES3, isChecked);
				edit.apply();
			}
		});
		check6.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				SharedPreferences.Editor edit = sPref.edit();
				edit.putBoolean(Statics.PREF_RES4, isChecked);
				edit.apply();
			}
		});
	}
}
