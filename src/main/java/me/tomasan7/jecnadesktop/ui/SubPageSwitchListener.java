package me.tomasan7.jecnadesktop.ui;

/**
 * A listener for {@link SubPage} switching inside a {@link SubPageManager}.
 */
public interface SubPageSwitchListener
{
	/**
	 * Called <b>right after</b> a subpage is switched.
	 * @param oldSubPage The previous {@link SubPage}.
	 * @param newSubPage The new {@link SubPage}.
	 */
	void onSwitch (SubPage oldSubPage, SubPage newSubPage);
}
