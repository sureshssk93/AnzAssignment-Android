package anz.project.userapp.ui.common

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun CoilImageFromUrl(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    isCircular: Boolean = false,
    width: Int,
    height: Int
) {
    val context = LocalContext.current
    
    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        modifier = modifier
            .width(width.dp)
            .height(height.dp)
            .clip(if (isCircular) CircleShape else RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop
    )
} 