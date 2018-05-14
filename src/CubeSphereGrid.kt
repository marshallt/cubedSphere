class CubeSphereGrid(val size: Int) {
    var cells = Array(6, { Array(size, {Array(size,{Cell()})})})

    init {
    }
}