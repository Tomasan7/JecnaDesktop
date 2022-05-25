package me.tomasan7.jecnadesktop.ui.scene

import me.tomasan7.jecnadesktop.JecnaDesktop
import me.tomasan7.jecnadesktop.ui.FxmlPage
import me.tomasan7.jecnadesktop.ui.JDScene
import me.tomasan7.jecnadesktop.ui.controller.MainPageController

class MainScene(jecnaDesktop: JecnaDesktop) : FxmlPage()
{
    override val fxmlLocation = JDScene.MAIN.location
    override val controller =  MainPageController(jecnaDesktop)
}