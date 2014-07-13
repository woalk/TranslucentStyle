package com.woalk.apps.xposed.translucentstyle;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;

public class Statics {
	private Statics() { }

	public static final String SYSTEMUI = "com.android.systemui";
	public static final String PACKAGENAME = "com.woalk.apps.xposed.translucentstyle";
	public static final String PREFERENCE_NAME = PACKAGENAME + ".MAINPREF";
	public static final String PREF_STATUSDRAWABLE = PACKAGENAME + ".PREF_STATUSDRAWABLE"; // int preference
	public static final String PREF_NAVDRAWABLE = PACKAGENAME + ".PREF_NAVDRAWABLE";       // int preference
	public static final String PREF_COLOR_STATUS = PACKAGENAME + ".PREF_COLOR_STATUS";     // int preference
	public static final String PREF_COLOR_NAV = PACKAGENAME + ".PREF_COLOR_NAV";           // int preference

	public static final int VALUE_NO_CHANGE = 0;
	public static final int VALUE_KITKAT = 1;
	public static final int VALUE_GRADIENT = 2;
	public static final int VALUE_NOTHING = 3;
	public static final int VALUE_SOLID = 4;
	public static final int VALUE_SENSE_5 = 5;
	public static final int VALUE_CARBLEND = 6;
	public static final int VALUE_HALO = 7;
	public static final int VALUE_BRACKET = 8;
	public static final int VALUE_CUTTER = 9;
	public static final int VALUE_XPERIA = 10;
	
	public static boolean isBitmap(int style) {
		return (style != VALUE_SOLID &&
				style != VALUE_GRADIENT &&
				style != VALUE_NO_CHANGE &&
				style != VALUE_NOTHING);
	}
	
	public static boolean isCustomizable(int style) {
		return (style == VALUE_GRADIENT || style == VALUE_SOLID);
	}
	
	public static Drawable getDrawable(int style, boolean isNav, int color, Context tsContext) {
		switch (style) {
		case VALUE_NO_CHANGE:
			return null;
		case VALUE_KITKAT:
			return Drawables.getKitKatGradient(isNav, tsContext);
		case VALUE_GRADIENT:
			return Drawables.getGradient(isNav, color);
		case VALUE_NOTHING:
			return Drawables.getEmpty();
		case VALUE_SOLID:
			return Drawables.getSolid(color);
		case VALUE_SENSE_5:
			return Drawables.getSense5(isNav, tsContext);
		case VALUE_CARBLEND:
			return Drawables.getCarBlend(isNav, tsContext);
		case VALUE_HALO:
			return Drawables.getHaloGradient(isNav, tsContext);
		case VALUE_BRACKET:
			return Drawables.getBracketGradient(isNav, tsContext);
		case VALUE_CUTTER:
			return Drawables.getCutter(isNav, tsContext);
		case VALUE_XPERIA:
			return Drawables.getXperia(isNav, tsContext);
		default:
			return null;
		}
	}

	public static class Drawables {
		public static Drawable getKitKatGradient(boolean isNav, Context tsContext) {
			if (!isNav)
				return tsContext.getResources().getDrawable(R.drawable.status_kkgradient);
			else
				return tsContext.getResources().getDrawable(R.drawable.nav_kkgradient);
		}

		public static Drawable getGradient(boolean isNav, int color) {
			int color2 = Color.argb(0, Color.red(color), Color.green(color), Color.blue(color));
			return new GradientDrawable((!isNav ? Orientation.TOP_BOTTOM : Orientation.BOTTOM_TOP),
					new int[]{color, color2});
		}

		public static Drawable getEmpty() {
			return getSolid(0x00000000);
		}

		public static Drawable getSolid(int color) {
			return new ColorDrawable(color);
		}
		
		public static Drawable getSense5(boolean isNav, Context tsContext) {
			if (!isNav)
				return tsContext.getResources().getDrawable(R.drawable.sense5status);
			else
				return tsContext.getResources().getDrawable(R.drawable.sense5nav);
		}

		public static Drawable getCarBlend(boolean isNav, Context tsContext) {
			if (!isNav)
				return tsContext.getResources().getDrawable(R.drawable.carblend_status);
			else
				return tsContext.getResources().getDrawable(R.drawable.carblend_nav);
		}

		public static Drawable getHaloGradient(boolean isNav, Context tsContext) {
			if (!isNav)
				return tsContext.getResources().getDrawable(R.drawable.halo_status);
			else
				return tsContext.getResources().getDrawable(R.drawable.halo_nav);
		}

		public static Drawable getBracketGradient(boolean isNav, Context tsContext) {
			if (!isNav)
				return tsContext.getResources().getDrawable(R.drawable.bracket_status);
			else
				return tsContext.getResources().getDrawable(R.drawable.bracket_nav);
		}

		public static Drawable getCutter(boolean isNav, Context tsContext) {
			if (!isNav)
				return tsContext.getResources().getDrawable(R.drawable.cutter_status);
			else
				return tsContext.getResources().getDrawable(R.drawable.cutter_nav);
		}
		
		public static Drawable getXperia(boolean isNav, Context tsContext) {
			if (!isNav)
				return tsContext.getResources().getDrawable(R.drawable.xperia_status);
			else
				return tsContext.getResources().getDrawable(R.drawable.xperia_nav);
		}
	}

	public static int getColor(String colorHex) {
		if (!colorHex.startsWith("#")) colorHex = "#" + colorHex;
		int a; int r; int g; int b;
		if (colorHex.length() == 7) {
			a = 0xFF;
			r = Integer.parseInt(colorHex.substring(1, 3), 16);
			g = Integer.parseInt(colorHex.substring(3, 5), 16);
			b = Integer.parseInt(colorHex.substring(5, 7), 16);
		} else if (colorHex.length() == 9) {
			a = Integer.parseInt(colorHex.substring(1, 3), 16);
			r = Integer.parseInt(colorHex.substring(3, 5), 16);
			g = Integer.parseInt(colorHex.substring(5, 7), 16);
			b = Integer.parseInt(colorHex.substring(7, 9), 16);
		} else return Color.BLACK;
		int c = Color.argb(a, r, g, b);
		return c;
	}
	
	public static String getColorHexString(int color) {
		String colorHex = "#";
		String a = Integer.toHexString(Color.alpha(color));
		if (a.length() == 1) a = "0" + a;
		String r = Integer.toHexString(Color.red(color));
		if (r.length() == 1) r = "0" + r;
		String g = Integer.toHexString(Color.green(color));
		if (g.length() == 1) g = "0" + g;
		String b = Integer.toHexString(Color.blue(color));
		if (b.length() == 1) b = "0" + b;
		colorHex += a + r + g + b;
		return colorHex;
	}
}
