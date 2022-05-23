package me.tomasan7.jecnadesktop.ui;

/**
 * A listener for {@link Page subpage} switching inside a {@link SubPageManager}.
 */
public interface SubPageSwitchListener
{
	/**
	 * Called <b>right after</b> a subpage is switched.
	 * @param oldSubPage The previous {@link Page subpage}.
	 * @param newSubPage The new {@link Page subpage}.
	 */
	void onSwitch (SubPage oldSubPage, SubPage newSubPage);
}
