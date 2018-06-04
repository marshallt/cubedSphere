import javafx.geometry.Point2D
import java.awt.Color
import java.awt.geom.Path2D

class CellRenderer(var cellColorBasedOn: Int = 0) {  //cellColorBasedOn - 0 = elevation, 1 = climate
    var climatePalette = Array<Color>(9, {Color(0,0,0,0)})
    var elevationPalette = Array<Color>(16, {Color(0,0,0,0)})
    val cornerOffsets = Array<Point2D>(6, {Point2D(0.0, 0.0)})
    var renderAllWaterAtOneDepth = true

    init {
        defineClimatePalette()
        defineElevationPalette()
    }

    fun getColor(cell: Cell): Color {
        var result = Color(0,0,0,0)

        if (cell.isEmpty) return result

        when (cellColorBasedOn) {
            0 -> result = getElevationColor(cell.elevation)
            1 -> result = getClimateColor(cell.climate)
        }
        return result
    }

    private fun getElevationColor(value: Int): Color {
        var result = elevationPalette[0]
        if (renderAllWaterAtOneDepth && value < 1) return elevationPalette[12]

        when (value) {
            in 10000..Int.MAX_VALUE ->  result = elevationPalette[1]
            in 8000..10000  ->          result = elevationPalette[2]
            in 6000..8000   ->          result = elevationPalette[3]
            in 4000..6000   ->          result = elevationPalette[4]
            in 3000..4000   ->          result = elevationPalette[5]
            in 2500..3000   ->          result = elevationPalette[6]
            in 2000..2500   ->          result = elevationPalette[7]
            in 1000..1500   ->          result = elevationPalette[8]
            in 500..1000    ->          result = elevationPalette[9]
            in 250..500     ->          result = elevationPalette[10]
            in 1..250       ->          result = elevationPalette[11]
            in -500..0      ->          result = elevationPalette[12]
            in -3000..-500  ->          result = elevationPalette[13]
            in -6000..-3000 ->          result = elevationPalette[14]
            in Int.MIN_VALUE..-6000 ->  result = elevationPalette[15]
        }

        return result
    }

    private fun getClimateColor(value: Int): Color {
        return climatePalette[value]
    }

    private fun defineClimatePalette() {
        climatePalette[0] = Color(0, 0, 0, 255) //unknown
        climatePalette[1] = Color(127, 255, 0, 255)   //tropical
        climatePalette[2] = Color(222, 184, 135, 255) //savannah
        climatePalette[3] = Color(255, 180, 0, 255)   //desert
        climatePalette[4] = Color(255, 250, 200, 255) //steppe
        climatePalette[5] = Color(50, 100, 0, 255)    //temperate
        climatePalette[6] = Color(0, 128, 128, 255)   //taiga
        climatePalette[7] = Color(0, 200, 200, 255)    //tundra
        climatePalette[8] = Color(255, 255, 255, 255) //icecap
    }

    private fun defineElevationPalette() {
        elevationPalette[0] = Color( 0, 0, 0, 255)        //unknown
        elevationPalette[1] = Color( 225, 225, 225, 255)  //max elevation
        elevationPalette[2] = Color( 100, 50, 30, 255)    //
        elevationPalette[3] = Color( 152, 109, 81, 255)   //
        elevationPalette[4] = Color( 200, 160, 130, 255)  //
        elevationPalette[5] = Color( 230, 190, 160, 255)  //low
        elevationPalette[6] = Color( 180, 250, 200, 255)  //
        elevationPalette[7] = Color( 120, 210, 160, 255)  //
        elevationPalette[8] = Color( 90, 180, 130, 255)   //
        elevationPalette[9] = Color( 60, 150, 80, 255)    //
        elevationPalette[10] = Color( 30, 120, 30, 255)   //
        elevationPalette[11] = Color( 0, 90, 0, 255)      //sea level / very low
        elevationPalette[12] = Color( 232, 245, 255, 255) //shallow water
        elevationPalette[13] = Color( 153, 196, 224, 255) //medium depth water
        elevationPalette[14] = Color( 99, 134, 216, 255)  //deep water
        elevationPalette[15] = Color( 47, 85, 226, 255)   //very deep water
    }

}