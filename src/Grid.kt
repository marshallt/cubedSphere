import javafx.embed.swing.SwingFXUtils
import javafx.scene.image.Image
import javafx.scene.paint.PhongMaterial
import javafx.scene.shape.CullFace
import javafx.scene.shape.MeshView
import javafx.scene.shape.TriangleMesh
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Stroke
import java.awt.image.BufferedImage
import java.awt.image.ColorModel

class Grid(val size: Int) {

    fun render(cubeSize: Float): MeshView {
        val s = cubeSize / 2f
        val triangleMesh = TriangleMesh()
        triangleMesh.points.addAll(s, s, s,
                s, s, -s,
                s, -s, s,
                s, -s, -s,
                -s, s, s,
                -s, s, -s,
                -s, -s, s,
                -s, -s, -s)

        val x0 = 0.00f
        val x1 = 0.25f
        val x2 = 0.50f
        val x3 = 0.75f
        val x4 = 1.00f

        val y0 = 0.0000f
        val y1 = 0.3333f
        val y2 = 0.6667f
        val y3 = 1.0000f

        triangleMesh.texCoords.addAll(x1, y0, x2, y0, x0, y1, x1, y1, x2, y1, x3, y1, x4, y1,
                x0, y2, x1, y2, x2, y2, x3, y2, x4, y2, x1, y3, x2, y3)

        triangleMesh.faces.addAll(
                0, 10, 2, 5, 1, 9,
                2, 5, 3, 4, 1, 9,
                4, 7, 5, 8, 6, 2,
                6, 2, 5, 8, 7, 3,
                0, 13, 1, 9, 4, 12,
                4, 12, 1, 9, 5, 8,
                2, 1, 6, 0, 3, 4,
                3, 4, 6, 0, 7, 3,
                0, 10, 4, 11, 2, 5,
                2, 5, 4, 11, 6, 6,
                1, 9, 3, 4, 5, 8,
                5, 8, 3, 4, 7, 3
        )

        triangleMesh.faceSmoothingGroups.addAll(0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5)

        val meshView = MeshView(triangleMesh)
        val material = PhongMaterial()
        material.diffuseMap = renderTexture()
        meshView.material = material
        meshView.cullFace = CullFace.NONE
        return meshView
    }

    fun renderTexture(): Image {
        val width = size * 4
        val height = size * 3

        val x0 = 0
        val x1 = (0.25 * width).toInt()
        val x2 = (0.5 * width).toInt()
        val x3 = (0.75 * width).toInt()
        val x4 = (width).toInt()

        val y0 = 0
        val y1 = (0.3333 * height).toInt()
        val y2 = (0.6667 * height).toInt()
        val y3 = (1.0000 * height).toInt()

        val image = BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR)
        val g = image.createGraphics() as Graphics2D
        val recSize = size + 1
        g.color = Color.RED
        g.fillRect(x1, y0, recSize, recSize )
        g.color = Color.GREEN
        g.fillRect(x0, y1, recSize, recSize )
        g.color = Color.BLUE
        g.fillRect(x1, y1, recSize, recSize )
        g.color = Color.GRAY
        g.fillRect(x2, y1, recSize, recSize )
        g.color = Color.YELLOW
        g.fillRect(x3, y1, recSize, recSize )
        g.color = Color.CYAN
        g.fillRect(x1, y2, recSize, recSize )

        g.dispose()

        return SwingFXUtils.toFXImage(image, null)
    }

}