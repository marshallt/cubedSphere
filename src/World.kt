class World() {
    private val GRID_SIZE = 256 //width and height of each face

    val grid = Grid(GRID_SIZE)

    init {
    }

    fun simulate(uiUpdateFunction: UIUpdateFunction, numberOfIterations: Int, yearsPerIteration: Int) {
        val simulation = Simulation(this, uiUpdateFunction, numberOfIterations, yearsPerIteration)
        simulation.run()
    }




}