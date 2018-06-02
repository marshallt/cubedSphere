import javafx.application.Application
import javafx.scene.*
import javafx.stage.Stage
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.paint.Color
import javafx.scene.paint.PhongMaterial
import javafx.scene.shape.*
import javafx.scene.transform.Rotate
import render.View3d


class Main : Application() {
    //@Throws(Exception::class)

    private val GRID_SIZE = 256 //width and height of each face
    private val SCALE = 2 //width and height (in pixels) to draw each Cell


    override fun start(primaryStage: Stage) {
        val SHOW3D = true

        val root = Group()
        val grid = Grid(256)

        if (SHOW3D) {
            val meshView = grid.render(100.0f)
            val rotateY = Rotate(20.0, Rotate.Y_AXIS)
            val rotateX = Rotate(20.0, Rotate.X_AXIS)
            meshView.transforms.addAll(rotateX, rotateY)
            root.children.addAll(meshView, AmbientLight())
            val scene = View3d(root, 1200.0, 900.0, true, SceneAntialiasing.BALANCED)
            primaryStage.scene = scene
        } else {
            val imageView = ImageView(grid.renderTexture())
            root.children.addAll(imageView)
            val scene = Scene(root, 1200.0, 900.0, true, SceneAntialiasing.BALANCED)
            primaryStage.scene = scene
        }

        primaryStage.show()

    }



    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(Main::class.java)
        }
    }
}