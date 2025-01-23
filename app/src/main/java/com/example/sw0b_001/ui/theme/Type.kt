package com.example.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.sw0b_001.R

val UnboundedFontFamily = FontFamily(
    Font(R.font.unbounded_light, FontWeight.Light),
    Font(R.font.unbounded_regular, FontWeight.Normal),
    Font(R.font.unbounded_medium, FontWeight.Medium),
    Font(R.font.unbounded_semibold, FontWeight.SemiBold),
    Font(R.font.unbounded_bold, FontWeight.Bold),
    Font(R.font.unbounded_extrabold, FontWeight.ExtraBold),
    Font(R.font.unbounded_black, FontWeight.Black)
)

val MonaSansFontFamily = FontFamily(
    Font(R.font.mona_sans_light, FontWeight.Light),
    Font(R.font.mona_sans_regular, FontWeight.Normal),
    Font(R.font.mona_sans_medium, FontWeight.Medium),
    Font(R.font.mona_sans_semibold, FontWeight.SemiBold),
    Font(R.font.mona_sans_bold, FontWeight.Bold),
    Font(R.font.mona_sans_extrabold, FontWeight.ExtraBold)
)

val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = UnboundedFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),
    displayMedium = TextStyle(
        fontFamily = UnboundedFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = MonaSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
)

