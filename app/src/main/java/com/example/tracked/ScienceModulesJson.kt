package com.example.tracked

data class ScienceTopic(
    val topicId: String,
    val title: String,
    val subtopics: List<String>
)

data class ScienceModule(
    val moduleNumber: Int,
    val moduleTitle: String,
    val topics: List<ScienceTopic>
)

object ScienceModulesJson {
    val navigation = listOf(
        ScienceModule(
            moduleNumber = 5,
            moduleTitle = "Motion in One Dimension",
            topics = listOf(
                ScienceTopic(
                    topicId = "M5_T1",
                    title = "Distance and Displacement",
                    subtopics = listOf(
                        "Definition of Motion",
                        "Reference Point",
                        "Difference Between Distance and Displacement"
                    )
                ),
                ScienceTopic(
                    topicId = "M5_T2",
                    title = "Speed and Velocity",
                    subtopics = listOf(
                        "Meaning of Speed",
                        "Meaning of Velocity",
                        "Average vs Instantaneous Speed",
                        "Formulas and Calculations"
                    )
                ),
                ScienceTopic(
                    topicId = "M5_T3",
                    title = "Acceleration",
                    subtopics = listOf(
                        "Definition of Acceleration",
                        "Positive and Negative Acceleration",
                        "Acceleration Formula"
                    )
                )
            )
        ),

        ScienceModule(
            moduleNumber = 6,
            moduleTitle = "Wave Motion",
            topics = listOf(
                ScienceTopic(
                    topicId = "M6_T1",
                    title = "Nature of Waves",
                    subtopics = listOf(
                        "Wave Pulse and Wave Train",
                        "Energy Transfer in Waves",
                        "Transverse and Longitudinal Waves"
                    )
                ),
                ScienceTopic(
                    topicId = "M6_T2",
                    title = "Wave Characteristics",
                    subtopics = listOf(
                        "Crest and Trough",
                        "Amplitude",
                        "Wavelength",
                        "Frequency and Period",
                        "Wave Speed Equation"
                    )
                ),
                ScienceTopic(
                    topicId = "M6_T3",
                    title = "Nature of Sound",
                    subtopics = listOf(
                        "Sound as a Longitudinal Wave",
                        "Medium of Sound Travel",
                        "Pitch, Loudness, and Quality",
                        "Infrasonic and Ultrasonic Waves"
                    )
                ),
                ScienceTopic(
                    topicId = "M6_T4",
                    title = "Light",
                    subtopics = listOf(
                        "Light as a Wave and Particle",
                        "Sources of Light",
                        "Transparent, Translucent, Opaque Materials",
                        "Color Spectrum and Dispersion"
                    )
                ),
                ScienceTopic(
                    topicId = "M6_T5",
                    title = "Heat and Temperature",
                    subtopics = listOf(
                        "Thermal Energy and Temperature",
                        "Celsius, Fahrenheit, and Kelvin Scales",
                        "Conduction",
                        "Convection",
                        "Radiation"
                    )
                )
            )
        )
    )
}
