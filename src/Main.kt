import javafx.application.Application
import javafx.scene.*
import javafx.stage.Stage
import javafx.scene.image.ImageView
import javafx.scene.paint.PhongMaterial
import javafx.scene.shape.MeshView
import javafx.scene.transform.Rotate
import render.View3d

typealias UIUpdateFunction = () -> Unit

class Main : Application() {
    //@Throws(Exception::class)
    lateinit var renderer: Renderer
    lateinit var scene2d: Scene
    lateinit var scene3d: Scene

    var stage = Stage()
    var imageView = ImageView()
    var meshView = MeshView()

    var activeRenderView: RenderView = RenderView.Peaked
        set(value) {
            field = value
            updateView()
        }


    override fun start(primaryStage: Stage) {
        stage = primaryStage
        val world = World()
        val root2d = Group()
        val root3d = Group()
        renderer = Renderer(world)

        //set up 3d
        meshView = renderer.render3d(100.0f)
        val rotateY = Rotate(20.0, Rotate.Y_AXIS)
        val rotateX = Rotate(20.0, Rotate.X_AXIS)
        meshView.transforms.addAll(rotateX, rotateY)
        root3d.children.addAll(meshView, AmbientLight())
        scene3d = View3d(root3d, 1200.0, 900.0, true, SceneAntialiasing.BALANCED)

        //set up 2d
        root2d.children.addAll(imageView)
        scene2d = Scene(root2d, 1200.0, 900.0, true, SceneAntialiasing.BALANCED)

        updateView()
        primaryStage.show()
        world.simulate(updateView, 10, 1)

    }

    fun updateView() {
        when (activeRenderView) {
            RenderView.CubeFlat -> {
                imageView.image = renderer.renderFlatCubeTexture()
                stage.scene = scene2d
            }
            RenderView.Peaked -> {
                imageView.image = renderer.projectPeakedSquares()
                stage.scene = scene2d
            }
            RenderView.Cube3d -> {
                val material = meshView.material as PhongMaterial
                material.diffuseMap = renderer.renderFlatCubeTexture()
                stage.scene = scene3d
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