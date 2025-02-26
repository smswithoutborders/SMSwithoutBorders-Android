package com.example.sw0b_001.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.example.sw0b_001.R


val MonaSans = FontFamily(
    Font(R.font.mona_sans_regular),
    Font(R.font.mona_sans_bold, FontWeight.Bold),
    Font(R.font.mona_sans_light, FontWeight.Light),
    Font(R.font.mona_sans_medium, FontWeight.Medium),
    Font(R.font.mona_sans_semibold, FontWeight.SemiBold),
    Font(R.font.mona_sans_extrabold, FontWeight.ExtraBold),
)

val Unbounded = FontFamily(
    Font(R.font.unbounded_regular),
    Font(R.font.unbounded_bold, FontWeight.Bold),
    Font(R.font.unbounded_light, FontWeight.Light),
    Font(R.font.unbounded_medium, FontWeight.Medium),
    Font(R.font.unbounded_semibold, FontWeight.SemiBold),
    Font(R.font.unbounded_extrabold, FontWeight.ExtraBold),
)

val RelayTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = Unbounded,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp
    ),
    displayMedium = TextStyle(
        fontFamily = Unbounded,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    displaySmall = TextStyle(
        fontFamily = Unbounded,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = MonaSans,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = MonaSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = MonaSans,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    labelLarge = TextStyle(
        fontFamily = MonaSans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    labelMedium = TextStyle(
        fontFamily = MonaSans,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    labelSmall = TextStyle(
        fontFamily = MonaSans,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp
    ),
    titleLarge = TextStyle(
        fontFamily = MonaSans,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    titleMedium = TextStyle(
        fontFamily = MonaSans,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    titleSmall = TextStyle(
        fontFamily = MonaSans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = Unbounded,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 24.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Unbounded,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = Unbounded,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)

