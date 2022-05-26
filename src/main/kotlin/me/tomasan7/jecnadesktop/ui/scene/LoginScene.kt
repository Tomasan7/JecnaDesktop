package me.tomasan7.jecnadesktop.ui.scene

import me.tomasan7.jecnadesktop.JecnaDesktop
import me.tomasan7.jecnadesktop.ui.FxmlPage
import me.tomasan7.jecnadesktop.ui.JDScene
import me.tomasan7.jecnadesktop.ui.controller.LoginPageController

class LoginScene(jecnaDesktop: JecnaDesktop) : FxmlPage()
{
    override val fxmlLocation = JDScene.LOGIN.location
    override val controller = LoginPageController(jecnaDesktop)
}