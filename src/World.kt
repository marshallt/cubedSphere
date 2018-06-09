class World() {
    private val GRID_SIZE = 8 //width and height of each face

    val grid = CubeGrid(GRID_SIZE)

    init {
    }

    fun simulate(uiUpdateFunction: UIUpdateFunction, numberOfIterations: Int, yearsPerIteration: Int) {
        val simulation = Simulation(this, uiUpdateFunction, numberOfIterations, yearsPerIteration)
        simulation.run()
    }




}