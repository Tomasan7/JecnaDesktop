package me.tomasan7.jecnadesktop.ui.component

import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox

abstract class SidebarElement : HBox()
{
    @FXML
    protected lateinit var icon: ImageView

    @FXML
    protected lateinit var label: Label

    init
    {
        styleClass.add("side-bar-element")
        stylesheets.add("/ui/component/SidebarElement.css")
    }

    fun getIcon() = icon.image.url

    fun setIcon(url: String)
    {
        icon.image = Image(url)
    }

    fun getLabel() = label.text

    fun setLabel(label: String?)
    {
        this.label.text = label
    }
}