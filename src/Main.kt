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
    val grid = Grid(GRID_SIZE)


    override fun start(primaryStage: Stage) {
        val SHOW3D = false

        val root = Group()
        initGrid()
        val cr = CubeRenderer(grid)

        if (SHOW3D) {
            val meshView = cr.render(100.0f)
            val rotateY = Rotate(20.0, Rotate.Y_AXIS)
            val rotateX = Rotate(20.0, Rotate.X_AXIS)
            meshView.transforms.addAll(rotateX, rotateY)
            root.children.addAll(meshView, AmbientLight())
            val scene3d = View3d(root, 1200.0, 900.0, true, SceneAntialiasing.BALANCED)
            primaryStage.scene = scene3d
        } else {
            val imageView = ImageView(cr.renderFlatCubeTexture())
            root.children.addAll(imageView)
            val scene2d = Scene(root, 1200.0, 900.0, true, SceneAntialiasing.BALANCED)
            primaryStage.scene = scene2d
        }

        primaryStage.show()

    }

    fun initGrid() {
        grid.cells(1, 128,128).elevation = 20000
        grid.neighbors(1, 128, 128).forEach({it.elevation = 2000})
        grid.cells.forEach {
            if (it.elevation > 1) {
                println("Cell: ${it.elevation}")
            }
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(Main::class.java)
        }
    }
}