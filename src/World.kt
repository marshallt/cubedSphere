import java.util.*

class World(val size: Int) {
    var grid = CubeSphereGrid(size)
    var cells = Array<Cell>(6 * size * size, {Cell()})
}