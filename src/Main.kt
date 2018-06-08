import javafx.application.Application
import javafx.scene.*
import javafx.scene.control.ScrollPane
import javafx.stage.Stage
import javafx.scene.image.ImageView
import javafx.scene.paint.Color
import javafx.scene.paint.PhongMaterial
import javafx.scene.shape.MeshView
import javafx.scene.transform.Rotate


typealias UIUpdateFunction = () -> Unit

class Main : Application() {
    //@Throws(Exception::class)
    private lateinit var renderer: Renderer
    private lateinit var scene2d: Scene
    private lateinit var scene3d: Scene

    private var stage = Stage()
    private var imageView = ImageView()
    private var meshView = MeshView()
    private var scale2d = 2

    private var activeRenderView: RenderView = RenderView.Peaked
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
        meshView = renderer.render3d(200.0f)
        val rotateY = Rotate(20.0, Rotate.Y_AXIS)
        val rotateX = Rotate(20.0, Rotate.X_AXIS)
        meshView.transforms.addAll(rotateX, rotateY)
        root3d.children.addAll(meshView, AmbientLight())
        scene3d = View3d(root3d, 1200.0, 900.0, true, SceneAntialiasing.BALANCED)

        //set up 2d
        val scrollPane = ScrollPane(imageView)
        scrollPane.setPrefSize(1200.0, 900.0)
        scrollPane.hbarPolicy = ScrollPane.ScrollBarPolicy.ALWAYS
        scrollPane.vbarPolicy = ScrollPane.ScrollBarPolicy.ALWAYS
        scrollPane.isPannable = true
        root2d.children.addAll(scrollPane)
        scene2d = Scene(root2d, 1200.0, 900.0, true, SceneAntialiasing.BALANCED)

        updateView()
        primaryStage.show()
        world.simulate(::updateView, 1000, 1)

    }

    private fun updateView() {
        when (activeRenderView) {
            RenderView.CubeFlat -> {
                imageView.image = renderer.renderFlatCubeTexture(scale2d)
                stage.scene = scene2d
            }
            RenderView.Peaked -> {
                imageView.image = renderer.projectPeakedSquares(scale2d)
                stage.scene = scene2d
            }
            RenderView.Cube3d -> {
                val material = meshView.material as PhongMaterial
                material.diffuseMap = renderer.renderFlatCubeTexture(1)
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