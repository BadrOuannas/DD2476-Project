2
https://raw.githubusercontent.com/AtomizedSoul/TulpApp/master/TulpApp.Android/obj/Release/90/android/src/crc643f46942d9dd1fff9/FormattedStringExtensions_TextDecorationSpan.java
package crc643f46942d9dd1fff9;


public class FormattedStringExtensions_TextDecorationSpan
	extends android.text.style.MetricAffectingSpan
	implements
		mono.android.IGCUserPeer
{
/** @hide */
	public static final String __md_methods;
	static {
		__md_methods = 
			"n_updateDrawState:(Landroid/text/TextPaint;)V:GetUpdateDrawState_Landroid_text_TextPaint_Handler\n" +
			"n_updateMeasureState:(Landroid/text/TextPaint;)V:GetUpdateMeasureState_Landroid_text_TextPaint_Handler\n" +
			"";
		mono.android.Runtime.register ("Xamarin.Forms.Platform.Android.FormattedStringExtensions+TextDecorationSpan, Xamarin.Forms.Platform.Android", FormattedStringExtensions_TextDecorationSpan.class, __md_methods);
	}


	public FormattedStringExtensions_TextDecorationSpan ()
	{
		super ();
		if (getClass () == FormattedStringExtensions_TextDecorationSpan.class)
			mono.android.TypeManager.Activate ("Xamarin.Forms.Platform.Android.FormattedStringExtensions+TextDecorationSpan, Xamarin.Forms.Platform.Android", "", this, new java.lang.Object[] {  });
	}


	public void updateDrawState (android.text.TextPaint p0)
	{
		n_updateDrawState (p0);
	}

	private native void n_updateDrawState (android.text.TextPaint p0);


	public void updateMeasureState (android.text.TextPaint p0)
	{
		n_updateMeasureState (p0);
	}

	private native void n_updateMeasureState (android.text.TextPaint p0);

	private java.util.ArrayList refList;
	public void monodroidAddReference (java.lang.Object obj)
	{
		if (refList == null)
			refList = new java.util.ArrayList ();
		refList.add (obj);
	}

	public void monodroidClearReferences ()
	{
		if (refList != null)
			refList.clear ();
	}
}