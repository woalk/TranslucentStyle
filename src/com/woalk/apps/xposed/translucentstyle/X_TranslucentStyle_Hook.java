package com.woalk.apps.xposed.translucentstyle;

import android.app.AndroidAppHelper;
import android.content.Context;
import android.content.res.XResources;
import android.graphics.drawable.Drawable;
import android.view.View;
import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class X_TranslucentStyle_Hook implements
		/* IXposedHookZygoteInit, */IXposedHookInitPackageResources,
		IXposedHookLoadPackage {
	/*
	 * -- Deprecated. --
	 */

	/*
	 * private static String MODULE_PATH = null;
	 * 
	 * @Override public void initZygote(StartupParam startupParam) throws
	 * Throwable { MODULE_PATH = startupParam.modulePath; }
	 */

	/*
	 * -- end deprecated --
	 */

	@Override
	public void handleInitPackageResources(InitPackageResourcesParam resparam)
			throws Throwable {
		if (!resparam.packageName.equals(Statics.SYSTEMUI))
			return;

		final XSharedPreferences XsPref = new XSharedPreferences(
				Statics.PACKAGENAME, Statics.PREFERENCE_NAME);
		// XModuleResources modRes =
		// XModuleResources.createInstance(MODULE_PATH, resparam.res);

		final int status_style = XsPref.getInt(Statics.PREF_STATUSDRAWABLE, 0);
		final int nav_style = XsPref.getInt(Statics.PREF_NAVDRAWABLE, 0);

		if (status_style > 0) {
			try {
				if (XsPref.getBoolean(Statics.PREF_RES1, true)) {
					resparam.res.setReplacement("com.android.systemui",
							"drawable", "status_background",
							new XResources.DrawableLoader() {
								@Override
								public Drawable newDrawable(XResources res,
										int id) throws Throwable {
									if (Statics.isBitmap(status_style))
										return Statics
												.getDrawable(
														status_style,
														false,
														-1,
														AndroidAppHelper
																.currentApplication()
																.getApplicationContext()
																.createPackageContext(
																		Statics.PACKAGENAME,
																		Context.CONTEXT_IGNORE_SECURITY));
									else {
										int color = XsPref.getInt(
												Statics.PREF_COLOR_STATUS, -1);
										if (color == -1)
											color = 0x50000000;
										return Statics.getDrawable(
												status_style, false, color,
												null);
									}
								}
							});
				}
			} catch (Throwable e) {
				XposedBridge.log(e);
			}
			try {
				if (XsPref.getBoolean(Statics.PREF_RES2, true)) {
					resparam.res.setReplacement("com.android.systemui",
							"drawable", "status_bar_background_transparent",
							new XResources.DrawableLoader() {
								@Override
								public Drawable newDrawable(XResources res,
										int id) throws Throwable {
									if (Statics.isBitmap(status_style))
										return Statics
												.getDrawable(
														status_style,
														false,
														-1,
														AndroidAppHelper
																.currentApplication()
																.getApplicationContext()
																.createPackageContext(
																		Statics.PACKAGENAME,
																		Context.CONTEXT_IGNORE_SECURITY));
									else {
										int color = XsPref.getInt(
												Statics.PREF_COLOR_STATUS, -1);
										if (color == -1)
											color = 0x50000000;
										return Statics.getDrawable(
												status_style, false, color,
												null);
									}
								}
							});
				}
			} catch (Throwable e) {
				XposedBridge.log(e);
			}
		}
		if (nav_style > 0) {
			try {
				if (XsPref.getBoolean(Statics.PREF_RES1, true)) {
					resparam.res.setReplacement("com.android.systemui",
							"drawable", "nav_background",
							new XResources.DrawableLoader() {
								@Override
								public Drawable newDrawable(XResources res,
										int id) throws Throwable {
									if (Statics.isBitmap(nav_style))
										return Statics
												.getDrawable(
														nav_style,
														true,
														-1,
														AndroidAppHelper
																.currentApplication()
																.getApplicationContext()
																.createPackageContext(
																		Statics.PACKAGENAME,
																		Context.CONTEXT_IGNORE_SECURITY));
									else {
										int color = XsPref.getInt(
												Statics.PREF_COLOR_NAV, -1);
										if (color == -1)
											color = 0x50000000;
										return Statics.getDrawable(nav_style,
												true, color, null);
									}
								}
							});
				}
			} catch (Throwable e) {
				XposedBridge.log(e);
			}
			try {
				if (XsPref.getBoolean(Statics.PREF_RES2, true)) {
					resparam.res.setReplacement("com.android.systemui",
							"drawable", "navigation_background_transparent",
							new XResources.DrawableLoader() {
								@Override
								public Drawable newDrawable(XResources res,
										int id) throws Throwable {
									if (Statics.isBitmap(nav_style))
										return Statics
												.getDrawable(
														nav_style,
														true,
														-1,
														AndroidAppHelper
																.currentApplication()
																.getApplicationContext()
																.createPackageContext(
																		Statics.PACKAGENAME,
																		Context.CONTEXT_IGNORE_SECURITY));
									else {
										int color = XsPref.getInt(
												Statics.PREF_COLOR_NAV, -1);
										if (color == -1)
											color = 0x50000000;
										return Statics.getDrawable(nav_style,
												true, color, null);
									}
								}
							});
				}
			} catch (Throwable e) {
				XposedBridge.log(e);
			}
		}
	}

	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		if (!lpparam.packageName.equals(Statics.SYSTEMUI))
			return;
		try {
			final XSharedPreferences XsPref = new XSharedPreferences(
					Statics.PACKAGENAME, Statics.PREFERENCE_NAME);

			if (!XsPref.getBoolean(Statics.PREF_M, true))
				return;

			final int status_style = XsPref.getInt(Statics.PREF_STATUSDRAWABLE,
					0);
			final int nav_style = XsPref.getInt(Statics.PREF_NAVDRAWABLE, 0);

			if (status_style > 0 && status_style < 11) {
				Class<?> clazz = XposedHelpers.findClass(Statics.SYSTEMUI
						+ ".statusbar.phone.PhoneStatusBarTransitions",
						lpparam.classLoader);

				XposedBridge.hookAllConstructors(clazz, new XC_MethodHook() {
					@Override
					protected void afterHookedMethod(MethodHookParam param)
							throws Throwable {
						Object thisObj = param.thisObject;
						Object barBackground = XposedHelpers.getObjectField(
								thisObj, "mBarBackground");
						if (Statics.isBitmap(status_style)) {
							View v = (View) XposedHelpers.getObjectField(
									thisObj, "mView");
							Context tsContext = v.getContext()
									.createPackageContext(Statics.PACKAGENAME,
											Context.CONTEXT_IGNORE_SECURITY);
							XposedHelpers.setObjectField(barBackground,
									"mGradient", Statics.getDrawable(nav_style,
											false, -1, tsContext));
						} else {
							int color = XsPref.getInt(
									Statics.PREF_COLOR_STATUS, -1);
							if (color == -1)
								color = 0x50000000;
							XposedHelpers.setObjectField(barBackground,
									"mGradient", Statics.getDrawable(
											status_style, false, color, null));
						}
					}
				});
			}

			if (nav_style > 0 && nav_style < 11) {
				Class<?> clazz = XposedHelpers.findClass(Statics.SYSTEMUI
						+ ".statusbar.phone.NavigationBarTransitions",
						lpparam.classLoader);

				XposedBridge.hookAllConstructors(clazz, new XC_MethodHook() {
					@Override
					protected void afterHookedMethod(MethodHookParam param)
							throws Throwable {
						Object thisObj = param.thisObject;
						Object barBackground = XposedHelpers.getObjectField(
								thisObj, "mBarBackground");
						if (Statics.isBitmap(nav_style)) {
							View v = (View) XposedHelpers.getObjectField(
									thisObj, "mView");
							Context tsContext = v.getContext()
									.createPackageContext(Statics.PACKAGENAME,
											Context.CONTEXT_IGNORE_SECURITY);
							XposedHelpers.setObjectField(barBackground,
									"mGradient", Statics.getDrawable(nav_style,
											true, -1, tsContext));
						} else {
							int color = XsPref.getInt(Statics.PREF_COLOR_NAV,
									-1);
							if (color == -1)
								color = 0x50000000;
							XposedHelpers.setObjectField(barBackground,
									"mGradient", Statics.getDrawable(nav_style,
											true, color, null));
						}
					}
				});
			}
		} catch (Throwable e) {
			XposedBridge.log(e);
		}
	}
}
