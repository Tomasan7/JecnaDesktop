package me.tomasan7.jecnadesktop.ui.scene

import me.tomasan7.jecnadesktop.ui.FxmlPage
import me.tomasan7.jecnadesktop.ui.JDScene

class LoadingScene : FxmlPage()
{
    override val fxmlLocation = JDScene.LOADING.location
    override val controller = null
}