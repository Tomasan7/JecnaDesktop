package me.tomasan7.jecnadesktop.ui.component

import javafx.beans.NamedArg
import javafx.fxml.FXMLLoader
import me.tomasan7.jecnadesktop.ui.SubPage
import java.io.IOException

class SidebarPageElement(@param:NamedArg("subPage") val subPage: SubPage) : SidebarElement()
{
    init
    {
        val fxmlLoader = FXMLLoader(javaClass.getResource("/ui/component/SidebarPageElement.fxml"))

        with(fxmlLoader)
        {
            setRoot(this@SidebarPageElement)
            setController(this@SidebarPageElement)
            load<Any>()
        }
    }
}