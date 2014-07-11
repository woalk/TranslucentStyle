package com.woalk.apps.xposed.translucentstyle;

public class Statics {
	private Statics() { }
	
	public static final String PACKAGENAME = "com.woalk.apps.xposed.translucentstyle";
	public static final String PREFERENCE_NAME = PACKAGENAME + ".MAINPREF";
	public static final String PREF_STATUSDRAWABLE = PACKAGENAME + ".PREF_STATUSDRAWABLE"; // int preference
	public static final String PREF_NAVDRAWABLE = PACKAGENAME + ".PREF_NAVDRAWABLE";       // int preference
	
	public static final int VALUE_NO_CHANGE = 0;
	public static final int VALUE_KITKAT = 1;
	public static final int VALUE_KITKAT_DARK = 2;
	public static final int VALUE_KITKAT_LIGHT = 3;
	public static final int VALUE_NOTHING = 4;
	public static final int VALUE_L = 5;
	
	public static int getDrawable(int style, boolean isNav) {
		switch (style) {
		case 0:
			return 0;
		case 1:
			if (!isNav)
				return R.drawable.status_kkgradient;
			else
				return R.drawable.nav_kkgradient;
		case 2:
			if (!isNav)
				return R.drawable.kk_dark_status;
			else
				return R.drawable.kk_dark_nav;
		case 3:
			if (!isNav)
				return R.drawable.kk_light_status;
			else
				return R.drawable.kk_light_nav;
		case 4:
			return R.drawable.empty;
		case 5:
			return R.drawable.l;
		case 6:
			return R.drawable.l_dark;
		case 7:
			return R.drawable.l_light;
		case 8:
			if (!isNav) 
				return R.drawable.sense5status;
			else
				return R.drawable.sense5nav;
		case 9:
			if (!isNav)
				return R.drawable.carblend_status;
			else
				return R.drawable.carblend_nav;
		case 10:
			if (!isNav)
				return R.drawable.halo_status;
			else
				return R.drawable.halo_nav;
		case 11:
			if (!isNav)
				return R.drawable.bracket_status;
			else
				return R.drawable.bracket_nav;
		case 12:
			if (!isNav)
				return R.drawable.cutter_status;
			else
				return R.drawable.cutter_nav;
		default:
			return -1;
		}
	}
}
