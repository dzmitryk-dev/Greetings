package greetings.app.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.atan2
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
        PiercedHeart(
            modifier = Modifier
                .fillMaxHeight(fraction = 0.5f)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        )
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "I love you!",
            fontSize = 32.sp,
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
        val heartSize = canvasWidth.coerceAtMost(canvasHeight)

        val heartPath = createHeartPath(heartSize, canvasWidth, canvasHeight)

        drawPath(
            path = heartPath,
            color = Color.Red,
        )
    }
}

private fun createHeartPath(size: Float, canvasWidth: Float, canvasHeight: Float): Path {
    val path = Path()
    val centerX = canvasWidth / 2 // Use canvasWidth for centering
    val centerY = canvasHeight / 2 // Use canvasHeight for centering
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

@Composable
fun PiercedHeart(modifier: Modifier = Modifier) {
    val arrowProgress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        arrowProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
        )
    }

    Box(modifier = modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val heartSize = canvasWidth.coerceAtMost(canvasHeight) * 0.8f

            val heartPath = createHeartPath(heartSize, canvasWidth, canvasHeight)
            drawPath(
                path = heartPath,
                color = Color.Red,
            )

            // Arrow
            val arrowStartX = canvasWidth * 0.1f
            val arrowStartY = canvasHeight * 0.2f
            val arrowEndX = canvasWidth * 0.9f
            val arrowEndY = canvasHeight * 0.8f

            val currentArrowX = arrowStartX + (arrowEndX - arrowStartX) * arrowProgress.value
            val currentArrowY = arrowStartY + (arrowEndY - arrowStartY) * arrowProgress.value

            drawArrow(
                start = Offset(arrowStartX, arrowStartY),
                end = Offset(currentArrowX, currentArrowY),
                color = Color.Black
            )
        }
    }
}

private fun DrawScope.drawArrow(start: Offset, end: Offset, color: Color) {
    val arrowHeadSize = 20f
    val angle = atan2(end.y - start.y, end.x - start.x).toFloat()

    // Shaft
    drawLine(
        color = color,
        start = start,
        end = end,
        strokeWidth = 4f
    )

    // Head
    val headBase1 = Offset(
        (end.x - arrowHeadSize * cos(angle - PI / 6)).toFloat(),
        (end.y - arrowHeadSize * sin(angle - PI / 6)).toFloat()
    )
    val headBase2 = Offset(
        (end.x - arrowHeadSize * cos(angle + PI / 6)).toFloat(),
        (end.y - arrowHeadSize * sin(angle + PI / 6)).toFloat()
    )

    drawPath(
        path = Path().apply {
            moveTo(end.x, end.y)
            lineTo(headBase1.x, headBase1.y)
            lineTo(headBase2.x, headBase2.y)
            close()
        },
        color = color
    )

    //Feather (optional, for more visual appeal)
    val featherLength = arrowHeadSize * 1.5f
    val featherOffset = arrowHeadSize * 0.5f
    val feather1 = Offset(
        start.x + featherOffset * cos(angle - PI / 2).toFloat(),
        start.y + featherOffset * sin(angle - PI / 2).toFloat()
    )
    val feather2 = Offset(
        start.x - featherOffset * cos(angle - PI / 2).toFloat(),
        start.y - featherOffset * sin(angle - PI / 2).toFloat()
    )
    val featherEnd1 = Offset(
        feather1.x - featherLength * cos(angle + PI / 4).toFloat(),
        feather1.y - featherLength * sin(angle + PI / 4).toFloat()
    )
    val featherEnd2 = Offset(
        feather2.x - featherLength * cos(angle - PI / 4).toFloat(),
        feather2.y - featherLength * sin(angle - PI / 4).toFloat()
    )
    drawLine(color, feather1, featherEnd1, strokeWidth = 2f)
    drawLine(color, feather2, featherEnd2, strokeWidth = 2f)
}


@Preview
@Composable
private fun HeartPreview(){
    Heart(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize())
}

@Preview
@Composable
private fun PiercedHeartPreview(){
    PiercedHeart(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize())
}

@Preview(showBackground = true)
@Composable
private fun ILoveYouScreenPreview() {
    ILoveYouScreen()
}