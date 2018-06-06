import javafx.concurrent.Task
import javafx.concurrent.WorkerStateEvent
import javafx.event.EventHandler
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.paint.PhongMaterial
import javafx.scene.shape.MeshView

class Simulation(val world: World, val uiUpdateFunction: UIUpdateFunction, val numberOfIterations: Int, val yearsPerIteration: Int) {
    var iterationCtr = 0
    var speckLocation = GridRef(1, 128, 128)
    val renderer = Renderer(world)

    fun run() {
        val thread = Thread(iterate())
        thread.isDaemon = true
        thread.start()
    }

    private fun iterate(): Task<Unit> {
        val task = object : Task<Unit>() {
            @Throws(Exception::class)
            override fun call() {
                updateWorld()
            }
        }

        task.onSucceeded = EventHandler<WorkerStateEvent>() {
            uiUpdateFunction()
            iterationCtr++
            if  (iterationCtr < numberOfIterations) {
                run()
            }
        }

        return task
    }

    private fun updateWorld() {
        val start = System.nanoTime()
        world.grid.reset()
        speckLocation = speckLocation.add(5, -5)
        world.grid.cells(speckLocation).elevation = 20000
        world.grid.neighborCells(speckLocation).forEach({
            it.elevation = 2000
        })


        world.grid.cells.forEachIndexed{i, cell ->
            if (cell.elevation > 1) {
                val (cellFace, cellX, cellY) = world.grid.location(i)
                println("$cellFace:($cellX, $cellY) - elevation = ${cell.elevation}")
            }
        }
        println("Update time: ${(System.nanoTime() - start)/1_000_000.0}ms")

    }

}

enum class RenderView {
    CubeFlat,
    Peaked,
    Cube3d
}