import javafx.application.Application
import javafx.scene.*
import javafx.scene.paint.Color
import javafx.stage.Stage
import javafx.scene.Scene
import javafx.scene.control.ScrollPane
import javafx.scene.image.ImageView


class Main : Application() {
    //@Throws(Exception::class)

    private val GRID_SIZE = 256 //width and height of each face
    private val SCALE = 2 //width and height (in pixels) to draw each Cell


    override fun start(primaryStage: Stage) {


        var world = World(GRID_SIZE)
        var renderer = CubeSphereRenderer(world.grid, SCALE)
        var imageView = ImageView()
        imageView.image = renderer.render()


        var scrollPane = ScrollPane(imageView)
        scrollPane.hbarPolicy = ScrollPane.ScrollBarPolicy.ALWAYS
        scrollPane.vbarPolicy = ScrollPane.ScrollBarPolicy.ALWAYS
        scrollPane.isPannable = true

        val scene = Scene(scrollPane, 1200.0, 900.0, true, SceneAntialiasing.BALANCED)

        primaryStage.scene = scene
        primaryStage.show()

    }



    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(Main::class.java)
        }
    }
}