import javafx.concurrent.Task
import javafx.concurrent.WorkerStateEvent
import javafx.event.EventHandler

class Simulation(val world: World, val uiUpdateFunction: UIUpdateFunction, val numberOfIterations: Int, val yearsPerIteration: Int) {
    private var iterationCtr = 0
    private var speckLocation = CubeVec(world.grid, 1, 0, 0)
    private var speckOffset = CubeVec(world.grid, 1, 2, 1)

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
            if  (iterationCtr <= numberOfIterations) {
                run()
            }
        }

        return task
    }

    private fun updateWorld() {
        try {
            Thread.sleep(500)
            //world.cubeGrid.reset()
            world.grid.cells(speckLocation).elevation = 20000
            speckLocation = speckLocation.add(speckOffset)
            //if you change faces, need to update the cubeGrid offset
            if (speckLocation.face != speckOffset.face) {
                speckOffset = speckOffset.adjustForFaceChange(newFace = speckLocation.face)
            }
//            world.cubeGrid.neighborCells(speckLocation).forEach({
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