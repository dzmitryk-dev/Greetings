package greetings.app.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

@Composable
fun ILoveYouScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Heart(modifier = Modifier.padding(16.dp))
        Text(
            text = "I love you!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Red
        )
    }
}

@Composable
private fun Heart(modifier: Modifier) {
    Canvas(modifier = modifier) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val heartSize = canvasWidth.coerceAtMost(canvasHeight) * 0.8f

        val heartPath = createHeartPath(heartSize)

        drawPath(
            path = heartPath,
            color = Color.Red,
        )
    }
}

private fun createHeartPath(size: Float): Path {
    val path = Path()
    val centerX = size / 2
    val centerY = size / 2
    val radius = size / 2
    val step = 0.01
    var t = 0.0
    while (t <= 2 * PI) {
        val x = 16 * sin(t).pow(3)
        val y = 13 * cos(t) - 5 * cos(2 * t) - 2 * cos(3 * t) - cos(4 * t)
        val canvasX = centerX + (x * radius / 20).toFloat()
        val canvasY = centerY - (y * radius / 20).toFloat()
        if (t == 0.0) {
            path.moveTo(canvasX, canvasY)
        } else {
            path.lineTo(canvasX, canvasY)
        }
        t += step
    }

    path.close()
    return path
}


@Preview
@Composable
private fun HeartPreview(){
    Heart(modifier = Modifier.padding(16.dp).fillMaxSize())
}

@Preview(showBackground = true)
@Composable
private fun ILoveYouScreenPreview() {
    ILoveYouScreen()
}