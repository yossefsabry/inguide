package com.inguide.app.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SkeletonLoader(
    modifier: Modifier = Modifier,
    shimmerColor: Color
) {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Box(
        modifier = modifier
            .alpha(alpha)
            .background(shimmerColor, RoundedCornerShape(8.dp))
    )
}

@Composable
fun SkeletonCard(
    modifier: Modifier = Modifier,
    shimmerColor: Color
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            SkeletonLoader(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(20.dp),
                shimmerColor = shimmerColor
            )
            Spacer(modifier = Modifier.height(8.dp))
            SkeletonLoader(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(16.dp),
                shimmerColor = shimmerColor
            )
        }
    }
}

@Composable
fun SkeletonListItem(
    modifier: Modifier = Modifier,
    shimmerColor: Color
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        SkeletonLoader(
            modifier = Modifier
                .size(48.dp)
                .background(shimmerColor, CircleShape),
            shimmerColor = shimmerColor
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            SkeletonLoader(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(18.dp),
                shimmerColor = shimmerColor
            )
            Spacer(modifier = Modifier.height(6.dp))
            SkeletonLoader(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(14.dp),
                shimmerColor = shimmerColor
            )
        }
    }
}
