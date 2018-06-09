import javafx.concurrent.Task
import javafx.concurrent.WorkerStateEvent
import javafx.event.EventHandler

class Simulation(val world: World, val uiUpdateFunction: UIUpdateFunction, val numberOfIterations: Int, val yearsPerIteration: Int) {
    private var iterationCtr = 0
    private var speckLocation = GridRef(world.grid, 1, 1, 1)
    private var speckOffset = GridRef(world.grid, 1, 2, 1)

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
        try {
            val start = System.nanoTime()
            Thread.sleep(1000)
            //world.grid.reset()
            speckLocation = world.grid.offset(speckLocation, speckOffset)
            //if you change faces, need to update the grid offset
            if (speckLocation.face != speckOffset.face) {
                speckOffset = world.grid.adjustOffsetForFaceChange(offset = speckOffset, newFace = speckLocation.face)
            }
            world.grid.cells(speckLocation).elevation = 20000
//            world.grid.neighborCells(speckLocation).forEach({
//                it.elevation = 2000
//           })
        } catch (e: Exception) {
            println("Exception: ${e.localizedMessage}")
        }

        //println("Update time: ${(System.nanoTime() - start)/1_000_000.0}ms")

    }

}

enum class RenderView {
    CubeFlat,
    Peaked,
    Cube3d
}