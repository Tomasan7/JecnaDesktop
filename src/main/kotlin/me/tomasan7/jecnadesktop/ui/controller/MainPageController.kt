package me.tomasan7.jecnadesktop.ui.controller

import javafx.css.PseudoClass
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.VBox
import me.tomasan7.jecnadesktop.JecnaDesktop
import me.tomasan7.jecnadesktop.ui.SubPage
import me.tomasan7.jecnadesktop.ui.SubPageManager
import me.tomasan7.jecnadesktop.ui.component.SidebarPageElement
import me.tomasan7.jecnadesktop.ui.subpage.AttendancesSubPage
import me.tomasan7.jecnadesktop.ui.subpage.GradesSubPage
import me.tomasan7.jecnadesktop.ui.subpage.TimetableSubPage
import java.net.URL
import java.util.*

class MainPageController(private val jecnaDesktop: JecnaDesktop) : Initializable
{
    @FXML
    private lateinit var sidebar: VBox

    @FXML
    private lateinit var subPageContainer: AnchorPane

    lateinit var subPageManager: SubPageManager
        private set

    override fun initialize(location: URL, resources: ResourceBundle?)
    {
        subPageManager = SubPageManager(subPageContainer)

        /* Making the container's left anchor equal (+ a little space) to the sidebar's width all the time. */
        sidebar.widthProperty().addListener { _, _, newValue -> AnchorPane.setLeftAnchor(subPageContainer, newValue as Double + 10) }

        subPageManager.addSubPage(SubPage.GRADES, GradesSubPage(jecnaDesktop.gradesRepository!!))
        subPageManager.addSubPage(SubPage.ATTENDANCES, AttendancesSubPage(jecnaDesktop.attendancesRepository!!))
        subPageManager.addSubPage(SubPage.TIMETABLE, TimetableSubPage(jecnaDesktop.timetableRepository!!))

        initSideBarElements()
    }

    private fun initSideBarElements()
    {
        /* Get the SidebarElements from the sidebar. */
        val sidebarElements = sidebar.childrenUnmodifiable.asSequence()
            .filter { it is SidebarPageElement }
            .map { it as SidebarPageElement }
            .toList()

        sidebarElements.forEach { sidebarElement ->
            sidebarElement.onMouseClicked = EventHandler {
                /* Switch to SidebarElement's subpage. */
                subPageManager.switchToPage(sidebarElement.subPage)
            }
        }

        subPageManager.registerSubPageSwitchListener { _, newSubPage: SubPage ->
            /* Edit the modified value of each SidebarElement based on whether it's subpage is the current active one or not. */
            sidebarElements.forEach { sbe ->
                sbe.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), sbe.subPage == newSubPage)
            }
        }

        subPageManager.switchToPage(SubPage.GRADES)
    }
}