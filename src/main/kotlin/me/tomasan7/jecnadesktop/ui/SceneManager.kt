package me.tomasan7.jecnadesktop.ui

import javafx.scene.Scene
import javafx.stage.Stage
import java.util.*
import kotlin.collections.HashMap

/**
 * Is responsible for swapping [scenes][JDScene] in a [javafx.stage.Stage].
 * Lazily loads the [scenes][Scene].
 * Caches all loaded [scenes][Scene], so when you switch to that scene again, it keeps it's state, since it's the same instance.
 */
class SceneManager(private val stage: Stage)
{
    private val pages: MutableMap<JDScene, Page> = EnumMap(JDScene::class.java)
    private val scenes: MutableMap<JDScene, Scene> = EnumMap(JDScene::class.java)

    fun switchToScene(jdScene: JDScene)
    {
        /* TODO: This is a very ugly fix.
		 * Basically, the problem is, that you cannot have more Scene instances with the same root instance. */

        val page = pages[jdScene] ?: throw IllegalArgumentException("No page for JDScene '$jdScene' registered.")
        val content = page.content
        var scene = scenes[jdScene]

        if (scene == null)
        {
            scene = Scene(content)
            scenes[jdScene] = scene
        }

        else scene.root = content
        stage.scene = scene
    }

    fun addScene(jdScene: JDScene, page: Page)
    {
        pages[jdScene] = page
    }

    fun removeScene(jdScene: JDScene) = pages.remove(jdScene)
}