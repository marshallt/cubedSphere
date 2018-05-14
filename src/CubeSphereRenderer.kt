import javafx.embed.swing.SwingFXUtils
import javafx.scene.image.Image
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage

class CubeSphereRenderer(val grid: CubeSphereGrid, var scale: Int) {
    var cellRenderer = CellRenderer() //elevation renderer by default
    var backgroundColor = Color.BLACK
    var fillColor = Color.RED

    fun render(): Image {
        var result = BufferedImage(grid.size * scale * 4, grid.size * scale * 3, BufferedImage.TYPE_INT_RGB)
        renderFaceIntoBufferedImage(0, result, grid.size * scale, 0)
        for (i in 1..4) {
            renderFaceIntoBufferedImage(i, result, (i - 1) * grid.size * scale, grid.size * scale)
        }
        renderFaceIntoBufferedImage(5, result, grid.size * scale, 2 * grid.size * scale)

        return SwingFXUtils.toFXImage(result, null)
    }

    fun renderFaceIntoBufferedImage(faceIndex: Int, destBufferedImage: BufferedImage, offsetX: Int, offsetY: Int) {
        var g2 = destBufferedImage.graphics as Graphics2D
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        for (x in 0..grid.size - 1) {
            for (y in 0.. grid.size - 1) {
                val elevation = grid.cells[faceIndex][x][y].elevation
                g2.color = cellRenderer.getColor(elevation)
                g2.fillRect(offsetX + x * scale, offsetY + y * scale, scale, scale)
            }
        }

    }

}