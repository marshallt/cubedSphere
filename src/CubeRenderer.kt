import javafx.embed.swing.SwingFXUtils
import javafx.scene.image.Image
import javafx.scene.paint.PhongMaterial
import javafx.scene.shape.CullFace
import javafx.scene.shape.MeshView
import javafx.scene.shape.TriangleMesh
import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage

class CubeRenderer(val grid: Grid) {
    val cr = CellRenderer()

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
        material.diffuseMap = renderFlatCubeTexture()
        //material.diffuseMap = useMapTexture()
        meshView.material = material
        meshView.cullFace = CullFace.NONE
        return meshView
    }

    fun useMapTexture(): Image {
        return Image("big-gallery-172worldmap.jpg")
    }

    fun renderFlatCubeTexture(): Image {
        val width = grid.size * 4
        val height = grid.size * 3

        val image = BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR)
        val g = image.createGraphics() as Graphics2D

        val cr = CellRenderer()
        var color = Color.RED
        var imageX = 0
        var imageY = 0

        for (face in 0..5) {
            for (y in 0 until grid.size) {
                for (x in 0 until grid.size) {
                    when (face) {
                        in 0..3 -> {
                            imageX = (face * grid.size) + x
                            imageY = grid.size + y
                        }
                        4 -> {
                            //north pole face. Rotate 90 degree counterclockwise and move right 1
                            imageX = grid.size + y
                            imageY = grid.size - x
                            //println("North ($imageX, $imageY)")
                        }
                        5 -> {
                            //south pole face. Rotate 90 degrees clockwise and move to the bottom of the image and right 1
                            imageX = grid.size * 2 - y
                            imageY = grid.size * 2 + x
                            //println("South ($imageX, $imageY)")
                        }
                    }
                    color = cr.getColor(grid.cells(face, x, y))
                    image.setRGB(imageX, imageY, color.rgb)
                }
            }
        }

        g.dispose()

        return SwingFXUtils.toFXImage(image, null)
    }

    fun projectPeakedSquares(): Image {
        val width = grid.size * 4
        val height = grid.size * 3

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

        var color = Color.RED
        var imageX = 0
        var imageY = 0

        for (face in 0..3) {
            for (y in 0 until grid.size) {
                for (x in 0 until grid.size) {
                    imageX = (face * grid.size) + x
                    imageY = grid.size + y
                    color = cr.getColor(grid.cells(face, x, y))
                    image.setRGB(imageX, imageY, color.rgb)
                }
            }
        }

        val halfSize = grid.size / 2
        var imageX1 = 0
        var imageY1 = 0
        var imageX2 = 0
        var imageY2 = 0
        var mirrorX = 0
        var mirrorY = 0
        for (y in 0 until grid.size) {
            for (x in 0 until grid.size) {
                //face 4 - NORTH POLE
                if (y < halfSize && x <= y) { //LEFT section. Rotate left 90 degrees CCW and put over face 0
                    imageX1 = y
                    imageY1 = grid.size - x - 1
                    mirrorX = x
                    mirrorY = grid.size - y - 1
                    imageX2 =  mirrorY
                    imageY2 = grid.size - mirrorX - 1
                    projectPolePixels(image, 4, x, y, imageX1, imageY1, mirrorX, mirrorY, imageX2, imageY2)
                }

                if (x < halfSize && x >= y) { //TOP section. Rotate 180 degrees and put over face 3
                    imageX1 = grid.size * 3 + x
                    imageY1 = grid.size - y - 1
                    mirrorX = grid.size - x - 1
                    mirrorY = y
                    imageX2 = grid.size * 4 - x - 1
                    imageY2 = imageY1
                    projectPolePixels(image, 4, x, y, imageX1, imageY1, mirrorX, mirrorY, imageX2, imageY2)
                }

                if (y >= halfSize && x >= y) { //RIGHT section. Rotate 90 degrees CW and put over face 2
                    imageX1 = grid.size * 3 - y - 1
                    imageY1 = x
                    mirrorX = x
                    mirrorY = grid.size - y - 1
                    imageX2 = grid.size * 3 - mirrorY - 1
                    imageY2 = mirrorX
                    //println("$x, $y, $imageX1, $imageY1, $mirrorX, $mirrorY, $imageX2, $imageY2")
                    projectPolePixels(image, 4, x, y, imageX1, imageY1, mirrorX, mirrorY, imageX2, imageY2)
                }

                if (x >= halfSize && x <= y) { //BOTTOM section. No rotation. Put over face 1
                    imageX1 = grid.size + x
                    imageY1 = y
                    mirrorX = grid.size - x - 1
                    mirrorY = y
                    imageX2 = grid.size + mirrorX
                    imageY2 = y
                    println("$x, $y, $imageX1, $imageY1, $mirrorX, $mirrorY, $imageX2, $imageY2")
                    projectPolePixels(image, 4, x, y, imageX1, imageY1, mirrorX, mirrorY, imageX2, imageY2)
                }
            }

        }

        g.dispose()

        return SwingFXUtils.toFXImage(image, null)
    }

    private fun projectPolePixels(image: BufferedImage, face: Int, x: Int, y: Int, imageX1: Int, imageY1: Int, mirrorX: Int, mirrorY: Int, imageX2: Int, imageY2: Int) {
        var color = cr.getColor(grid.cells(face, x, y))
        image.setRGB(imageX1, imageY1, color.rgb)
        color = cr.getColor(grid.cells(face, mirrorX, mirrorY))
        image.setRGB(imageX2, imageY2, color.rgb)
    }

}