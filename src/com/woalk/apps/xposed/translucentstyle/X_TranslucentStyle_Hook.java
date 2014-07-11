package com.woalk.apps.xposed.translucentstyle;

import android.content.res.XModuleResources;
import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;

import static de.robv.android.xposed.XposedBridge.log;

public class X_TranslucentStyle_Hook implements IXposedHookZygoteInit, IXposedHookInitPackageResources {
	private static String MODULE_PATH = null;

	@Override
	public void initZygote(StartupParam startupParam) throws Throwable {
		MODULE_PATH = startupParam.modulePath;
	}

	@Override
	public void handleInitPackageResources(InitPackageResourcesParam resparam) throws Throwable {
		if (!resparam.packageName.equals("com.android.systemui"))
			return;

		XSharedPreferences XsPref = new XSharedPreferences(Statics.PACKAGENAME, Statics.PREFERENCE_NAME);
		XModuleResources modRes = XModuleResources.createInstance(MODULE_PATH, resparam.res);
		
		final int status_style = XsPref.getInt(Statics.PREF_STATUSDRAWABLE, 0);
		final int nav_style = XsPref.getInt(Statics.PREF_NAVDRAWABLE, 0);

		if (status_style > 0) {
	        try {
	        	resparam.res.setReplacement("com.android.systemui", "drawable", "status_background", modRes.fwd(Statics.getDrawable(status_style, false)));
	        } catch (Throwable e) {
	        	log("AOSP SystemUI status bar resource not present.");
	        }
	        try {
	        	resparam.res.setReplacement("com.android.systemui", "drawable", "status_bar_background_transparent", modRes.fwd(Statics.getDrawable(status_style, false)));
	        } catch (Throwable e) {
	        	log("HTC SystemUI status bar resource not present.");
	        }
		}
		if (nav_style > 0) {
			try {
				resparam.res.setReplacement("com.android.systemui", "drawable", "nav_background", modRes.fwd(Statics.getDrawable(nav_style, true)));
			} catch (Throwable e) {
				log("AOSP SystemUI navigation bar resource not present.");
			}
			try {
				resparam.res.setReplacement("com.android.systemui", "drawable", "navigation_background_transparent", modRes.fwd(Statics.getDrawable(nav_style, true)));
			} catch (Throwable e) {
				log("HTC SystemUI navigation bar resource not present.");
			}
		}
	}
}
