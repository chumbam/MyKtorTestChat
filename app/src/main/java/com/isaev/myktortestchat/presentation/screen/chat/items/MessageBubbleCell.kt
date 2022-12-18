package com.isaev.myktortestchat.presentation.screen.chat.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.isaev.myktortestchat.domain.model.Message

@Composable
fun MessageBubble(
    isOwnMessage: Boolean,
    message: Message
) {
    Box(
        Modifier.fillMaxWidth(),
        contentAlignment = if (isOwnMessage) Alignment.CenterEnd
        else Alignment.CenterStart
    ) {
        Column(
            modifier = Modifier
                .width(200.dp)
                .drawBehind {
                    val cornerRadius = 10.dp.toPx()
                    val triHeight = 20.dp.toPx()
                    val triWidth = 25.dp.toPx()
                    val trianglePath = Path().apply {
                        if (isOwnMessage) {
                            moveTo(size.width, size.height - cornerRadius)
                            lineTo(size.width, size.height + triHeight)
                            lineTo(size.width - triWidth, size.height - cornerRadius)
                            close()
                        } else {
                            moveTo(0f, size.height - cornerRadius)
                            lineTo(0f, size.height + triHeight)
                            lineTo(triWidth, size.height - cornerRadius)
                            close()
                        }
                    }
                    drawPath(
                        path = trianglePath,
                        color = if (isOwnMessage) Color.Green else Color.Gray
                    )
                }
                .background(
                    color = if (isOwnMessage) Color.Green else Color.Gray,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(8.dp)
        ) {
            message.username?.let {
                Text(
                    text = it,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            message.text?.let {
                Text(
                    text = it,
                    fontWeight = FontWeight.Medium,
                    color = Color.White

                )
            }
            message.time?.let {
                Text(
                    modifier = Modifier.align(Alignment.End),
                    text = it,
                    fontWeight = FontWeight.Light,
                    color = Color.White,
                    fontStyle = FontStyle.Italic,

                    )
            }
        }
    }
    Spacer(modifier = Modifier.height(32.dp))
}