package me.tomasan7.jecnadesktop.ui.component

import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import java.awt.Desktop
import java.net.URI

class SidebarLinkElement : SidebarElement()
{
    @FXML
    lateinit var linkIcon: IconView

    @FXML
    lateinit var link: String

    init
    {
        val fxmlLoader = FXMLLoader(javaClass.getResource("/ui/component/SidebarLinkElement.fxml"))

        with(fxmlLoader)
        {
            setRoot(this@SidebarLinkElement)
            setController(this@SidebarLinkElement)
            load<Any>()
        }

        onMouseClicked = EventHandler {
            if (!Desktop.isDesktopSupported())
                return@EventHandler

            val desktop = Desktop.getDesktop()

            desktop.browse(URI.create(link))
        }
    }
}