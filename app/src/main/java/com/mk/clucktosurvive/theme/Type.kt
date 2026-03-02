package com.mk.clucktosurvive.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mk.clucktosurvive.R

val Montserrat = FontFamily(
    Font(R.font.montserrat_bold, FontWeight.Bold),
    Font(R.font.montserrat_extrabold, FontWeight.ExtraBold)
)


// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp,
    ),

    displayLarge = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 25.sp
    )
)
/*
labelSmall = TextStyle(
fontFamily = FontFamily.Default,
fontWeight = FontWeight.Medium,
fontSize = 11.sp,
lineHeight = 16.sp,
letterSpacing = 0.5.sp
)
*/


