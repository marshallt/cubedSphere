import javafx.scene.image.ImageView
import javafx.scene.shape.MeshView

class World() {
    private val GRID_SIZE = 256 //width and height of each face
    private val SCALE = 2 //width and height (in pixels) to draw each Cell

    var activeView = RenderView.Peaked
    val grid = Grid(GRID_SIZE)

    init {
    }

    fun simulate(uiUpdateFunction: UIUpdateFunction, numberOfIterations: Int, yearsPerIteration: Int) {
        var simulation = Simulation(this, uiUpdateFunction, numberOfIterations, yearsPerIteration)
        simulation.run()
    }




}