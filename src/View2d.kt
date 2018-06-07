package render

import javafx.event.EventHandler
import javafx.scene.Parent
import javafx.scene.PerspectiveCamera
import javafx.scene.Scene
import javafx.scene.SceneAntialiasing
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.input.ScrollEvent
import javafx.scene.transform.Rotate

class View2d(root: Parent, val imageView: ImageView, width: Double, height: Double, depthBuffer: Boolean, antiAliasing: SceneAntialiasing):
        Scene(root, width, height, depthBuffer, antiAliasing) {

    var mousePosX = 0.0
    var mousePosY = 0.0
    var mouseOldX = 0.0
    var mouseOldY = 0.0

    init {
        handleMouseEvents()
    }

    private fun handleMouseEvents() {
        this.onScroll = EventHandler<ScrollEvent> { me: ScrollEvent ->
            //imageView.zoom
        }
    }

}